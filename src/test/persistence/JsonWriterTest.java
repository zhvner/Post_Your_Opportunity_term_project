package persistence;

import model.Availability;
import model.OpportunityList;
import model.OpportunityPost;
import model.OpportunityType;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

        @Test
        void testWriterInvalidFile(){
            try {
                OpportunityList opportunityList = new OpportunityList("My opportunity list");
                JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
                writer.open();
                fail("IOException was expected");
            } catch (FileNotFoundException e) {
                //pass
            }
        }

        @Test
    void testWriterEmptyWorkroom(){
            try {
                OpportunityList opportunityList = new OpportunityList("My opportunity list");
                JsonWriter writer = new JsonWriter("./data/testWriterEmptyOpportunityList.json");
                writer.open();
                writer.write(opportunityList);
                writer.close();

                JsonReader reader = new JsonReader("./data/testWriterEmptyOpportunityList.json");
                opportunityList = reader.read();
                assertEquals("My opportunity list", opportunityList.getName());
                assertEquals(0, opportunityList.numOpportunities());
            } catch (FileNotFoundException e) {
                fail("Exception should not have been thrown");
            }catch(IOException e){
                fail("Exception should not have been thrown");
            }
        }

        @Test
    void testWriterGeneralWorkroom(){
            try {
                OpportunityList opportunityList = new OpportunityList("My opportunity list");
                OpportunityPost op1 = new OpportunityPost("need a partner", OpportunityType.designTeam,
                        new Date(2022-1900, 11-1,30),
                        Availability.available);
                opportunityList.addOpp(op1);
                OpportunityPost op2 = new OpportunityPost("need a dog shelter volunteer", OpportunityType.volunteering,
                        new Date(2023-1900, 1-1,31),
                        Availability.available);
                opportunityList.addOpp(op2);
                JsonWriter writer = new JsonWriter("./data/testWriterGeneralOpportunityList.json");
                writer.open();
                writer.write(opportunityList);
                writer.close();

                JsonReader reader = new JsonReader("./data/testWriterGeneralOpportunityList.json");
                opportunityList = reader.read();
                assertEquals("My opportunity list", opportunityList.getName());
                List<OpportunityPost> opportunityPosts = opportunityList.getOpportunityPosts();
                assertEquals(2, opportunityList.numOpportunities());

                checkOppPost(op1.getPostName(),
                        op1.getOpportunityType(),
                        op1.getDueDate(),
                        op1.getStatus(), opportunityPosts.get(0));
                checkOppPost(op2.getPostName(),
                        op2.getOpportunityType(),
                        op2.getDueDate(),
                        op2.getStatus(), opportunityPosts.get(1));

            } catch (FileNotFoundException e) {
                fail("Exception should not have been thrown");
            } catch (IOException e){
                fail("Exception should not have been thrown");
            }
        }
}
