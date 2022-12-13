package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

//represents a list of opportunity posts
public class OpportunityList implements Writable {
    //private final OpportunityPost opportunityPost;
    private final String name;
    private final List<OpportunityPost> opportunityPosts;

    //EFFECTS: Creates constructs a new ArrayList and list name
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
        EventLog.getInstance().logEvent(new Event("Opportunity is selected" + opportunityPosts.get(n - 1)));
        return opportunityPosts.get(n - 1);
    }

    // EFFECTS: returns true if the given post is in list,
    //          else return false
    public boolean containsOp(OpportunityPost op) {
        return opportunityPosts.contains(op);
    }

    // MODIFIES: this
    // REQUIRES: adds a OpportunityPost object to the list
    public void addOpp(OpportunityPost op) {
        //EventLog.getInstance().logEvent(new Event("Opportunity is added " + op));
        opportunityPosts.add(op);
    }


    // EFFECTS: removes a post with the given index
    public void removeOpp(int n) {
        EventLog.getInstance().logEvent(new Event("Opportunity is removed "
                                        + opportunityPosts.get(n)));
        opportunityPosts.remove(n - 1);
    }

    // EFFECTS: returns false and removes the given post if it is in list,
    //          else return true in GUI3
    public boolean removeOp(OpportunityPost post) {
        if (containsOp(post)) {
            opportunityPosts.remove(post);
            return false;
        } else {
            return true;
        }
    }


    public int numOpportunities() {
        return opportunityPosts.size();
    }

    // EFFECTS: converts a list into a JSON object
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
