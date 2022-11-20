package persistence;

import model.Availability;
import model.OpportunityList;
import model.OpportunityPost;
import model.OpportunityType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile(){
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            OpportunityList opportunityList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyOpportunityList(){
        JsonReader reader = new JsonReader("./data/testReaderEmptyOpportunityList.json");
        try {
            OpportunityList opportunityList = reader.read();
            assertEquals("name of my opportunities", opportunityList.getName());
            assertEquals(0, opportunityList.numOpportunities());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOpportunityList(){
        JsonReader reader = new JsonReader("./data/testReaderGeneralOpportunityList.json");
        try {
            OpportunityList opportunityList = reader.read();
            assertEquals("My opportunity list", opportunityList.getName());
            List<OpportunityPost> opportunityPosts = opportunityList.getOpportunityPosts();
            assertEquals(2, opportunityList.numOpportunities());
            checkOppPost("need a partner",
                        OpportunityType.designTeam,
                        new Date(2022-1900, 11-1,30),
                        Availability.available, opportunityPosts.get(0));
            checkOppPost("need a dog shelter volunteer",
                        OpportunityType.volunteering,
                        new Date(2023-1900,1-1, 31),
                        Availability.available, opportunityPosts.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
