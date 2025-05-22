export interface User {
    id: number;
    email: string;
    firstName: string;
    lastName: string;
    role: UserRole;
    active: boolean;
    createdAt: string;
  }
  
  export enum UserRole {
    ADMIN = 'ADMIN',
    BERTH_PLANNER = 'BERTH_PLANNER',
    YARD_PLANNER = 'YARD_PLANNER',
    OPERATIONS_MANAGER = 'OPERATIONS_MANAGER',
    CLIENT = 'CLIENT',
    DOCUMENTATION_SERVICE = 'DOCUMENTATION_SERVICE'
  }
  
  export interface Container {
    id: number;
    containerNumber: string;
    type: ContainerType;
    status: ContainerStatus;
    currentLatitude?: number;
    currentLongitude?: number;
    destination: string;
    origin: string;
    weight: number;
    refrigerated: boolean;
    hazardous: boolean;
    arrivalTime?: string;
    departureTime?: string;
  }
  
  export enum ContainerType {
    STANDARD_20FT = 'STANDARD_20FT',
    STANDARD_40FT = 'STANDARD_40FT',
    HIGH_CUBE_40FT = 'HIGH_CUBE_40FT',
    REFRIGERATED_20FT = 'REFRIGERATED_20FT',
    REFRIGERATED_40FT = 'REFRIGERATED_40FT',
    OPEN_TOP = 'OPEN_TOP',
    FLAT_RACK = 'FLAT_RACK'
  }
  
  export enum ContainerStatus {
    ARRIVING = 'ARRIVING',
    UNLOADING = 'UNLOADING',
    STORED = 'STORED',
    LOADING = 'LOADING',
    DEPARTED = 'DEPARTED',
    IN_TRANSIT = 'IN_TRANSIT'
  }
  
  export interface Ship {
    id: number;
    name: string;
    imoNumber: string;
    callSign: string;
    flag: string;
    capacity: number;
    type: string;
    latitude?: number;
    longitude?: number;
    speed?: number;
    course?: number;
    lastUpdate?: string;
  }
  
  export interface Escale {
    id: number;
    ship: Ship;
    eta: string;
    etd: string;
    ata?: string;
    atd?: string;
    status: EscaleStatus;
    berthAssigned: string;
    expectedContainers: number;
  }
  
  export enum EscaleStatus {
    PLANNED = 'PLANNED',
    IN_APPROACH = 'IN_APPROACH',
    DOCKED = 'DOCKED',
    LOADING_UNLOADING = 'LOADING_UNLOADING',
    DEPARTED = 'DEPARTED'
  }