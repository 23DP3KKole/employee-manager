package lv.rvt;

import java.util.*;
import java.io.*;

public class Main 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        Employee myEmployee = new Employee("Kirill", "1", "Java developement", 10000);

        Employee.consoleStart();

        Employee.consoleChoose();
        String optionChosen = scanner.nextLine();
        
        myEmployee.dataHandling(optionChosen, myEmployee);
    }
}