package entity;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.io.*;

/**
 * Camp object that stores all the information related to Camps
 * @version 26/11/2023
 */
public class Camp implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String name;
    private Date startDate;
    private Date closingDate;
    private String location;
    private int totalSlots;
    private int availSlots;
    private int committeeSlots;
    private String description;
    private String inCharge;
    private boolean visibility;
    private Report report;
    private String faculty;
    private Date regDeadline;

    public Camp(String name, int startYear, int startMonth, int startDate, String faculty)
    {
        // leaving other attributes as NULL
    	this.name = name;
    	this.setStartDate(startYear, startMonth, startDate);
        this.setRegDeadline(startYear, startMonth, startDate);
        this.visibility = false;
        this.faculty = faculty;
    }

    public String getName()
    {
        return this.name;
    }

    public Date getStartDate()
    {
        return this.startDate;
    }

    public Date getClosingDate()
    {
        return this.closingDate;
    }

    public Date getRegDeadline()
    {
        return this.regDeadline;
    }

    public String getLocation()
    {
        return this.location;
    }

    public int getTotalSlots()
    {
        return this.totalSlots;
    }

    public int getAvailSlots()
    {
        return this.availSlots;
    }

    public int getCommitteeSlots()
    {
        return this.committeeSlots;
    }

    public String getDescription()
    {
        return this.description;
    }

    public String getInCharge()
    {
        return this.inCharge;
    }

    public boolean getVisibility()
    {
        return this.visibility;
    }

    public boolean setName(String newName)
    {
        this.name = newName;
        return true;
    }

    public void setStartDate(int year, int month, int date)
    {
        if (year < 0 || month < 1 || month > 12 || date < 1 || date > 31) // checks input formats
        {
            System.out.println("Wrong Input");
            return;
        }
        GregorianCalendar input = new GregorianCalendar(year,month-1,date);
        if (date > input.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
            // checks if valid date (leap years not accounted)
            System.out.println("Invalid Date");
            return;
        }
        this.startDate = input.getTime();
        // note: starting time is by default midnight
    }

    public void setClosingDate(int year, int month, int date)
    {
        if (year < 0 || month < 1 || month > 12 || date < 1 || date > 31) // checks input formats
        {
            System.out.println("Wrong Input");
            return;
        }
        GregorianCalendar input = new GregorianCalendar(year,month-1,date);
        if (date > input.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
            // checks if valid date (leap years not accounted)
            System.out.println("Invalid Date");
            return;
        }
        this.closingDate = input.getTime();
        // note: starting time is by default midnight
    }

    public void setRegDeadline(int year, int month, int date)
    {
        if (year < 0 || month < 1 || month > 12 || date < 1 || date > 31) // checks input formats
        {
            System.out.println("Wrong Input");
            return;
        }
        GregorianCalendar input = new GregorianCalendar(year,month-1,date);
        if (date > input.getActualMaximum(Calendar.DAY_OF_MONTH))
        {
            // checks if valid date (leap years not accounted)
            System.out.println("Invalid Date");
            return;
        }
        this.regDeadline = input.getTime();
        // note: starting time is by default midnight
    }

    public void setLocation (String newLocation)
    {
        this.location = newLocation;
    }

    public void setTotalSlots(int newTotalSlots)
    {
        this.totalSlots = newTotalSlots;
    }
    
    public void setAvailSlots(int newAvailSlots) {
    	this.availSlots = newAvailSlots;
    }

    public void setCommitteeSlots(int newCommiteeSlots)
    {
        this.committeeSlots = newCommiteeSlots;
    }

    public void setDescription (String newDescription)
    {
        this.description = newDescription;
    }

    public void setInCharge (String newInCharge)
    {
        this.inCharge = newInCharge;
    }

    public void setVisibility ()
    {
        this.visibility = !this.visibility;
    }
    
    public String getFaculty() {
    	return this.faculty;
    }

	public void setFaculty(String newFaculty) {
		this.faculty = newFaculty;
	}
	
    public Report getReport() {
    	return this.report;
    }
    
    public void setReport(Report report) {
    	this.report = report;
    }
}
