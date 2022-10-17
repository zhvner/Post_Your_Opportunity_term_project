package model;

import java.util.Date;

public class OpportunityPost {

    public enum OpportunityType {
        internship,
        designTeam,
        research,
        volunteering
    }

    public enum Availability {
        available,
        expired
    }

//    private static final String INTERNS = "internship";
    private String postName;
    private OpportunityType opportunityType;
    private Date dueDate;
    private Availability status;

    public OpportunityPost(String postName, OpportunityType opportunityType,
                           Date dueDate, Availability status) {
       this.postName = postName;
       this.opportunityType = opportunityType;
       this.dueDate = dueDate;
       this.status = status;
    }


    public String getPostName() {
        return this.postName;
    }
    public OpportunityType getOpportunityType(){
        return this.opportunityType;
    }

    public Date getDueDate(){
        return dueDate;
    }

    public Availability getStatus(){
        return status;
    }

    public void setPostName(String name){
        postName = name;
    }

    public void setOpportunityType (OpportunityType type){
        opportunityType = type;
    }

    public void setDueDate(Date date){
        dueDate = date;
    }
    public void setStatus(Availability st){
        status =st;
    }

    /*
     * EFFECTS: returns a string representation of opportunity post
     */
    @Override
    public String toString() {
        String output = "Name: " + postName +
                " Type: " + opportunityType +
                " Date: " + dueDate.toString() +
                " Status: " + status;
        return output;
    }
}
