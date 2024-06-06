CREATE OR REPLACE FUNCTION realizar_reserva(
    store_id INTEGER,
    bike_id INTEGER,
    customer_id INTEGER,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    amount DOUBLE PRECISION
) RETURNS VOID AS $$
BEGIN
INSERT INTO Reservation (storeId, bikeId, customerId, startDate, endDate, amount, isActive)
VALUES (store_id, bike_id, customer_id, start_date, end_date, amount, TRUE);
END;
$$ LANGUAGE plpgsql;