package entity;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Report object that stores the list of students and CCM registered for a camp
 * @version 26/11/2023
 */
public class Report implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**
	 * Stores UserIDs of Students registered to a camp
	 */
	private ArrayList<String> studentList;
    private ArrayList<CCM> CCMList;

    public Report()
    {
        studentList = new ArrayList<String>();
        CCMList = new ArrayList<CCM>();
    }

    public ArrayList<String> getStudentList()
    {
        return this.studentList;
    }

    public ArrayList<CCM> getCCMList()
    {
        return this.CCMList;
    }

    public void addStudent(String studentID)
    {
        this.studentList.add(studentID);
    }

    public void addCCM(CCM ccm)
    {
        this.CCMList.add(ccm);
    }
    
    public void removeStudent(String studentID) {
    	this.studentList.remove(studentID);
    }
}
