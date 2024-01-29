package entity;

import java.util.ArrayList;
// import java.io.*;

/**
 * Student object that stores all information related to Student
 * @version 26/11/2023
 */
public class Student extends User // implements Serializable
{
    private static final long serialVersionUID = 1L;
    private ArrayList<String> withdrawnCamps;
    
    public Student(String userID, String password, String faculty, boolean firstLogin, UserType userType)
    {
        super(userID, password, faculty, firstLogin, userType);
    }
    
    public void initialiseWithdrawnCamps() {
    	this.withdrawnCamps = new ArrayList<String>();
    }
    
    public ArrayList<String> getWithdrawnCamps(){
    	return this.withdrawnCamps;
    }
    
    public void setWithdrawnCamps(String campName) {
    	this.withdrawnCamps.add(campName);
    }
}
