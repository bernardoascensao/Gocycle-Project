CREATE OR REPLACE FUNCTION check_bike_availability(
    bike_id INTEGER,
    date_time TIMESTAMP
) RETURNS BOOLEAN AS $$
BEGIN
    RETURN NOT EXISTS (
        SELECT 1 FROM Reservation
        WHERE bike = bike_id AND startDate <= date_time AND endDate >= date_time
    );
END;
$$ LANGUAGE plpgsql;