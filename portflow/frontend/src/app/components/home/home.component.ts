import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  template: `
    <div class="home-container">
      <div class="hero-section">
        <img src="assets/images/port-image.jpg" alt="Port de Casablanca" class="port-image">
        <div class="hero-overlay">
          <div class="hero-content">
            <div class="logo-section">
              <img src="assets/images/portflow-logo.png" alt="PortFlow Logo" class="logo">
              <h1 class="app-title">PortFlow</h1>
            </div>
            <p class="subtitle">Système de gestion intelligente des flux portuaires</p>
            <button class="btn btn-primary btn-lg welcome-btn" (click)="navigateToLogin()">
              Bienvenue - Se connecter
            </button>
          </div>
        </div>
      </div>
      
      <div class="features-section">
        <div class="container">
          <h2>Fonctionnalités Principales</h2>
          <div class="row">
            <div class="col-md-4">
              <div class="feature-card">
                <i class="fas fa-map-marker-alt"></i>
                <h3>Suivi en Temps Réel</h3>
                <p>Localisation et traçabilité complète des conteneurs via RFID, GPS et AIS</p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="feature-card">
                <i class="fas fa-warehouse"></i>
                <h3>Gestion Intelligente</h3>
                <p>Optimisation automatique des espaces de stockage et allocation des ressources</p>
              </div>
            </div>
            <div class="col-md-4">
              <div class="feature-card">
                <i class="fas fa-chart-line"></i>
                <h3>Analytics Avancés</h3>
                <p>Tableaux de bord interactifs et prévision des congestions</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  
  navigateToLogin(): void {
    window.location.href = '/login';
  }
}