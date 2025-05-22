import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// Components
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/auth/login.component';
import { AdminDashboardComponent } from './components/admin/admin-dashboard.component';
import { BerthPlannerComponent } from './components/berth-planner/berth-planner.component';
import { YardPlannerComponent } from './components/yard-planner/yard-planner.component';
import { OperationsManagerComponent } from './components/operations-manager/operations-manager.component';
import { ClientDashboardComponent } from './components/client/client-dashboard.component';
import { DocumentationComponent } from './components/documentation/documentation.component';

// Services
import { AuthService } from './services/auth.service';
import { ContainerService } from './services/container.service';
import { ShipService } from './services/ship.service';
import { AlertService } from './services/alert.service';

// Guards
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

// Third-party modules
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { NgChartsModule } from 'ng2-charts';
import { FullCalendarModule } from '@fullcalendar/angular';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    AdminDashboardComponent,
    BerthPlannerComponent,
    YardPlannerComponent,
    OperationsManagerComponent,
    ClientDashboardComponent,
    DocumentationComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    LeafletModule,
    NgChartsModule,
    FullCalendarModule,
    ToastrModule.forRoot(),
    RouterModule.forRoot([
      { path: '', component: HomeComponent },
      { path: 'login', component: LoginComponent },
      { 
        path: 'admin', 
        component: AdminDashboardComponent, 
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: 'ADMIN' }
      },
      { 
        path: 'berth-planner', 
        component: BerthPlannerComponent, 
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: 'BERTH_PLANNER' }
      },
      { 
        path: 'yard-planner', 
        component: YardPlannerComponent, 
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: 'YARD_PLANNER' }
      },
      { 
        path: 'operations-manager', 
        component: OperationsManagerComponent, 
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: 'OPERATIONS_MANAGER' }
      },
      { 
        path: 'client', 
        component: ClientDashboardComponent, 
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: 'CLIENT' }
      },
      { 
        path: 'documentation', 
        component: DocumentationComponent, 
        canActivate: [AuthGuard, RoleGuard],
        data: { expectedRole: 'DOCUMENTATION_SERVICE' }
      }
    ])
  ],
  providers: [
    AuthService,
    ContainerService,
    ShipService,
    AlertService,
    AuthGuard,
    RoleGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }