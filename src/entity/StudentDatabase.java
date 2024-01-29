package entity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Database to load/save all Student objects created
 * @version 26/11/2023
 */
public class StudentDatabase implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Student> studentMap; // Keyed by userID
    private String filePath;

    public StudentDatabase(String filePath) {
        this.studentMap = new HashMap<>();
        this.filePath = filePath;
        loadFromFile();
    }

    public void addStudent(Student student) {
        studentMap.put(student.getUserID(), student);
    }

    public Student getStudent(String userID) {
        return studentMap.get(userID);
    }

    public void updateStudent(Student student) {
        studentMap.put(student.getUserID(), student);
    }

    public void removeStudent(String userID) {
        studentMap.remove(userID);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    // Save the studentMap to a file
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(studentMap);
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
                studentMap = (Map<String, Student>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
