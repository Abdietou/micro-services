# CONFIG SERVICE

## Features

This microservice provides the following features:

- Centralized Configuration
- Environment-specific Configurations

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
cd config-service
```

### Build the project using Maven
```bash
mvn clean install
```

### Config Service Port Configuration
**Environment Variable** : CONFIG_SERVICE_PORT<br>
**Description** : Specifies the port used by the Config service.<br>
**Default Value** : 9999


### Run the application
```bash
mvn spring-boot:run
```

# Config repo
You can find the configuration repository here :
https://github.com/Abdietou/config-repo.git