package ui;

import com.toedter.calendar.JDateChooser;
import model.OpportunityList;
import model.OpportunityPost;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Objects;

public class GUI3 extends JFrame implements ActionListener {

    private static final String OPPORTUNITY_LIST = "./data/OpportunityList.json";
    private JFrame mainMenuFrame;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private OpportunityList opportunityList;
    private OpportunityPost post;
    private final String[] buttonNames = {"See current posts", "Create a post", "Remove a post", "Save a post",
            "Edit post", "Exit app"};
    private final String[] actionCommands = {"see", "create", "remove", "save", "edit", "exit"};

    private final JLabel posts = new JLabel();

    private JFrame opportunityListPanel;
    private JFrame postsPage;
    private JFrame editPostPanel;

    private JTextField t1;

    private JComboBox cb1;
    private JLabel selectedType;
    private static final String[] typeOptions = {"internship", "designTeam", "research", "volunteering"};


    private JLabel selectedDueDate;

    private static final String[] statusOptions = {"available", "expired"};
    private JComboBox<Object> cb2;
    private JLabel selectedStatus;

    private JScrollPane scroll;


    //private Container cc = mainMenuFrame.getContentPane();
    private JLabel oppPosted;
    private boolean posted;
    JLabel postedBLN;


    public GUI3() {
        initializeMenu();
        makeOppListPanel();
        makePostYourOppPanel();

        //makeEditPostPanel();
    }


    public GUI3(OpportunityList opportunityList) {
        this.opportunityList = opportunityList;
        initializeMenu();
    }

    //EFFECTS: Creates a new JFrame of the main menu
    private void initializeMenu() {
        mainMenuFrame = new JFrame("Opportunity Post App");
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        addMenu(mainMenuFrame);

        addComponentsToPane(mainMenuFrame.getContentPane());

        mainMenuFrame.pack();
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setVisible(true);

        posts.setText("No posts available yet. You need to load them first!");
    }

    //MODIFIES: this
    //EFFECTS: adds menu to the main menu
    private void addMenu(JFrame frame) {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuLoad = new JMenuItem("Load posts");
        menuLoad.setActionCommand("load");
        menuLoad.addActionListener(this);
        menu.add(menuLoad);
        bar.add(menu);
        frame.setJMenuBar(bar);
    }

    //MODIFIES: this
    //EFFECTS: adds image logo and menu buttons panel
    private void addComponentsToPane(Container contentPane) {
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("./src/main/images/image.png"));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(logo, BorderLayout.PAGE_END);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 2, 2));
        addActionToButton(buttonPanel);// added buttons before image
        contentPane.add(buttonPanel, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons panel to the main menu
    private void addActionToButton(JPanel buttonPanel) {
        for (int i = 0; i < buttonNames.length; i++) {
            addButton(buttonNames[i], buttonPanel, actionCommands[i]);
        }
    }

    //EFFECTS: creates and adds a button with action command
    private void addButton(String buttonName, JPanel panelButton, String actionCommand) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(new Dimension(300, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        panelButton.add(button);
    }

    //blank
    private void makeEditPostPanel() {
        editPostPanel = new JFrame();
        editPostPanel.setLayout(new GridLayout(0, 1));
        editPostPanel.setSize(new Dimension(WIDTH, HEIGHT));
        editPostPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        editPostPanel.setDefaultCloseOperation(HIDE_ON_CLOSE);
        editPostPanel.setLocationRelativeTo(null);
        editPostPanel.pack();

        String[] opportunityListNames = {String.valueOf(opportunityList.getOpportunityPosts().toArray())};
        JComboBox listToEdit =
                new JComboBox(opportunityListNames);

        editPostPanel.add(listToEdit);
        editPostPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates a new frame with opportunity lists for viewing
    private void makeOppListPanel() {
        opportunityListPanel = new JFrame();
        opportunityListPanel.setLayout(new GridLayout(0, 1));
        opportunityListPanel.setSize(new Dimension(WIDTH, HEIGHT));
        opportunityListPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        opportunityListPanel.setDefaultCloseOperation(HIDE_ON_CLOSE);
        opportunityListPanel.setLocationRelativeTo(null);

        opportunityListPanel.pack();
        opportunityListPanel.setBackground(Color.pink);
        opportunityListPanel.setVisible(false);

        scroll = new JScrollPane(posts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        opportunityListPanel.getContentPane().add(scroll);

        //return button
        JButton returnToMainMenu = new JButton("Return to Main Menu");
        returnToMainMenu.setActionCommand("return");
        returnToMainMenu.addActionListener(this);
        opportunityListPanel.getContentPane().add(returnToMainMenu);
    }

    //EFFECTS: creates a new Jframe with opportunity lists for adding a new post
    private void makePostYourOppPanel() {
        postsPage = new JFrame();
        postsPage.setLayout(new GridLayout(0, 2));
        postsPage.setSize(new Dimension(WIDTH, HEIGHT));
        postsPage.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        postsPage.setDefaultCloseOperation(HIDE_ON_CLOSE);
        postsPage.setLocationRelativeTo(null);

        createPostsPage();

        JButton returnButton = new JButton("RETURN TO MENU");
        returnButton.setActionCommand("return");
        returnButton.addActionListener(this);
        postsPage.add(returnButton);

        JButton addPost = new JButton("ADD POST");
        addPost.setActionCommand("add");
        addPost.addActionListener(this);
        postsPage.add(addPost);
        //addPanelButton(returnButton, postsPage);
        //addLabelsToPosts();
    }

    //EFFECTS: adds components to post creation frame
    private void createPostsPage() {

        addPostName();
        typeToText();
        addDueDate();
        statusToText();
        addPostedLabel();
    }

    //EFFECTS: creates and adds to JFrame new jlabels for post name
    private void addPostName() {
        //create post
        JLabel opName = new JLabel("POST NAME:");
        t1 = new JTextField(50);

        postsPage.add(opName);
        postsPage.add(t1);
    }

    //MODIFIES: selectedType
    //EFFECTS: creates and adds to JFrame new JLabels, JComboBox for opportunity type
    private void typeToText() {
        JLabel selectType = new JLabel("SELECT OPPORTUNITY TYPE: ");
        cb1 = new JComboBox<>(typeOptions);
        JLabel selectedTypeLabel = new JLabel("SELECTED OPPORTUNITY TYPE: ");
        selectedType = new JLabel();

        cb1.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                try {
                    String no = Objects.requireNonNull(cb1.getSelectedItem()).toString();
                    selectedType.setText(no);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        postsPage.add(selectType);
        postsPage.add(cb1);
        postsPage.add(selectedTypeLabel);
        postsPage.add(selectedType);
    }

    //MODIFIES: selectedDueDate
    //EFFECTS: creates and adds to JFrame new JLabels, JDateChooser for opportunity due date
    private void addDueDate() {
        JLabel selectDueDate = new JLabel("SELECT DUE DATE: ");
        JDateChooser dateChooser = new JDateChooser();
        JLabel selectedDateLabel = new JLabel("SELECTED DUE DATE: ");
        selectedDueDate = new JLabel("No date");

        dateChooser.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("date")) {
                    selectedDueDate.setText(String.valueOf(dateChooser.getDate()));
                }
            }
        });


        postsPage.add(selectDueDate);
        postsPage.add(dateChooser);
        postsPage.add(selectedDateLabel);
        postsPage.add(selectedDueDate);
    }

    //MODIFIES: selectedStatus
    //EFFECTS: creates and adds to JFrame new JLabels, JComboBox for opportunity status
    private void statusToText() {
        JLabel selectStatus = new JLabel("SELECT AVAILABILITY:");
        cb2 = new JComboBox<>(statusOptions);
        JLabel selectedStatusLabel = new JLabel("SELECTED AVAILABILITY: ");
        selectedStatus = new JLabel();

        cb2.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                try {
                    String no = Objects.requireNonNull(cb2.getSelectedItem()).toString();
                    selectedStatus.setText(no);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        postsPage.add(selectStatus);
        postsPage.add(cb2);
        postsPage.add(selectedStatusLabel);
        postsPage.add(selectedStatus);

    }

    //EFFECTS: creates and adds to JFrame new JLabels to show if opportunity is posted
    private void addPostedLabel() {
        JLabel postedLabel = new JLabel("Is this opportunity posted?");
        postedBLN = new JLabel(String.valueOf(posted));

        postsPage.add(postedLabel);
        postsPage.add(postedBLN);
    }




    @Override
    //MODIFIES: this
    //EFFECTS: assigns actions to each command by actionEvent parameter
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
//
        if (e.getActionCommand().equals("load")) {
            loadPosts();
        } else if (e.getActionCommand().equals("return")) {
            returnToMainMenu();
        } else if (e.getActionCommand().equals("exit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("see")) {
            initializeOppPostsPanel();
        } else if (e.getActionCommand().equals("add")) {
            addOppToList();
        } else if (e.getActionCommand().equals("create")) {
            initializeCreatePostPanel();
        } else if (e.getActionCommand().equals("remove")) {
            removePost(post);
        } else if (e.getActionCommand().equals("save")) {
            savePosts();
        } else if (e.getActionCommand().equals("edit")) {
            editPosts(post);

        }
    }

    //MODIFIES: postedBLN
    // EFFECTS: adds opportunity to the list
    private void addOppToList() {
        posted = true;
        postedBLN.setText(String.valueOf(true));
//        try {
        post = new OpportunityPost(t1.getText(), selectedType.getText(),
                    selectedDueDate.getText(),
                    selectedStatus.getText());
        opportunityList.addOpp(post);
        posts.setText("<html><pre>Current opportunity posts: \n" + opportunityList.getOpportunityPosts().toString()
                    + "\n</pre></html>");
        scroll.setToolTipText(String.valueOf(posts));
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid input! Try again!");
//        } catch (IndexOutOfBoundsException e) {
//            posts.setText("Before proceeding, initialize the list of posts");
//        } catch (IllegalArgumentException e) {
//            System.out.println(" ");
//        }
    }

    // MODIFIES: this
    // EFFECTS: reads data from selected file
    private void loadPosts() {
        try {
            JsonReader reader = new JsonReader(OPPORTUNITY_LIST);
            opportunityList = null;
            opportunityList = reader.read();
            this.posts.setText("Current Posts: \n" + opportunityList.getOpportunityPosts()
                    + " \n");
            System.out.println("Posts loaded from file " + OPPORTUNITY_LIST);
        } catch (IOException e) {
            this.posts.setText("No posts are added yet!");
        } catch (IndexOutOfBoundsException e) {
            this.posts.setText("Initialize posts file before proceeding!");
        } catch (NullPointerException e) {
            this.posts.setText("No idea!");
        }

    }

    // MODIFIES: this
    // EFFECTS: writes data from selected file
    private void savePosts() {
        try {
            JsonWriter writer = new JsonWriter(OPPORTUNITY_LIST);
            writer.write(opportunityList);
            writer.close();
            System.out.println("Saved " + opportunityList.getName() + "to" + OPPORTUNITY_LIST);
        } catch (Exception e) {
            System.out.println("Please load the file before trying to save it");
        }
    }

    private void initializeOppPostsPanel() {
        opportunityListPanel.setVisible(true);
        mainMenuFrame.setVisible(false);
        postsPage.setVisible(false);
    }

    private void initializeCreatePostPanel() {
        postsPage.setVisible(true);
        opportunityListPanel.setVisible(false);
        mainMenuFrame.setVisible(false);
        //oppPosted.setText("Is opportunity posted? " + posted);

    }

    private void returnToMainMenu() {
        mainMenuFrame.setVisible(true);
        opportunityListPanel.setVisible(false);
        postsPage.setVisible(false);
    }

    private void removePost(OpportunityPost post) {
        try {
            opportunityList.removeOp(post);
            posts.setText("<html><pre>Current opportunity posts: \n" + opportunityList.getOpportunityPosts()
                    + "\n</pre></html>");
            System.out.println("The opportunity is no longer posted");
            posted = false;
            postedBLN.setText(String.valueOf(posted));
        } catch (NullPointerException e) {
            System.out.println("Create a post before removing it!");
        } catch (IndexOutOfBoundsException e) {
            posts.setText("Before proceeding, initialize the list of posts");
        }
    }

    private void editPosts(OpportunityPost post) {
        postsPage.setVisible(false);
        opportunityListPanel.setVisible(false);
        mainMenuFrame.setVisible(false);
        editPostPanel.setVisible(true);
    }
}
