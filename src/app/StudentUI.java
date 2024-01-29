package app;

import controllers.CampStudentController;
import controllers.EnquiryStudentController;
import controllers.IOManager;
import controllers.LoginUserController;
import entity.*;

/**
 * Page for a student login, with only student specific functions
 * @version 26/11/2023
 */
public class StudentUI
{

	/**
	 * Main page for student
	 * @param currentUser student that is currently logged in
	 */
	public static void studentPage(User currentUser)
	{
		
		//for user funcs
		LoginUserController luc = new LoginUserController();
		
		//for camp funcs
		CampDatabase campDB = new CampDatabase("camps.ser");
		
		//for enquiry funcs
		EnquiryDatabase enqDB = new EnquiryDatabase("enquiries.ser");
		
		Student currentStudent = new Student(currentUser.getUserID(), currentUser.getPassword(), 
				currentUser.getFaculty(), currentUser.getFirstLogin(), currentUser.getUserType());
		
		int choice;
		do {
			System.out.println("\n================ Student UI Menu ================");
			System.out.println("1. Change Password");
			System.out.println("2. Camp Affairs");
			System.out.println("3. Enquiry Affairs");
			System.out.println("4. Exit");
			System.out.println("=================================================");
			System.out.print("Enter your choice: ");
			choice = IOManager.readInt();
			switch (choice)
			{
				case 1:
					luc.changePassword(currentUser);
					break;
					
				case 2:
					campFunctions(campDB, currentStudent);
					break;
				
				case 3:
					enquiryFunctions(enqDB, campDB, currentStudent);
					break;
					
				case 4:
					System.out.println("\nExiting Application...");
					System.out.println();
					System.exit(0);
					
				default:
					System.out.println("\nInvalid input! Please try again.");
					break;
			}
		} while(choice!= 4);
	}

	/**
	 * Page for student if they want to access camp related methods
	 * @param campDB database of all camps
	 * @param currentStudent student that is currently logged in
	 */
	public static void campFunctions(CampDatabase campDB, Student currentStudent)
	{
			
			int choice;
			
			CampStudentController csc = new CampStudentController(campDB);
			
			do
			{
				System.out.println("\n================= Camp Menu =====================");
				System.out.println("1. View Your Faculty's Camps");
				System.out.println("2. Register for a Camp");
				System.out.println("3. Withdraw from a Camp");
				System.out.println("4. View Your Registered Camps");
				System.out.println("5. Back");
				System.out.println("=================================================");
				System.out.print("Enter your choice: ");
				choice = IOManager.readInt();

				switch (choice)
				{
					case 1:
						csc.viewCamps(currentStudent);
						break;
						
					case 2:
						csc.registerCamps(currentStudent);
						break;	

					case 3:
						csc.withdraw(currentStudent);
						break;
						
					case 4:
						csc.viewOwnCamps(currentStudent);
						break;	

					case 5:
						//System.out.println("=================================================");
						return;
						
					default:
						System.out.println("\nInvalid input! Please try again.");
						break;
				}
			} while(choice != 5);
		}

	/**
	 * Page for student if they want to access enquiry related methods
	 * @param enqDB database of all enquiries
	 * @param campDB database of all camps
	 * @param currentStudent student that is currently logged in
	 */
	public static void enquiryFunctions(EnquiryDatabase enqDB, CampDatabase campDB, Student currentStudent)
	{

			int choice;
			EnquiryStudentController esc = new EnquiryStudentController(enqDB, campDB);

			do
			{
				System.out.println("\n================ Enquiry Menu ===================");
				System.out.println("1. View Your Enquiries");
				System.out.println("2. Submit a New Enquiry");
				System.out.println("3. Edit an Existing Enquiry");
				System.out.println("4. Delete an Existing Enquiry");
				System.out.println("5. Back");
				System.out.println("=================================================");
				System.out.print("Enter your choice: ");
				choice = IOManager.readInt();
				
				switch (choice)
				{
					case 1:
						esc.viewEnquiry(currentStudent);
						break;

					case 2:
						esc.submitEnquiry(currentStudent);
						break;
						
					case 3:
						esc.editEnquiry(currentStudent);
						break;
					
					case 4:
						esc.deleteEnquiry(currentStudent);
						break;
					
					case 5:
						return;	
					
					default:
						System.out.println("\nInvalid input! Please try again.");
						break;
				}
			} while (choice != 4);
	}
}