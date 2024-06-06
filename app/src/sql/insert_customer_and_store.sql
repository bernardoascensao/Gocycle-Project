-- Inserção de dados na tabela Customer
INSERT INTO Customer (name, address, email, phone, ccNumber, nationality, atrdisc)
VALUES
    ('Alice Smith', 'rua b', 'alice.smith@example.com', '123', '123456789012', 'USA', 'C'),
    ('Bob Johnson', 'rua c', 'bob.johnson@example.com', '456', '234567890123', 'Canada', 'G');

-- Inserção de dados na tabela Store
INSERT INTO Store (email, address, locality, phoneNumber, managerId)
VALUES
    ('store1@example.com', 'rua store', 'City1', '123-456', 2);