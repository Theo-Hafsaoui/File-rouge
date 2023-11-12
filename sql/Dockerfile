#Temporary dockerfile to setup database
FROM postgres:12-bullseye
COPY init_db.sql /docker-entrypoint-initdb.d/
COPY country.csv /docker-entrypoint-initdb.d/
COPY Order.csv /docker-entrypoint-initdb.d/
COPY product.csv /docker-entrypoint-initdb.d/
EXPOSE 5432
