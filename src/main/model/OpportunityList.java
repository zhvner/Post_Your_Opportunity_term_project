package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class OpportunityList implements Writable {
    //private final OpportunityPost opportunityPost;
    private final String name;
    private final List<OpportunityPost> opportunityPosts;

    public OpportunityList(String name) {
        //opportunityPost = new OpportunityPost();
        this.name = name;
        opportunityPosts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<OpportunityPost> getOpportunityPosts() {
        return opportunityPosts;
    }

//    public OpportunityPost getOpportunityPostByIndex(int index) {
//        return opportunityPosts.get(index);
//    }

    public OpportunityPost selectOpp(int n) {
        if (opportunityPosts.get(n - 1) == null) {
            throw new IndexOutOfBoundsException("Opportunities not initialized");
        }
        return opportunityPosts.get(n - 1);
    }

    public boolean containsOp(OpportunityPost op) {
        return opportunityPosts.contains(op);
    }

    public void addOpp(OpportunityPost op) {
        opportunityPosts.add(op);
    }

    public void removeOpp(int n) {
        opportunityPosts.remove(n - 1);
    }


    public int numOpportunities() {
        return opportunityPosts.size();
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("opportunities", opportunityPostsToJson());
        return json;
    }

    private JSONArray opportunityPostsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (OpportunityPost op : opportunityPosts) {
            jsonArray.put(op.toJson());
        }
        return jsonArray;
    }
}
