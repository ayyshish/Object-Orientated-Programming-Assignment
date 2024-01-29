package controllers;

import java.util.GregorianCalendar;
import java.util.List;

import entity.Camp;
import entity.CampDatabase;
import entity.Report;
import entity.Staff;

/**
 * Controller specifically for methods only accessed by Staff
 * These methods affect the Camp object/database
 * @version 26/11/2023
 */
public class CampStaffController {

	protected CampDatabase campDB;

	/**
	 * Make a new CampStaffController object for the respective user interface
	 * @param campDB database of all camps
	 */
	public CampStaffController(CampDatabase campDB) {
		this.campDB = campDB;
	}

	/**
	 * Creates a new camp and adds it to the camp database
	 * @param staff staff that is currently logged in
	 */
	public void createNewCamp(Staff staff) {
        System.out.println("\n====================== Create New Camp ======================");
        System.out.println("Creating a new camp:");
        System.out.print("Enter Camp Name: ");
        String name = IOManager.readString();
        boolean dateFlag = true;
        int startYear, startMonth, startDate, closingYear, closingMonth, closingDate;

        if(campDB.getCamp(name) != null) {
        	System.out.println("Error! Camp with same name already exists");
            System.out.println("==============================================================");
        	return;
        }

        do
        {
            System.out.print("Enter Starting Date (Year): ");
            startYear = IOManager.readInt();
            System.out.print("Enter Starting Date (Month): ");
            startMonth = IOManager.readInt();
            System.out.print("Enter Starting Date (Date): ");
            startDate = IOManager.readInt();

            System.out.print("Enter Closing Date (Year): ");
            closingYear = IOManager.readInt();
            System.out.print("Enter Closing Date (Month): ");
            closingMonth = IOManager.readInt();
            System.out.print("Enter Closing Date (Date): ");
            closingDate = IOManager.readInt();

            // Check if closing date is after starting date
            if (closingYear < startYear || (closingYear == startYear && closingMonth < startMonth)
            || (closingYear == startYear && closingMonth == startMonth && closingDate < startDate))
            {
                System.out.println("Error! Please try again. Note that closing date must be after starting date.");

            }
            else
            {
                System.out.println("Dates registered. Registration Deadline is set as Start Date.");
                System.out.println();
                dateFlag = false;
            }
        } while (dateFlag);

        Camp newCamp = new Camp(name, startYear, startMonth, startDate, staff.getFaculty());
        newCamp.setClosingDate(closingYear, closingMonth, closingDate);
        newCamp.setInCharge(staff.getUserID());

        Report report = new Report();
        newCamp.setReport(report);

        System.out.print("Enter Camp Description: ");
        String description = IOManager.readString();
        newCamp.setDescription(description);

        System.out.print("Enter Camp Faculty (If open to whole NTU, type ALL): ");
        String faculty = IOManager.readString().toUpperCase();
        newCamp.setFaculty(faculty);

        System.out.print("Enter Camp Location: ");
        String location = IOManager.readString();
        newCamp.setLocation(location);

        System.out.print("Enter total number of slots: ");
        int slots = IOManager.readInt();
        newCamp.setTotalSlots(slots);
        newCamp.setAvailSlots(slots);

        int ccmSlots;

        do {
        System.out.print("Enter number of camp committee slots: ");
        ccmSlots = IOManager.readInt();
        if(ccmSlots >= slots) {
        	System.out.println("Please set the number as less than total slots.");
        }
        else break;
        }while(true);

        newCamp.setCommitteeSlots(ccmSlots);

        newCamp.setVisibility(); 		//defaults to true, maybe want to change in the future

        campDB.addCamp(newCamp);
        campDB.saveToFile();
        //TO-DO save camp to DB

        System.out.println("New Camp created successfully.");
        System.out.println("==============================================================");
        return;
    }


	/**
	 * Deletes a camp that the currently logged in staff has created
	 * Updates the camp database
	 * @param campList list of camps created by the staff that is currently logged in
	 */
	public void deleteCamp(List<Camp> campList){
        System.out.println("\n================ Delete Camp ================");
        if(campList.isEmpty()) {
			System.out.println("You have no camps!");
            System.out.println("=============================================");
            return;
		}

        System.out.println();
        System.out.println("Your camps:");
        for (Camp camp : campList) {
			System.out.println(camp.getName());
		}

        System.out.println();
		System.out.println("Enter the name of the camp to delete: ");

		String deleteThis = IOManager.readString();

        for(Camp camp: campList) {
        	if(camp.getName().equals(deleteThis)) {
        		campDB.removeCamp(deleteThis);
        		System.out.println("Camp successfully deleted!");
                campDB.saveToFile();
                System.out.println("=============================================");
                return;
        	}                    	
        }

        System.out.println("Camp not found!");
        System.out.println("=============================================");
        return;

	}

	/**
	 * Edits a camp that the currently logged in staff has created
	 * Updates the camp database
	 * @param campList list of camps created by the staff that is currently logged in
	 */
	public void editCamp(List<Camp> campList){
		Camp currentCamp = null;
        System.out.println("\n====================== Edit Camp ======================");
        if(campList.isEmpty()){
            System.out.println("You have no camps!");
            System.out.println("=======================================================");
            return;
        }

        System.out.println();
        System.out.println("Your camps:");
        for (Camp camp : campList) {
			System.out.println(camp.getName());
		}

        System.out.println();
		System.out.println("Enter the name of the camp to edit");
		System.out.print("Enter name here: ");
		String name = IOManager.readString();
		System.out.println();

		for(Camp camp:campList) {
			if(camp.getName().equals(name)) {
				currentCamp = camp;
				break;
			}
		}

		if(currentCamp==null) {
			System.out.print("Camp not found!");
            System.out.println("=======================================================");
			return;
		}

        boolean repeat = true;
        System.out.println("Editing Camp Attributes..");
        while (repeat) {
            System.out.println();
            System.out.println("Choose the Camp attribute to edit: ");
            System.out.println("1. Name");
            System.out.println("2. Starting Date");
            System.out.println("3. Closing Date");
            System.out.println("4. Registration Deadline");
            System.out.println("5. Faculty");
            System.out.println("6. Location");
            System.out.println("7. Total Slots");
            System.out.println("8. Committee Slots");
            System.out.println("9. Description");
            System.out.println("10. In Charge");
            System.out.println("11. Visibility");
            System.out.println("12. Exit");
            System.out.print("Enter the index your choice: ");
            int choice = IOManager.readInt();

            switch (choice){
                case 1: // edit name
                	System.out.println();
                    System.out.println("Editing Camp Name");
                    System.out.print("Enter new name here: ");
                    String newName = IOManager.readString();
                    currentCamp.setName(newName);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 2: // edit start date
                	System.out.println();
                    System.out.println("Editing Camp Start Date");
                    int newStartYear, newStartMonth, newStartDate;
                    System.out.print("Enter year: ");
                    newStartYear = IOManager.readInt();
                    System.out.print("Enter month: ");
                    newStartMonth = IOManager.readInt();
                    System.out.print("Enter date: ");
                    newStartDate = IOManager.readInt();
                    GregorianCalendar inputStartingDate = new GregorianCalendar(newStartYear,newStartMonth-1,newStartDate);
                    if (inputStartingDate.getTime().compareTo(currentCamp.getClosingDate()) > 0)
                    {
                        System.out.println("Error! Starting Date cannot be after Closing Date.");
                        System.out.println("=======================================================");
                        break;
                    }
                    else
                    {
                        currentCamp.setStartDate(newStartYear, newStartMonth, newStartDate);
                        campDB.saveToFile();
                        System.out.println("Successfully changed!");
                        System.out.println("=======================================================");
                        break;
                    }

                case 3: // edit closing date
                	System.out.println();
                    System.out.println("Editing Camp Closing Date");
                    int newClosingYear, newClosingMonth, newClosingDate;
                    System.out.print("Enter year:");
                    newClosingYear = IOManager.readInt();
                    System.out.print("Enter month: ");
                    newClosingMonth = IOManager.readInt();
                    System.out.print("Enter date: ");
                    newClosingDate = IOManager.readInt();

                    GregorianCalendar inputClosingDate = new GregorianCalendar(newClosingYear,newClosingMonth-1,newClosingDate);
                    if (inputClosingDate.getTime().compareTo(currentCamp.getStartDate()) < 0)
                    {
                        System.out.println("Error! Closing Date cannot be before Starting Date.");
                        System.out.println("=======================================================");
                        break;
                    }
                    else
                    {
                        currentCamp.setClosingDate(newClosingYear, newClosingMonth, newClosingDate);
                        campDB.saveToFile();
                        System.out.println("Successfully changed!");
                        System.out.println("=======================================================");
                        break;
                    }

                case 4: // edit registration deadline
                    System.out.println("Editing Camp Registration Deadline");
                    int newRegYear, newRegMonth, newRegDate;
                    System.out.print("Enter year:");
                    newRegYear = IOManager.readInt();
                    System.out.print("Enter month: ");
                    newRegMonth = IOManager.readInt();
                    System.out.print("Enter date: ");
                    newRegDate = IOManager.readInt();

                    GregorianCalendar inputRegDate = new GregorianCalendar(newRegYear,newRegMonth-1,newRegDate);
                    if (inputRegDate.getTime().compareTo(currentCamp.getStartDate()) > 0)
                    {
                        System.out.println("Error! Registration Deadline cannot be after Starting Date.");
                        System.out.println("=======================================================");
                        break;
                    }
                    else
                    {   
                        currentCamp.setRegDeadline(newRegYear, newRegMonth, newRegDate);
                        campDB.saveToFile();
                        System.out.println("Successfully changed!");
                        System.out.println("=======================================================");
                        break;
                    }

                case 5: // edit user group
                    System.out.println("Editing Faculty");
                    String newFaculty;
                    System.out.print("Enter user group (If open to whole NTU, type ALL): ");
                    newFaculty = IOManager.readString().toUpperCase();
                    currentCamp.setFaculty(newFaculty);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 6: // edit camp location
                    System.out.println("Editing Camp Location");
                    String newLocation;
                    System.out.print("Enter location: ");
                    newLocation = IOManager.readString();
                    currentCamp.setLocation(newLocation);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 7: // edit total slots
                	System.out.println();
                    System.out.println("Editing Camp Total Slots");
                    int newTotalSlots;

                    //check if new total slots is less than how many students have signed up
                    do{
                    	System.out.print("Enter total slots: ");
                    	newTotalSlots = IOManager.readInt();
                    	if(newTotalSlots<=0 || newTotalSlots-currentCamp.getAvailSlots()<0) {
                    		System.out.println("Invalid number");
                    	}

                    	else System.out.println("======================================================="); break;
                    }while (true);


                    int slotsDiff = currentCamp.getTotalSlots() - newTotalSlots; //Calculate difference between total slots
                    currentCamp.setAvailSlots(currentCamp.getAvailSlots()-slotsDiff);  //change avail slots accordingly
                    currentCamp.setTotalSlots(newTotalSlots);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 8: // edit committee slots
                	System.out.println();
                    System.out.println("Editing Camp Committee Member Slots");
                    int newCommitteeSlots;
                    System.out.print("Enter committee slots: ");
                    newCommitteeSlots = IOManager.readInt();
                    currentCamp.setCommitteeSlots(newCommitteeSlots);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 9: // edit description
                	System.out.println();
                    System.out.println("Editing Camp Description");
                    String newDescription;
                    System.out.print("Enter description: ");
                    newDescription = IOManager.readString();
                    currentCamp.setDescription(newDescription);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 10: // set inCharge
                	System.out.println();
                	System.out.println("WARNING: Camp will no longer be under your supervision.");
                	System.out.println();
                    System.out.println("Editing Camp In Charge");
                    String newInCharge;
                    System.out.print("Enter in charge:");
                    newInCharge = IOManager.readString();

                    currentCamp.setInCharge(newInCharge);
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 11:
                	System.out.println();
                    System.out.println("Toggling Visibility");
                    currentCamp.setVisibility();
                    System.out.println("Camp Visibility: " + currentCamp.getVisibility());
                    campDB.saveToFile();
                    System.out.println("Successfully changed!");
                    System.out.println("=======================================================");
                    break;

                case 12:
                    repeat = false;
                    System.out.println("=======================================================");
                    break;

                default:
                    System.out.println("\nInvalid input! Please try again.");
                    break;
	        }
        }
        return;
	}

	/**
	 * Views all the camps stored in the camp database
	 * This method only shows the camp's name, the camp's staff in charge, and the camp's faculty
	 */
	public void viewAllCamps() {
		System.out.println("\n======================== All Camps ========================");

		List<Camp> campList = campDB.getAllCamps();
        System.out.printf("%-30s %-20s %-20s\n", "Camp Name", "In Charge", "Faculty");
		for(Camp camp: campList) {
			// System.out.print(camp.getName() + " | ");
			// System.out.print(camp.getInCharge() + " | ");
			// System.out.print(camp.getFaculty() + " | ");
			// System.out.println();
            System.out.printf("%-30s %-20s %-20s\n", camp.getName(), camp.getInCharge(), camp.getFaculty());
		}
        System.out.println("===========================================================");
		return;
	}

	/**
	 * Toggles the visibility of a camp created by the currently logged in staff
	 * @param campList list of camps created by the staff that is currently logged in
	 */
	public void toggleCampVisibility(List<Camp> campList) {

		System.out.println("\n================ Toggle Camp Visibility ================");

		if(campList.isEmpty()) {
			System.out.println("You have no camps!");
            System.out.println("========================================================");
            return;
		}

        System.out.println();
        System.out.println("Your camps:");
        for (Camp camp : campList) {
			System.out.println(camp.getName());
		}

        System.out.println();
		System.out.println("Enter the name of the camp to toggle visibility:");
		System.out.print("Enter name here: ");
		String name = IOManager.readString();

		for (Camp camp : campList) {
			if(camp.getName().equals(name)) {
				System.out.println();
				camp.setVisibility();
				System.out.println("Updated camp visibility: " + camp.getVisibility());
				campDB.saveToFile();                        // save to DB
				System.out.println("Camp visibility toggled successfully.");
                System.out.println("========================================================");
				return;
			}
		}
		System.out.println("Camp not found!");
        System.out.println("========================================================");
        return;
	}

	/**
	 * Views a camp that the currently logged in staff has created
	 * @param campList list of camps created by the staff that is currently logged in
	 */
	public void viewOwnCamps(List<Camp> campList) {
        System.out.println("\n====================== Your Camps ======================");
        if(campList.isEmpty()){
            System.out.println("You have no camps!");
            System.out.println("========================================================");
            return;
        }

		System.out.println();
        System.out.println("Your camps:");
        for (Camp camp : campList) {
			System.out.println(camp.getName());
		}

        System.out.println();
		System.out.println("Enter the name of the camp you wish to view more details of:");
		System.out.print("Enter name here: ");
		String name = IOManager.readString();

		for(Camp camp:campList) {
			if(camp.getName().equals(name)) {
                System.out.println();
				System.out.println("Name: " + camp.getName());
				System.out.println("Staff in charge: " + camp.getInCharge());
				System.out.println("Start Date: " + camp.getStartDate());
				System.out.println("Closing Date: " + camp.getClosingDate());
				System.out.println("Location: " + camp.getLocation());
				System.out.println("Faculty: " + camp.getFaculty());
				System.out.println("Total Slots: " + camp.getTotalSlots());
				System.out.println("Available Slots: " + camp.getAvailSlots());
				System.out.println("Committee Slots: " + camp.getCommitteeSlots());
				System.out.println("Visibility: " + camp.getVisibility());
				System.out.println("Description: " + camp.getDescription());
                System.out.println("========================================================");
				return;
			}
		}
		System.out.println("Camp not found!");
        System.out.println("========================================================");
        return;
	}
}