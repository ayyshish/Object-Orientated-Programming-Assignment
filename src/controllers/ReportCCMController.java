package controllers;

import java.util.List;

import entity.CCM;
import entity.Camp;
import entity.Report;

import java.io.PrintWriter;
import java.io.IOException;

/**
 * Controller specifically for methods only accessed by Camp Committee Member(CCM)
 * These methods affect the Report object
 * @version 26/11/2023
 */
public class ReportCCMController {

	/**
	 * Generates the attendance report of a specific camp that the Camp Committee Member oversees
	 * @param camp camp to generate a report of
	 */
	public void generateReport(Camp camp) {
		System.out.println("\n================ Camp Report ================");
		// actual report generation
		System.out.println("Camp: " + camp.getName());
		System.out.println("Camp Description: " + camp.getDescription());
		Report report = camp.getReport();

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

			writer.println(camp.getName());

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
		System.out.println("================================================");
		return;
	}
}