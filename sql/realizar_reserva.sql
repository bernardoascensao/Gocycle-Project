CREATE OR REPLACE FUNCTION realizar_reserva(
    store_id INT,
    bike_id INT,
    customer_id VARCHAR,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    amount NUMERIC
) RETURNS VOID AS $$
BEGIN
INSERT INTO Reservation (storeId, bikeId, customerId, startDate, endDate, amount, isActive)
VALUES (store_id, bike_id, customer_id, start_date, end_date, amount, TRUE);
END;
$$ LANGUAGE plpgsql;