import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-dashboard',
  template: `
    <div class="admin-dashboard">
      <div class="dashboard-header">
        <h1>Administration PortFlow</h1>
        <button class="btn btn-danger" (click)="logout()">Déconnexion</button>
      </div>

      <div class="row">
        <div class="col-md-8">
          <div class="card">
            <div class="card-header">
              <h3>Gestion des Utilisateurs</h3>
            </div>
            <div class="card-body">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>Email</th>
                    <th>Nom</th>
                    <th>Rôle</th>
                    <th>Statut</th>
                    <th>Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let user of users">
                    <td>{{user.email}}</td>
                    <td>{{user.firstName}} {{user.lastName}}</td>
                    <td>{{getRoleDisplayName(user.role)}}</td>
                    <td>
                      <span class="badge" [class.badge-success]="user.active" [class.badge-secondary]="!user.active">
                        {{user.active ? 'Actif' : 'Inactif'}}
                      </span>
                    </td>
                    <td>
                      <button class="btn btn-sm btn-primary me-2" (click)="editUser(user)">Modifier</button>
                      <button class="btn btn-sm btn-danger" (click)="deleteUser(user.id)">Supprimer</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div class="col-md-4">
          <div class="card">
            <div class="card-header">
              <h3>{{editingUser ? 'Modifier' : 'Ajouter'}} Utilisateur</h3>
            </div>
            <div class="card-body">
              <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
                <div class="form-group mb-3">
                  <label for="email">Email</label>
                  <input type="email" class="form-control" formControlName="email">
                </div>
                
                <div class="form-group mb-3" *ngIf="!editingUser">
                  <label for="password">Mot de passe</label>
                  <input type="password" class="form-control" formControlName="password">
                </div>
                
                <div class="form-group mb-3">
                  <label for="firstName">Prénom</label>
                  <input type="text" class="form-control" formControlName="firstName">
                </div>
                
                <div class="form-group mb-3">
                  <label for="lastName">Nom</label>
                  <input type="text" class="form-control" formControlName="lastName">
                </div>
                
                <div class="form-group mb-3">
                  <label for="role">Rôle</label>
                  <select class="form-control" formControlName="role">
                    <option value="ADMIN">Administrateur</option>
                    <option value="BERTH_PLANNER">Berth Planner</option>
                    <option value="YARD_PLANNER">Yard Planner</option>
                    <option value="OPERATIONS_MANAGER">Operations Manager</option>
                    <option value="CLIENT">Client</option>
                    <option value="DOCUMENTATION_SERVICE">Service Documentation</option>
                  </select>
                </div>
                
                <div class="btn-group w-100">
                  <button type="submit" class="btn btn-primary" [disabled]="userForm.invalid">
                    {{editingUser ? 'Modifier' : 'Ajouter'}}
                  </button>
                  <button type="button" class="btn btn-secondary" (click)="resetForm()" *ngIf="editingUser">
                    Annuler
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  users: User[] = [];
  userForm!: FormGroup;
  editingUser: User | null = null;
  private apiUrl = 'http://localhost:8080/api';

  constructor(
    private http: HttpClient,
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadUsers();
  }

  initForm(): void {
    this.userForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', this.editingUser ? [] : [Validators.required]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      role: ['BERTH_PLANNER', Validators.required]
    });
  }

  loadUsers(): void {
    this.http.get<User[]>(`${this.apiUrl}/admin/users`).subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error) => {
        this.toastr.error('Erreur lors du chargement des utilisateurs', 'Erreur');
      }
    });
  }

  onSubmit(): void {
    if (this.userForm.invalid) return;

    const userData = this.userForm.value;

    if (this.editingUser) {
      // Mise à jour
      this.http.put(`${this.apiUrl}/admin/users/${this.editingUser.id}`, userData).subscribe({
        next: () => {
          this.toastr.success('Utilisateur modifié avec succès', 'Succès');
          this.loadUsers();
          this.resetForm();
        },
        error: () => {
          this.toastr.error('Erreur lors de la modification', 'Erreur');
        }
      });
    } else {
      // Création
      this.http.post(`${this.apiUrl}/admin/users`, userData).subscribe({
        next: () => {
          this.toastr.success('Utilisateur créé avec succès', 'Succès');
          this.loadUsers();
          this.resetForm();
        },
        error: () => {
          this.toastr.error('Erreur lors de la création', 'Erreur');
        }
      });
    }
  }

  editUser(user: User): void {
    this.editingUser = user;
    this.userForm.patchValue({
      email: user.email,
      firstName: user.firstName,
      lastName: user.lastName,
      role: user.role
    });
    
    // Remove password requirement for edit
    this.userForm.get('password')?.clearValidators();
    this.userForm.get('password')?.updateValueAndValidity();
  }

  deleteUser(userId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur?')) {
      this.http.delete(`${this.apiUrl}/admin/users/${userId}`).subscribe({
        next: () => {
          this.toastr.success('Utilisateur supprimé avec succès', 'Succès');
          this.loadUsers();
        },
        error: () => {
          this.toastr.error('Erreur lors de la suppression', 'Erreur');
        }
      });
    }
  }

  resetForm(): void {
    this.editingUser = null;
    this.userForm.reset();
    this.userForm.patchValue({ role: 'BERTH_PLANNER' });
    
    // Restore password requirement for new users
    this.userForm.get('password')?.setValidators([Validators.required]);
    this.userForm.get('password')?.updateValueAndValidity();
  }

  getRoleDisplayName(role: string): string {
    const roleNames: { [key: string]: string } = {
      'ADMIN': 'Administrateur',
      'BERTH_PLANNER': 'Berth Planner',
      'YARD_PLANNER': 'Yard Planner',
      'OPERATIONS_MANAGER': 'Operations Manager',
      'CLIENT': 'Client',
      'DOCUMENTATION_SERVICE': 'Service Documentation'
    };
    return roleNames[role] || role;
  }

  logout(): void {
    this.authService.logout();
  }
}