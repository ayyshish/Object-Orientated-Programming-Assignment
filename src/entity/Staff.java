package entity;

import java.util.ArrayList;

/**
 * Staff object that stores all information related to Staff
 * @version 26/11/2023
 */
public class Staff extends User
{
    private static final long serialVersionUID = 1L;
    private ArrayList<Camp> CampList;
    
    public Staff(String userID, String password, String faculty, boolean firstLogin, UserType userType)
    {
        super(userID, password, faculty, firstLogin, userType);
        this.CampList = new ArrayList<Camp>();
    }

    public ArrayList<Camp> getCampList()
    {
        return this.CampList;
    }
}
