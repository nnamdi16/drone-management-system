#!/bin/bash

# Function to kill all processes using a specified port
#kill_port() {
#  PORT=$1
#  echo "Killing all processes using port $PORT..."
#
#  # List processes using the port and extract process IDs
#  PIDS=$(lsof -t -i:$PORT)
#
#  # Check if any processes were found
#  if [ -z "$PIDS" ]; then
#    echo "No processes found using port $PORT."
#  else
#    # Kill the processes
#    echo $PIDS | xargs kill -9
#    echo "Killed processes using port $PORT: $PIDS"
#  fi
#}
#
## Ensure a port number is provided
#if [ -z "$1" ]; then
#  echo "Usage: $0 <port_number>"
#  exit 1
#fi
#
#PORT=$1
#
## Step 0: Kill processes using the specified port
#kill_port $PORT

# Step 1: Build the Docker image using Jib
mvn clean compile jib:dockerBuild

# Step 2: Load the Docker image into the Kind cluster
kind load docker-image drone-management-system:latest

# Step 3: Update the Helm release
helm upgrade --install drone-management-system ./drone-management-system

# Step 4: Port-forward the service
#kubectl port-forward svc/drone-management-system 5000:8080

echo "Deployment updated with the latest Docker image."
