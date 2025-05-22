import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  demoAccounts = [
    { role: 'Administrator', email: 'admin@portflow.com', password: 'admin123' },
    { role: 'Berth Planner', email: 'berth@portflow.com', password: 'berth123' },
    { role: 'Yard Planner', email: 'yard@portflow.com', password: 'yard123' },
    { role: 'Operations Manager', email: 'operations@portflow.com', password: 'ops123' },
    { role: 'Client', email: 'client@portflow.com', password: 'client123' },
    { role: 'Documentation Service', email: 'docs@portflow.com', password: 'docs123' }
  ];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {}

  fillDemoAccount(account: any) {
    this.loginForm.patchValue({
      email: account.email,
      password: account.password
    });
  }

  onLogin() {
    if (this.loginForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      
      this.authService.login(this.loginForm.value).subscribe({
        next: (response) => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('userRole', response.userRole);
          this.redirectBasedOnRole(response.userRole);
        },
        error: (error) => {
          this.errorMessage = 'Email ou mot de passe incorrect';
          this.isLoading = false;
        }
      });
    }
  }

  private redirectBasedOnRole(role: string) {
    switch (role) {
      case 'ADMINISTRATOR':
        this.router.navigate(['/admin/dashboard']);
        break;
      case 'BERTH_PLANNER':
        this.router.navigate(['/berth-planner/dashboard']);
        break;
      case 'YARD_PLANNER':
        this.router.navigate(['/yard-planner/dashboard']);
        break;
      case 'OPERATIONS_MANAGER':
        this.router.navigate(['/operations/dashboard']);
        break;
      case 'CLIENT':
        this.router.navigate(['/client/dashboard']);
        break;
      case 'DOCUMENTATION_SERVICE':
        this.router.navigate(['/documentation/dashboard']);
        break;
      default:
        this.router.navigate(['/']);
    }
  }
}