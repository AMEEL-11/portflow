"use client"

import { useEffect, useState } from "react"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { InfoIcon } from "lucide-react"

export function EnvChecker() {
  const [missingEnvVars, setMissingEnvVars] = useState<boolean>(false)

  useEffect(() => {
    // Vérifier les variables d'environnement côté client
    const supabaseUrl = process.env.NEXT_PUBLIC_SUPABASE_URL
    const supabaseAnonKey = process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY

    if (!supabaseUrl || !supabaseAnonKey) {
      setMissingEnvVars(true)
      console.error("Variables d'environnement Supabase manquantes:", {
        NEXT_PUBLIC_SUPABASE_URL: supabaseUrl ? "Défini" : "Non défini",
        NEXT_PUBLIC_SUPABASE_ANON_KEY: supabaseAnonKey ? "Défini" : "Non défini",
      })
    }
  }, [])

  if (!missingEnvVars) return null

  return (
    <Alert variant="destructive" className="mb-6 max-w-3xl">
      <InfoIcon className="h-4 w-4" />
      <AlertTitle>Configuration requise</AlertTitle>
      <AlertDescription>
        <p>Les variables d'environnement Supabase ne sont pas configurées. Veuillez suivre ces étapes :</p>
        <ol className="list-decimal pl-5 mt-2 space-y-1">
          <li>
            Créez un fichier <code className="bg-muted px-1 rounded">.env.local</code> à la racine du projet
          </li>
          <li>
            Ajoutez les variables suivantes :
            <pre className="bg-muted p-2 rounded mt-1 text-xs">
              NEXT_PUBLIC_SUPABASE_URL=votre_url_supabase
              <br />
              NEXT_PUBLIC_SUPABASE_ANON_KEY=votre_clé_anon
            </pre>
          </li>
          <li>
            Redémarrez le serveur de développement avec <code className="bg-muted px-1 rounded">npm run dev</code>
          </li>
        </ol>
      </AlertDescription>
    </Alert>
  )
}
