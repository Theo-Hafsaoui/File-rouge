#!/bin/bash

usage() { echo "No correct flag given" 1>&2; exit 1; }


while getopts "cd" arg; do
  case $arg in
        c)
            echo "docker compose";;
        d)
            echo "decoupled mode"
            docker run  -e POSTGRES_PASSWORD="test" -e POSTGRES_DB=red-db -d -p 5432:5432 postgres;
            cd ./sql && psql -h localhost -p 5432 -U postgres -d red-db -f init_db_decoupled.sql &&
            cd ../server && mvn compile && mvn exec:java;;
    *)
            usage;;
    esac
done
