begin;

-- Drop da tabela Reservation, se existir
DROP TABLE IF EXISTS Reservation;

-- Drop da tabela Bike, se existir
DROP TABLE IF EXISTS Bike;

-- Drop da tabela Store, se existir
DROP TABLE IF EXISTS Store;

-- Drop da tabela Customer, se existir
DROP TABLE IF EXISTS Customer;

-- Drop da tabela GPSDevice, se existir
DROP TABLE IF EXISTS GPSDevice;

create table GPSDevice(
                          serialNumber serial primary key,
                          latitude numeric(6,4) check (latitude between -90 and 90),
                          longitude numeric(6,4)check (longitude between -180 and 180),
                          batteryPercentage integer check (batteryPercentage between 0 and 100)
);

CREATE TABLE Bike (
                      id SERIAL PRIMARY KEY,
                      type CHAR(1) CHECK (type IN ('C', 'E')),
                      weight NUMERIC(4,2) CHECK (weight >= 0),
                      model VARCHAR(20),
                      brand VARCHAR(30),
                      numberOfGears INTEGER CHECK (numberOfGears IN (1,6,18,24)),
                      state VARCHAR(30) CHECK (state IN ('livre', 'ocupado', 'em manutenção')),
                      autonomy INTEGER CHECK (type = 'E' OR autonomy IS NULL),
                      maxSpeed INTEGER CHECK (type = 'E' OR maxSpeed IS NULL),
                      GPSSerialNumber INTEGER,
                      isActive boolean default true,
                      FOREIGN KEY (GPSSerialNumber) REFERENCES GPSDevice(serialNumber)
);

create table Customer(
                       id SERIAL primary key,
                       name varchar(40),
                       address varchar(150),
                       email varchar(40) check (position('@' in email) > 0) unique,
                       phone varchar(30) unique,
                       ccNumber char(12) unique,
                       nationality varchar(20),
                       atrdisc char(2) check (atrdisc in ('C', 'G')),
                       isActive BOOLEAN DEFAULT TRUE
);


create table Store(
                     id SERIAL primary key,
                     email varchar(40) check (position('@' in email) > 0) unique,
                     address varchar(100),
                     locality varchar(30),
                     phoneNumber varchar(30) unique,
                     manager integer,
                     isActive BOOLEAN DEFAULT TRUE,
                     foreign key(manager) references Customer(id)
);

create table Reservation(
                        id SERIAL,
                        store integer,
                        primary key (id, store),
                        customer integer,
                        startDate timestamp,
                        endDate timestamp,
                        amount numeric(5,2),
                        bike integer,
                        version integer not null default 0,
                        foreign key(bike) references Bike(id),
                        foreign key(store) references Store(id),
                        foreign key(customer) references Customer(id)
);

commit;