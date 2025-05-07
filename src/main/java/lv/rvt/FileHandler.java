package lv.rvt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class FileHandler {

    private static final String EMPLOYEES_FILE = "employees.json";
    private static final String DEPARTMENTS_FILE = "departments.json";
    private static final String SALARIES_FILE = "salaries.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    public final Scanner scanner = new Scanner(System.in);

    public void createFiles() {
        createFileIfNotExists(EMPLOYEES_FILE);
        createFileIfNotExists(DEPARTMENTS_FILE);
        createFileIfNotExists(SALARIES_FILE);
    }

     private void createFileIfNotExists(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                // Initialize with an empty JSON array
                objectMapper.writeValue(file, new ArrayList<>());
                System.out.println("Created file: " + filename);
            } catch (IOException e) {
                System.err.println("Error creating file " + filename + ": " + e.getMessage());
            }
        } else {
            System.out.println("File already exists: " + filename);
        }
    }

     public List<Employee> readEmployeeData() {
        try {
            File file = new File(EMPLOYEES_FILE);
            // TypeReference is used to tell Jackson the specific type of the List
            return objectMapper.readValue(file, new TypeReference<List<Employee>>() {});
        } catch (IOException e) {
            System.err.println("Error reading employee data from " + EMPLOYEES_FILE + ": " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public void saveEmployeeData(List<Employee> employees) {
        try {
            objectMapper.writeValue(new File(EMPLOYEES_FILE), employees);
            System.out.println("Employee data saved to " + EMPLOYEES_FILE);
        } catch (IOException e) {
            System.err.println("Error saving employee data to " + EMPLOYEES_FILE + ": " + e.getMessage());
        }
    }

    public void addEmployee() {
        System.out.println("\nEnter details for the new employee:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Department: ");
        String department = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character left by nextDouble()

        Employee newEmployee = new Employee(name, id, department, salary);
        List<Employee> employees = readEmployeeData();
        employees.add(newEmployee);
        saveEmployeeData(employees);
        System.out.println("New employee added successfully!");
    }

    public void changeEmployeeData() {
        List<Employee> employees = readEmployeeData();
        if (employees.isEmpty()) {
            System.out.println("No employees found to update.");
            return;
        }

        System.out.println("\nCurrent employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.print("Enter the ID of the employee you want to update: ");
        String employeeIdToUpdate = scanner.nextLine();

        List<Employee> foundEmployees = employees.stream()
                .filter(employee -> employee.getId().equals(employeeIdToUpdate))
                .collect(Collectors.toList());

        if (foundEmployees.isEmpty()) {
            System.out.println("Employee with ID '" + employeeIdToUpdate + "' not found.");
            return;
        }

        if (foundEmployees.size() > 1) {
            System.out.println("Warning: Multiple employees found with the same ID. Please ensure IDs are unique.");
            // For simplicity, we'll update the first one found. A more robust solution might be needed.
        }

        Employee employeeToUpdate = foundEmployees.get(0);

        System.out.println("\nWhat information would you like to update for employee with ID '" + employeeIdToUpdate + "'?");
        System.out.println("1. Name");
        System.out.println("2. Department");
        System.out.println("3. Salary");
        System.out.print("Enter your choice (1-3): ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter new name: ");
                employeeToUpdate.setName(scanner.nextLine());
                break;
            case "2":
                System.out.print("Enter new department: ");
                employeeToUpdate.setDepartment(scanner.nextLine());
                break;
            case "3":
                System.out.print("Enter new salary: ");
                try {
                    double newSalary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    employeeToUpdate.setSalary(newSalary);
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid salary format. Update failed.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid choice. Update failed.");
                return;
        }

        saveEmployeeData(employees);
        System.out.println("Employee with ID '" + employeeIdToUpdate + "' updated successfully!");
    }

    public void deleteEmployeeData() {
        List<Employee> employees = readEmployeeData();
        if (employees.isEmpty()) {
            System.out.println("No employees found to delete.");
            return;
        }

        System.out.println("\nCurrent employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.print("Enter the ID of the employee you want to delete: ");
        String employeeIdToDelete = scanner.nextLine();

        List<Employee> updatedEmployees = employees.stream()
                .filter(employee -> !employee.getId().equals(employeeIdToDelete))
                .collect(Collectors.toList());

        if (updatedEmployees.size() < employees.size()) {
            saveEmployeeData(updatedEmployees);
            System.out.println("Employee with ID '" + employeeIdToDelete + "' deleted successfully!");
        } else {
            System.out.println("Employee with ID '" + employeeIdToDelete + "' not found.");
        }
    }

    public void searchEmployeeData() {
        List<Employee> employees = readEmployeeData();
        if (employees.isEmpty()) {
            System.out.println("No employees found to search.");
            return;
        }

        System.out.println("\nSearch employees by:");
        System.out.println("1. Name");
        System.out.println("2. ID");
        System.out.println("3. Department");
        System.out.println("4. Salary");
        System.out.print("Enter your choice (1-4): ");
        String searchChoice = scanner.nextLine();

        System.out.print("Enter the search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<Employee> searchResults = new ArrayList<>();

        switch (searchChoice) {
            case "1":
                searchResults = employees.stream()
                        .filter(emp -> emp.getName().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList());
                break;
            case "2":
                searchResults = employees.stream()
                        .filter(emp -> emp.getId().toLowerCase().equals(searchTerm))
                        .collect(Collectors.toList());
                break;
            case "3":
                searchResults = employees.stream()
                        .filter(emp -> emp.getDepartment().toLowerCase().contains(searchTerm))
                        .collect(Collectors.toList());
                break;
            case "4":
                try {
                    double searchSalary = Double.parseDouble(searchTerm);
                    searchResults = employees.stream()
                            .filter(emp -> emp.getSalary() == searchSalary)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary format.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid search criteria.");
                return;
        }

        if (searchResults.isEmpty()) {
            System.out.println("No employees found matching your search criteria.");
        } else {
            System.out.println("\nSearch results:");
            searchResults.forEach(System.out::println);
        }
    }

        public void sortEmployeeData() {
        List<Employee> employees = readEmployeeData();
        if (employees.isEmpty()) {
            System.out.println("No employees found to sort.");
            return;
        }

        System.out.println("\nSort employees by:");
        System.out.println("1. Name");
        System.out.println("2. ID");
        System.out.println("3. Department");
        System.out.println("4. Salary");
        System.out.print("Enter your choice (1-4): ");
        String sortChoice = scanner.nextLine();

        System.out.println("Sort order:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Enter your choice (1-2): ");
        String orderChoice = scanner.nextLine();
        boolean ascending = orderChoice.equals("1");

        Comparator<Employee> comparator = null;

        switch (sortChoice) {
            case "1":
                comparator = Comparator.comparing(Employee::getName);
                break;
            case "2":
                comparator = Comparator.comparing(Employee::getId);
                break;
            case "3":
                comparator = Comparator.comparing(Employee::getDepartment);
                break;
            case "4":
                comparator = Comparator.comparingDouble(Employee::getSalary);
                break;
            default:
                System.out.println("Invalid sort criteria.");
                return;
        }

        if (comparator != null) {
            if (!ascending) {
                comparator = comparator.reversed();
            }
            employees.sort(comparator);
            System.out.println("\nSorted employee data:");
            employees.forEach(System.out::println);
        }
    }

    // Methods for Departments
    public List<String> readDepartmentData() {
        try {
            File file = new File(DEPARTMENTS_FILE);
            return objectMapper.readValue(file, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            System.err.println("Error reading department data from " + DEPARTMENTS_FILE + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveDepartmentData(List<String> departments) {
        try {
            objectMapper.writeValue(new File(DEPARTMENTS_FILE), departments);
            System.out.println("Department data saved to " + DEPARTMENTS_FILE);
        } catch (IOException e) {
            System.err.println("Error saving department data to " + DEPARTMENTS_FILE + ": " + e.getMessage());
        }
    }

    public void addDepartment() {
        System.out.print("Enter the name of the new department: ");
        String departmentName = scanner.nextLine();
        List<String> departments = readDepartmentData();
        departments.add(departmentName);
        saveDepartmentData(departments);
        System.out.println("Department '" + departmentName + "' added successfully!");
    }

    public void changeDepartmentData() {
        List<String> departments = readDepartmentData();
        if (departments.isEmpty()) {
            System.out.println("No departments found to update.");
            return;
        }

        System.out.println("\nCurrent departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }

        System.out.print("Enter the number of the department you want to update: ");
        try {
            int indexToUpdate = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (indexToUpdate >= 1 && indexToUpdate <= departments.size()) {
                String oldDepartmentName = departments.get(indexToUpdate - 1);
                System.out.print("Enter the new name for department '" + oldDepartmentName + "': ");
                String newDepartmentName = scanner.nextLine();
                departments.set(indexToUpdate - 1, newDepartmentName);
                saveDepartmentData(departments);
                System.out.println("Department '" + oldDepartmentName + "' updated to '" + newDepartmentName + "' successfully!");
            } else {
                System.out.println("Invalid department number.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
        }
    }

    public void deleteDepartmentData() {
        List<String> departments = readDepartmentData();
        if (departments.isEmpty()) {
            System.out.println("No departments found to delete.");
            return;
        }

        System.out.println("\nCurrent departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }

        System.out.print("Enter the number of the department you want to delete: ");
        try {
            int indexToDelete = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (indexToDelete >= 1 && indexToDelete <= departments.size()) {
                String deletedDepartment = departments.remove(indexToDelete - 1);
                saveDepartmentData(departments);
                System.out.println("Department '" + deletedDepartment + "' deleted successfully!");
            } else {
                System.out.println("Invalid department number.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume invalid input
        }
    }

    public void searchDepartmentData() {
        List<String> departments = readDepartmentData();
        if (departments.isEmpty()) {
            System.out.println("No departments found to search.");
            return;
        }

        System.out.print("Enter the department name to search for (partial matches allowed): ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<String> searchResults = departments.stream()
                .filter(dept -> dept.toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        if (searchResults.isEmpty()) {
            System.out.println("No departments found matching your search criteria.");
        } else {
            System.out.println("\nSearch results:");
            searchResults.forEach(System.out::println);
        }
    }

    public void sortDepartmentData() {
        List<String> departments = readDepartmentData();
        if (departments.isEmpty()) {
            System.out.println("No departments found to sort.");
            return;
        }

        System.out.println("\nSort departments by:");
        System.out.println("1. Name");
        System.out.print("Enter your choice (1): ");
        scanner.nextLine(); // Consume the newline

        System.out.println("Sort order:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Enter your choice (1-2): ");
        String orderChoice = scanner.nextLine();
        boolean ascending = orderChoice.equals("1");

        departments.sort(ascending ? String::compareTo : String::compareToIgnoreCase); // Case-insensitive sort

        if (!ascending) {
            Collections.reverse(departments);
        }

        System.out.println("\nSorted department data:");
        departments.forEach(System.out::println);
    }

    // Methods for Salaries
    public List<Salary> readSalaryData() {
        try {
            File file = new File(SALARIES_FILE);
            return objectMapper.readValue(file, new TypeReference<List<Salary>>() {});
        } catch (IOException e) {
            System.err.println("Error reading salary data from " + SALARIES_FILE + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void saveSalaryData(List<Salary> salaries) {
        try {
            objectMapper.writeValue(new File(SALARIES_FILE), salaries);
            System.out.println("Salary data saved to " + SALARIES_FILE);
        } catch (IOException e) {
            System.err.println("Error saving salary data to " + SALARIES_FILE + ": " + e.getMessage());
        }
    }

    public void addSalary() {
        System.out.print("Enter the ID of the employee for this salary: ");
        String employeeId = scanner.nextLine();
        System.out.print("Enter the salary for employee ID '" + employeeId + "': ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Salary newSalary = new Salary(employeeId, salary);
        List<Salary> salaries = readSalaryData();
        salaries.add(newSalary);
        saveSalaryData(salaries);
        System.out.println("Salary for employee ID '" + employeeId + "' added successfully!");
    }

    public void changeSalaryData() {
        List<Salary> salaries = readSalaryData();
        if (salaries.isEmpty()) {
            System.out.println("No salary data found to update.");
            return;
        }

        System.out.println("\nCurrent salary data:");
        salaries.forEach(System.out::println);

        System.out.print("Enter the ID of the employee whose salary you want to update: ");
        String employeeIdToUpdate = scanner.nextLine();

        List<Salary> foundSalaries = salaries.stream()
                .filter(salary -> salary.getEmployeeId().equals(employeeIdToUpdate))
                .collect(Collectors.toList());

        if (foundSalaries.isEmpty()) {
            System.out.println("Salary data not found for employee ID '" + employeeIdToUpdate + "'.");
            return;
        }

        if (foundSalaries.size() > 1) {
            System.out.println("Warning: Multiple salary entries found for the same employee ID. Please ensure IDs are consistent.");
            // For simplicity, we'll update the first one found.
        }

        Salary salaryToUpdate = foundSalaries.get(0);

        System.out.print("Enter the new salary for employee ID '" + employeeIdToUpdate + "': ");
        try {
            double newSalary = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            salaryToUpdate.setSalary(newSalary);
            saveSalaryData(salaries);
            System.out.println("Salary for employee ID '" + employeeIdToUpdate + "' updated successfully!");
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid salary format. Update failed.");
        }
    }

    public void deleteSalaryData() {
        List<Salary> salaries = readSalaryData();
        if (salaries.isEmpty()) {
            System.out.println("No salary data found to delete.");
            return;
        }

        System.out.println("\nCurrent salary data:");
        salaries.forEach(System.out::println);

        System.out.print("Enter the ID of the employee whose salary you want to delete: ");
        String employeeIdToDelete = scanner.nextLine();

        List<Salary> updatedSalaries = salaries.stream()
                .filter(salary -> !salary.getEmployeeId().equals(employeeIdToDelete))
                .collect(Collectors.toList());

        if (updatedSalaries.size() < salaries.size()) {
            saveSalaryData(updatedSalaries);
            System.out.println("Salary data for employee ID '" + employeeIdToDelete + "' deleted successfully!");
        } else {
            System.out.println("No salary data found for employee ID '" + employeeIdToDelete + "'.");
        }
    }

    public void searchSalaryData() {
        List<Salary> salaries = readSalaryData();
        if (salaries.isEmpty()) {
            System.out.println("No salary data found to search.");
            return;
        }

        System.out.println("\nSearch salaries by:");
        System.out.println("1. Employee ID");
        System.out.println("2. Salary Value");
        System.out.print("Enter your choice (1-2): ");
        String searchChoice = scanner.nextLine();

        System.out.print("Enter the search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<Salary> searchResults = new ArrayList<>();

        switch (searchChoice) {
            case "1":
                searchResults = salaries.stream()
                        .filter(salary -> salary.getEmployeeId().toLowerCase().equals(searchTerm))
                        .collect(Collectors.toList());
                break;
            case "2":
                try {
                    double searchSalary = Double.parseDouble(searchTerm);
                    searchResults = salaries.stream()
                            .filter(salary -> salary.getSalary() == searchSalary)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary format.");
                    return;
                }
                break;
            default:
                System.out.println("Invalid search criteria.");
                return;
        }

        if (searchResults.isEmpty()) {
            System.out.println("No salaries found matching your search criteria.");
        } else {
            System.out.println("\nSearch results:");
            searchResults.forEach(System.out::println);
        }
    }

    public void sortSalaryData() {
        List<Salary> salaries = readSalaryData();
        if (salaries.isEmpty()) {
            System.out.println("No salary data found to sort.");
            return;
        }

        System.out.println("\nSort salaries by:");
        System.out.println("1. Employee ID");
        System.out.println("2. Salary Value");
        System.out.print("Enter your choice (1-2): ");
        String sortChoice = scanner.nextLine();

        System.out.println("Sort order:");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Enter your choice (1-2): ");
        String orderChoice = scanner.nextLine();
        boolean ascending = orderChoice.equals("1");

        Comparator<Salary> comparator = null;

        switch (sortChoice) {
            case "1":
                comparator = Comparator.comparing(Salary::getEmployeeId);
                break;
            case "2":
                comparator = Comparator.comparingDouble(Salary::getSalary);
                break;
            default:
                System.out.println("Invalid sort criteria.");
                return;
        }

        if (comparator != null) {
            if (!ascending) {
                comparator = comparator.reversed();
            }
            salaries.sort(comparator);
            System.out.println("\nSorted salary data:");
            salaries.forEach(System.out::println);
        }
    }


    public void deleteFile() {
        System.out.println("\nWhich file do you want to delete?");
        System.out.println("1. Employees (" + EMPLOYEES_FILE + ")");
        System.out.println("2. Departments (" + DEPARTMENTS_FILE + ")");
        System.out.println("3. Salaries (" + SALARIES_FILE + ")");
        System.out.print("Enter your choice (1-3): ");
        String choice = scanner.nextLine();

        String filenameToDelete = "";
        switch (choice) {
            case "1":
                filenameToDelete = EMPLOYEES_FILE;
                break;
            case "2":
                filenameToDelete = DEPARTMENTS_FILE;
                break;
            case "3":
                filenameToDelete = SALARIES_FILE;
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        File fileToDelete = new File(filenameToDelete);
        if (fileToDelete.exists()) {
            System.out.print("Are you sure you want to delete '" + filenameToDelete + "'? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();
            if (confirmation.equals("yes")) {
                if (fileToDelete.delete()) {
                    System.out.println("File '" + filenameToDelete + "' deleted successfully!");
                    // Optionally, you might want to re-create an empty file
                    createFileIfNotExists(filenameToDelete);
                } else {
                    System.err.println("Failed to delete file '" + filenameToDelete + "'.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("File '" + filenameToDelete + "' does not exist.");
        }
    }

    public void mainMenu() {
        String choice;
        do {
            System.out.println("\nEmployee Data Management Application");
            System.out.println("------------------------------------");
            System.out.println("1. Add New Employee");
            System.out.println("2. Change Employee Data");
            System.out.println("3. Delete Employee Data");
            System.out.println("4. Search Employees");
            System.out.println("5. Sort Employees");
            System.out.println("------------------------------------");
            System.out.println("6. Add New Department");
            System.out.println("7. Change Department Data");
            System.out.println("8. Delete Department Data");
            System.out.println("9. Search Departments");
            System.out.println("10. Sort Departments");
            System.out.println("------------------------------------");
            System.out.println("11. Add New Salary Entry");
            System.out.println("12. Change Salary Data");
            System.out.println("13. Delete Salary Data");
            System.out.println("14. Search Salaries");
            System.out.println("15. Sort Salaries");
            System.out.println("------------------------------------");
            System.out.println("16. Delete Data File");
            System.out.println("17. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEmployee();
                    break;
                case "2":
                    changeEmployeeData();
                    break;
                case "3":
                    deleteEmployeeData();
                    break;
                case "4":
                    searchEmployeeData();
                    break;
                case "5":
                    sortEmployeeData();
                    break;
                case "6":
                    addDepartment();
                    break;
                case "7":
                    changeDepartmentData();
                    break;
                case "8":
                    deleteDepartmentData();
                    break;
                case "9":
                    searchDepartmentData();
                    break;
                case "10":
                    sortDepartmentData();
                    break;
                case "11":
                    addSalary();
                    break;
                case "12":
                    changeSalaryData();
                    break;
                case "13":
                    deleteSalaryData();
                    break;
                case "14":
                    searchSalaryData();
                    break;
                case "15":
                    sortSalaryData();
                    break;
                case "16":
                    deleteFile();
                    break;
                case "17":
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!choice.equals("17"));
    }
}
