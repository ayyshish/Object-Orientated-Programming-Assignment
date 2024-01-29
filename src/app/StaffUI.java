package app;

//import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controllers.CampStaffController;
import controllers.EnquiryStaffController;
import controllers.IOManager;
import controllers.LoginUserController;
import controllers.SuggestionStaffController;
import controllers.ReportStaffController;
import entity.CCM;
import entity.CCMDatabase;
import entity.Camp;
import entity.CampDatabase;
import entity.EnquiryDatabase;
import entity.Staff;
import entity.Suggestion;
import entity.SuggestionDatabase;
import entity.User;

/**
 * Page for a staff login, with only staff specific functions
 * @version 26/11/2023
 */
public class StaffUI {

	/**
	 * Main page for staff
	 * @param currentUser staff that is currently logged in
	 */
	public static void start(User currentUser) {
		// for login funcs
		LoginUserController luc = new LoginUserController();

		// for camp funcs
		CampDatabase campDB = new CampDatabase("camps.ser");

		// for enquiry funcs
		EnquiryDatabase enqDB = new EnquiryDatabase("enquiries.ser");

		// for suggestion funcs
		SuggestionDatabase suggDB = new SuggestionDatabase("suggestions.ser");

		Staff currentStaff = new Staff(currentUser.getUserID(), currentUser.getPassword(), 
				currentUser.getFaculty(), currentUser.getFirstLogin(), currentUser.getUserType());

		int choice;
		do {
			System.out.println("\n================ Staff UI Menu =================");
			System.out.println("1. Change Password");
			System.out.println("2. Camp Affairs");
			System.out.println("3. Enquiry Affairs");
			System.out.println("4. Suggestion Affairs");
			System.out.println("5. Generate Report");
			System.out.println("6. Exit");
			System.out.println("================================================");
			System.out.print("Enter your choice: ");

			choice = IOManager.readInt();
			
			List<Camp> campList = campDB.getCampsByInCharge(currentStaff.getUserID());
			
			switch (choice) {
			case 1: // change password
				luc.changePassword(currentUser);
				break;

			case 2: // go to camp functions
				campFunctions(campDB, currentStaff);
				break;
			
			case 3: // go to enquiry functions
				enquiryFunctions(enqDB, campDB, campList);
				break;
			
			case 4: // go to suggestion functions
				suggestionFunctions(suggDB, campDB, currentUser.getUserID());
				break;

			case 5: // generate report
				reportFunctions(campList);
				break;

			case 6: // exit
				System.out.println("\nExiting Application...");
				System.out.println();
				System.exit(0);
				break;

			default: // invalid choice
				System.out.println("\nInvalid input! Please try again.");
				break;
			}

		} while (choice != 6);
	}

	/**
	 * Page for staff if they want to access camp related methods
	 * @param campDB database of all camps
	 * @param currentStaff staff who is currently logged in
	 */
    public static void campFunctions(CampDatabase campDB, Staff currentStaff) {
    	
    	int choice;
    	
    	CampStaffController csc = new CampStaffController(campDB);
    	
		do {
			System.out.println("\n===================== Camp Menu =====================");
			System.out.println("1. View Your Camps");
			System.out.println("2. Toggle Camp Visibility");
			System.out.println("3. Edit Camp");
			System.out.println("4. Delete Camp");
			System.out.println("5. Create New Camp");
			System.out.println("6. View All Camps");
			System.out.println("7. Back");
			System.out.println("=====================================================");
			System.out.print("Enter your choice: ");
			choice = IOManager.readInt();

			List<Camp> campList = campDB.getCampsByInCharge(currentStaff.getUserID());

			switch (choice) {
			case 1: // view your camps
				csc.viewOwnCamps(campList);
				break;
				
			case 2: // toggle camp visibility
				csc.toggleCampVisibility(campList);
				break;	
				

			case 3: // edit camp
				csc.editCamp(campList);
				break;
				
			case 4: // delete camp
				csc.deleteCamp(campList);
				break;	

			case 5: // create new camp
				csc.createNewCamp(currentStaff);
				break;

			case 6: // view all camps
				csc.viewAllCamps();
				break;
				
			case 7:
				return;
				
			default:
				System.out.println("Invalid input!");
				break;
			}
		} while(choice != 7);
	}

    /**
     * Page for staff if they want to access enquiry related methods
     * @param enqDB database of all enquiries
     * @param campDB database of all camps
     * @param campList list of camps created by the current staff that is logged in
     */
	public static void enquiryFunctions(EnquiryDatabase enqDB, CampDatabase campDB, List<Camp> campList) {

		int choice;
		EnquiryStaffController esc = new EnquiryStaffController(enqDB, campDB);

		do {
			System.out.println("\n================ Enquiry Menu ===================");
			System.out.println("1. View Enquiries");
			System.out.println("2. Reply Enquiries");
			System.out.println("3. Back");
			System.out.println("=================================================");
			System.out.print("Enter your choice: ");
			choice = IOManager.readInt();
			
			switch (choice) {
			case 1: // view enquiries
				esc.viewEnquiries(campList);
				break;

			case 2: // reply enquiries
				esc.replyEnquiries(campList);
				break;
				
			case 3:
				return;
			
			default:
				System.out.println("Invalid input!");
				break;
			}
		} while (choice != 3);
	}
	
	/**
	 * Page for staff if they want to access suggestion related methods
	 * @param suggestionDB database of all suggestions
	 * @param campDB database of all camps
	 * @param UserID UserID of the staff who is currently logged in
	 */
	public static void suggestionFunctions(SuggestionDatabase suggestionDB, CampDatabase campDB, String UserID) {

		int choice;

		SuggestionStaffController ssc = new SuggestionStaffController(suggestionDB);
		
		do {
			System.out.println("\n=============== Suggestion Menu =================");
			System.out.println("1. View Suggestions");
			System.out.println("2. Approve Suggestion");
			System.out.println("3. Back");
			System.out.println("=================================================");
			System.out.print("Enter your choice: ");
			choice = IOManager.readInt();

			switch (choice) {
			case 1: // view suggestions
				System.out.println("Viewing Suggestions");
				ssc.viewSuggestions();
				break;

			case 2: // approve suggestions
				
				CCMDatabase CCMDB = new CCMDatabase("ccms.ser");
				
				List<Suggestion> suggestions = new ArrayList<Suggestion>();
				int index = 1;
				System.out.println();
				for (Suggestion suggestion : suggestionDB.getSuggestions().values()) {
					System.out.println(index + ". Description: " + suggestion.getDescription());
					suggestions.add(suggestion);
					index++;
				}

				if (suggestions.isEmpty()) {
					System.out.println("No suggestions available to approve.");
					break;
				}

				System.out.print("\nEnter the index of the suggestion to approve: ");
				int approveIndex = IOManager.readInt() - 1; // Adjust for 0-based index

				CCM thisCCM = null;
				Suggestion sugg = null;
				if (approveIndex >= 0 && approveIndex < suggestions.size()) {
					sugg = suggestions.get(approveIndex);
					thisCCM = CCMDB.getCCM(sugg.getCreator());
					
					if (ssc.approveSuggestion(sugg.getSuggestionID()) && thisCCM != null){
						thisCCM.addPoint();
						CCMDB.addOrUpdateCCM(thisCCM);
                    	CCMDB.saveToFile();
					}
					
				} else {
					System.out.println("\nInvalid index!");
				}
				break;

			case 3:
				return;

			default:
				System.out.println("\nInvalid input! Please try again.");
				break;
			}
		} while (choice != 3);
	}
	
	/**
	 * Page for staff if they want to access report related methods
	 * @param campList list of camps created by the staff that is currently logged in
	 */
	public static void reportFunctions(List<Camp> campList) {
		
		int choice;
		
		ReportStaffController rsc = new ReportStaffController();
		
		do {
			System.out.println("\n=============== Camp Report Menu =================");
			System.out.println("1. Camp Attendance Report");
			System.out.println("2. Camp Committee Member Performance Report");
			System.out.println("3. Back");
			System.out.println("==================================================");
			System.out.print("Enter your choice: ");
			choice = IOManager.readInt();
			switch (choice) {
			case 1:
				rsc.generateReport(campList);
				break;
				
			case 2:
				rsc.generatePerformanceReport(campList);
				break;
				
			case 3:
				return;
				
			default:
				System.out.println("\nInvalid input! Please try again.");
				break;
			}

		} while (choice != 3);
	}
}