# GATEWAY SERVICE

## Features

This microservice provides the following features:

- Request Routing

## Prerequisites

Before getting started, make sure you have the following tools installed:

- Java Development Kit (JDK) 21 or latest
- Kotlin 1.9.25 or latest
- Apache Maven 3.9.1 or latest

## Installation

### Clone this repository to your local machine
```bash
git clone https://github.com/Abdietou/micro-services.git
```

### Navigate to the project directory
```bash
cd gateway-service
```

### Build the project using Maven
```bash
mvn clean install
```

### Gateway Service Port Configuration
**Environment Variable** : GATEWAY_SERVICE_PORT<br>
**Description** : Specifies the port used by the Gateway service.<br>
**Default Value** : 8888

### Run the application
```bash
mvn spring-boot:run
```