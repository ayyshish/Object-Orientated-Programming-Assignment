package entity;

import java.io.Serializable;

/**
 * User object that stores all information related to User
 * @version 26/11/2023
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String userID;
    private String password;
    private String faculty;
    protected boolean firstLogin;
    private UserType userType;

    public User(String newUserID, String password, String newFaculty, Boolean firstLogin, UserType usertype)
    {
        this.userID = newUserID;
        this.password = password;
        this.faculty = newFaculty;
        this.firstLogin = firstLogin;
        this.userType = usertype;
    }
    
    public User(String newUserID, String newFaculty) {
    	this.userID = newUserID;
    	this.faculty = newFaculty;
    }

    public String getUserID ()
    {
        return this.userID;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getFaculty()
    {
        return this.faculty;
    }
    
    public boolean getFirstLogin()
    {
    	return this.firstLogin;
    }
    
    public UserType getUserType() 
    {
    	return this.userType;
    }

    public boolean setPassword(String newPassword)
    {
        if (newPassword.length() < 8) //currently conditions not defined
        {
            return false;
        }
        else
        {
            this.password = newPassword;
            if (this.firstLogin == true) // firstlogin detected, set flag to false
            {
                this.firstLogin = false;
            }
            return true;
        }
    }
    
    public void setFirstLogin(boolean b)
    {
    	this.firstLogin = b;
    }
    
    public void setUserType(UserType userType)
    {
    	this.userType = userType;
    }
    
}