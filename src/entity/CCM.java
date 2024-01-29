package entity;

import java.util.ArrayList;

/**
 * CCM Object that stores all information related to Camp Committee Members
 * @version 26/11/2023
 */
public class CCM extends Student
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int points;
    private final String overseenCampName;
    
    public CCM(String userID, String password, String faculty, boolean firstLogin, UserType userType, 
    		ArrayList<String> withdrawnCamps, String overseenCampName)
    {
        super(userID, password, faculty, firstLogin, userType);
        this.overseenCampName = overseenCampName;
        this.points = 0;
        //this.suggestions = new ArrayList<Suggestion>();
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points++;
    }

    public void minusPoint(){
        this.points--;
    }

    public String getOverseenCamp() {
        return overseenCampName;
    }
}
