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

export default function LoginPage() {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [error, setError] = useState("")
  const router = useRouter()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError("")

    // Dans une implémentation réelle, ceci serait connecté à une API d'authentification
    // Pour l'instant, nous simulons une connexion simple
    try {
      // Simulation d'authentification - à remplacer par une vraie API
      if (email && password) {
        // Rediriger vers le tableau de bord approprié selon le rôle
        // Ceci serait normalement déterminé par le backend
        if (email.includes("admin")) {
          router.push("/admin/dashboard")
        } else if (email.includes("berth")) {
          router.push("/berth-planner/dashboard")
        } else if (email.includes("yard")) {
          router.push("/yard-planner/dashboard")
        } else if (email.includes("operations")) {
          router.push("/operations-manager/dashboard")
        } else if (email.includes("doc")) {
          router.push("/documentation/dashboard")
        } else if (email.includes("client")) {
          router.push("/client/dashboard")
        } else {
          setError("Rôle non reconnu")
        }
      } else {
        setError("Veuillez remplir tous les champs")
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
            <Button type="submit" className="w-full">
              Se connecter
            </Button>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
