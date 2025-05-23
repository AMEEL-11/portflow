-- Clear existing data
DELETE FROM conteneurs;
DELETE FROM escales;
DELETE FROM navires;
DELETE FROM emplacements;
DELETE FROM zones_stockage;
DELETE FROM alertes;
DELETE FROM users;

-- Reset sequences
ALTER SEQUENCE IF EXISTS users_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS navires_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS zones_stockage_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS emplacements_id_seq RESTART WITH 1;
ALTER SEQUENCE IF EXISTS conteneurs_id_seq RESTART WITH 1;

-- Insert users (password is "password123" for all)
INSERT INTO users (email, password, role, first_name, last_name, active, created_at) VALUES
('admin@portflow.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdoRIxlMarnUI7CO', 'ADMIN', 'Admin', 'System', true, NOW()),
('berth@portflow.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdoRIxlMarnUI7CO', 'BERTH_PLANNER', 'Berth', 'Planner', true, NOW()),
('yard@portflow.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdoRIxlMarnUI7CO', 'YARD_PLANNER', 'Yard', 'Planner', true, NOW()),
('operations@portflow.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdoRIxlMarnUI7CO', 'OPERATIONS_MANAGER', 'Operations', 'Manager', true, NOW()),
('client@portflow.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdoRIxlMarnUI7CO', 'CLIENT', 'Client', 'User', true, NOW()),
('doc@portflow.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lbdoRIxlMarnUI7CO', 'DOCUMENTATION_SERVICE', 'Documentation', 'Service', true, NOW());

-- Insert storage zones
INSERT INTO zones_stockage (nom, type, capacite_max, coordonnee_x, coordonnee_y, superficie) VALUES
('Zone A - Conteneurs 20FT', 'CONTENEURS_20FT', 500, 100.0, 200.0, 2500.0),
('Zone B - Conteneurs 40FT', 'CONTENEURS_40FT', 300, 300.0, 200.0, 3000.0),
('Zone C - Frigorifique', 'FRIGORIFIQUE', 100, 500.0, 200.0, 1000.0);

-- Insert locations
INSERT INTO emplacements (reference, statut, rangee, position, niveau, zone_stockage_id) VALUES
('A1-01', 'LIBRE', 1, 1, 1, 1),
('A1-02', 'LIBRE', 1, 2, 1, 1),
('A1-03', 'OCCUPE', 1, 3, 1, 1),
('B1-01', 'LIBRE', 1, 1, 1, 2),
('B1-02', 'RESERVE', 1, 2, 1, 2),
('C1-01', 'LIBRE', 1, 1, 1, 3);

-- Insert ships
INSERT INTO navires (nom, imo, mmsi, type, longueur, largeur, capacite_conteneurs, latitude, longitude, vitesse, statut, eta_prevu, depart_prevu) VALUES
('MSC CRISTINA', '9234567', '211234567', 'PORTE-CONTENEURS', 300.0, 48.0, 2000, 36.8167, 10.1833, 12.5, 'EN_ROUTE', '2025-05-24 10:00:00', '2025-05-25 18:00:00'),
('MAERSK VIENNA', '9345678', '219876543', 'PORTE-CONTENEURS', 280.0, 45.0, 1800, 36.9000, 10.2000, 14.2, 'APPROCHE', '2025-05-23 16:00:00', '2025-05-24 08:00:00');

-- Insert containers
INSERT INTO conteneurs (numero_conteneur, type, statut, poids, contenu, client_id, navire_id, rfid_tag, date_arrivee) VALUES
('MSKU1234567', '40FT', 'EN_TRANSIT', 25000.0, 'Électronique', 5, 1, 'RFID001', '2025-05-23 08:00:00'),
('MSCU7654321', '20FT', 'STOCKE', 18000.0, 'Textile', 5, 2, 'RFID002', '2025-05-22 14:00:00');