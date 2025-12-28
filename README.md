# 🎬 Movie Booking System

A Spring Boot–based Movie Booking System built using a microservices architecture, designed to simulate a real-world online movie ticket booking platform.

The system leverages MongoDB for flexible, document-based data storage along with secure authentication and a scalable backend design.

## 🚀 Tech Stack

Java

Spring Boot

Spring Data JPA

Spring Data MongoDB

Spring Security (JWT)

MySQL

MongoDB

Maven

REST APIs

## 🧩 Microservices Overview
## 🎥 Movie Service

Stores and manages movie metadata

Title, language, genre, duration, etc.

## 🏛️ Theatre Service

Manages theatres and screens

Handles seat layout configurations

## ⏰ Show Service

Manages show timings

Dynamic pricing and seat availability

## 💺 Booking Service

Handles ticket booking and seat allocation

Maintains booking history

Uses MongoDB for efficient seat and booking state storage

## 🔐 User / Auth Service

User registration and login

JWT-based authentication and authorization

## ⭐ Key Features

🎥 Movie listing and show discovery

🏛️ Theatre and screen management

⏰ Show scheduling with dynamic pricing

💺 Real-time seat booking and locking logic

🔐 Secure authentication using JWT

💳 Integrated payment flow for ticket booking

🧾 Booking history stored in MongoDB

⚡ Scalable and modular microservice architecture

🗂️ Hybrid persistence using MySQL + MongoDB

## 🏗️ Architecture Highlights

Independent Spring Boot microservices

REST-based inter-service communication

Database-per-service approach

MongoDB for high-write, flexible booking data

Stateless services with JWT-based security


## ⚙️ How to Run the Project
✅ Prerequisites

Java 17+

Maven

MySQL

MongoDB

▶️ Steps

1️⃣ Clone the repository

git clone https://github.com/Ayaz-Deraiya/MovieBooking.git

2️⃣ Configuration

create application.properties in each microservice and put the credentials that are essentially requried 

3️⃣ Build the project

mvn clean install

4️⃣ Run services individually

mvn spring-boot:run

## 📌 Why MongoDB?

Flexible schema for booking and seat data

Faster writes during high booking traffic

Easy storage of nested seat and show structures

Better scalability for booking history and logs

## 🧠 Learning Outcomes

Designing real-world microservices architecture

Working with relational + NoSQL databases

JWT-based authentication and authorization

Backend system design for scalability

REST API best practices

## 🔮 Future Enhancements

🚦 Rate Limiting to control excessive API requests

⚖️ Load Balancer for better traffic distribution and high availability

⚡ Caching Layer (Redis) to improve performance and reduce DB load

☁️ Cloud-based Image Service for storing and serving movie posters

🌐 API Gateway (Spring Cloud Gateway)

🔍 Service Discovery (Eureka)

📊 Distributed tracing and monitoring

## 👨‍💻 Author

Ayaz Deraiya
B.Tech Student | Java Backend Developer

🔗 GitHub: https://github.com/Ayaz-Deraiya
