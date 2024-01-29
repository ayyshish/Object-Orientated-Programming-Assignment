package controllers;

import java.util.Scanner;

/**
 * General IOManager for easier error handling when reading inputs
 * @version 26/11/2023
 */
public class IOManager {

	/**
	 * Scanner that is persistent while the application is running
	 */
	private static final Scanner scanner = new Scanner(System.in);
	
	/**
	 * Reads a string input by the user
	 * @return string inputted by the user
	 */
	public static String readString() {
		try {
            String str = scanner.nextLine();
            if(str.isEmpty()){
                throw new RuntimeException();
            }
            return str;
        } catch (Exception exception) {
            System.out.println("Invalid input! Please try again.");
        }
        return readString();
	}
	
	/**
	 * Reads an int input by the user
	 * @return int inputted by the user
	 */
	public static int readInt() {
		try {
            int input = Integer.parseInt(scanner.nextLine());

            return input;
        } catch (Exception exception) {
            //exception.printStackTrace();
            System.out.println("Invalid input! Please try again.");
        }
        return readInt();
	}
}