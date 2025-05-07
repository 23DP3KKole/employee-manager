package lv.rvt;

import java.util.*;
import java.io.*;

public class App 
{
    public static void main( String[] args )
    {
        FileHandler fileHandler = new FileHandler();
        fileHandler.createFiles();
        fileHandler.mainMenu();
        fileHandler.scanner.close(); // Close the scanner when done
    }
}