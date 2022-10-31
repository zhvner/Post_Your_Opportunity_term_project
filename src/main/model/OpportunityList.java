package model;

import java.util.ArrayList;
import java.util.List;

public class OpportunityList {
    private final List<OpportunityPost> opportunityPosts;

    public OpportunityList() {
        opportunityPosts = new ArrayList<>();
    }


    public List<OpportunityPost> getOpportunityPosts() {
        return opportunityPosts;
    }

    public OpportunityPost selectOpp(int n) {
        return opportunityPosts.get(n);
    }

    public void addOpp(OpportunityPost op) {
        opportunityPosts.add(op);
    }

    public void removeOpp(int n) {
        opportunityPosts.remove(n);
    }

    public void printOpportunities() {
        for (int i = 0; i < opportunityPosts.size(); i++) {
            System.out.println(i + 1 + " : " + opportunityPosts.get(i).toString());
        }
    }
}
