package persistence;

import model.Availability;
import model.OpportunityList;
import model.OpportunityPost;
import model.OpportunityType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.stream.Stream;


public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public OpportunityList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOpportunityList(jsonObject);
    }

    // EFFECTS: parses opportunity list from JSON object and returns it
    private OpportunityList parseOpportunityList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        OpportunityList opportunityList = new OpportunityList(name);
        addOpportunities(opportunityList, jsonObject);
        return opportunityList;
    }

    private void addOpportunities(OpportunityList opportunityList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("opportunities");
        for (Object json : jsonArray) {
            JSONObject nextOpportunity = (JSONObject) json;
            addOpportunity(opportunityList, nextOpportunity);
        }
    }

    private void addOpportunity(OpportunityList opportunityList, JSONObject jsonObject) {
        String postName = jsonObject.getString("post name");
        OpportunityType opportunityType = OpportunityType.valueOf(jsonObject.getString("opportunity type"));
        long date = jsonObject.getLong("due date");
        Date dueDate = new Date(date);
        Availability status = Availability.valueOf(jsonObject.getString("status"));
        OpportunityPost opportunityPost = new OpportunityPost(postName,opportunityType,dueDate, status);
        opportunityList.addOpp(opportunityPost);
    }


//    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

}
