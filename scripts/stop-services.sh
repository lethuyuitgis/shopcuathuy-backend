#!/bin/bash

echo "🛑 Stopping ShopCuaThuy Backend Services..."

# Stop Docker services
docker-compose down

echo "✅ Services stopped successfully!"
echo ""
echo "💡 To start services again: ./scripts/start-services.sh"
echo "🗑️  To remove all data: docker-compose down -v"
