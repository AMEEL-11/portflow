"use client"

import type React from "react"

import { useState } from "react"
import { useRouter } from "next/navigation"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { AlertCircle } from "lucide-react"
import { Alert, AlertDescription } from "@/components/ui/alert"
import { useAuth } from "@/components/auth-provider"
import { createSupabaseClient } from "@/lib/supabase"

export default function LoginPage() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [error, setError] = useState("")
  const router = useRouter()
  const { signIn, loading } = useAuth()
  const supabase = createSupabaseClient()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError("")

    try {
      const { error, data } = await signIn(email, password)

      if (error) {
        setError(error.message)
        return
      }

      if (data?.user) {
        // Récupérer le profil pour déterminer le rôle
        const { data: profileData } = await supabase.from("profiles").select("role").eq("id", data.user.id).single()

        if (profileData) {
          // Rediriger vers le tableau de bord approprié selon le rôle
          switch (profileData.role) {
            case "admin":
              router.push("/admin/dashboard")
              break
            case "berth_planner":
              router.push("/berth-planner/dashboard")
              break
            case "yard_planner":
              router.push("/yard-planner/dashboard")
              break
            case "operations_manager":
              router.push("/operations-manager/dashboard")
              break
            case "documentation":
              router.push("/documentation/dashboard")
              break
            case "client":
              router.push("/client/dashboard")
              break
            default:
              router.push("/")
          }
        } else {
          router.push("/")
        }
      }
    } catch (err) {
      setError("Erreur de connexion")
      console.error(err)
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center p-4 md:p-6">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle className="text-2xl">Connexion</CardTitle>
          <CardDescription>Entrez vos identifiants pour accéder au système PortFlow</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-4">
            {error && (
              <Alert variant="destructive">
                <AlertCircle className="h-4 w-4" />
                <AlertDescription>{error}</AlertDescription>
              </Alert>
            )}
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="votre.email@exemple.com"
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">Mot de passe</Label>
              <Input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            <Button type="submit" className="w-full" disabled={loading}>
              {loading ? "Connexion en cours..." : "Se connecter"}
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
