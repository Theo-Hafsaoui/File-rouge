#!/bin/bash
#Simple launcher to make some test easier
#the docker-compose cmd is wraped into fzf to make the naviagtion into the logs easier

usage() { echo "No correct flag given" 1>&2; exit 1; }

while getopts "cdt" arg; do
  case $arg in
        t)
            echo "--Testing working application--";
            {
                docker-compose down && docker-compose up
            }&
            sleep 60;
            ./.github/integration_tests/client_test.sh
            v=$?
            docker-compose down && exit "$v";;
        c)
            echo "--docker compose--";
            {
                docker compose down && docker compose build  && docker compose watch 
            }&
            sleep 30 && firefox http://localhost:3000/;;

        d)
            echo "--decoupled mode--"
            docker run  -e POSTGRES_PASSWORD="test" -e POSTGRES_DB=red-db -d -p 5432:5432 postgres;
            cd ./sql && psql -h localhost -p 5432 -U postgres -d red-db -f init_db_decoupled.sql &&
            cd ../server && mvn compile && mvn exec:java;;
    *)
            usage;;
    esac
done
