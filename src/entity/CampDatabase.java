package entity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Database to load/save all Camp objects created
 * @version 26/11/2023
 */
public class CampDatabase implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Camp> campMap; // Keyed by camp name
    private String filePath;

    public CampDatabase(String filePath) {
        this.campMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    public void addCamp(Camp camp) {
        campMap.put(camp.getName(), camp);
    }

    public Camp getCamp(String name) {
        return campMap.get(name);
    }

    public void updateCamp(Camp camp) {
        campMap.put(camp.getName(), camp);
    }

    public void removeCamp(String name) {
        campMap.remove(name);
    }

    public List<Camp> getAllCamps() {
        return new ArrayList<>(campMap.values());
    }
    
    public List<Camp> getCampsByInCharge(String inCharge){
        return campMap.values().stream()
                .filter(camp -> camp.getInCharge().equals(inCharge))
                .collect(Collectors.toList());
    }
    
	public List<Camp> getCampsByFaculty(String faculty) {
		return campMap.values().stream()
                .filter(camp -> camp.getFaculty().equals(faculty))
                .collect(Collectors.toList());
	}

    // Save the studentMap to a file
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(campMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the studentMap from a file
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                campMap = (Map<String, Camp>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
