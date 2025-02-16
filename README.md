# MindMapAPI 🧠

A powerful and feature-rich Java-based API designed to manage and analyze journal entries with enhanced security, real-time messaging, and performance optimization. Built using **Spring Boot**, **MongoDB Atlas**, **Redis**, and **Apache Kafka** to deliver a seamless user experience with advanced capabilities like **Sentiment Analysis**, **Caching**, and **Role-Based Authorization**.

## 🌟 Features

### 🛡️ Security & Authorization
- **Role-Based Authorization**: Implemented secure access control based on user roles.
- **Advanced Authentication**: Secure journal entries with Spring Security and robust password protection.
- **Refined Authorization Middleware**: Ensures improved security for API endpoints.
- **SECURE TOKEN-BASED AUTHENTICATION: Secure user sessions using JWT (JSON Web Token) for stateless authentication.

### 📊 Data Management
- **MongoDB Atlas Integration**: Store and manage journal entries on a cloud-based MongoDB database.
- **Enhanced User Functionalities**: Seamless binding of user profiles with journal entries.
- **CRUD Operations**: Perform Create, Read, Update, and Delete operations efficiently.
- **Response Entities**: Return HTTP status codes for better API communication.

### 🚀 Performance Optimization
- **Redis Cloud Caching**: Optimize API responses with high-speed caching using Redis Cloud.
- **PostConstruct & AppCache**: Improve performance with preloaded data caches.
- **MongoTemplate, Criteria & Query**: Implement advanced querying using Spring Boot's MongoTemplate.

### 📢 Messaging & External Integrations
- **Apache Kafka**: Real-time messaging and event streaming for high throughput.
- **External API Integration**: Connect securely with third-party APIs for extended functionality.
- **Java Mail Sender**: Send automated mood-based email alerts via integrated email functionality.

### 📈 Sentiment Analysis
- **Mood-Based Insights**: Analyze journal entries to derive emotional patterns.
- **Email Alerts**: Trigger real-time email notifications based on sentiment analysis results.

### 📚 Logging & Monitoring
- **Logback Implementation**: Utilize rolling policy logs to track system activity at one-minute intervals.
- **Enhanced Logging**: Improved monitoring with detailed log outputs.

## 🛠️ Installation

Follow these steps to set up and run the **MindMapAPI** project locally:

### Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven
- Docker (for Redis container)
- MongoDB Atlas account

### Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/shahzaib-arain/MindMapAPI.git
   cd MindMapAPI
   ```

2. Configure MongoDB Atlas and Redis:

   - Set up a MongoDB Atlas cluster and get your connection URI.
   - Ensure Redis is running in Docker:

   ```bash
   docker run -d --name redis-container -p 6379:6379 redis
   ```

3. Set environment variables:

   ```bash
   export MONGO_URI="your-mongo-uri"
   export REDIS_HOST="localhost"
   export REDIS_PORT=6379
   ```

4. Build and run the application:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## 📌 API Endpoints

| Method | Endpoint                 | Description                            |
|--------|--------------------------|----------------------------------------|
| POST   | `/api/users/register`    | Register a new user                    |
| POST   | `/api/users/login`       | Authenticate user                      |
| GET    | `/api/journals`          | Retrieve all journal entries           |
| POST   | `/api/journals`          | Create a new journal entry             |
| PUT    | `/api/journals/{id}`     | Update a journal entry                 |
| DELETE | `/api/journals/{id}`     | Delete a journal entry                 |
| GET    | `/api/sentiment`         | Perform sentiment analysis on entries  |

## 🧰 Tech Stack

- **Backend**: Java (Spring Boot)
- **Database**: MongoDB Atlas
- **Caching**: Redis Cloud
- **Messaging**: Apache Kafka
- **Email**: Java Mail Sender
- **Security**: Spring Security (Role-Based Authorization)

## 📊 System Architecture

1. **User Authentication**: Secured with Spring Security and role-based access.
2. **Journal Management**: CRUD operations with seamless user binding.
3. **Caching Layer**: Redis Cloud to optimize repeated API responses.
4. **Event Streaming**: Apache Kafka for real-time messaging.
5. **Sentiment Analysis**: Analyze and respond to journal moods.

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repository and submit a pull request.

### Steps to Contribute
1. Fork the repo.
2. Create a new feature branch.
3. Commit your changes.
4. Push to your fork and submit a pull request.

## 📧 Contact

Reach out for collaboration or questions:

- **Shahzaib Sajjad**  
- 📧 Email: shahzaibarain.0080@gmail.com  
- 🔗 [LinkedIn](https://www.linkedin.com/in/shahzaib-arain-373a02276)  

## ⭐

If you find this project helpful, give it a ⭐ on [GitHub](https://github.com/shahzaib-arain/MindMapAPI)!

