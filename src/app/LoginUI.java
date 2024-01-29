package app;

import controllers.LoginUserController;
import entity.User;
import entity.UserType;

/**
 * First page after HomeUI
 * @version 26/11/2023
 */
public class LoginUI {

	/**
	 * main display page when logging in
	 * @param args for main method
	 */
	public static void main(String[] args) {
		LoginUserController login = new LoginUserController();

		User currentUser = login.login();

		if (currentUser.getFirstLogin()) {
			System.out.println("\n=============== First Time Login Detected ===============");
			System.out.println("Welcome! As a first-time user, please change your password.");
			login.changePassword(currentUser);
			currentUser.setFirstLogin(false);
			System.out.println("=========================================================");
		}

		UserType userType = currentUser.getUserType();
		switch (userType) {
			case STUDENT:
				StudentUI.studentPage(currentUser);
				break;
			case CCM:
				CcmUI.start(currentUser);
				break;
			case STAFF:
				StaffUI.start(currentUser);
				break;
		}
	}
}
