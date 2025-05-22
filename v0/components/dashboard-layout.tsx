"use client"

import type { ReactNode } from "react"
import Link from "next/link"
import Image from "next/image"
import { LayoutDashboard, Ship, Package, Map, Bell, BarChart, Users, LogOut, Menu, X } from "lucide-react"
import { Button } from "@/components/ui/button"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import { usePathname } from "next/navigation"
import { cn } from "@/lib/utils"

interface DashboardLayoutProps {
  children: ReactNode
  title: string
  userRole: string
}

export default function DashboardLayout({ children, title, userRole }: DashboardLayoutProps) {
  const pathname = usePathname()

  const navigation = [
    { name: "Tableau de bord", href: "#", icon: LayoutDashboard },
    { name: "Navires", href: "#", icon: Ship },
    { name: "Conteneurs", href: "#", icon: Package },
    { name: "Zones de stockage", href: "#", icon: Map },
    { name: "Alertes", href: "#", icon: Bell },
    { name: "Rapports", href: "#", icon: BarChart },
    { name: "Utilisateurs", href: "#", icon: Users },
  ]

  return (
    <div className="flex min-h-screen flex-col">
      <header className="sticky top-0 z-30 flex h-16 items-center gap-4 border-b bg-background px-4 md:px-6">
        <Sheet>
          <SheetTrigger asChild>
            <Button variant="outline" size="icon" className="md:hidden">
              <Menu className="h-5 w-5" />
              <span className="sr-only">Toggle navigation menu</span>
            </Button>
          </SheetTrigger>
          <SheetContent side="left" className="w-72">
            <div className="flex h-16 items-center border-b px-4">
              <Link href="/" className="flex items-center gap-2 font-semibold">
                <Image src="/logo.svg" alt="PortFlow Logo" width={24} height={24} />
                <span>PortFlow</span>
              </Link>
              <X className="ml-auto h-5 w-5" />
            </div>
            <nav className="grid gap-2 py-4">
              {navigation.map((item) => (
                <Link
                  key={item.name}
                  href={item.href}
                  className={cn(
                    "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium hover:bg-accent hover:text-accent-foreground",
                    pathname === item.href ? "bg-accent text-accent-foreground" : "transparent",
                  )}
                >
                  <item.icon className="h-5 w-5" />
                  {item.name}
                </Link>
              ))}
            </nav>
          </SheetContent>
        </Sheet>
        <div className="flex items-center gap-2">
          <Link href="/" className="flex items-center gap-2 font-semibold">
            <Image src="/logo.svg" alt="PortFlow Logo" width={24} height={24} />
            <span className="hidden md:inline-block">PortFlow</span>
          </Link>
        </div>
        <div className="ml-auto flex items-center gap-4">
          <div className="hidden md:flex">
            <span className="text-sm text-muted-foreground">Connecté en tant que</span>
            <span className="ml-2 text-sm font-medium">{userRole}</span>
          </div>
          <Button variant="outline" size="sm">
            <LogOut className="mr-2 h-4 w-4" />
            Déconnexion
          </Button>
        </div>
      </header>
      <div className="flex flex-1">
        <aside className="hidden w-64 border-r md:block">
          <div className="flex h-full flex-col gap-2 p-4">
            <nav className="grid gap-1 py-2">
              {navigation.map((item) => (
                <Link
                  key={item.name}
                  href={item.href}
                  className={cn(
                    "flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium hover:bg-accent hover:text-accent-foreground",
                    pathname === item.href ? "bg-accent text-accent-foreground" : "transparent",
                  )}
                >
                  <item.icon className="h-5 w-5" />
                  {item.name}
                </Link>
              ))}
            </nav>
          </div>
        </aside>
        <main className="flex-1 p-4 md:p-6">
          <div className="mb-6">
            <h1 className="text-2xl font-bold tracking-tight">{title}</h1>
          </div>
          {children}
        </main>
      </div>
    </div>
  )
}
