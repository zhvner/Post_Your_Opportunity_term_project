package ui;

import com.toedter.calendar.JDateChooser;
import model.Availability;
import model.OpportunityList;
import model.OpportunityPost;
import model.OpportunityType;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class GUI extends JFrame implements ActionListener {

    private static final String OPPORTUNITY_LIST = "./data/OpportunityList.txt";

    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private static final String[] typeOptions = {"internship", "design team","research", "volunteering"};

    private OpportunityList opportunityList;
    private OpportunityPost post;

    private JPanel mainMenu;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;

    private JPanel opportunityListPanel; //carListingsPanel
    private JLabel posts; //listings
    private boolean posted; // listed

    private JPanel postsPage;
    private JButton addPost;
    private JTextField t1;
    private JTextField t2;
    //private JTextField t3;
    private JTextField t4;
    private JComboBox<String> cb1;
    private JComboBox<String> cb2;

    private JLabel opName;
    private JLabel opType;
    private JLabel opTypeText;
    private JLabel dueDate;
    private JLabel dueDateText;
    private JLabel status;
    private JLabel statusText;
    private JLabel oppPosted; //carListed
    private Container cc;


    // Makes a new JFrame with different attributes
    public GUI() {
        super("Opportunity Post App");
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializeMenu();

        makeOppListPanel();
        makePostOppPanel();

        JLabel postYourOppLabel = new JLabel("Post Your Opportunity!");
        //JLabel mainScreenImage = new JLabel();
        addLabel(postYourOppLabel);

        initializeButtonsMenu();

        addButtons(button1, button2, button3, button4, button5, button6, button7);
        addActionToButton();

        mainMenu.setVisible(true);


    }

    private void initializeMenu() {

        mainMenu = new JPanel();
        mainMenu.setBackground(Color.yellow);
        add(mainMenu);

        posts = new JLabel();
        posts.setText("No opportunity posts are available!");
    }

    private void makeOppListPanel() {
        opportunityListPanel = new JPanel(new FlowLayout());
        JScrollPane scroll = new JScrollPane(posts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to Main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, opportunityListPanel);

        posts.setFont(new Font("Serif", BOLD, 13));
        opportunityListPanel.add(scroll);

    }

    private void addMenuButton(JButton button, JPanel panel) {
        button.setFont(new Font("Serif", BOLD, 13));
        button.setBackground(Color.BLACK);
        button.setForeground(Color.pink);
        panel.add(button1);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
    }

    private void makePostOppPanel() {
        postsPage = new JPanel(new GridLayout(0, 2));
        JButton mainButtonMenu = new JButton("Return to Main Menu");
        mainButtonMenu.setActionCommand("Return to Main menu");
        mainButtonMenu.addActionListener(this);

        addMenuButton(mainButtonMenu, postsPage);

        createPostsPage();
        addLabelsToPosts();

    }

    private void createPostsPage() {
        addPost = new JButton("Add Opportunity Post to the list");
        addPost.setActionCommand("Add Opportunity Post to the list");
        addPost.addActionListener(this);

        opName = new JLabel("POST NAME:");
        t1 = new JTextField(50);

        opType = new JLabel("SELECT OPPORTUNITY TYPE:");
        cb1 = new JComboBox<>(typeOptions);
        comboBoxTypeToText();

        dueDate = new JLabel("SELECT DUE DATE: ");
        dueDateText = new JLabel("no date selected");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.getDateEditor().addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("date")) {
                dueDateText.setText(String.valueOf(dateChooser.getDate()));
            }
        });

        status = new JLabel("SELECT AVAILABILITY:");
        cb2 = new JComboBox<>();
        t4 = new JTextField(10);
        comboBoxStatusText();

    }


    private void comboBoxTypeToText() {
        opTypeText = new JLabel("OPPORTUNITY TYPE NAME:");
        t2 = new JTextField(10);
        cc = getContentPane();
        cc.setLayout(null);
        cc.add(opType);
        cc.add(cb1);
        cc.add(t2);
        cc.add(opTypeText);
        cb1.addItem("internship");
        cb1.addItem("design team");
        cb1.addItem("research");
        cb1.addItem("volunteering");
        cb1.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                try {
                    String no = Objects.requireNonNull(cb1.getSelectedItem()).toString();
                    t2.setText(no);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void comboBoxStatusText() {
        statusText = new JLabel("AVAILABILITY: ");
        cc.setLayout(null);
        cc.add(status);
        cc.add(cb2);
        cc.add(t4);
        cc.add(statusText);
        cb2.addItem("available");
        cb2.addItem("expired");
        cb2.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                try {
                    String no = Objects.requireNonNull(cb2.getSelectedItem()).toString();
                    t4.setText(no);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addLabelsToPosts() {
        postsPage.add(addPost);

        postsPage.add(opName);
        postsPage.add(t1);
        postsPage.add(opType);
        postsPage.add(opTypeText);
        postsPage.add(t2);

        postsPage.add(dueDate);
        postsPage.add(dueDateText);

        postsPage.add(status);
        postsPage.add(statusText);
        postsPage.add(t4);

        postsPage.add(oppPosted);

    }

    private void addLabel(JLabel postYourOppLabel) {
        postYourOppLabel.setFont(new Font("Serif", BOLD, 50));
        mainMenu.add(postYourOppLabel);
    }

    private void initializeButtonsMenu() {
        button1 = new JButton("See current posts");
        button2 = new JButton("Add a post");
        button3 = new JButton("Remove a post");
        button4 = new JButton("Save your post");
        button5 = new JButton("Load your post");
        button6 = new JButton("Edit a post");
        button7 = new JButton("Exit application");
    }

    private void addButtons(JButton button1, JButton button2, JButton button3, JButton button4,
                            JButton button5, JButton button6, JButton button7) {
        addButton(button1, mainMenu);
        addButton(button2, mainMenu);
        addButton(button3, mainMenu);
        addButton(button4, mainMenu);
        addButton(button5, mainMenu);
        addButton(button6, mainMenu);
        addButton(button7, mainMenu);
    }

    private void addButton(JButton button, JPanel panel) {
        button.setFont(new Font("Serif", Font.BOLD, 13));
        button.setBackground(Color.pink);
        panel.add(button);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }


    private void addActionToButton() {
        button1.addActionListener(this);
        button1.setActionCommand("See current posts");

        button2.addActionListener(this);
        button2.setActionCommand("Create a post");

        button3.addActionListener(this);
        button3.setActionCommand("Remove a post");

        button4.addActionListener(this);
        button4.setActionCommand("Save your post");

        button5.addActionListener(this);
        button5.setActionCommand("Load your post");

        button6.addActionListener(this);
        button6.setActionCommand("Edit a post");

        button7.addActionListener(this);
        button7.setActionCommand("Exit application");
    }

    //addPost = new JButton("Add Opportunity Post to the list");
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("See current posts")) {
            initializeOppPostsPanel();
        } else if (e.getActionCommand().equals("Create a post")) {
            initializeCreatePostPanel();
        } else if (e.getActionCommand().equals("Remove a post")) {
            removePost(post);
        } else if (e.getActionCommand().equals("Save your post")) {
            savePosts();
        } else if (e.getActionCommand().equals("Load your post")) {
            loadPosts();
        } else if (e.getActionCommand().equals("Edit a post")) {
            editPosts(post);
        } else if (e.getActionCommand().equals("Exit application")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("Return to Main menu")) {
            returnToMainMenu();
        } else if (e.getActionCommand().equals("Add Opportunity Post to the list")) {
            addOppToList();
        }

    }

    private void initializeOppPostsPanel() {
        add(opportunityListPanel);
        opportunityListPanel.setVisible(true);
        mainMenu.setVisible(false);
        postsPage.setVisible(false);
    }

    private void initializeCreatePostPanel() {
        add(postsPage);
        postsPage.setVisible(true);
        mainMenu.setVisible(false);
        opportunityListPanel.setVisible(false);
        oppPosted.setText("Is opportunity posted? " + posted);
    }

    private void removePost(OpportunityPost post) {
        try {
            int index = opportunityList.getOpportunityPosts().indexOf(post);
            opportunityList.removeOpp(index);
            posts.setText("<html><pre>Current opportunity posts: \n" + opportunityList.getOpportunityPosts()
                    + "\n</pre></html>");
            System.out.println("The opportunity is no longer posted");
            posted = false;
        } catch (NullPointerException e) {
            System.out.println("Create a post before removing it!");
        } catch (IndexOutOfBoundsException e) {
            posts.setText("Before proceeding, initialize the list of posts");
        }


    }

    private void addOppToList() {
        opportunityList = new OpportunityList("My list");
        try {
            post = new OpportunityPost(t1.getText(), OpportunityType.valueOf(t2.getText()),
                    Date.valueOf(dueDateText.getText()),
                    Availability.valueOf(t4.getText()));
            opportunityList.addOpp(post);
            posts.setText("<html><pre>Current opportunity posts: \n" + opportunityList.getOpportunityPosts()
                    + "\n</pre></html>");
            posted = true;
            oppPosted.setText("is Opportunity posted?" + posted);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Try again!");
        } catch (IndexOutOfBoundsException e) {
            posts.setText("Before proceeding, initialize the list of posts");
        }
    }


    private void returnToMainMenu() {
        mainMenu.setVisible(true);
        opportunityListPanel.setVisible(false);
        postsPage.setVisible(false);
    }


    private void editPosts(OpportunityPost post) {
        int index = opportunityList.getOpportunityPosts().indexOf(post);
        opportunityList.removeOpp(index - 1);
        initializeCreatePostPanel();
        addOppToList();
    }


    private void loadPosts() {
        try {
            JsonReader reader = new JsonReader(OPPORTUNITY_LIST);
            reader.read();
            posts.setText("<html><pre>Current Listings: \n" + opportunityList.getOpportunityPosts()
                    + "\n</pre></html>");
            System.out.println("Posts loaded from file " + OPPORTUNITY_LIST);
        } catch (IOException e) {
            posts.setText("No posts are added yet!");
        } catch (IndexOutOfBoundsException e) {
            posts.setText("Initialize posts file before proceeding!");
        }

    }


    private void savePosts() {
        try {
            JsonWriter writer = new JsonWriter(OPPORTUNITY_LIST);
            writer.write(opportunityList);
            writer.close();
            System.out.println("Saved " + opportunityList.getName() + "to" + OPPORTUNITY_LIST);
        } catch (NullPointerException e) {
            System.out.println("Please load the file before trying to save it");
        }
    }


}
