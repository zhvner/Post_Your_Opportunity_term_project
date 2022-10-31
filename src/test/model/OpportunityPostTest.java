package model;

import model.OpportunityPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpportunityPostTest {
    private OpportunityPost testOpportunityPost;

    @BeforeEach
    void runBefore(){
        testOpportunityPost = new OpportunityPost("Need a partner");
    }

    @Test
    void testConstructor(){
        assertEquals("Need a partner", testOpportunityPost.getPostName());
        assertEquals("internship", testOpportunityPost.getOpportunityType());
        assertEquals("Thu Oct 27 00:00:00 PDT 2022 ", testOpportunityPost.getDueDate());
        assertEquals("available", testOpportunityPost.getStatus());
    }

    @Test
    void testToString() {
        assertTrue( testOpportunityPost.toString().contains("Name: Need a partner, " +
                        "Type:internship, Thu Oct 27 00:00:00 PDT 2022, Status: available"));
    }
}

}