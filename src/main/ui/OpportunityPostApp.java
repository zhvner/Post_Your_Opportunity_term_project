package ui;

import model.OpportunityList;
import model.OpportunityPost;

import java.util.Date;
import java.util.Scanner;

import static model.OpportunityPost.Availability.available;
import static model.OpportunityPost.Availability.expired;

public class OpportunityPostApp {
    private Scanner input;
    private OpportunityList opportunityList;

    // EFFECTS: runs the teller application
    public OpportunityPostApp() {
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        boolean keepGoing = true;
        String command;

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
        opportunityList = new OpportunityList();
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add post");
        System.out.println("\tr -> remove post");
        System.out.println("\tl -> see posts list");
        System.out.println("\te -> edit the post");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddPost();
        } else if (command.equals("r")) {
            doRemovePost();
        } else if (command.equals("l")) {
            doListPosts();
        } else if (command.equals("e")) {
            doEdit();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: edits existing post
    private void doEdit() {
        System.out.println("Which opportunity do you want to edit?");
        Integer n = input.nextInt();
        opportunityList.selectOpp(n);
        String name = getName();
        OpportunityPost.OpportunityType type = getOpportunityType();
        Date date = getDate();
        OpportunityPost.Availability status = getStatus();
        OpportunityPost op = new OpportunityPost(name, type, date, status);
        opportunityList.addOpp(op);
        System.out.println("Opportunity has been added! \n");
    }

    // MODIFIES: this
    // EFFECTS: lists existing posts
    private void doListPosts() {
        opportunityList.printOpportunities();
    }

    // MODIFIES: this
    // EFFECTS: removes posts
    private void doRemovePost() {
        System.out.println("Which opportunity do you want to remove?");
        Integer n = input.nextInt();
        opportunityList.removeOpp(n - 1);
        System.out.println("Opportunity has been removed! \n");
    }

    // MODIFIES: this
    // EFFECTS: adds a post
    private void doAddPost() {
        String name = getName();
        OpportunityPost.OpportunityType type = getOpportunityType();
        Date date = getDate();
        OpportunityPost.Availability status = getStatus();
        OpportunityPost op = new OpportunityPost(name, type, date, status);
        opportunityList.addOpp(op);
        System.out.println("Opportunity has been added! \n");
    }

    private String getName() {
        System.out.println("What's the name of the post?");
        String name = input.next();
        return name;
    }

    private OpportunityPost.OpportunityType getOpportunityType() {
        System.out.println("What's the type fo the post?");
        System.out.println("\ti -> internship");
        System.out.println("\td -> design team");
        System.out.println("\tr -> research");
        System.out.println("\tv -> volunteering");
        String type = input.next();
        OpportunityPost.OpportunityType opportunityType = OpportunityPost.OpportunityType.internship;
        if (type.equals("i")) {
            opportunityType = OpportunityPost.OpportunityType.internship;
        } else if (type.equals("d")) {
            opportunityType = OpportunityPost.OpportunityType.designTeam;
        } else if (type.equals("r")) {
            opportunityType = OpportunityPost.OpportunityType.research;
        } else if (type.equals("v")) {
            opportunityType = OpportunityPost.OpportunityType.volunteering;
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

    private OpportunityPost.Availability getStatus() {
        System.out.println("What is the status of the opportunity?");
        System.out.println("\ta -> available");
        System.out.println("\te -> expired");
        String status = input.next();
        OpportunityPost.Availability availability = available;
        if (status.equals("a")) {
            availability = available;
        } else if (status.equals("e")) {
            availability = expired;
        }
        return availability;
    }

}
