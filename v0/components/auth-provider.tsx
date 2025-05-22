"use client"

import type React from "react"

import { createContext, useContext, useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { createSupabaseClient } from "@/lib/supabase"
import type { User } from "@supabase/supabase-js"
import type { Profile } from "@/lib/supabase"
import { toast } from "@/components/ui/use-toast"

type AuthContextType = {
  user: User | null
  profile: Profile | null
  signIn: (
    email: string,
    password: string,
  ) => Promise<{
    error: Error | null
    data: { user: User | null } | null
  }>
  signOut: () => Promise<void>
  loading: boolean
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState<User | null>(null)
  const [profile, setProfile] = useState<Profile | null>(null)
  const [loading, setLoading] = useState(true)
  const router = useRouter()

  // Créer le client Supabase à la demande
  const supabase = createSupabaseClient()

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const {
          data: { user },
        } = await supabase.auth.getUser()
        setUser(user)

        if (user) {
          const { data } = await supabase.from("profiles").select("*").eq("id", user.id).single()
          setProfile(data)
        }
      } catch (error) {
        console.error("Erreur lors de la récupération de l'utilisateur:", error)
        toast({
          title: "Erreur de configuration",
          description: "Impossible de se connecter à Supabase. Vérifiez vos variables d'environnement.",
          variant: "destructive",
        })
      } finally {
        setLoading(false)
      }
    }

    fetchUser()

    try {
      const {
        data: { subscription },
      } = supabase.auth.onAuthStateChange(async (event, session) => {
        setUser(session?.user ?? null)

        if (session?.user) {
          const { data } = await supabase.from("profiles").select("*").eq("id", session.user.id).single()
          setProfile(data)
        } else {
          setProfile(null)
        }

        setLoading(false)
      })

      return () => {
        subscription.unsubscribe()
      }
    } catch (error) {
      console.error("Erreur lors de l'abonnement aux changements d'authentification:", error)
      setLoading(false)
    }
  }, [])

  const signIn = async (email: string, password: string) => {
    setLoading(true)
    try {
      const response = await supabase.auth.signInWithPassword({
        email,
        password,
      })
      return response
    } catch (error) {
      console.error("Erreur de connexion:", error)
      return {
        error: new Error("Impossible de se connecter à Supabase. Vérifiez vos variables d'environnement."),
        data: null,
      }
    } finally {
      setLoading(false)
    }
  }

  const signOut = async () => {
    try {
      await supabase.auth.signOut()
      router.push("/login")
    } catch (error) {
      console.error("Erreur lors de la déconnexion:", error)
    }
  }

  const value = {
    user,
    profile,
    signIn,
    signOut,
    loading,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider")
  }
  return context
}
