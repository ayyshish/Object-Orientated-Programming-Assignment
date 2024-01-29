package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
//import java.util.*;
import java.util.stream.Collectors;

/**
 * Database to load/save all Enquiry objects created
 * @version 26/11/2023
 */
public class EnquiryDatabase implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Enquiry> enquiryMap;
    private String filePath;

    public EnquiryDatabase(String filePath) {
        this.enquiryMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    public boolean addEnquiry(Enquiry enquiry) {
        enquiryMap.put(enquiry.getEnquiryID(), enquiry);
        return true;
    }

    public Enquiry getEnquiry(String enquiryID) {
        return enquiryMap.get(enquiryID);
    }

    public boolean updateEnquiry(String enquiryID, String newQuestion) {
        Enquiry enquiry = getEnquiry(enquiryID);
        if (enquiry != null && !enquiry.getResolvedStatus()) {
            enquiry.setQuestion(newQuestion);
            return true;
        }
        return false;
    }

    public boolean deleteEnquiry(String enquiryID) {
        enquiryMap.remove(enquiryID);
        return true;
    }

    public List<Enquiry> getAllEnquiries() {
        return new ArrayList<>(enquiryMap.values());
    }

    public List<Enquiry> getResolvedEnquiries() {
        return enquiryMap.values().stream()
                .filter(Enquiry::getResolvedStatus)
                .collect(Collectors.toList());
    }

    public List<Enquiry> getUnresolvedEnquiries() {
        return enquiryMap.values().stream()
                .filter(enquiry -> !enquiry.getResolvedStatus())
                .collect(Collectors.toList());
    }

    public List<Enquiry> getEnquiriesByCreator(String creator) {
        return enquiryMap.values().stream()
                .filter(enquiry -> enquiry.getCreator().equals(creator))
                .collect(Collectors.toList());
    }

    public List<Enquiry> getResolvedEnquiriesByCreator(String creator) {
        return getEnquiriesByCreator(creator).stream()
                .filter(enquiry -> enquiry.getResolvedStatus())
                .collect(Collectors.toList());
    }

    public List<Enquiry> getUnresolvedEnquiriesByCreator(String creator) {
        return getEnquiriesByCreator(creator).stream()
                .filter(enquiry -> !enquiry.getResolvedStatus())
                .collect(Collectors.toList());
    }
    
    public List<Enquiry> getEnquiriesByCamp(String campName){
    	return enquiryMap.values().stream()
    			.filter(enquiry -> enquiry.getCamp().equals(campName))
    			.collect(Collectors.toList());
    }
    
    public List<Enquiry> getResolvedEnquiriesByCamp(String campName){
    	return getEnquiriesByCamp(campName).stream()
    			.filter(enquiry -> enquiry.getResolvedStatus())
    			.collect(Collectors.toList());
    }
    
    public List<Enquiry> getUnresolvedEnquiriesByCamp(String campName){
    	return getEnquiriesByCamp(campName).stream()
    			.filter(enquiry -> !enquiry.getResolvedStatus())
    			.collect(Collectors.toList());
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(enquiryMap);
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
                enquiryMap = (Map<String, Enquiry>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
