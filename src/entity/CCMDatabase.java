package entity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Database to load/save all CCM objects created
 * @version 26/11/2023
 */
public class CCMDatabase implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Map<String, CCM> ccmMap; // Keyed by CCM ID
    private String filePath;

    public CCMDatabase(String filePath)
    {
        this.ccmMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    public void addOrUpdateCCM(CCM ccm)
    {
        ccmMap.put(ccm.getUserID(), ccm);
    }

    public CCM getCCM(String name)
    {
        return ccmMap.get(name);
    }

    public boolean containsCCM(String userID) {
        return ccmMap.containsKey(userID);
    }

    public CCM getCCMByUserID(String userID) {
        // This assumes that the map only contains CCM objects.
        return ccmMap.get(userID);
    }

    // public void updateCCM(CCM ccm)
    // {
    //     ccmMap.put(ccm.getUserID(), ccm);
    // }

    public void removeCCM(String name)
    {
        ccmMap.remove(name);
    }

    public List<CCM> getAllCCM()
    {
        return new ArrayList<>(ccmMap.values());
    }
    
    /* public List<CCM> getCCMByCamps(String campName) // we might not need this
    {
        return ccmMap.values().stream()
                .filter(CCM -> CCM.getUserID().equals(campName))
                .collect(Collectors.toList());
    } */
    
	public List<CCM> getCCMByFaculty(String faculty)
    {
		return ccmMap.values().stream()
                .filter(ccm -> ccm.getFaculty().equals(faculty))
                .collect(Collectors.toList());
	}

    // Save the CCMMap to a file
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(ccmMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the CCMMap from a file
    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                ccmMap = (Map<String, CCM>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
