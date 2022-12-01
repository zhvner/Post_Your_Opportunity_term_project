package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static model.Availability.*;
import static model.OpportunityType.*;
import static org.junit.jupiter.api.Assertions.*;

public class OpportunityListTest {
    private OpportunityList testOpportunityList;
    private OpportunityPost op1;
    private OpportunityPost op2;
    private OpportunityPost op3;
    private OpportunityPost op4;

    @BeforeEach
    void setup() {
        testOpportunityList = new OpportunityList("My opportunity list");
        op1 = new OpportunityPost("Need a partner",
                designTeam,
                new Date(2022 - 1900, 9, 27),
                expired);
        testOpportunityList.addOpp(op1);
        op2 = new OpportunityPost("Need a volunteer",
                volunteering,
                new Date(2022 - 1900, 11, 27),
                available);
        testOpportunityList.addOpp(op2);
        op3 = new OpportunityPost("Need an intern",
                internship,
                new Date(2023 - 1900, 9, 27),
                available);
        testOpportunityList.addOpp(op3);

        op4 = new OpportunityPost("Need a student researcher",
                research, new Date(2023 - 1900, 10, 7), available);

    }

    @Test
    void testAddOpp() {
        //testOpportunityList.addOpp(op1);
        assertTrue(testOpportunityList.containsOp(op1));
        testOpportunityList.addOpp(op4);
        assertTrue(testOpportunityList.containsOp(op4));
    }

    @Test
    void testAddMultipleOpp() {
        testOpportunityList.addOpp(op1);
        assertTrue(testOpportunityList.containsOp(op1));
        assertEquals(4, testOpportunityList.numOpportunities());
        testOpportunityList.addOpp((op2));
        assertTrue(testOpportunityList.containsOp(op2));
        assertEquals(5, testOpportunityList.numOpportunities());
        testOpportunityList.addOpp(op3);
        assertTrue(testOpportunityList.containsOp(op3));
        assertEquals(6, testOpportunityList.numOpportunities());
    }

    @Test
    void testSelectOp() {
        testOpportunityList.getOpportunityPosts();
        assertEquals(op1, testOpportunityList.selectOpp(1));
        assertEquals(op2, testOpportunityList.selectOpp(2));
        assertEquals(op3, testOpportunityList.selectOpp(3));
        testOpportunityList.addOpp(op4);
        assertEquals(op4, testOpportunityList.selectOpp(4));
    }


    @Test
    void testRemoveOpp() {
        testOpportunityList.removeOpp(1);
        testOpportunityList.getOpportunityPosts();
        assertEquals(2, testOpportunityList.numOpportunities());
    }

    @Test
    void testNumOpportunities() {
        assertEquals(3, testOpportunityList.numOpportunities());
        testOpportunityList.addOpp(op4);
        assertEquals(4, testOpportunityList.numOpportunities());

    }


}
