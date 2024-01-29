package controllers;

import java.util.List;

import entity.User;
import entity.UserDatabase;

/**
 * Controller specifically for methods pertaining to User object/database manipulation
 * @version 26/11/2023
 */
public class LoginUserController {
	
	/**
	 * Checks through the user database to see if the inputted username and password are valid
	 * @return a User object of the user who is currently logging in
	 */
	public User login() {
		try{
			//username
			List<User> users = UserDatabase.readUsers("src/user_list.csv");
			
			System.out.println("\n================= User Login =================");
			System.out.println("Please enter your username");
			System.out.print("Enter username here: ");
			String username = IOManager.readString();
			username = username.toUpperCase();
			
			//password
			System.out.println();
			System.out.println("Please enter your password");
			System.out.print("Enter password here: ");
			String password = IOManager.readString();
			
			//check through DB to see if data is valid
			for(User user: users) {
				String name = user.getUserID();
				if(name.equals(username)) {
					String pw = user.getPassword();
					if(pw.equals(password)) {
						System.out.println();
						System.out.println("Successfully logged in!");
						System.out.println("==============================================");
						UserDatabase.saveUsers("src/user_list.csv", users);
						return user;
					}
				}
			}
			System.out.println();
			System.out.println("Wrong username or password!");
			System.out.println("==============================================");
		} catch(Exception e) {
			System.out.println("\nSomething went wrong");
			System.out.println("==============================================");
		}
		
		return login();
	}
	
	/**
	 * Changes the password of a User and saves it to the User database
	 * @param currentuser User that is currently logged in
	 */
	public void changePassword(User currentuser) {

		try {
			List<User> users = UserDatabase.readUsers("src/user_list.csv");
			
			for(User user: users) {
				if(user.getUserID().equals(currentuser.getUserID())){
					currentuser = user;
				}
			}
			
			String password1, password2;
			do {
				do {
					System.out.println("\n=========== Password Change ===========");
					System.out.println("Please enter a new password");
					System.out.print("Enter password here: ");
					password1 = IOManager.readString();

					if (password1.equals("password") || currentuser.getPassword().equals(password1)) {
						System.out.println("Invalid password, please enter a different password");
						System.out.println();
						continue;
					}

					break;
				} while (true);

				System.out.println();
				System.out.println("Please enter your new password again");
				System.out.print("Enter password here: ");
				password2 = IOManager.readString();

				if (password1.equals(password2)) {
					currentuser.setPassword(password2);
					UserDatabase.saveUsers("src/user_list.csv", users);
					System.out.println();
					System.out.println("Successfully changed password!");
					break;
				} else {
					System.out.println("\nPasswords do not match. Please try again.");
					System.out.println();
				}
			} while (true);
		} catch (Exception e) {
			System.out.println("\nSomething went wrong");
		}
	}
}
