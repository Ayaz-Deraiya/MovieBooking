#!/bin/bash

# Configuration
PID_FILE="microservices.pid"
LOG_DIR="logs"

echo "Stopping all microservices and cleaning up..."
echo "=========================================="

# Check if PID file exists
if [ ! -f "$PID_FILE" ]; then
    echo "Error: PID file '$PID_FILE' not found!"
    echo "No services to stop."
    
    # Clean up logs directory if it exists
    if [ -d "$LOG_DIR" ]; then
        echo "Cleaning up logs directory..."
        rm -rf "$LOG_DIR"
        echo "✓ Logs directory removed: $LOG_DIR"
    fi
    
    exit 1
fi

# Read PID file and kill processes
while IFS=: read -r pid service_name; do
    if kill -0 "$pid" 2>/dev/null; then
        echo "Stopping $service_name (PID: $pid)..."
        kill "$pid"
        
        # Wait for process to terminate gracefully
        for i in {1..10}; do
            if ! kill -0 "$pid" 2>/dev/null; then
                echo "✓ $service_name stopped gracefully"
                break
            fi
            sleep 1
        done
        
        # Force kill if still running
        if kill -0 "$pid" 2>/dev/null; then
            echo "Force killing $service_name..."
            kill -9 "$pid"
            echo "✓ $service_name force stopped"
        fi
    else
        echo "⚠️  Process $pid ($service_name) not found or already stopped"
    fi
done < "$PID_FILE"

# Clean up PID file
rm -f "$PID_FILE"
echo "✓ PID file removed: $PID_FILE"

# Clean up logs directory if it exists
if [ -d "$LOG_DIR" ]; then
    echo "Cleaning up logs directory..."
    rm -rf "$LOG_DIR"
    echo "✓ Logs directory removed: $LOG_DIR"
else
    echo "ℹ️  Logs directory not found: $LOG_DIR"
fi

echo "=========================================="
echo "All services stopped and cleanup complete!"