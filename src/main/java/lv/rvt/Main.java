package lv.rvt;

import java.util.*;
import java.io.*;

public class Main 
{
    public static void main( String[] args )
    {
        Employee myEmployee = new Employee("Kirill", "1", "Java developement", 10000);


        System.out.println(myEmployee.getName());

        myEmployee.createNewJson("Employee");
    }
}