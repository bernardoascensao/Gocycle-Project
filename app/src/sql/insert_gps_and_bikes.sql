-- Inserir dispositivos GPS
INSERT INTO GPSDevice (latitude, longitude, batteryPercentage)
VALUES
    (38.8, -77.3, 90),
    (40.7, -74.6, 85),
    (34.0, -18.2, 80),
    (51.0, -0.1, 95),
    (35.6, 19.7, 75);

-- Inserir bicicletas
INSERT INTO Bike (type, weight, model, brand, numberOfGears, state, autonomy, maxSpeed, isActive, GPSSerialNumber)
VALUES
    ('C', 15.5, 'Mountain', 'Trek', 18, 'livre', NULL, NULL, TRUE, 1),
    ('E', 20.3, 'City', 'Specialized', 6, 'livre', 50, 25, TRUE, 2),
    ('C', 12.8, 'Road', 'Giant', 24, 'ocupado', NULL, NULL, TRUE, 3),
    ('E', 18.9, 'Hybrid', 'Cannondale', 18, 'livre', 70, 30, TRUE, 4),
    ('C', 14.2, 'Touring', 'Fuji', 6, 'livre', NULL, NULL, TRUE, 5);