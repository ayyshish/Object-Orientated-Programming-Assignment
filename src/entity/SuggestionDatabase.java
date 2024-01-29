package entity;

//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
//import java.util.*;
import java.util.stream.Collectors;

/**
 * Database to load/save all Suggestion objects created
 * @version 26/11/2023
 */
public class SuggestionDatabase implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Suggestion> suggestionMap;
    private String filePath;

    public SuggestionDatabase(String filePath) {
        this.suggestionMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    public Map<String, Suggestion> getSuggestions() {
        return suggestionMap;
    }
    
    public boolean submitSuggestion(Suggestion suggestion) {
        suggestionMap.put(suggestion.getSuggestionID(), suggestion);
        return true;
    }

    public Suggestion viewSuggestion(String suggestionID) {
        return suggestionMap.get(suggestionID);
    }

    public boolean editSuggestion(String suggestionID, String newDescription) {
        Suggestion suggestion = viewSuggestion(suggestionID);
        if (suggestion != null) {
            suggestion.setDescription(newDescription);
            return true;
        }
        return false;
    }

    public boolean deleteSuggestion(String suggestionID) {
        return suggestionMap.remove(suggestionID) != null;
    }

    public boolean approveSuggestion(String suggestionID) {
        Suggestion suggestion = viewSuggestion(suggestionID);
        if (suggestion != null && suggestion.getStatus() == false) {
            suggestion.setStatus(true);
            return true;
        }
        return false;
    }

    public List<Suggestion> getSuggestionsByCreator(String creator) {
        return suggestionMap.values().stream()
                .filter(suggestion -> suggestion.getCreator().equals(creator))
                .collect(Collectors.toList());
    }

    public List<Suggestion> getApprovedSuggestionsByCreator(String creator) {
        return getSuggestionsByCreator(creator).stream()
                .filter(Suggestion::getStatus)
                .collect(Collectors.toList());
    }

    public List<Suggestion> getUnapprovedSuggestionsByCreator(String creator) {
        return getSuggestionsByCreator(creator).stream()
                .filter(suggestion -> !suggestion.getStatus())
                .collect(Collectors.toList());
    }

    public List<Suggestion> getApprovedSuggestions() {
        return suggestionMap.values().stream()
                .filter(Suggestion::getStatus)
                .collect(Collectors.toList());
    }

    public List<Suggestion> getUnapprovedSuggestions() {
        return suggestionMap.values().stream()
                .filter(suggestion -> !suggestion.getStatus())
                .collect(Collectors.toList());
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(suggestionMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the suggestionMap from a file
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                suggestionMap = (Map<String, Suggestion>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //older methods
    // public void saveToFile(String filename) throws IOException {
    //     try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
    //         oos.writeObject(suggestionMap);
    //     }
    // }

    // @SuppressWarnings("unchecked")
    // public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
    //         suggestionMap = (Map<String, Suggestion>) ois.readObject();
    //     }
    // }
}
