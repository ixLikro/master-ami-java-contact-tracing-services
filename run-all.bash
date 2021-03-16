#!/bin/bash

echo ami/team1 - Kontaktnachverfolgung
echo starte 10 Services

podman network create net-t1

podman run -i --rm -p 8080:8080 --name=data-generator --net=net-t1 --env-file Container.env  ami/team1/data-generator \
	& podman run -i --rm -p 8081:8081 --name=analyse --net=net-t1 --env-file Container.env ami/team1/analyse \
	& podman run -i --rm -p 8082:8082 --name=data-acceptance --env-file Container.env -e quarkus.datasource.jdbc.url='jdbc:postgresql://db-buffer:5432/movement_data' --net=net-t1 ami/team1/dataacceptance \
	& podman run -i --rm -p 8083:8083 --name=preprocessing --env-file Container.env --net=net-t1  ami/team1/preprocessing \
	& podman run -i --rm -p 8084:8084 --name=landingpage --env-file Container.env --net=net-t1  ami/team1/landingpage \
	& podman run -i --rm -p 8085:8085 --name=visualisation --env-file Container.env --net=net-t1  ami/team1/visualization \
	& sleep 10 && podman run -i --rm -p 8086:8086 --name=trend --env-file Container.env --net=net-t1  ami/team1/trend \
	& podman run -i --rm -p 8087:8087 --name=contact-tracking --env-file Container.env --net=net-t1  ami/team1/contacttracking \
	& podman run -i --rm -p 5432:5432 --name=db-main --net=net-t1  ami/team1/db_main \
	& podman run -i --rm -p 5433:5432 --name=db-buffer --net=net-t1  ami/team1/db_buffer \
	& sleep 30 && firefox "http://localhost:8084"