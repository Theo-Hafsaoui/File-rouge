    create schema red;

    create table red.Product
        (
            name text primary key,
            description text 
        );

    \copy red.Product from '/csvs/product.csv' with csv delimiter E','

    create table red.Country
        (
            name text primary key,
            description text
        );

    \copy red.Country from '/csvs/country.csv' with csv delimiter E','

    /**
    Table for the order made between two country for a product
    */
    create table red.Order
        (
            Suplier text REFERENCES red.Country,
            Recipient text REFERENCES red.Country,
            Quantity int CHECK (Quantity > 0),
            Product text REFERENCES red.Product,
            Date timestamptz,
            Description text,
            primary key(Date, Product, Suplier, Recipient)
        );

    \copy red.Order from '/csvs/Order.csv' with csv delimiter E','

    create table red.Delivery
        (
            id serial,
            Date timestamptz,
            status text
        );
        
    create table red.Invoice
        (
            id serial,
            Order_invoice text,
            Delivery_invoice text,
            description text
        );

    select count(*) from red.Country ;
    select count(*) from red.Product ;
    select count(*) from red.Order ;
