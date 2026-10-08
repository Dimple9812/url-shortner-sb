# URL Shortener (Spring Boot)

A simple URL shortener built with **Spring Boot**. Paste a long URL, get a short link, and be redirected to the original URL whenever the short link is opened.

## Features

- Shorten any valid long URL
- Redirect short links to the original URL
- Unique short code generation
- Persistent storage using a database
- REST API with JSON request/response
- Input validation and error handling

## Tech Stack

| Layer      | Technology                          |
|------------|-------------------------------------|
| Language   | Java 17+                            |
| Framework  | Spring Boot (Web, Data JPA)         |
| Database   | H2 (dev) / MySQL / PostgreSQL       |
| Build Tool | Maven                               |

> Update this table to match your `pom.xml`.

## Project Structure

```
url-shortner-sb/
├── src/
│   ├── main/
│   │   ├── java/com/example/urlshortener/
│   │   │   ├── controller/    # REST controllers
│   │   │   ├── service/       # Business logic
│   │   │   ├── repository/    # Spring Data JPA repositories
│   │   │   ├── model/         # Entity classes
│   │   │   └── UrlShortenerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven 3.8+
- (Optional) MySQL/PostgreSQL if not using H2

### Installation

```bash
# Clone the repository
git clone https://github.com/Dimple9812/url-shortner-sb.git
cd url-shortner-sb

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The app starts at `http://localhost:8080`.

### Configuration

Edit `src/main/resources/application.properties`:

```properties
server.port=8080

# Database (example: MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/urlshortener
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## API Endpoints

### 1. Create a short URL

**POST** `/api/shorten`

Request:
```json
{
  "originalUrl": "https://www.example.com/some/very/long/path"
}
```

Response:
```json
{
  "originalUrl": "https://www.example.com/some/very/long/path",
  "shortUrl": "http://localhost:8080/abc123"
}
```

### 2. Redirect to the original URL

**GET** `/{shortCode}`

Redirects (HTTP 302) to the original URL. Returns `404` if the code does not exist.

### Example with cURL

```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"originalUrl": "https://www.example.com"}'
```

## How It Works

1. The client sends a long URL to the API.
2. The service generates a unique short code (e.g. random Base62 string or encoded ID).
3. The mapping `shortCode -> originalUrl` is saved in the database.
4. When someone visits the short link, the app looks up the code and redirects to the original URL.

## Running Tests

```bash
mvn test
```

## Future Improvements

- Custom aliases for short links
- Link expiration
- Click analytics
- User accounts and authentication
- Rate limiting
- Docker support

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/my-feature`)
3. Commit your changes (`git commit -m "Add my feature"`)
4. Push the branch (`git push origin feature/my-feature`)
5. Open a Pull Request

Link: https://url-shortner-12.netlify.app/login 

