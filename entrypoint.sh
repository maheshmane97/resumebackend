#!/bin/bash

set -a
source /app/.env
set +a

java -jar target/resume-builder-0.0.1.jar
