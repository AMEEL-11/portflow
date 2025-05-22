"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { UserPlus, Trash2 } from "lucide-react"
import DashboardLayout from "@/components/dashboard-layout"

// Données simulées pour les utilisateurs
const initialUsers = [
  { id: 1, name: "Jean Dupont", email: "berth@portflow.com", role: "Berth Planner" },
  { id: 2, name: "Marie Martin", email: "yard@portflow.com", role: "Yard Planner" },
  { id: 3, name: "Pierre Durand", email: "operations@portflow.com", role: "Operations Manager" },
  { id: 4, name: "Sophie Lefebvre", email: "doc@portflow.com", role: "Documentation Service" },
  { id: 5, name: "Entreprise ABC", email: "client@portflow.com", role: "Client" },
]

export default function AdminDashboard() {
  const [users, setUsers] = useState(initialUsers)
  const [newUser, setNewUser] = useState({ name: "", email: "", password: "", role: "" })

  const handleAddUser = (e: React.FormEvent) => {
    e.preventDefault()
    if (newUser.name && newUser.email && newUser.password && newUser.role) {
      setUsers([...users, { id: users.length + 1, name: newUser.name, email: newUser.email, role: newUser.role }])
      setNewUser({ name: "", email: "", password: "", role: "" })
    }
  }

  const handleDeleteUser = (id: number) => {
    setUsers(users.filter((user) => user.id !== id))
  }

  return (
    <DashboardLayout title="Administration" userRole="Administrateur">
      <div className="grid gap-6">
        <Card>
          <CardHeader>
            <CardTitle>Ajouter un utilisateur</CardTitle>
            <CardDescription>Créez de nouveaux comptes utilisateurs pour le système</CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleAddUser} className="space-y-4">
              <div className="grid grid-cols-1 gap-4 sm:grid-cols-2">
                <div className="space-y-2">
                  <Label htmlFor="name">Nom complet</Label>
                  <Input
                    id="name"
                    value={newUser.name}
                    onChange={(e) => setNewUser({ ...newUser, name: e.target.value })}
                    placeholder="Nom de l'utilisateur"
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="email">Email</Label>
                  <Input
                    id="email"
                    type="email"
                    value={newUser.email}
                    onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
                    placeholder="email@exemple.com"
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="password">Mot de passe</Label>
                  <Input
                    id="password"
                    type="password"
                    value={newUser.password}
                    onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="role">Rôle</Label>
                  <Select value={newUser.role} onValueChange={(value) => setNewUser({ ...newUser, role: value })}>
                    <SelectTrigger>
                      <SelectValue placeholder="Sélectionner un rôle" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="Administrateur">Administrateur</SelectItem>
                      <SelectItem value="Berth Planner">Berth Planner</SelectItem>
                      <SelectItem value="Yard Planner">Yard Planner</SelectItem>
                      <SelectItem value="Operations Manager">Operations Manager</SelectItem>
                      <SelectItem value="Documentation Service">Documentation Service</SelectItem>
                      <SelectItem value="Client">Client</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>
              <Button type="submit" className="mt-4">
                <UserPlus className="mr-2 h-4 w-4" />
                Ajouter l'utilisateur
              </Button>
            </form>
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle>Gestion des utilisateurs</CardTitle>
            <CardDescription>Liste de tous les utilisateurs du système</CardDescription>
          </CardHeader>
          <CardContent>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Nom</TableHead>
                  <TableHead>Email</TableHead>
                  <TableHead>Rôle</TableHead>
                  <TableHead className="w-[100px]">Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {users.map((user) => (
                  <TableRow key={user.id}>
                    <TableCell>{user.name}</TableCell>
                    <TableCell>{user.email}</TableCell>
                    <TableCell>{user.role}</TableCell>
                    <TableCell>
                      <Button variant="ghost" size="icon" onClick={() => handleDeleteUser(user.id)}>
                        <Trash2 className="h-4 w-4" />
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </CardContent>
        </Card>
      </div>
    </DashboardLayout>
  )
}
