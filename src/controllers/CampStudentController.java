package controllers;

import java.util.Date;
import java.util.List;

import entity.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller specifically for methods only accessed by Student
 * These methods affect the Camp object/database
 * @version 26/11/2023
 */
public class CampStudentController {
	
	protected CampDatabase campDB;
	
	protected StudentDatabase studentDB;
	
	/**
	 * Make a new CampStudentController object for the respective user interface
	 * @param campDB database of all camps
	 */
	public CampStudentController(CampDatabase campDB) {
		this.campDB = campDB;
		this.studentDB = new StudentDatabase("students.ser");
	}
	
	/**
	 * Views all the camps that can be viewed by the student
	 * Camps displayed depends on the camp's visbility and faculty
	 * @param student student that is currently logged in
	 */
	public void viewCamps(Student student) {
		System.out.println("\n================ Available Camps ================");
		//campDB, find camps with user.getFaculty
		//System.out.println("List of camps available from your faculty: ");
		System.out.printf("%-30s %-20s\n", "Camp Name", "Available Slots");
		List<Camp> allCamps = campDB.getAllCamps();
		
		for(Camp camp: allCamps) {
			if(camp.getVisibility()==true && (camp.getFaculty().equals(student.getFaculty()) || camp.getFaculty().equals("ALL"))){
				//System.out.println(camp.getName() + " | Available slots: " + camp.getAvailSlots());
				System.out.printf("%-30s %-20d\n", camp.getName(), camp.getAvailSlots());
			}
		}
		System.out.println("=================================================");
		return;
	}
	
	/**
	 * Registers a student for a particular camp available to him
	 * Student can register as either an attendee or a Camp Committee Member
	 * @param student student that is currently logged in
	 */
	public void registerCamps(Student student) {
		System.out.println("\n================ Register for Camps ================");
		System.out.println("List of camps available for registration:");

		Date currentDate = new Date();

		List<Camp> allCamps = campDB.getAllCamps();
		
		List<Camp> availableCamps = new ArrayList<Camp>();
		
		List<String> withdrawnCamps = new ArrayList<String>();
		
		if(studentDB.getStudent(student.getUserID())!=null) {
			withdrawnCamps = (studentDB.getStudent(student.getUserID()).getWithdrawnCamps());
		}
		
		for(Camp camp: allCamps) {
			List<String> studentList = camp.getReport().getStudentList();
			
			List<CCM> ccmList = camp.getReport().getCCMList();
			
			List<String> ccms = new ArrayList<String>();
			
			for(CCM ccm: ccmList) {
				ccms.add(ccm.getUserID());
			}
			
			if(camp.getVisibility()==true 
					&& (camp.getFaculty().equals(student.getFaculty()) || camp.getFaculty().equals("ALL"))
					&& camp.getAvailSlots()>0
					&& !withdrawnCamps.contains(camp.getName())
					&& !studentList.contains(student.getUserID())
					&& !ccms.contains(student.getUserID())
					&& !checkCampClash(student, camp)
					&& currentDate.compareTo(camp.getRegDeadline()) < 0){
				availableCamps.add(camp);
				System.out.println(camp.getName() + " | Available slots: " + camp.getAvailSlots());
			}
		}
		
		if(availableCamps.isEmpty()) {
			System.out.println("No camps available for registration!");
			System.out.println("====================================================");
			return;
		}
		
		System.out.println();
		System.out.println("Which camp would you like to register for?");
		System.out.print("Enter name here: ");
		String choice = IOManager.readString();
		System.out.println();
		
		for (Camp camp : availableCamps) {
			if (camp.getName().equals(choice)) {
				// TO-DO ask if register as student or CCM
				int selection = selectCCM();
				if (selection == 1) {
					camp.setAvailSlots(camp.getAvailSlots() - 1);
					camp.getReport().addStudent(student.getUserID());
					campDB.saveToFile();
					System.out.println("Registration successful!");
					System.out.println("====================================================");
					return;
				}
				if (selection == 2) {
					if(camp.getCommitteeSlots()<1) {
						System.out.println("Committee slots for this camp are full!");
						System.out.println("====================================================");
						return;
					}
					else {
						//save student as CCM
						try {
							List<User> users = UserDatabase.readUsers("src/user_list.csv");
							
							User stud = (User) student;
							for(User user:users) {
								if(user.getUserID().equals(student.getUserID())) {
									if(user.getUserType().equals(UserType.CCM)) {
										System.out.println("You are already a committee member for another camp!");
										System.out.println("====================================================");
										return;
									}
									stud = user;		//so that changes can be made to list
									stud.setUserType(UserType.CCM);
								}
							}
							UserDatabase.saveUsers("src/user_list.csv", users);
						} catch (IOException e) {
							e.printStackTrace();
						}
						//put new CCM into CCM list
						CCMDatabase ccmDB = new CCMDatabase("ccms.ser");
						CCM ccm = new CCM(student.getUserID(), student.getPassword(), 
								student.getFaculty(), student.getFirstLogin(), student.getUserType(),
								student.getWithdrawnCamps(), camp.getName());
						ccmDB.addOrUpdateCCM(ccm);
						ccmDB.saveToFile();
						
						camp.setAvailSlots(camp.getAvailSlots()-1);
						camp.setCommitteeSlots(camp.getCommitteeSlots()-1);
						camp.getReport().addCCM(ccm);
						campDB.saveToFile();
						System.out.println("Registration successful!");
						System.out.println("====================================================");
						return;
					}
				
				}

			}
		}
		
		System.out.println("Camp not found!");
		System.out.println("====================================================");
		return;
	}
	
	/**
	 * Removes a student from a camp that they have registered for
	 * Student will be unable to register for a camp that they have withdrawn from
	 * The withdrawn camp will be saved to a student using the student database
	 * @param student student that is currently logged in
	 */
	public void withdraw(Student student) {
		System.out.println("\n================ Withdraw from Camps ================");
		System.out.println("List of camps to withdraw from:");
		
		List<Camp> allCamps = campDB.getAllCamps();
		
		List<Camp> registeredCamps = new ArrayList<Camp>();
		
		for(Camp camp: allCamps) {
			List<String> studentList = camp.getReport().getStudentList();
			
			if(studentList.contains(student.getUserID())) {
				System.out.println(camp.getName());
				registeredCamps.add(camp);
			}
		}
		
		if(registeredCamps.isEmpty()) {
			System.out.println("You do not have any camps to withdraw from!");
			System.out.println("======================================================");
			return;
		}
		
		System.out.println();
		System.out.println("Which camp would you like to withdraw from?");
		System.out.print("Enter name here: ");
		String choice = IOManager.readString();
		System.out.println();
		
		for(Camp camp: registeredCamps) {
			if(camp.getName().equals(choice)) {
				
				if(studentDB.getStudent(student.getUserID())==null) {
					student.initialiseWithdrawnCamps();
					student.setWithdrawnCamps(camp.getName());
					studentDB.addStudent(student);
				}
				else {
					studentDB.getStudent(student.getUserID()).setWithdrawnCamps(camp.getName());
				}
				
				camp.getReport().removeStudent(student.getUserID());
				camp.setAvailSlots(camp.getAvailSlots()+1);
				System.out.println("Withdrawal successful!");
				System.out.println("======================================================");
				studentDB.saveToFile();
				campDB.saveToFile();
				return;
			}
		}
		
		System.out.println("Camp not found!");
		System.out.println("======================================================");
		return;
	}
	
	/**
	 * Displays the camps that a student has currently registered for
	 * @param student student that is currently logged in
	 */
	public void viewOwnCamps(Student student) {
		System.out.println("\n================ Your Registered Camps ================");
		System.out.println("Currently registered camps:");
		
		List<Camp> allCamps = campDB.getAllCamps();
		
		List<Camp> registeredCamps = new ArrayList<Camp>();
		
		//check if student is in student list or CCM list for all camps
		for (Camp camp : allCamps) {
			List<String> studentList = camp.getReport().getStudentList();

			if (studentList.contains(student.getUserID())) {
				System.out.println(camp.getName());
				registeredCamps.add(camp);
			}

			List<CCM> ccmList = camp.getReport().getCCMList();
			for (CCM ccm : ccmList) {
				if (ccm.getUserID().equals(student.getUserID())) {
					System.out.println(camp.getName());
					registeredCamps.add(camp);
				}
			}
		}
		
		if(registeredCamps.isEmpty()) {
			System.out.println("You have not registered for any camps!");
		}
		System.out.println("=======================================================");
		return;
	}
	
	/**
	 * Method to supplement the registerCamp method
	 * Lets the currently logged in Student choose whether they want to register as an attendee or Camp Committee Member
	 * @return Student's selection
	 */
	public int selectCCM() {
			System.out.println("\n====================== Register as CCM ======================");
		do {
			System.out.println("Would you like to register as a Camp committee member?");
			System.out.println("1. No");
			System.out.println("2. Yes");
			System.out.print("Enter your choice: ");
			int choice = IOManager.readInt();
			switch (choice) {
			case 1:
				return 1;
			case 2:
				return 2;
			default:
				System.out.println("Invalid choice! Please try again:");
				break;
			}
		} while (true);
	}

	/**
	 * Method to supplement the registerCamp method
	 * Checks the dates of camps available to the student
	 * If the dates conflict with the dates of a camp that the student has currently registered for, the conflicting camp will not be displayed
	 * @param student student that is currently logged in
	 * @param newCamp camp that will be checked for clashes
	 * @return true if there is a clash in dates, false if not
	 */
	public boolean checkCampClash(Student student, Camp newCamp)
	{
		List<Camp> allCamps = campDB.getAllCamps();
		for (Camp camp : allCamps) {
			List<String> studentList = camp.getReport().getStudentList();
			if (studentList.contains(student.getUserID())) {
				if ((newCamp.getStartDate().compareTo(camp.getStartDate()) >= 0 && newCamp.getStartDate().compareTo(camp.getClosingDate()) <= 0) ||
					(newCamp.getClosingDate().compareTo(camp.getStartDate()) >= 0 && newCamp.getClosingDate().compareTo(camp.getClosingDate()) <= 0)) {
					return true; // There is a clash
				}
			}
		}
		return false; // No clash
	}
}
