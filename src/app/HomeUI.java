package app;

import controllers.IOManager;

/**
 * Very first page of CAMS
 * @version 26/11/2023
 */
public class HomeUI {
	
	/**
	 * main display
	 * @param args for main method
	 */
	public static void main(String[] args) {
		
		System.out.println();
        System.out.println("    _//         _/       _//       _//  _// //  ");
        System.out.println(" _//   _//     _/ //     _/ _//   _///_//    _//");
        System.out.println("_//           _/  _//    _// _// _ _// _//      ");
        System.out.println("_//          _//   _//   _//  _//  _//   _//    ");
        System.out.println("_//         _////// _//  _//   _/  _//      _// ");
        System.out.println(" _//   _// _//       _// _//       _//_//    _//");
        System.out.println("   _////  _//         _//_//       _//  _// //  ");
		System.out.println("\n================== Welcome to CAMS! ==================");
        System.out.println("(1) Login");
        System.out.println("(2) Exit");
        System.out.println("======================================================");
        System.out.print("Enter your choice: ");
		int choice = IOManager.readInt();
		switch(choice) {
		case 1:
			LoginUI.main(null);
			break;
		case 2:
			System.out.println("\nExiting Application...");
			System.out.println();
			System.exit(0);
			break;
		default:
			System.out.println("Please enter 1 or 2");
			break;
		}
	}

}
