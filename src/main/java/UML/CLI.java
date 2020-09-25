/*
    Author: Chris, Tyler, Drew, Dominic, Cory. 
    Date: 09/24/2020
    Purpose: 
 */
import java.io.Console;
import java.util.Scanner;

public class CLI {
    
    public CLI()
    {
        printHeader();
        Scanner s = new Scanner(System.in);
        System.out.print("Schwifty-UML> ");
    }

    private void printHeader()
    {
       System.out.println("  _____      _             _  __ _");
       System.out.println(" /  ___|    | |           (_)/ _| |");
       System.out.println(" \\ `--.  ___| |____      ___| |_| |_ _   _"); 
       System.out.println("  `--. \\/ __| '_ \\ \\ /\\ / / |  _| __| | | |");
       System.out.println(" /\\__/ / (__| | | \\ V  V /| | | | |_| |_| |");
       System.out.println(" \\____/ \\___|_| |_|\\_/\\_/ |_|_|  \\__|\\__, |");
       System.out.println("                                      __/ |");
       System.out.println("                                     |___ /"); 
    }
}
