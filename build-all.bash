#!/bin/bash

echo ami/team1 - Kontaktnachverfolgung
echo build 10 Images, 5 quarkus uber jars, 3 bootable-jars and 2 Databases

echo
echo
echo build data generator
podman build -f data-generator/Containerfile.multistage -t ami/team1/data-generator data-generator/

echo
echo
echo build landingpage
podman build -f landingpage/Containerfile.multistage -t ami/team1/landingpage landingpage/

echo
echo
echo build analyse
podman build -f analyse/Containerfile.multistage -t ami/team1/analyse analyse/

echo
echo
echo build visualization
podman build -f visualization/Containerfile.multistage -t ami/team1/visualization visualization/

echo
echo
echo build data acceptence
podman build -f dataacceptance/Containerfile.multistage -t ami/team1/dataacceptance dataacceptance/

echo
echo
echo build trend
podman build -f trend/Containerfile.multistage -t ami/team1/trend trend/

echo
echo
echo build preprocessing
podman build -f preprocessing/Containerfile.multistage -t ami/team1/preprocessing preprocessing/

echo
echo
echo build main database
podman build -f Containerfile_db_nachverfolgung -t ami/team1/db_main . \

echo
echo
echo build contact tracking
podman build -f contacttracking/Containerfile.multistage -t ami/team1/contacttracking contacttracking/

echo
echo
echo build buffer database
podman build -f Containerfile_db_movement -t ami/team1/db_buffer .