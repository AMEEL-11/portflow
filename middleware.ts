import { createMiddlewareClient } from "@supabase/auth-helpers-nextjs"
import { NextResponse } from "next/server"
import type { NextRequest } from "next/server"

export async function middleware(req: NextRequest) {
  try {
    const res = NextResponse.next()
    const supabase = createMiddlewareClient({ req, res })

    const {
      data: { session },
    } = await supabase.auth.getSession()

    // Si l'utilisateur n'est pas connecté et essaie d'accéder à une page protégée
    if (!session && !req.nextUrl.pathname.startsWith("/login") && req.nextUrl.pathname !== "/") {
      const redirectUrl = req.nextUrl.clone()
      redirectUrl.pathname = "/login"
      return NextResponse.redirect(redirectUrl)
    }

    // Si l'utilisateur est connecté et essaie d'accéder à la page de login
    if (session && req.nextUrl.pathname.startsWith("/login")) {
      const redirectUrl = req.nextUrl.clone()
      redirectUrl.pathname = "/"
      return NextResponse.redirect(redirectUrl)
    }

    return res
  } catch (error) {
    console.error("Erreur dans le middleware:", error)
    // En cas d'erreur, permettre l'accès à la page d'accueil et à la page de connexion
    if (req.nextUrl.pathname === "/" || req.nextUrl.pathname.startsWith("/login")) {
      return NextResponse.next()
    }
    // Rediriger vers la page d'accueil pour les autres pages
    const redirectUrl = req.nextUrl.clone()
    redirectUrl.pathname = "/"
    return NextResponse.redirect(redirectUrl)
  }
}

export const config = {
  matcher: ["/((?!_next/static|_next/image|favicon.ico|.*\\.png$).*)"],
}
