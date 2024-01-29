package entity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Suggestion object that stores all information related to Suggestion
 * @version 26/11/2023
 */
public class Suggestion implements Serializable
{   
    private static final long serialVersionUID = 1L;
    private String suggestionID;
    private String creator;
    private String camp;
    private boolean status;
    private String description;

    public Suggestion()
    {
        this.suggestionID = UUID.randomUUID().toString();
        // leaving other attributes as NULL
        this.status = false;
    }

    public String getCreator()
    {
        return this.creator;
    }

    public String getCamp()
    {
        return this.camp;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setCreator(String newCreator)
    {
        this.creator = newCreator;
    }

    public void setCamp(String newCamp)
    {
        this.camp = newCamp;
    }

    public void setDescription(String newDescription)
    {
        this.description = newDescription;
    }

    public String getSuggestionID() {
        return suggestionID;
    }

    public void setSuggestionID(String suggestionID) {
        this.suggestionID = suggestionID;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
