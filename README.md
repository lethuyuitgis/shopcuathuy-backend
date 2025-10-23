# ShopCuaThuy Backend API

A comprehensive backend API for the ShopCuaThuy e-commerce platform built with Spring Boot, JPA, Redis, RabbitMQ, and Docker.

## 🚀 Features

- **Spring Boot 3.2** - Latest Spring Boot framework
- **JPA/Hibernate** - Database ORM with MySQL
- **Redis Integration** - Caching and session management
- **RabbitMQ** - Message queue for async processing
- **Elasticsearch** - Full-text search capabilities
- **Docker Support** - Full containerization
- **Security** - JWT authentication and authorization
- **Validation** - Bean validation with custom validators
- **Documentation** - OpenAPI/Swagger documentation
- **Monitoring** - Actuator endpoints and metrics
- **Testing** - Unit and integration tests

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.2
- **Language**: Java 17
- **Database**: MySQL 8.0
- **Cache**: Redis 7
- **Message Queue**: RabbitMQ 3
- **Search**: Elasticsearch 8
- **ORM**: JPA/Hibernate
- **Security**: Spring Security + JWT
- **Documentation**: OpenAPI 3
- **Monitoring**: Micrometer + Prometheus
- **Containerization**: Docker + Docker Compose

## 📁 Project Structure

```
shopcuathuy-backend/
├── src/
│   ├── main/
│   │   ├── java/com/shopcuathuy/
│   │   │   ├── entity/           # JPA entities
│   │   │   ├── repository/       # Data repositories
│   │   │   ├── service/          # Business logic
│   │   │   ├── controller/       # REST controllers
│   │   │   ├── dto/              # Data transfer objects
│   │   │   ├── mapper/           # MapStruct mappers
│   │   │   ├── config/           # Configuration classes
│   │   │   ├── security/         # Security configuration
│   │   │   ├── exception/        # Exception handling
│   │   │   ├── util/             # Utility classes
│   │   │   └── ShopCuaThuyBackendApplication.java
│   │   └── resources/
│   │       ├── application.yml   # Application configuration
│   │       └── static/           # Static resources
│   └── test/
│       ├── java/                 # Test classes
│       └── resources/            # Test resources
├── docker-compose.yml           # Docker services
├── Dockerfile                   # Docker image
├── pom.xml                      # Maven dependencies
└── README.md                    # Documentation
```

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- MySQL 8.0
- Redis 7
- RabbitMQ 3

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd shopcuathuy-backend
   ```

2. **Environment setup**
   ```bash
   cp env.example .env
   # Edit .env with your configuration
   ```

3. **Start with Docker Compose**
   ```bash
   docker-compose up -d
   ```

4. **Or start locally**
   ```bash
   # Start database and Redis
   docker-compose up -d mysql redis rabbitmq elasticsearch
   
   # Run the application
   mvn spring-boot:run
   ```

## 🐳 Docker Services

The application includes the following Docker services:

- **app** - Main Spring Boot application
- **mysql** - MySQL 8.0 database
- **redis** - Redis cache and session store
- **rabbitmq** - RabbitMQ message broker
- **elasticsearch** - Search engine
- **kibana** - Elasticsearch management UI
- **nginx** - Reverse proxy
- **prometheus** - Metrics collection
- **grafana** - Metrics visualization
- **mailhog** - Email testing
- **minio** - Object storage
- **jaeger** - Distributed tracing

## 📊 API Documentation

Once the application is running, you can access:

- **API Documentation**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/api/actuator/health
- **Grafana Dashboard**: http://localhost:3001
- **Kibana**: http://localhost:5601
- **RabbitMQ Management**: http://localhost:15672
- **MailHog**: http://localhost:8025
- **MinIO Console**: http://localhost:9001
- **Jaeger UI**: http://localhost:16686

## 🔧 Configuration

### Environment Variables

Key environment variables to configure:

```env
# Database
DB_HOST=localhost
DB_USERNAME=root
DB_PASSWORD=password
JPA_DDL_AUTO=update

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_PASSWORD=redis_password

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_USERNAME=admin
RABBITMQ_PASSWORD=admin123

# JWT
JWT_SECRET=mySecretKey
JWT_EXPIRATION=86400000

# Email
MAIL_HOST=smtp.gmail.com
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-app-password
```

## 🏗️ Development

### Available Maven Goals

```bash
# Development
mvn spring-boot:run              # Start development server
mvn clean package                 # Build for production
mvn spring-boot:start            # Start production server

# Testing
mvn test                         # Run tests
mvn test -Dtest=ClassName     # Run specific test class
mvn test -Dtest=*Test           # Run all tests

# Code Quality
mvn clean compile                # Compile source code
mvn clean package               # Package application
mvn clean install               # Install to local repository
mvn dependency:tree             # Show dependency tree
mvn dependency:analyze          # Analyze dependencies

# Docker
mvn clean package               # Build JAR
docker build -t shopcuathuy-backend .  # Build Docker image
docker run -p 8080:8080 shopcuathuy-backend  # Run Docker container
```

### Database Migrations

```bash
# Create migration
mvn flyway:migrate

# Validate migrations
mvn flyway:validate

# Clean database
mvn flyway:clean
```

### Background Jobs

The application uses RabbitMQ for background processing:

- **Email Queue** - Send emails (welcome, verification, notifications)
- **SMS Queue** - Send SMS messages
- **Notification Queue** - Push notifications
- **Analytics Queue** - Process analytics data
- **Image Queue** - Process and optimize images
- **Order Queue** - Process orders
- **Search Queue** - Update search indexes

## 🔒 Security Features

- **JWT Authentication** - Secure token-based authentication
- **Password Hashing** - BCrypt with configurable rounds
- **Rate Limiting** - Prevent abuse with Spring Security
- **CORS** - Cross-origin resource sharing configuration
- **Input Validation** - Bean validation with custom validators
- **SQL Injection Protection** - JPA query builder
- **XSS Protection** - Input sanitization

## 📈 Monitoring & Observability

- **Health Checks** - `/api/actuator/health` endpoint
- **Metrics** - Micrometer metrics collection
- **Logging** - Structured logging with Logback
- **Error Tracking** - Comprehensive exception handling
- **Performance Monitoring** - Request/response timing
- **Distributed Tracing** - Jaeger integration

## 🚀 Deployment

### Production Deployment

1. **Build the application**
   ```bash
   mvn clean package -Pprod
   ```

2. **Run the application**
   ```bash
   java -jar target/shopcuathuy-backend-1.0.0.jar
   ```

### Docker Deployment

```bash
# Build and start all services
docker-compose up -d

# Scale the application
docker-compose up -d --scale app=3
```

### Environment-specific Configuration

- **Development**: Hot reload, debug logging
- **Staging**: Production-like with debug features
- **Production**: Optimized for performance and security

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Ensure all tests pass
6. Submit a pull request

## 📝 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:

- Create an issue in the repository
- Check the documentation
- Review the API documentation at `/swagger-ui.html`

## 🔄 Changelog

### v1.0.0
- Initial release
- Complete e-commerce backend API
- Docker support
- Authentication system
- Product management
- Order processing
- Payment integration
- Real-time features
- Analytics and reporting
- Message queue integration
- Search capabilities
- Monitoring and observability
# shopcuathuy-backend
