import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastrService } from 'ngx-toastr';
import { UserRole } from '../../models/user-role';

@Component({
  selector: 'app-login',
  template: `
    <div class="login-container">
      <div class="login-card">
        <div class="card-header">
          <img src="assets/images/portflow-logo.png" alt="PortFlow" class="login-logo">
          <h2>Connexion PortFlow</h2>
        </div>
        
        <form [formGroup]="loginForm" (ngSubmit)="onSubmit()" class="login-form">
          <div class="form-group">
            <label for="email">Email:</label>
            <input 
              type="email" 
              id="email"
              class="form-control" 
              formControlName="email"
              [class.is-invalid]="loginForm.get('email')?.invalid && loginForm.get('email')?.touched">
            <div class="invalid-feedback" *ngIf="loginForm.get('email')?.invalid && loginForm.get('email')?.touched">
              Email requis et valide
            </div>
          </div>
          
          <div class="form-group">
            <label for="password">Mot de passe:</label>
            <input 
              type="password" 
              id="password"
              class="form-control" 
              formControlName="password"
              [class.is-invalid]="loginForm.get('password')?.invalid && loginForm.get('password')?.touched">
            <div class="invalid-feedback" *ngIf="loginForm.get('password')?.invalid && loginForm.get('password')?.touched">
              Mot de passe requis
            </div>
          </div>
          
          <button type="submit" class="btn btn-primary btn-block" [disabled]="loginForm.invalid || loading">
            <span *ngIf="loading" class="spinner-border spinner-border-sm mr-2"></span>
            Se connecter
          </button>
        </form>
        
        <div class="demo-accounts mt-4">
          <h5>Comptes de démonstration:</h5>
          <div class="demo-user" *ngFor="let demo of demoAccounts" (click)="loginWithDemo(demo)">
            <strong>{{demo.role}}</strong><br>
            <small>{{demo.email}} / {{demo.password}}</small>
          </div>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loading = false;

  demoAccounts = [
    { role: 'Administrateur', email: 'admin@portflow.ma', password: 'admin123' },
    { role: 'Berth Planner', email: 'berth@portflow.ma', password: 'berth123' },
    { role: 'Yard Planner', email: 'yard@portflow.ma', password: 'yard123' },
    { role: 'Operations Manager', email: 'ops@portflow.ma', password: 'ops123' },
    { role: 'Client', email: 'client@portflow.ma', password: 'client123' },
    { role: 'Documentation', email: 'doc@portflow.ma', password: 'doc123' }
  ];

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    // Si déjà connecté, rediriger
    if (this.authService.isLoggedIn()) {
      this.redirectToRoleDashboard();
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    const { email, password } = this.loginForm.value;

    this.authService.login(email, password).subscribe({
      next: (response) => {
        this.loading = false;
        this.toastr.success('Connexion réussie!', 'Bienvenue');
        this.redirectToRoleDashboard();
      },
      error: (error) => {
        this.loading = false;
        this.toastr.error('Email ou mot de passe incorrect', 'Erreur de connexion');
      }
    });
  }

  loginWithDemo(demo: any): void {
    this.loginForm.patchValue({
      email: demo.email,
      password: demo.password
    });
  }

  private redirectToRoleDashboard(): void {
    const user = this.authService.currentUserValue;
    if (!user) return;

    switch (user.role) {
      case UserRole.ADMIN:
        this.router.navigate(['/admin']);
        break;
      case UserRole.BERTH_PLANNER:
        this.router.navigate(['/berth-planner']);
        break;
      case UserRole.YARD_PLANNER:
        this.router.navigate(['/yard-planner']);
        break;
      case UserRole.OPERATIONS_MANAGER:
        this.router.navigate(['/operations-manager']);
        break;
      case UserRole.CLIENT:
        this.router.navigate(['/client']);
        break;
      case UserRole.DOCUMENTATION_SERVICE:
        this.router.navigate(['/documentation']);
        break;
      default:
        this.router.navigate(['/']);
    }
  }
}