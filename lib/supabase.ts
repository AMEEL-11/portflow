import { createClient } from "@supabase/supabase-js"

const supabaseUrl = process.env.NEXT_PUBLIC_SUPABASE_URL
const supabaseAnonKey = process.env.NEXT_PUBLIC_SUPABASE_ANON_KEY

if (!supabaseUrl) {
  throw new Error(
    "Please set NEXT_PUBLIC_SUPABASE_URL environment variable in .env.local"
  )
}

if (!supabaseAnonKey) {
  throw new Error(
    "Please set NEXT_PUBLIC_SUPABASE_ANON_KEY environment variable in .env.local"
  )
}

// Debug: Log environment variables
console.log('NEXT_PUBLIC_SUPABASE_URL:', supabaseUrl)
console.log('NEXT_PUBLIC_SUPABASE_ANON_KEY:', supabaseAnonKey)

// Function to create a Supabase client
export function createSupabaseClient() {
  return createClient(supabaseUrl as string, supabaseAnonKey as string)
}

// Also export a default client instance for convenience
export const supabase = createSupabaseClient()
