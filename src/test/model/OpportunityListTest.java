package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static model.Availability.*;
import static model.OpportunityType.*;
import static org.junit.jupiter.api.Assertions.*;

public class OpportunityListTest {
    private OpportunityList testOpportunityList;

    @BeforeEach
    void setup(){
        testOpportunityList = new OpportunityList("My opportunity list");
    }

    @Test
    void testSelectOp(){
        OpportunityPost op1 = new OpportunityPost("Need a partner",
                designTeam,
                new Date(2022-1900, 9, 27),
                expired);
        OpportunityPost op2 = new OpportunityPost("Need a volunteer",
                volunteering,
                new Date(2022-1900, 11, 27),
                available);
        OpportunityPost op3 = new OpportunityPost("Need an intern",
                internship,
                new Date(2023-1900, 9, 27),
                available);
        testOpportunityList.addOpp(op1);
        testOpportunityList.addOpp(op2);
        testOpportunityList.addOpp(op3);
        assertEquals(op1, testOpportunityList.selectOpp(1));
        assertEquals(op2, testOpportunityList.selectOpp(2));
        assertEquals(op3, testOpportunityList.selectOpp(3));
    }

    @Test
    void testPrintOpportunities(){
        OpportunityPost op1 = new OpportunityPost("Need a partner",
                designTeam,
                new Date(2022-1900, 9, 27),
                expired);
        testOpportunityList.addOpp(op1);
        //assertEquals("1 : Need a partner ");


    }
}
