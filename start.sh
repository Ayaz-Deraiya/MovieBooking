#!/bin/bash

# Configuration
SERVICES=("UserService" "BookingService" "MovieService" "ShowtimeService" "TheatreService" "ApiGateway149")
PID_FILE="microservices.pid"
LOG_DIR="logs"

# Create log directory if it doesn't exist
mkdir -p "$LOG_DIR"

# Initialize PID file
> "$PID_FILE"

echo "Starting all microservices..."
echo "=========================================="

# Function to start a service
start_service() {
    local service_name="$1"
    local log_file="$LOG_DIR/${service_name}.log"
    
    echo "Starting $service_name..."
    
    # Navigate to service directory and start it in background
    # Assuming each service is in its own directory with the same name
    if [ -d "$service_name" ]; then
        cd "$service_name" || return
        
        # Start the Spring Boot service and capture PID
        if [ -f "pom.xml" ]; then
            mvn spring-boot:run > "../$log_file" 2>&1 &
            local pid=$!
            echo "$pid:$service_name" >> "../$PID_FILE"
            echo "✓ $service_name started with PID: $pid (logs: $log_file)"
        elif [ -f "build.gradle" ]; then
            ./gradlew bootRun > "../$log_file" 2>&1 &
            local pid=$!
            echo "$pid:$service_name" >> "../$PID_FILE"
            echo "✓ $service_name started with PID: $pid (logs: $log_file)"
        else
            echo "✗ Error: No build file found for $service_name"
            return
        fi
        
        cd ..
    else
        echo "✗ Error: Directory $service_name not found"
    fi
    
    # Give the service some time to start
    sleep 10
}

# Start all services
for service in "${SERVICES[@]}"; do
    start_service "$service"
done

echo "=========================================="
echo "All services started!"
echo "PID file created: $PID_FILE"
echo "Logs directory: $LOG_DIR"
echo ""
echo "To stop all services, run: ./stop.sh"