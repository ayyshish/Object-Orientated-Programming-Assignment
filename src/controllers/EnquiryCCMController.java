package controllers;

import java.util.List;
import java.util.stream.Collectors;

import entity.CampDatabase;
import entity.Enquiry;
import entity.EnquiryDatabase;

/**
 * Controller specifically for methods only accessed by Camp Committee Member(CCM)
 * These methods affect the Enquiry object/Database
 * @version 26/11/2023
 */
public class EnquiryCCMController {
    protected CampDatabase campDB;
    protected EnquiryDatabase enqDB;

    /**
     * Make a new EnquiryCCMController object for the respective user interface
     * @param enqDB database of all enquiries
     * @param campDB database of all camps
     */
    public EnquiryCCMController(EnquiryDatabase enqDB, CampDatabase campDB) {
        this.enqDB = enqDB;
        this.campDB = campDB;
    }

    /**
     * View all enquiries made to the camp overseen by the currently logged in Camp Committee Member
     * @param campName name of camp overseen by CCM
     */
    public void viewEnquiry(String campName) {
    	System.out.println("\n================ Enquiries for Camp: " + campName + " ================");
        List<Enquiry> campEnquiries = enqDB.getAllEnquiries().stream()
                                            .filter(e -> e.getCamp().equals(campName))
                                            .collect(Collectors.toList());

        if (campEnquiries.isEmpty()) {
            System.out.println("No enquiries found for camp: " + campName);
            System.out.println("============================================================");
            return;
        }

        System.out.println("Enquiries for camp: " + campName);
        for (Enquiry enquiry : campEnquiries) {
            System.out.println("ID: " + enquiry.getEnquiryID() + ", Question: " + enquiry.getQuestion());
        }
        System.out.println("============================================================");
    }

    /**
     * Reply to a specific enquiry made to the camp overseen by the currently logged in Camp Committee Member
     * @param campName name of camp overseen by CCM
     * @return true if reply is successful, false otherwise
     */
    public boolean replyEnquiry(String campName) {
    	System.out.println("\n================ Reply to Enquiries for Camp: " + campName + " ================");
        List<Enquiry> campEnquiries = enqDB.getAllEnquiries().stream()
                                            .filter(e -> e.getCamp().equals(campName) && !e.getResolvedStatus())
                                            .collect(Collectors.toList());

        if (campEnquiries.isEmpty()) {
            System.out.println("No unresolved enquiries found for camp: " + campName);
            System.out.println("====================================================================");
            return false;
        }

        System.out.println("These are the enquries that have not been replied:");
        for (Enquiry enquiry : campEnquiries) {
            System.out.println("ID: " + enquiry.getEnquiryID() + ", Question: " + enquiry.getQuestion());
        }
        
        System.out.println("Select the ID of the enquiry you want to reply to:");
        System.out.print("Enter ID here: ");
        String enquiryID = IOManager.readString();
        System.out.println("Enter your reply to the enquiry:");
        System.out.print("Enter reply here: ");
        String reply = IOManager.readString();
        System.out.println();

        Enquiry selectedEnquiry = enqDB.getEnquiry(enquiryID);
        if (selectedEnquiry != null && selectedEnquiry.getCamp().equals(campName)) {
            selectedEnquiry.setReply(reply);
            selectedEnquiry.setResolvedStatus(true);
            System.out.println("Enquiry replied successfully.");
            enqDB.saveToFile();
            System.out.println("====================================================================");
            return true;
        } else {
            System.out.println("Invalid enquiry ID or enquiry does not belong to the specified camp.");
            System.out.println("====================================================================");
            return false;
        }
    }
}
