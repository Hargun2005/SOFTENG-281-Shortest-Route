# 🗺️ Risk Map Routing Application

This project is a Java-based command-line application that models the world map from the classic board game *Risk* as a graph. The primary goal is to find the shortest travel route between any two countries, calculate the associated "fuel" cost for the journey, and provide a breakdown of the continents visited.

This application was developed as part of Assignment #3 for the SOFTENG281 course at the University of Auckland.

<p align="center">
  <img src= https://softeng281.digitaledu.ac.nz/_images/assignments/a3/risk_game_board.svg alt="Risk Map" width="600"/>
  
</p>

---

## ✨ Features

-   **Graph-Based World Map**: Loads 42 countries and their adjacencies from CSV files into a graph data structure at startup.
-   **Country Information**: Users can query detailed information about any country, including its continent, fuel cost, and a list of its neighbors.
-   **Shortest Path Calculation**: Implements **Breadth-First Search (BFS)** to find the shortest route between two countries based on the number of border crossings.
-   **Fuel Cost Analysis**: Calculates the total fuel cost for a given route, excluding the start and end points.
-   **Continent Travel Summary**:
    -   Displays a list of all continents visited along a route.
    -   Calculates the total fuel spent within each continent.
    -   Identifies the continent where the most fuel was consumed.
-   **Robust Input Handling**: Gracefully handles invalid country names by re-prompting the user until valid input is provided, using a custom exception.

---

## 🛠️ Technical Implementation

This project is built using Object-Oriented Programming (OOP) principles in Java and leverages several key data structures and algorithms.

-   **Graph Representation**: The world map is represented as a graph where countries are vertices and adjacencies are edges. The graph is implemented using a `Map<Country, List<Country>>` to store the adjacency list.
-   **Pathfinding Algorithm**: **Breadth-First Search (BFS)** is used to guarantee finding the shortest path in terms of the number of countries visited (i.e., edge count).
-   **Core Data Structures**:
    -   `Map`: Used for the graph's adjacency list and for quick lookup of `Country` objects by name.
    -   `Set`: Used to keep track of visited nodes during BFS to prevent cycles and redundant processing.
    -   `Queue`: The core data structure for the BFS algorithm's frontier.
    -   `List`: Used to store the final path and lists of neighbors.
-   **Error Handling**: A custom exception class is implemented to handle cases where a user enters a non-existent country name. This allows for clean separation of error detection and error handling logic.
-   **Dependency Management**: The project is managed by **Apache Maven**, with a provided Maven Wrapper (`mvnw`) to ensure a consistent build and execution environment.

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

-   [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) (Version 11 or higher)
-   [Git](https://git-scm.com/)

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/your-repository-name.git
    cd your-repository-name
    ```

2.  **Run the application:**
    Use the Maven Wrapper to compile and run the application in interactive mode.

    -   On **Windows** (using Command Prompt or PowerShell):
        ```bash
        .\mvnw.cmd clean compile exec:java@run
        ```
    -   On **Unix/Mac OS/Linux** (using Terminal):
        ```bash
        ./mvnw clean compile exec:java@run
        ```

    You will see the welcome prompt `281-map>`.

3.  **Run the tests:**
    To ensure everything is working correctly, you can run the provided JUnit tests.

    -   On **Windows**:
        ```bash
        .\mvnw.cmd clean compile test
        ```
    -   On **Unix/Mac OS/Linux**:
        ```bash
        ./mvnw clean compile test
        ```

---

## 📝 Command Reference

The application supports the following commands (case-insensitive, hyphens and underscores are interchangeable).

### `INFO-COUNTRY`
Displays information about a specific country. The application will prompt you to enter the country's name.

**Example:**
```text
281-map> info-country
Insert the name of the country:
japan
Japan => continent: Asia, fuel cost: 5, neighbours: [Kamchatka, Mongolia]
281-map> info-country
Insert the name of the country:
new zeland
ERROR! This country was not found: New Zeland, try again!
Argentina
Argentina => continent: South America, fuel cost: 1, neighbours: [Peru, Brazil]
281-map> route
Insert the name of the country where you start the journey:
Argentina
Insert the name of the country of destination:
Japan
The fastest route is: [Argentina, Peru, Venezuela, Central America, Western United States, Alberta, Alaska, Kamchatka, Japan]
You will spend this amount of fuel for your journey: 28 units
You will visit the following continents: [South America (7), North America (15), Asia (6)]
The continent where you will spend the most fuel is: North America (15)

