package app;

import controllers.IOManager;
import controllers.EnquiryCCMController;
import controllers.ReportCCMController;
import controllers.SuggestionCCMController;
import entity.CCM;
import entity.CCMDatabase;
import entity.Camp;
import entity.CampDatabase;
import entity.EnquiryDatabase;
import entity.SuggestionDatabase;
import entity.User;

/**
 * Page for a Camp Committee Member login, showing both student functions and Camp Committee Member functions
 * @version 26/11/2023
 */
public class CcmUI extends StudentUI {

	/**
	 * Main page for Camp Committee Member
	 * @param currentUser Camp Committee Member that is currently logged in
	 */
    public static void start(User currentUser) {
        int topLevelChoice;
        do {
            System.out.println("\n================ Top Level Menu ================");
            System.out.println("1. Access Student Menu");
            System.out.println("2. Access Camp Committee Member Menu");
            System.out.println("3. Exit");
            System.out.println("================================================");
            System.out.print("Enter your choice: ");


            topLevelChoice = IOManager.readInt();

            switch (topLevelChoice) {
                case 1:
                    // Delegate to StudentUI's functionality
                    studentPage(currentUser);
                    break;
                case 2:
                    // Show CCM specific menu
                    showCcmMenu(currentUser);
                    break;
                case 3:
                    System.out.println("\nExiting Application...");
                    System.out.println();
                    break;
                default:
                    System.out.println("\nInvalid input! Please try again.");
                    break;
            }
        } while (topLevelChoice != 3);
    }

    /**
     * Page for Camp Committee Member if they want to access functions specific to Camp Committee Members
     * @param currentUser
     */
    private static void showCcmMenu(User currentUser) {

        CampDatabase campDB = new CampDatabase("camps.ser");
		
		EnquiryDatabase enqDB = new EnquiryDatabase("enquiries.ser");
		
		// enquiryCCMController ecc = new enquiryCCMController(enqDB, campDB);

        SuggestionDatabase suggDB = new SuggestionDatabase("suggestions.ser");
        
        // suggestionCCMController suggCCMc = new suggestionCCMController(suggDB);

        CCMDatabase CCMDB = new CCMDatabase("ccms.ser");

        int ccmChoice;
        do {
            System.out.println("\n========== Camp Committee Member UI Menu ==========");
            // CCM specific options
            System.out.println("1. Suggestion Affairs");
            System.out.println("2. Enquiry Affairs");
            System.out.println("3. Generate Report");
            System.out.println("4. Return to Top Level Menu");
            System.out.println("===================================================");
            System.out.print("Enter your choice: ");

            ccmChoice = IOManager.readInt();

            CCM currentCCM = CCMDB.getCCMByUserID(currentUser.getUserID());
            Camp currentCamp = campDB.getCamp(currentCCM.getOverseenCamp());
            
            switch (ccmChoice) {
                case 1:
                    suggestionFunctions(suggDB, currentUser, currentCCM.getOverseenCamp(), currentCCM, CCMDB);
                    break;

                case 2:
                    enquiryFunctions(enqDB, campDB, currentCCM.getOverseenCamp());
                    break;

                case 3:
                    ReportCCMController rcc = new ReportCCMController();

                    rcc.generateReport(currentCamp);
                    System.out.println("Report generated successfully.");
                    break;

                case 4:
                    System.out.println("Returning to Top Level Menu.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (ccmChoice != 4);
    }

    /**
     * Page for Camp Committee Members if they want to access suggestion related methods
     * @param suggDB Database of all suggestions
     * @param currentUser Camp Committee Member that is currently logged in
     * @param overseenCampName Name of camp that the Camp Committee Member oversees
     * @param thisCCM Camp Committee Member that is currently logged in
     * @param CCMDB Database of all Camp Committee Members
     */
    public static void suggestionFunctions(SuggestionDatabase suggDB, User currentUser, String overseenCampName, CCM thisCCM, CCMDatabase CCMDB)
	{

        int choice;
        CCM thisisnewCCM;
        SuggestionCCMController suggCCMc = new SuggestionCCMController(suggDB);

        do
        {
            System.out.println("\n============= CCM Suggestion Menu =============");
            System.out.println("1. View Suggestion");
            System.out.println("2. Submit Suggestion");
            System.out.println("3. Edit Suggestion");
            System.out.println("4. Delete Suggestion");
            System.out.println("5. Back");
            System.out.println("===============================================");
            System.out.print("Enter your choice: ");

            choice = IOManager.readInt();
    
            switch (choice)
            {
                case 1:
                    suggCCMc.viewSuggestionsByCreator(currentUser.getUserID());
                    break;

                case 2:
                    // System.out.print("Enter camp name: ");
                    // String campName = IOManager.readString();
                	System.out.println();
                    System.out.print("Enter description: ");
                    String description = IOManager.readString();
                    suggCCMc.submitSuggestion(currentUser.getUserID(), overseenCampName, description);
                    thisisnewCCM = CCMDB.getCCMByUserID(currentUser.getUserID());
                    suggDB.saveToFile();
                    thisisnewCCM.addPoint();
                    CCMDB.addOrUpdateCCM(thisisnewCCM);
                    CCMDB.saveToFile();
                    System.out.println("Suggestion submitted successfully.");
                    break;
                    
                case 3:
                    suggCCMc.editSuggestion(currentUser.getUserID());
                    suggDB.saveToFile();
                    break;

                case 4:
                    thisisnewCCM = CCMDB.getCCMByUserID(currentUser.getUserID());
                    suggCCMc.deleteSuggestion(currentUser.getUserID());
                    suggDB.saveToFile();
                    thisisnewCCM.minusPoint();
                    CCMDB.addOrUpdateCCM(thisisnewCCM);
                    CCMDB.saveToFile();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid input!");
                    break;
            }
        } while (choice != 5);
	}
    
    /**
     * Page for Camp Committee Member to access enquiry related methods
     * @param enqDB database of all enquiries
     * @param campDB database of all camps
     * @param overseenCampName name of camp that the Camp Committee Member oversees
     */
    public static void enquiryFunctions(EnquiryDatabase enqDB, CampDatabase campDB, String overseenCampName)
	{

        int choice;
        EnquiryCCMController ecc = new EnquiryCCMController(enqDB, campDB);
        do
        {
            System.out.println("\n============= CCM Enquiry Menu =============");
            System.out.println("1. View Enquiry");
            System.out.println("2. Reply Enquiry");
            System.out.println("3. Back");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");
            choice = IOManager.readInt();
            
            switch (choice)
            {
                case 1:
                    ecc.viewEnquiry(overseenCampName);
                    break;

                case 2:
                    ecc.replyEnquiry(overseenCampName);
                    break;
                    
                case 3:
                    return;
                
                default:
                    System.out.println("Invalid input!");
                    break;
            }
        } while (choice != 3);
	}
}