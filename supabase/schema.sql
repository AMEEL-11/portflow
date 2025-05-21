-- Création des tables pour le système PortFlow

-- Activation de l'extension UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table des profils utilisateurs (extension de la table auth.users de Supabase)
CREATE TABLE profiles (
  id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
  email TEXT NOT NULL,
  role TEXT NOT NULL CHECK (role IN ('admin', 'berth_planner', 'yard_planner', 'operations_manager', 'documentation', 'client')),
  nom TEXT,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des clients
CREATE TABLE clients (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  user_id UUID REFERENCES profiles(id) ON DELETE SET NULL,
  nom_entreprise TEXT NOT NULL,
  contact TEXT,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des navires
CREATE TABLE navires (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nom TEXT NOT NULL,
  imo TEXT UNIQUE NOT NULL,
  longueur FLOAT,
  largeur FLOAT,
  tirant_eau FLOAT,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des escales
CREATE TABLE escales (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  navire_id UUID REFERENCES navires(id) ON DELETE CASCADE NOT NULL,
  eta TIMESTAMP WITH TIME ZONE NOT NULL,
  etd TIMESTAMP WITH TIME ZONE NOT NULL,
  statut TEXT NOT NULL CHECK (statut IN ('planifiée', 'en_cours', 'terminée', 'annulée')),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des conteneurs
CREATE TABLE conteneurs (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  numero TEXT UNIQUE NOT NULL,
  client_id UUID REFERENCES clients(id) ON DELETE SET NULL,
  type TEXT NOT NULL,
  poids FLOAT,
  statut TEXT NOT NULL,
  escale_id UUID REFERENCES escales(id) ON DELETE SET NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des zones de stockage
CREATE TABLE zones_stockage (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nom TEXT NOT NULL,
  capacite INTEGER NOT NULL,
  taux_occupation FLOAT DEFAULT 0,
  type TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des emplacements
CREATE TABLE emplacements (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  zone_id UUID REFERENCES zones_stockage(id) ON DELETE CASCADE NOT NULL,
  position TEXT NOT NULL,
  statut TEXT NOT NULL CHECK (statut IN ('disponible', 'réservé', 'occupé')),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des mouvements de conteneurs
CREATE TABLE mouvements_conteneurs (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  conteneur_id UUID REFERENCES conteneurs(id) ON DELETE CASCADE NOT NULL,
  emplacement_id UUID REFERENCES emplacements(id) ON DELETE SET NULL,
  date_debut TIMESTAMP WITH TIME ZONE NOT NULL,
  date_fin TIMESTAMP WITH TIME ZONE,
  type_operation TEXT NOT NULL CHECK (type_operation IN ('entrée', 'sortie', 'déplacement')),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des alertes
CREATE TABLE alertes (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  type TEXT NOT NULL,
  message TEXT NOT NULL,
  statut TEXT NOT NULL CHECK (statut IN ('nouvelle', 'en_cours', 'résolue')),
  navire_id UUID REFERENCES navires(id) ON DELETE SET NULL,
  conteneur_id UUID REFERENCES conteneurs(id) ON DELETE SET NULL,
  zone_id UUID REFERENCES zones_stockage(id) ON DELETE SET NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Table des KPIs
CREATE TABLE kpis (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nom TEXT NOT NULL,
  valeur FLOAT NOT NULL,
  date_mesure TIMESTAMP WITH TIME ZONE NOT NULL,
  unite TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT TIMEZONE('utc', NOW())
);

-- Création des politiques de sécurité (Row Level Security)
ALTER TABLE profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE clients ENABLE ROW LEVEL SECURITY;
ALTER TABLE navires ENABLE ROW LEVEL SECURITY;
ALTER TABLE escales ENABLE ROW LEVEL SECURITY;
ALTER TABLE conteneurs ENABLE ROW LEVEL SECURITY;
ALTER TABLE zones_stockage ENABLE ROW LEVEL SECURITY;
ALTER TABLE emplacements ENABLE ROW LEVEL SECURITY;
ALTER TABLE mouvements_conteneurs ENABLE ROW LEVEL SECURITY;
ALTER TABLE alertes ENABLE ROW LEVEL SECURITY;
ALTER TABLE kpis ENABLE ROW LEVEL SECURITY;

-- Politique pour les administrateurs (accès complet)
CREATE POLICY "Les administrateurs ont un accès complet" ON profiles
  FOR ALL USING (auth.uid() IN (SELECT id FROM profiles WHERE role = 'admin'));

-- Politique pour les utilisateurs (accès à leur propre profil)
CREATE POLICY "Les utilisateurs peuvent voir leur propre profil" ON profiles
  FOR SELECT USING (auth.uid() = id);

-- Trigger pour créer automatiquement un profil lors de la création d'un utilisateur
CREATE OR REPLACE FUNCTION public.handle_new_user()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO public.profiles (id, email, role, nom)
  VALUES (new.id, new.email, 'client', new.raw_user_meta_data->>'nom');
  RETURN new;
END;
$$ LANGUAGE plpgsql SECURITY DEFINER;

CREATE TRIGGER on_auth_user_created
  AFTER INSERT ON auth.users
  FOR EACH ROW EXECUTE PROCEDURE public.handle_new_user();

-- Ajouter d'autres politiques selon les besoins de votre application
