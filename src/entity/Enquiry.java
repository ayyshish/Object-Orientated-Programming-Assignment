package entity;

import java.util.UUID;
import java.io.*;

/**
 * Enquiry object that stores all information related to Enquiry
 * @version 26/11/2023
 */
public class Enquiry implements Serializable{
    private static final long serialVersionUID = 1L;
    private String enquiryID;
    private String creator;
    private String camp;
    private boolean resolvedStatus;
    private String question;
    private String reply;
    
    /**
     * Generates an Enquiry with a randomised ID
     */
    public Enquiry() {
        // leaving other attributes as NULL for now

        // Generate a unique identifier for this enquiry
        this.enquiryID = UUID.randomUUID().toString();
        this.resolvedStatus = false;
    }

    public String getEnquiryID() {
        return enquiryID;
    }

    public void setEnquiryID(String enquiryID) {
        this.enquiryID = enquiryID;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCamp() {
        return camp;
    }

    public void setCamp(String campName) {
        this.camp = campName;
    }

    public boolean getResolvedStatus() {
        return resolvedStatus;
    }

    public void setResolvedStatus(boolean resolvedStatus) {
        this.resolvedStatus = resolvedStatus;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}