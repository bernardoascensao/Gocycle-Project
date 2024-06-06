begin;

create table GPSDevice(
                          serialNumber integer primary key,
                          latitude numeric(6,4) check (latitude between -90 and 90),
                          longitude numeric(6,4)check (longitude between -180 and 180),
                          batteryPercentage integer check (batteryPercentage between 0 and 100),
                          bike SERIAL,
                          FOREIGN KEY (bike) REFERENCES BIKE(id)
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
                      isActive BOOLEAN DEFAULT TRUE,
                      gpsDevice INTEGER,
                      FOREIGN KEY (gpsDevice) REFERENCES GPSDevice(serialNumber)
);


-- create table CLASSICA(
--                          bicicleta integer,
--                          foreign key (bicicleta) references bicicleta(id),
--                          nomudanca integer check (nomudanca between 0 and 5)
-- );
--
-- create table ELETRICA(
--                          bicicleta integer,
--                          foreign key (bicicleta) references bicicleta(id),
--                          autonomia integer,
--                          velocidade integer
-- );

create table Customer(
                       idNumber SERIAL primary key,
                       name varchar(40),
                       address varchar(150),
                       email varchar(40) check (position('@' in email) > 0) unique,
                       phone varchar(30) unique,
                       ccNumber char(12) unique,
                       nationality varchar(20),
                       atrdisc char(2) check (atrdisc in ('C', 'G'))
);


create table Store(
                     id SERIAL primary key,
                     email varchar(40) check (position('@' in email) > 0) unique,
                     endereco varchar(100),
                     localidade varchar(30),
                     manager integer,
                     foreign key(manager) references Customer(idNumber)
);

create table Reservation(
                        id SERIAL,
                        storeId integer,
                        primary key (id, storeId),
                        customerId integer,
                        startDate timestamp,
                        endDate timestamp,
                        amount numeric(5,2),
                        bikeId integer,
                        isActive boolean default true,
                        foreign key(bikeId) references Bike(id),
                        foreign key(storeId) references Store(id),
                        foreign key(customerId) references Customer(idNumber)
);

-- create table TELEFONELOJA(
--                              loja integer,
--                              foreign key (loja) references loja(codigo),
--                              numero varchar(10),
--                              primary key (numero)
-- );
--
-- create table CLIENTERESERVA(
--                                cliente integer,
--                                foreign key (cliente) references pessoa(id),
--                                reserva integer,
--                                loja integer,
--                                foreign key (reserva, loja) references reserva(noreserva, loja)
--
--
-- );

commit;