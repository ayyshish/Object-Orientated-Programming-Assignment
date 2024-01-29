package controllers;

import entity.*;

/**
 * Controller specifically for methods only accessed by Staff
 * These methods affect the Suggestion object/Database
 * @version 26/11/2023
 */
public class SuggestionStaffController {
    protected SuggestionDatabase suggestionDatabase;

    /**
     * Make a new SuggestionStaffController object for the respective user interface
     * @param suggestionDatabase database of all suggestions
     */
    public SuggestionStaffController(SuggestionDatabase suggestionDatabase) {
        this.suggestionDatabase = suggestionDatabase;
    }


    /**
     * Views all suggestions in the Suggestion database
     */
    public void viewSuggestions() {
        System.out.println("\n================ List of Suggestions ================");
        System.out.println();
        for (Suggestion suggestion : suggestionDatabase.getSuggestions().values()) {
            System.out.println("ID: " + suggestion.getSuggestionID());
            System.out.println("Creator: " + suggestion.getCreator());
            System.out.println("Camp: " + suggestion.getCamp());
            System.out.println("Status: " + (suggestion.getStatus() ? "Approved" : "Pending"));
            System.out.println("Description: " + suggestion.getDescription());
            System.out.println();
        }
        System.out.println("=====================================================");
    }

    /**
     * Approves a Suggestion made to a camp
     * @param suggestionID ID of Suggestion made to a camp
     * @return true if approval is successful, false otherwise
     */
    public boolean approveSuggestion(String suggestionID) {
        System.out.println("\n=============== Approving Suggestion ================");
        boolean success = suggestionDatabase.approveSuggestion(suggestionID);
        if (success) {
            suggestionDatabase.saveToFile(); // Save changes to file
            System.out.println("Suggestion approved successfully.");
            System.out.println("=====================================================");
            return true;
        } else {
            System.out.println("Suggestion not found or already approved.");
            System.out.println("=====================================================");
            return false;
        }
    }
}