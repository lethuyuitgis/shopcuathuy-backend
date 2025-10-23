#!/bin/bash

echo "🚀 Starting ShopCuaThuy Backend Services..."

# Create necessary directories
mkdir -p logs uploads database/init database/backups monitoring/grafana/dashboards monitoring/grafana/datasources nginx/ssl

# Copy environment file if it doesn't exist
if [ ! -f .env ]; then
    echo "📋 Creating .env file from template..."
    cp env.example .env
fi

# Start Docker services
echo "🐳 Starting Docker containers..."
docker-compose up -d

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check services status
echo "📊 Checking services status..."
docker-compose ps

# Show service URLs
echo ""
echo "✅ Services started successfully!"
echo ""
echo "🌐 Access URLs:"
echo "   - Spring Boot API: http://localhost:8080/api"
echo "   - RabbitMQ Management: http://localhost:15672 (admin/admin123)"
echo "   - MinIO Console: http://localhost:9001 (minioadmin/minioadmin123)"
echo "   - MailHog: http://localhost:8025"
echo "   - Elasticsearch: http://localhost:9200"
echo "   - Kibana: http://localhost:5601"
echo "   - Prometheus: http://localhost:9090"
echo "   - Grafana: http://localhost:3001 (admin/admin123)"
echo "   - Jaeger: http://localhost:16686"
echo "   - Nginx: http://localhost"
echo ""
echo "📊 Database:"
echo "   - MySQL: localhost:3306 (shopcuathuy/shopcuathuy123)"
echo "   - Redis: localhost:6379"
echo ""
echo "🔧 To view logs: docker-compose logs -f [service_name]"
echo "🛑 To stop services: docker-compose down"
