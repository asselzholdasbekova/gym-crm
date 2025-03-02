# Gym CRM System

## Overview
This project is a **Spring Core** application that serves as a simple **Gym CRM System**.

## Features

## Project Structure
```
gym-crm/
│── src/
│   ├── main/
│   │   ├── java/com/gym/
│   ├── test/
│   │   ├── java/com/gym/ (Unit tests)
│── pom.xml (Maven dependencies)
│── README.md (Project documentation)
```

## Setup Instructions
### Prerequisites
- **Java 17+**
- **Maven 3.6+**
- **IntelliJ IDEA (Community Edition)**

### Build and Run
1. **Clone the repository**:
   ```sh
   git clone https://github.com/asselzholdasbekova/gym-crm.git
   cd gym-crm
   ```
2. **Generate the project using Maven**:
   ```sh
   mvn archetype:generate -DgroupId=com.gym -DartifactId=gym-crm -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```
3. **Open the project in IntelliJ IDEA**.
4. **Build the project**:
   ```sh
   mvn clean install
   ```
5. **Run the application**:
   ```sh
   mvn exec:java -Dexec.mainClass="com.gym.App"
   ```

## Configuration

## Future Enhancements

---
### Author: Assel Zholdasbekova

