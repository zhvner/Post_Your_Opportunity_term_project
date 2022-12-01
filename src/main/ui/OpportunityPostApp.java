package ui;

import model.Availability;
import model.OpportunityList;
import model.OpportunityPost;
import model.OpportunityType;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static model.Availability.*;


public class OpportunityPostApp {
    private static final String JSON_STORE = "./data/OpportunityList.json";
    private Scanner input;
    private OpportunityList opportunityList;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private OpportunityType opportunityType;


    // EFFECTS: runs the teller application
    public OpportunityPostApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        opportunityList = new OpportunityList("");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes posts
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        opportunityList = new OpportunityList(opportunityList.getName());
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add post");
        System.out.println("\tr -> remove post");
        System.out.println("\tp -> print posts list"); //print
        System.out.println("\ts -> save work room to file"); //new
        System.out.println("\tl -> load work room from file"); //new
        System.out.println("\te -> edit the post");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "a" -> doAddPost();
            case "r" -> doRemovePost();
            case "p" -> doListPosts();
            case "s" -> doSavePosts();
            case "l" -> doLoadPosts();
            case "e" -> doEdit();
            default -> System.out.println("Selection is not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: edits existing post
    private void doEdit() {
        System.out.println("Which opportunity do you want to edit?");
        int n = input.nextInt();
        opportunityList.removeOpp(n-1);
        opportunityList.selectOpp(n-1);
        String name = getName();
        OpportunityType type = getOpportunityType();
        Date date = getDate();
        Availability status = getStatus();
        OpportunityPost op = new OpportunityPost(name, type, date, status);
        opportunityList.addOpp(op);
        System.out.println("Opportunity has been added! \n");
    }

    // MODIFIES: this
    // EFFECTS: lists existing posts
    private void doListPosts() {
        List<OpportunityPost> opportunityPosts = opportunityList.getOpportunityPosts();
            for (int i = 0; i < opportunityPosts.size(); i++) {
                try{
                    System.out.println(i+1 + " : " + opportunityPosts.get(i).toString());
                }catch (IndexOutOfBoundsException e){
                    System.out.println("not valid opportunity list");
                }
            }
        //}
//        for (OpportunityPost op : opportunityPosts) {
//            System.out.println(op);
    }


    // MODIFIES: this
    // EFFECTS: removes posts
    private void doRemovePost() {
        System.out.println("Which opportunity do you want to remove?");
        int n = input.nextInt();
        opportunityList.removeOpp(n - 1);
        System.out.println("Opportunity has been removed! \n");
    }

//    private void doRemovePost(){
//
//    }

    // MODIFIES: this
    // EFFECTS: adds a post
    private void doAddPost() {
        String name = getName();
        OpportunityType type = getOpportunityType();
        Date date = getDate();
        Availability status = getStatus();

        OpportunityPost op = new OpportunityPost(name, type, date, status);
        opportunityList.addOpp(op);
        System.out.println("Opportunity has been added! \n");
    }

    private String getName() {
        System.out.println("What's the name of the post?");
        String name = input.next();
        return name;
    }

    private OpportunityType getOpportunityType() {
        System.out.println("What's the type for your post?");
        System.out.println("\ti -> internship");
        System.out.println("\td -> design team");
        System.out.println("\tr -> research");
        System.out.println("\tv -> volunteering");
        String type = input.next();
        switch (type) {
            case "i" -> opportunityType = OpportunityType.internship;
            case "d" -> opportunityType = OpportunityType.designTeam;
            case "r" -> opportunityType = OpportunityType.research;
            case "v" -> opportunityType = OpportunityType.volunteering;
            default -> System.out.println("Selection is not valid...");
        }
        return opportunityType;
    }



    private Date getDate() {
        System.out.println("What year is it due?");
        int year = Integer.parseInt(input.next());
        System.out.println("What month is it due?(1-12)");
        int month = Integer.parseInt(input.next());
        System.out.println("What day is it due?(1-31)");
        int day = Integer.parseInt(input.next());

        Date date = new Date(year - 1900, month - 1, day);
        return date;
    }

    private Availability getStatus() {
        System.out.println("What is the status of the opportunity?");
        System.out.println("\ta -> available");
        System.out.println("\te -> expired");
        String status = input.next();
        Availability availability = available;
        if (status.equals("a")) {
            availability = available;
        } else if (status.equals("e")) {
            availability = expired;
        } else {
            System.out.println("Selection is not valid...");
        }
        return availability;
    }

    private void doLoadPosts() {
        try {
            opportunityList = jsonReader.read();
            System.out.println("Loaded " + opportunityList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void doSavePosts() {
        try {
            jsonWriter.open();
            jsonWriter.write(opportunityList);
            jsonWriter.close();
            System.out.println("Saved " + opportunityList.getName() + "to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }

    }


}
