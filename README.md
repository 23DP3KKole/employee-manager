# Employee Data Management Console Application

## Overview

This is a Java console application designed to manage employee data stored in JSON files. It provides functionalities to:

* **Create:** Initialize the necessary JSON data files (employees, departments, salaries).
* **Change (Update):** Modify existing data in any of the data files.
* **Delete:** Remove specific data entries or delete entire data files.
* **Search:** Find specific data based on various criteria within each file.
* **Sort:** Arrange the data in each file based on chosen fields and order (ascending/descending).

The application uses the Jackson library for handling JSON data and provides a simple text-based menu for user interaction in the console.

## Functionalities

The application offers the following options through its main menu:

**Employee Management:**

1.  **Add New Employee:** Allows you to enter details for a new employee (Name, ID, Department, Salary) and saves it to `employees.json`.
2.  **Change Employee Data:** Prompts for an employee ID and allows you to update their Name, Department, or Salary.
3.  **Delete Employee Data:** Prompts for an employee ID and removes their record from `employees.json`.
4.  **Search Employees:** Allows searching for employees by Name, ID, Department (partial matches), or exact Salary.
5.  **Sort Employees:** Enables sorting the employee data by Name, ID, Department (alphabetical), or Salary (numerical), in ascending or descending order.

**Department Management:**

6.  **Add New Department:** Allows you to enter a new department name and adds it to `departments.json`.
7.  **Change Department Data:** Displays existing departments and lets you update a selected department's name.
8.  **Delete Department Data:** Displays existing departments and allows you to remove one.
9.  **Search Departments:** Allows searching for departments by name (partial matches).
10. **Sort Departments:** Sorts the department names alphabetically (ascending or descending).

**Salary Management:**

11. **Add New Salary Entry:** Allows you to enter an employee ID and their corresponding salary, which is saved to `salaries.json`.
12. **Change Salary Data:** Prompts for an employee ID and lets you update their salary.
13. **Delete Salary Data:** Prompts for an employee ID and removes their salary entry from `salaries.json`.
14. **Search Salaries:** Allows searching for salary entries by Employee ID or exact Salary value.
15. **Sort Salaries:** Enables sorting the salary data by Employee ID (alphabetical) or Salary value (numerical), in ascending or descending order.

**General File Operations:**

16. **Delete Data File:** Allows you to choose and delete one of the data files (`employees.json`, `departments.json`, `salaries.json`). It prompts for confirmation before deletion and re-creates an empty file afterwards.
17. **Exit:** Closes the application.

## Getting Started

### Prerequisites

* **Java Development Kit (JDK):** Make sure you have Java installed on your system. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/javase-downloads.html) or through your operating system's package manager.
* **Maven:** This project uses Maven for dependency management and building. You can download and install it from [Apache Maven's website](https://maven.apache.org/download.cgi).

### Running the Application

1.  **Clone the Repository:** If you have the project files in a repository, clone it to your local machine.
2.  **Navigate to the Project Directory:** Open your terminal or command prompt and navigate to the root directory of the project (where the `pom.xml` file is located).
3.  **Build and Run:** Execute the following Maven command:

    ```bash
    mvn clean compile exec:java
    ```

    This command will:
    * `clean`: Remove any previously built files.
    * `compile`: Compile the Java source code.
    * `exec:java`: Execute the main class (`com.yourpackage.App`) defined in the `pom.xml`.

4.  **Follow the Menu:** Once the application starts, you will see the main menu in the console. Enter the number corresponding to the action you want to perform and follow the prompts.