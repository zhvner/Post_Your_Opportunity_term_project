package persistence;

import model.Availability;
import model.OpportunityPost;
import model.OpportunityType;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    public void checkOppPost(String postName, OpportunityType type, Date dueDate, Availability status,
                             OpportunityPost opportunityPost){
        assertEquals(postName, opportunityPost.getPostName());
        assertEquals(type, opportunityPost.getOpportunityType());
        assertEquals(dueDate, opportunityPost.getDueDate());
        assertEquals(status, opportunityPost.getStatus());
    }
}
