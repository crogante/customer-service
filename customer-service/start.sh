#!/bin/bash
set -e

echo "🛑 Deteniendo servicios previos..."
docker compose down -v

# 1️⃣ Levanta Postgres, RabbitMQ y Keycloak
echo "Iniciando Postgres, RabbitMQ y Keycloak..."
docker compose up -d postgres rabbitmq keycloak

# 2️⃣ Espera que Postgres esté listo
echo "Esperando que Postgres esté disponible..."
until docker exec customer-postgres pg_isready -U postgres > /dev/null 2>&1; do
    echo "Postgres no disponible aún, esperando..."
    sleep 2
done

# 3️⃣ Espera que RabbitMQ esté listo
echo "Esperando que RabbitMQ esté disponible..."
until docker exec customer-rabbitmq rabbitmqctl status > /dev/null 2>&1; do
    echo "RabbitMQ no disponible aún, esperando..."
    sleep 2
done

# 4️⃣ Espera que Keycloak esté levantado
echo "Esperando que Keycloak esté disponible..."
until curl -s http://localhost:8081/ > /dev/null; do
    echo "Keycloak no disponible aún, esperando..."
    sleep 5
done

echo "Servicios externos listos ✅"

# 5️⃣ Construye y levanta la app
echo "Construyendo y levantando la aplicación..."
docker compose up --build -d customer-service
echo "Aplicación levantada en http://localhost:8080"

echo "👉 Podés correr tu aplicación localmente con perfil 'dev'."
