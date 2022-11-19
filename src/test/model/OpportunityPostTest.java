package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static model.Availability.*;
import static model.OpportunityType.internship;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class OpportunityPostTest {
    private OpportunityPost testOpportunityPost;

    @BeforeEach
    void runBefore(){
        testOpportunityPost = new OpportunityPost("Need a partner", internship,
                new Date(2022-1900, 9,27), available);
    }

    @Test
    void testConstructor(){
        assertEquals("Need a partner", testOpportunityPost.getPostName());
        assertEquals(internship, testOpportunityPost.getOpportunityType());
        assertEquals(2022-1900, testOpportunityPost.getDueDate().getYear());
        assertEquals(Calendar.OCTOBER, testOpportunityPost.getDueDate().getMonth());
        assertEquals(27, testOpportunityPost.getDueDate().getDate());
        assertEquals(available, testOpportunityPost.getStatus());
    }

    @Test
    void testToString() {
        assertTrue(testOpportunityPost.toString().contains("Name: Need a partner, " +
                "Type: internship, " +
                "Date: Thu Oct 27 00:00:00 PDT 2022, Status: available"));
    }
}