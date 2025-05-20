import Image from "next/image"
import Link from "next/link"
import { Button } from "@/components/ui/button"

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col">
      <header className="border-b">
        <div className="container flex h-16 items-center justify-between px-4 md:px-6">
          <div className="flex items-center gap-2">
            <Image src="/logo.svg" alt="PortFlow Logo" width={40} height={40} />
            <span className="text-xl font-bold">PortFlow</span>
          </div>
          <Link href="/login">
            <Button variant="default">Bienvenue</Button>
          </Link>
        </div>
      </header>
      <div className="flex-1 flex flex-col items-center justify-center p-4 md:p-6">
        <div className="relative w-full max-w-5xl aspect-[16/9] overflow-hidden rounded-lg">
          <Image src="/port-image.jpg" alt="Image du port" fill className="object-cover" priority />
        </div>
        <div className="mt-8 text-center max-w-3xl">
          <h1 className="text-3xl font-bold tracking-tight sm:text-4xl md:text-5xl">Système de Gestion Portuaire</h1>
          <p className="mt-4 text-muted-foreground">
            Plateforme complète pour la gestion des escales, le suivi des navires, la planification des zones de
            stockage et la gestion des conteneurs.
          </p>
          <div className="mt-8">
            <Link href="/login">
              <Button size="lg">Commencer</Button>
            </Link>
          </div>
        </div>
      </div>
    </main>
  )
}
