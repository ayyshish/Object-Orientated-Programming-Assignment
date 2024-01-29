package controllers;

import java.util.List;

import entity.*;

import java.util.ArrayList;

/**
 * Controller specifically for methods only accessed by Student
 * These methods affect the Enquiry object/Database
 * @version 26/11/2023
 */
public class EnquiryStudentController {
	
	protected CampDatabase campDB;
	protected EnquiryDatabase enqDB;
	
	/**
	 * Make a new EnquiryStudentController object for the respective user interface
	 * @param enqDB database of all enquiries
	 * @param campDB database of all camps
	 */
	public EnquiryStudentController(EnquiryDatabase enqDB, CampDatabase campDB) {
		this.enqDB = enqDB;
		this.campDB = campDB;
	}
	
	/**
	 * View all the enquiries made by the currently logged in Student
	 * @param student student that is currently logged in
	 */
	public void viewEnquiry(Student student) {
		System.out.println("\n================ Your Enquiries ================");
		List<Enquiry> enqList = enqDB.getEnquiriesByCreator(student.getUserID());
		
		if(enqList.isEmpty()) {
			System.out.println("You have no enquiries!");
			return;
		}
		
		for(Enquiry enquiry: enqList) {
			System.out.println();
			System.out.println("Enquiry ID: " + enquiry.getEnquiryID());
			System.out.println("Camp: " + enquiry.getCamp());
			System.out.println("Question: " + enquiry.getQuestion());
			System.out.println("Status: " + (enquiry.getResolvedStatus() ? "Replied" : "Pending"));
			System.out.println("Reply: " + enquiry.getReply());
			System.out.println();
		}
		System.out.println("=================================================");
	}

	/**
	 * Submits a new enquiry to a camp that the currently logged in student is registered to
	 * Saves the Enquiry to the Enquiry database
	 * @param student student that is currently logged in
	 */
	public void submitEnquiry(Student student) {
		List<Camp> allCamps = campDB.getAllCamps();
		System.out.println("\n================ Submit Enquiry ================");
		System.out.println("Available Camps");
		
		for (Camp camp : allCamps)
		{
			if (camp.getFaculty().equals(student.getFaculty()) || camp.getFaculty().equals("ALL"))
			System.out.println(camp.getName());
		}

		System.out.println();
		System.out.println("Enter the name of the camp you wish to enquire about");
		System.out.print("Enter name here: ");
		String choice = IOManager.readString();
		System.out.println();
		
		for (Camp camp: allCamps)
		{
			if(choice.equals(camp.getName()))
			{
				System.out.println("Please write down your enquiry");
				String question = IOManager.readString();
				Enquiry enquiry = new Enquiry();
				
				enquiry.setCreator(student.getUserID());
				enquiry.setQuestion(question);
				enquiry.setCamp(camp.getName());
				
				System.out.println("Enquiry successfully submitted!");
				
				enqDB.addEnquiry(enquiry);		
				enqDB.saveToFile();
				System.out.println("=================================================");
				return;
			}
		}
		
		System.out.println("Camp not found!");
		System.out.println("=================================================");
		return;
	}
	
	/**
	 * Allows the currently logged in student to edit an Enquiry they have previously made
	 * @param student student that is currently logged in
	 */
	public void editEnquiry(Student student) {
		//filter to only get enquiries by this creator
		System.out.println("\n================ Edit Your Enquiries ================");
		System.out.println("Your enquiries:");
		
		List<Enquiry> enqList = enqDB.getEnquiriesByCreator(student.getUserID());
		
		List<Enquiry> unresolvedEnqList = new ArrayList<Enquiry>();

		for(Enquiry enquiry: enqList){
			if(!enquiry.getResolvedStatus()){
				unresolvedEnqList.add(enquiry);
			}
		}

		if(enqList.isEmpty() || unresolvedEnqList.isEmpty()) {
			System.out.println("You do not have any enquiries to edit");
			System.out.println("=====================================================");
			return;
		}

		int i = 1;		
		for(Enquiry enquiry: unresolvedEnqList) {
			System.out.println(i + ":");
			System.out.println("Camp: " + enquiry.getCamp());
			System.out.println("Original Enquiry: " + enquiry.getQuestion());
			System.out.println();
			i++;
		}
		
		//match user input with actual List index
		System.out.println("Enter the index of the enquiry you would like to edit:");
		System.out.println("Enter your choice: ");
		int choice = IOManager.readInt();
		System.out.println();
		
		choice = choice-1;
		
		if(choice>=0 && choice<unresolvedEnqList.size()) {
			System.out.println("Write down your new question: ");
			String edit = IOManager.readString();
			unresolvedEnqList.get(choice).setQuestion(edit);
			enqDB.saveToFile();
			System.out.println();
			System.out.println("Enquiry successfully edited!");
			System.out.println("=====================================================");
			return;
		}
		
		System.out.println("Invalid choice!");
		System.out.println("=====================================================");
		return;
	}
	
	/**
	 * Allows the currently logged in student to delete an Enquiry they have previously made
	 * @param student student that is currently logged in
	 */
	public void deleteEnquiry(Student student) {
		System.out.println("\n================ Delete Your Enquiry ================");
		System.out.println("Your enquiries:");
		
		
		int i = 1;
		// filter to only get enquiries by this creator
		List<Enquiry> enqList = enqDB.getEnquiriesByCreator(student.getUserID());

		List<Enquiry> unresolvedEnqList = new ArrayList<Enquiry>();

		for(Enquiry enquiry: enqList){
			if(!enquiry.getResolvedStatus()){
				unresolvedEnqList.add(enquiry);
			}
		}

		if(enqList.isEmpty() || unresolvedEnqList.isEmpty()) {
			System.out.println("You do not have any enquiries to delete");
			System.out.println("=====================================================");
			return;
		}

		for (Enquiry enquiry : unresolvedEnqList) {
			System.out.println(i + ": ");
			System.out.println("Camp: " + enquiry.getCamp());
			System.out.println("Question: " + enquiry.getQuestion());
			System.out.println();
			i++;
		}

		// match user input with actual List index
		System.out.println("Enter the index of enquiry would you like to delete:");
		System.out.println("Enter your choice: ");
		int choice = IOManager.readInt();
		System.out.println();
		
		choice = choice - 1;

		if (choice >= 0 && choice < unresolvedEnqList.size()) {
			String deleteThis = unresolvedEnqList.get(choice).getEnquiryID();
			enqDB.deleteEnquiry(deleteThis);
			System.out.println("Enquiry deleted.");
			enqDB.saveToFile();
			System.out.println("=====================================================");
			return;
		}

		System.out.println("Invalid choice!");
		System.out.println("=====================================================");
		return;
	}
}