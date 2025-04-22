package lv.rvt;

import java.io.File;
import java.util.Scanner;

public class Employee {
    private String name;
    private String ID;
    private String department;
    private double salary;

    public Employee(String employeeName, String employeeID, String employeeDepartment, double employeeSalary){
        this.name = employeeName;
        this.ID = employeeID;
        this.department = employeeDepartment;
        this.salary = employeeSalary;
    }

    String optionChosen;

    static Scanner scanner = new Scanner(System.in);

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.ID;
    }

    public String getDepartment(){
        return this.department;
    }

    public double getSalary(){
        return this.salary;
    }

    public void createNewJson(String fileType){

        if (fileType == "E"){
            File employeeFile = new File("employeeList.json");
        } else if (fileType == "D"){
            File departmentFile = new File("departmentFile.json");
        } else if (fileType == "S"){
            File salaryHistoryfile = new File("salaryHistory.json");
        }
    }

    static public void consoleStart(){
        System.out.println("\r\n" + //
                        "  ______                 _                         __  __                             \r\n" + //
                        " |  ____|               | |                       |  \\/  |                            \r\n" + //
                        " | |__   _ __ ___  _ __ | | ___  _   _  ___  ___  | \\  / | __ _ _ __   __ _  ___ _ __ \r\n" + //
                        " |  __| | '_ ` _ \\| '_ \\| |/ _ \\| | | |/ _ \\/ _ \\ | |\\/| |/ _` | '_ \\ / _` |/ _ \\ '__|\r\n" + //
                        " | |____| | | | | | |_) | | (_) | |_| |  __/  __/ | |  | | (_| | | | | (_| |  __/ |   \r\n" + //
                        " |______|_| |_| |_| .__/|_|\\___/ \\__, |\\___|\\___| |_|  |_|\\__,_|_| |_|\\__, |\\___|_|   \r\n" + //
                        "                  | |             __/ |                                __/ |          \r\n" + //
                        "                _ |_|         ___|___/            ____                |___/           \r\n" + //
                        "               | |           |  ____|            / __ \\                               \r\n" + //
                        "               | |__  _   _  | |__  _   _  ___  | |  | |_ __                          \r\n" + //
                        "               | '_ \\| | | | |  __|| | | |/ _ \\ | |  | | '_ \\                         \r\n" + //
                        "               | |_) | |_| | | |___| |_| |  __/ | |__| | | | |                        \r\n" + //
                        "               |_.__/ \\__, | |______\\__, |\\___|  \\____/|_| |_|                        \r\n" + //
                        "                       __/ |         __/ |                                            \r\n" + //
                        "                      |___/         |___/                                             \r\n" + //
                        "");
    }

    static public void consoleChoose(){
        System.out.println("What do you want to do?\n"+
        "Create new file (1)\n" +
        "Change data in file (2)\n" +
        "Delete a file (3)\n"+
        "Search a file (4)\n\n"+
        "Choose an option: ");
    }

    public void dataHandling(String optionChosen, Employee myEmployee){
        switch (optionChosen){
            case "1":
            System.out.println("what file do you want?(E,D,S)");
            String fileType = scanner.nextLine();
            myEmployee.createNewJson(fileType);
            System.out.println("new file created\n");
            System.out.println("exit or continue?(exit)\n");

            optionChosen = scanner.nextLine();
            case "2":
            System.out.println("data changed");
            break;
            case "3":
            System.out.println("delete file");
            break;
            case "4":
            System.out.println("search file");
            break;
            case "exit":
            break;
        }
    }
}