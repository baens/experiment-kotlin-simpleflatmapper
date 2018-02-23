#!/bin/bash

docker run \
    -e POSTGRES_PASSWORD=password \
    -v "$(pwd)"/src/sql:/docker-entrypoint-initdb.d \
    --rm \
    -p 5432:5432 \
    postgres:10.1
