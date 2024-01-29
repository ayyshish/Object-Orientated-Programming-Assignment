package controllers;

//import java.util.ArrayList;
import java.util.List;

import entity.Suggestion;
import entity.SuggestionDatabase;

/**
 * Controller specifically for methods only accessed by Camp Committee Member(CCM)
 * These methods affect the Suggestion object/Database
 * @version 26/11/2023
 */
public class SuggestionCCMController {
    private SuggestionDatabase suggestionDatabase;

    /**
     * Make a new SuggestionCCMController object for the respective user interface
     * @param suggestionDatabase database of all suggestions
     */
    public SuggestionCCMController(SuggestionDatabase suggestionDatabase) {
        this.suggestionDatabase = suggestionDatabase;
    }

    /**
     * Submits a new Suggestion to a camp that the currently logged in CCM oversees
     * @param creator UserID of the currently logged in CCM
     * @param camp Name of camp that the currently logged in CCM oversees
     * @param description Description of the suggestion to be submitted
     * @return true if submission is successful, false otherwise
     */
    public boolean submitSuggestion(String creator, String camp, String description) {
        Suggestion newSuggestion = new Suggestion();
        newSuggestion.setCreator(creator);
        newSuggestion.setCamp(camp);
        newSuggestion.setDescription(description);
        return suggestionDatabase.submitSuggestion(newSuggestion);
    }

    /**
     * Views all suggestions created by the currently logged in CCM
     * @param creator UserID of the currently logged in CCM
     */
    public void viewSuggestionsByCreator(String creator) {
        System.out.println("\n================= View Suggestion =================");
    	System.out.println();
        System.out.println("List of Suggestions by " + creator + ":");
        for (Suggestion suggestion : suggestionDatabase.getSuggestionsByCreator(creator)) {
            System.out.println("ID: " + suggestion.getSuggestionID());
            System.out.println("Camp: " + suggestion.getCamp());
            System.out.println("Status: " + (suggestion.getStatus() ? "Approved" : "Pending"));
            System.out.println("Description: " + suggestion.getDescription());
            System.out.println();
        }
        System.out.println("====================================================");
    }

    /**
     * Allows the currently logged in CCM to edit a Suggestion they have previously made
     * @param creator UserID of the currently logged in CCM
     */
    public void editSuggestion(String creator) {
    	System.out.println("\n======================= Edit Suggestion =======================");
    	
        List<Suggestion> userSuggestions = suggestionDatabase.getUnapprovedSuggestionsByCreator(creator);
        if (userSuggestions.isEmpty()) {
            System.out.println("You do not have any suggestions that can be edited.");
            return;
        }

        // Display unapproved suggestions and ask for which one to edit
        for (Suggestion suggestion : userSuggestions) {
            System.out.println("ID: " + suggestion.getSuggestionID() + ", Description: " + suggestion.getDescription());
        }
        System.out.println("Enter the ID of the suggestion you want to edit");
        System.out.print("Enter ID here: ");
        String suggestionID = IOManager.readString();
        System.out.println("Write down your updated suggestion description");
        System.out.print("Enter description here: ");
        String newDescription = IOManager.readString();
        System.out.println();
        
        if (suggestionDatabase.editSuggestion(suggestionID, newDescription)) {
            System.out.println("Suggestion updated successfully.");
            System.out.println("============================================================================");
        } else {
            System.out.println("Failed to update the suggestion. Please check the inputted ID and try again.");
            System.out.println("============================================================================");
        }
    }

    /**
     * Allows the currently logged in CCM to delete a Suggestion they have previously made
     * @param creator UserID of the currently logged in CCM
     * @return true if deletion is successful, false otherwise
     */
    public boolean deleteSuggestion(String creator) {
    	System.out.println("\n============================ Delete Suggestion ============================");
    	
        List<Suggestion> userSuggestions = suggestionDatabase.getUnapprovedSuggestionsByCreator(creator);
        if (userSuggestions.isEmpty()) {
            System.out.println("You do not have any suggestions that can be deleted.");
            return false;
        }

        // Display unapproved suggestions and ask for which one to edit
        for (Suggestion suggestion : userSuggestions) {
            System.out.println("ID: " + suggestion.getSuggestionID() + ", Description: " + suggestion.getDescription());
        }

        System.out.println("Enter the ID of the suggestion you want to delete: ");
        String suggestionID = IOManager.readString();

        if (suggestionDatabase.deleteSuggestion(suggestionID)) {
            System.out.println("Suggestion updated successfully.");
            System.out.println("============================================================================");
            return true;
        } else {
            System.out.println("Failed to delete the suggestion. Please check the inputted ID and try again.");
            System.out.println("============================================================================");
            return false;
        }
    }
}