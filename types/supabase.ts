export type Json = string | number | boolean | null | { [key: string]: Json | undefined } | Json[]

export interface Database {
  public: {
    Tables: {
      profiles: {
        Row: {
          id: string
          email: string
          role: string
          nom: string
          created_at: string
        }
        Insert: {
          id: string
          email: string
          role: string
          nom?: string
          created_at?: string
        }
        Update: {
          id?: string
          email?: string
          role?: string
          nom?: string
          created_at?: string
        }
      }
      clients: {
        Row: {
          id: string
          user_id: string | null
          nom_entreprise: string
          contact: string | null
          created_at: string
        }
        Insert: {
          id?: string
          user_id?: string | null
          nom_entreprise: string
          contact?: string | null
          created_at?: string
        }
        Update: {
          id?: string
          user_id?: string | null
          nom_entreprise?: string
          contact?: string | null
          created_at?: string
        }
      }
      navires: {
        Row: {
          id: string
          nom: string
          imo: string
          longueur: number | null
          largeur: number | null
          tirant_eau: number | null
          created_at: string
        }
        Insert: {
          id?: string
          nom: string
          imo: string
          longueur?: number | null
          largeur?: number | null
          tirant_eau?: number | null
          created_at?: string
        }
        Update: {
          id?: string
          nom?: string
          imo?: string
          longueur?: number | null
          largeur?: number | null
          tirant_eau?: number | null
          created_at?: string
        }
      }
      // Autres tables...
    }
  }
}
