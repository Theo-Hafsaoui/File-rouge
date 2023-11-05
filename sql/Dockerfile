#Temporary dockerfile to setup database
FROM postgres
ENV POSTGRES_PASSWORD docker
ENV POSTGRES_DB world
COPY init_db.sql /docker-entrypoint-initdb.d/
