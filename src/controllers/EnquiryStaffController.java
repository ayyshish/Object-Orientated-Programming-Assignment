package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;

import entity.Camp;
import entity.CampDatabase;
import entity.Enquiry;
import entity.EnquiryDatabase;

/**
 * Controller specifically for methods only accessed by Staff
 * These methods affect the Enquiry object/Database
 * @version 26/11/2023
 */
public class EnquiryStaffController
{
    protected CampDatabase campDB;
    protected EnquiryDatabase enqDB;

    /**
     * Make a new EnquiryStaffController object for the respective user interface
     * @param enqDB database of all enquiries
     * @param campDB database of all camps
     */
    public EnquiryStaffController(EnquiryDatabase enqDB, CampDatabase campDB)
    {
        this.enqDB = enqDB;
        this.campDB = campDB;
    }

    /**
     * View all the enquiries made to a camp created by the currently logged in Staff
     * Writes enquiries to a txt file enquiryreport.txt
     * @param campList list of camps created by the staff that is currently logged in
     */
	public void viewEnquiries(List<Camp> campList) {
		System.out.println("\n================ List of Enquiries ================");
		System.out.println("List of Enquiries:");

		List<Enquiry> enqList = new ArrayList<Enquiry>();
		
		List<Enquiry> allEnqs = new ArrayList<Enquiry>();

		for (Camp camp : campList) {
			enqList = enqDB.getEnquiriesByCamp(camp.getName());
			if (!enqList.isEmpty()) {
				System.out.println("\nEnquiries for camp: " + camp.getName());
				for (Enquiry enquiry : enqList) {
					System.out.println("ID: " + enquiry.getEnquiryID());
					System.out.println("Creator: " + enquiry.getCreator());
					System.out.println("Camp: " + enquiry.getCamp());
					System.out.println("Status: " + (enquiry.getResolvedStatus() ? "Replied" : "Pending"));
					System.out.println("Description: " + enquiry.getQuestion());
					if(enquiry.getResolvedStatus()){
						System.out.println("Reply: " + enquiry.getReply());
					}
					System.out.println();
					allEnqs.add(enquiry);
				}
			}
		}
		
		try {
			PrintWriter writer = new PrintWriter("enquiryreport.txt");
			
			if(allEnqs.isEmpty()) {
				writer.println("No enquiries submmited by students.");
			}
			
			for (Enquiry enquiry : allEnqs) {
				writer.println("ID: " + enquiry.getEnquiryID());
				writer.println("Creator: " + enquiry.getCreator());
				writer.println("Camp: " + enquiry.getCamp());
				writer.println("Status: " + (enquiry.getResolvedStatus() ? "Replied" : "Pending"));
				writer.println("Description: " + enquiry.getQuestion());
				writer.println("Reply: " + enquiry.getReply());
				writer.println();
			}
			writer.close();
			System.out.println("Enquiries saved in 'enquiryreport.txt'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("==================================================");
		return;
	}

	/**
	 * Reply to a specific enquiry made to a camp created by the currently logged in Staff
	 * @param campList list of camps created by the staff that is currently logged in
	 */
    public void replyEnquiries(List<Camp> campList)
    {
    	System.out.println("\n====================== Reply to Camp Enquiries ======================");
    	System.out.println("Your camps:");
    	for(Camp camp: campList) {
    		System.out.println(camp.getName());
    	}
    	
    	System.out.println();
    	System.out.println("Reply Enquiries for which camp?");
    	System.out.print("Enter name here: ");
    	Camp selectedCamp = null;
    	
    	String campName = IOManager.readString();
    	for(Camp camp: campList) {
    		if(camp.getName().equals(campName)) {
    			selectedCamp = camp;
    		}
    	}
    	
    	if(selectedCamp==null) {
    		System.out.println("Camp not found!");
			System.out.println("=====================================================================");
    		return;
    	}

        List<Enquiry> campEnquiries = enqDB.getUnresolvedEnquiriesByCamp(campName);

        if (campEnquiries.isEmpty()) {
            System.out.println("No unresolved enquiries found for camp: " + campName);
			System.out.println("=====================================================================");
            return;
        }

        System.out.println("These are the enquries that have not been replied:");
        for (Enquiry enquiry : campEnquiries) {
            System.out.println("ID: " + enquiry.getEnquiryID() + ", Question: " + enquiry.getQuestion());
        }

        System.out.println("Copy the ID of the enquiry you want to reply to: ");
        String enquiryID = IOManager.readString();

        Enquiry selectedEnquiry = enqDB.getEnquiry(enquiryID);
        if (selectedEnquiry != null && selectedEnquiry.getCamp().equals(campName)) {
        	System.out.print("Enter your reply: ");
        	String reply = IOManager.readString();
            selectedEnquiry.setReply(reply);
            selectedEnquiry.setResolvedStatus(true);
            enqDB.saveToFile();
            System.out.println("\nEnquiry replied successfully.");
			System.out.println("=====================================================================");
            return;
        } else {
            System.out.println("Invalid enquiry ID or enquiry does not belong to the specified camp.");
			System.out.println("=====================================================================");
            return;
        }
    }
}
