package controllers;

import java.util.List;

import entity.CCM;
import entity.CCMDatabase;
import entity.Camp;
import entity.Report;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * Controller specifically for methods only accessed by Staff
 * These methods affect the Report object
 * @version 26/11/2023
 */
public class ReportStaffController {

	/**
	 * Generates an attendance report of a specific camp created by the currently logged in Staff
	 * @param campList list of camps created by the currently logged in Staff
	 */
	public void generateReport(List<Camp> campList) {
		System.out.println("\n================ Generate Camp Report ================");
		// camp selection
		System.out.println("Your camps:");
		for (Camp camp : campList) {
			System.out.println(camp.getName());
		}
		
		System.out.println();
		System.out.println("Enter the name of the camp you wish to generate a report of: ");
		System.out.print("Enter name here: ");
		Camp selectedCamp = null;
		String campName = IOManager.readString();
		
		for (Camp camp : campList) {
			if (camp.getName().equals(campName)) {
				selectedCamp = camp;
			}
		}
		
		System.out.println();
		if (selectedCamp == null) {
			System.out.println("Camp not found!");
			System.out.println("=======================================================");
			return;
		}
		System.out.println("================ Camp Report ================");
		// actual report generation
		System.out.println();
		System.out.println("Camp: " + selectedCamp.getName());
		System.out.println("Camp Description: " + selectedCamp.getDescription());
		Report report = selectedCamp.getReport();

		List<String> studentList = report.getStudentList();

		System.out.println("\n---------------- Students ----------------");
		if (studentList.isEmpty()) {
			System.out.println("No students have signed up yet!");
		}

		else {
			for (String student : studentList) {
				System.out.println(student + ": Student");
			}
		}

		List<CCM> ccmList = report.getCCMList();

		System.out.println("\n---------- Camp Committee Members ----------");
		if (ccmList.isEmpty()) {
			System.out.println("No camp committee member has signed up yet!");
		}

		else {
			for (CCM ccm : ccmList) {
				System.out.println(ccm.getUserID() + ": CCM");
			}
		}

		try {
			PrintWriter writer = new PrintWriter("report.txt");

			writer.println(selectedCamp.getName());

			for (String student : studentList) {
				writer.print(student);
				writer.println(": Student");
			}

			for (CCM ccm : ccmList) {
				writer.print(ccm.getUserID());
				writer.println(": CCM");
			}

			writer.close();
			System.out.println("\nReport saved to 'report.txt'");

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("==============================================");
		System.out.println("=======================================================");
		return;
	}

	/**
	 * Generates a performance report of all Camp Commmittee Members of a specific camp created by the currently logged in Staff
	 * Performance report consists of the CCM's points
	 * @param campList list of camps created by the currently logged in Staff
	 */
	public void generatePerformanceReport(List<Camp> campList) {
		System.out.println("\n========== Generate Performance Report ==========");
		// camp selection
		System.out.println("Your camps:");
		for (Camp camp : campList) {
			System.out.println(camp.getName());
		}
		
		System.out.println();
		System.out.println("Enter the name of the camp you wish to generate a report of: ");
		System.out.print("Enter name here: ");
		Camp selectedCamp = null;
		String campName = IOManager.readString();
		
		for (Camp camp : campList) {
			if (camp.getName().equals(campName)) {
				selectedCamp = camp;
			}
		}

		System.out.println();
		if (selectedCamp == null) {
			System.out.println("Camp not found!");
			System.out.println("=================================================");
			return;
		}

		// report generation
		System.out.println("Camp Name:" + selectedCamp.getName());
		System.out.println("Camp Description: " + selectedCamp.getDescription());
		Report report = selectedCamp.getReport();


		//edit this to get from CCMDB
		List<CCM> ccmList = report.getCCMList();

		CCMDatabase CCMDB = new CCMDatabase("ccms.ser");

		System.out.println("\n-- Camp Committee Members Performance --");
		if (ccmList.isEmpty()) {
			System.out.println("No camp committee member has signed up yet!");
		}

		else {
			for (CCM ccm : ccmList) {
				CCM currentCCM = CCMDB.getCCM(ccm.getUserID());
				System.out.println(ccm.getUserID() + ": " + currentCCM.getPoints() + " points");
			}
		}

		try {
			PrintWriter writer = new PrintWriter("performancereport.txt");

			writer.println(selectedCamp.getName());

			for (CCM ccm : ccmList) {
				writer.println(ccm.getUserID() + ": " + ccm.getPoints() + " points");
			}

			writer.close();
			System.out.println("\nPerformance report saved to 'performancereport.txt'");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("=================================================");
		return;
	}
}
