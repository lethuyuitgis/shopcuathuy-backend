# 🐳 ShopCuaThuy Backend - Docker Setup

## 📋 Tổng quan

Dự án ShopCuaThuy Backend sử dụng Docker Compose để quản lý tất cả các services cần thiết cho một ứng dụng e-commerce hoàn chỉnh.

## 🚀 Cài đặt nhanh

### 1. Khởi động tất cả services
```bash
# Sử dụng script tự động
./scripts/start-services.sh

# Hoặc thủ công
docker-compose up -d
```

### 2. Kiểm tra trạng thái
```bash
docker-compose ps
```

### 3. Xem logs
```bash
# Tất cả services
docker-compose logs -f

# Service cụ thể
docker-compose logs -f app
docker-compose logs -f mysql
docker-compose logs -f redis
```

## 🏗️ Kiến trúc Services

### Core Services
- **app**: Spring Boot Application (port 8080)
- **mysql**: MySQL Database (port 3306)
- **redis**: Redis Cache (port 6379)
- **rabbitmq**: Message Queue (port 5672, UI: 15672)

### Storage & Search
- **minio**: Object Storage (port 9000, UI: 9001)
- **elasticsearch**: Search Engine (port 9200)
- **kibana**: Elasticsearch UI (port 5601)

### Monitoring & Observability
- **prometheus**: Metrics Collection (port 9090)
- **grafana**: Metrics Visualization (port 3001)
- **jaeger**: Distributed Tracing (port 16686)

### Development Tools
- **mailhog**: Email Testing (port 8025)
- **nginx**: Reverse Proxy (port 80)

## 🔧 Cấu hình

### Environment Variables
Tất cả cấu hình được định nghĩa trong file `.env`:

```bash
# Database
DB_HOST=mysql
DB_USERNAME=shopcuathuy
DB_PASSWORD=shopcuathuy123

# Redis
REDIS_HOST=redis

# RabbitMQ
RABBITMQ_HOST=rabbitmq
RABBITMQ_USERNAME=admin
RABBITMQ_PASSWORD=admin123

# MinIO
MINIO_ENDPOINT=http://minio:9000
MINIO_ACCESS_KEY=minioadmin
MINIO_SECRET_KEY=minioadmin123
```

### Health Checks
Tất cả services đều có health checks để đảm bảo chúng sẵn sàng trước khi Spring Boot app khởi động.

## 📊 Monitoring

### Prometheus Metrics
- **Spring Boot**: http://localhost:8080/api/actuator/prometheus
- **Prometheus UI**: http://localhost:9090

### Grafana Dashboards
- **URL**: http://localhost:3001
- **Username**: admin
- **Password**: admin123

### Jaeger Tracing
- **URL**: http://localhost:16686

## 🗄️ Database

### MySQL
- **Host**: localhost:3306
- **Database**: shopcuathuy
- **Username**: shopcuathuy
- **Password**: shopcuathuy123

### Redis
- **Host**: localhost:6379
- **Password**: (không có)

## 📧 Email Testing

### MailHog
- **SMTP**: localhost:1025
- **Web UI**: http://localhost:8025

## 🔍 Search

### Elasticsearch
- **URL**: http://localhost:9200
- **Kibana**: http://localhost:5601

## 📁 File Storage

### MinIO
- **API**: http://localhost:9000
- **Console**: http://localhost:9001
- **Username**: minioadmin
- **Password**: minioadmin123

## 🚦 Message Queue

### RabbitMQ
- **AMQP**: localhost:5672
- **Management**: http://localhost:15672
- **Username**: admin
- **Password**: admin123

## 🛠️ Development Commands

### Khởi động services
```bash
./scripts/start-services.sh
```

### Dừng services
```bash
./scripts/stop-services.sh
```

### Rebuild và khởi động lại
```bash
docker-compose down
docker-compose up -d --build
```

### Xóa tất cả data
```bash
docker-compose down -v
```

### Xem logs real-time
```bash
docker-compose logs -f app
```

### Vào container
```bash
# Spring Boot app
docker-compose exec app bash

# MySQL
docker-compose exec mysql mysql -u shopcuathuy -p shopcuathuy

# Redis
docker-compose exec redis redis-cli
```

## 🔒 Security

### Nginx Configuration
- Rate limiting cho API endpoints
- Security headers
- SSL/TLS support (cần cấu hình certificates)

### Database Security
- Root password được bảo vệ
- User permissions được giới hạn
- Connection encryption

## 📈 Performance

### Resource Limits
- Elasticsearch: 512MB RAM
- MySQL: Auto-scaling
- Redis: Persistent storage

### Caching Strategy
- Redis cho session storage
- Redis cho API response caching
- Database connection pooling

## 🐛 Troubleshooting

### Services không khởi động
```bash
# Kiểm tra logs
docker-compose logs [service_name]

# Kiểm tra trạng thái
docker-compose ps

# Restart service
docker-compose restart [service_name]
```

### Database connection issues
```bash
# Kiểm tra MySQL
docker-compose exec mysql mysql -u root -p

# Test connection từ app
docker-compose exec app curl http://localhost:8080/api/actuator/health
```

### Memory issues
```bash
# Xem resource usage
docker stats

# Clean up
docker system prune -f
```

## 📚 Additional Resources

- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [MySQL Docker Hub](https://hub.docker.com/_/mysql)
- [Redis Docker Hub](https://hub.docker.com/_/redis)
- [RabbitMQ Docker Hub](https://hub.docker.com/_/rabbitmq)
- [MinIO Docker Hub](https://hub.docker.com/_/minio)
