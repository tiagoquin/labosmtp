#!/bin/bash

docker build --tag java-mockmock .

docker run -p 2525:2525 -p 8080:8080 java-mockmock