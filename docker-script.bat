@echo off

@rem
mvn clean package

@rem
docker-compose down

@rem
docker-compose up -d --build

