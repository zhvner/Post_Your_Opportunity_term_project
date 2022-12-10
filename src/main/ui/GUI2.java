package ui;

import com.toedter.calendar.JDateChooser;
import model.OpportunityList;
import model.OpportunityPost;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class GUI2 extends JFrame implements ActionListener {
    private static final String OPPORTUNITY_LIST = "./data/OpportunityList.txt";
    private JFrame frame;
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;


    private static final String[] typeOptions = {"internship", "design team", "research", "volunteering"};

    private OpportunityList opportunityList;
    private OpportunityPost post;
    private String[] buttonNames = {"See current posts", "Add a post", "Remove a post", "Save a post",
            "Load posts", "Edit post", "Exit app"};
    private String[] actionCommands = {"see", "add", "remove", "save", "load", "edit", "exit"};


    private JPanel mainMenu;

    private JPanel opportunityListPanel;
    private static final FlowLayout listPanelLayout = new FlowLayout();
    private JLabel posts;
    private boolean posted;

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

    public GUI2() {
        createUI(); //new

//        initializeMenu();
//
//        makeOppListPanel();
        //makePostOppPanel();
//
//        JLabel postYourOppLabel = new JLabel("Post Your Opportunity!");
//        //JLabel mainScreenImage = new JLabel();
//        addLabel(postYourOppLabel);
//
//        initializeButtonsMenu();
//
//        addButtons(button1, button2, button3, button4, button5, button6, button7);
//        addActionToButton();

        //mainMenu.setVisible(true);


    }

    //new
    private void createUI() {
        frame = (JFrame) new Frame("Opportunity Post App");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addMenu(frame);
        addComponentsToPane(frame.getContentPane());
        frame.pack();
        frame.setBackground(Color.pink);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


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

    private void addComponentsToPane(Container contentPane) {
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("./src/images/image.png"));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(logo, BorderLayout.PAGE_START);

        //change names and dimensions
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4, 1, 5, 5));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        //addActionButtons(btnPanel);// added buttons after image
        //pane.add(btnPanel, BorderLayout.CENTER);
    }

    private void initializeMenu() {

//        mainMenu = new JPanel();
//        mainMenu.setBackground(Color.yellow);
//        add(mainMenu);
//
//        posts = new JLabel();
//        posts.setText("No opportunity posts are available!");
    }

    private void makeOppListPanel() {
        opportunityListPanel = new JPanel();
        opportunityListPanel.setLayout(listPanelLayout);
        listPanelLayout.setAlignment(FlowLayout.TRAILING);

        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        JScrollPane scroll = new JScrollPane(posts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton mainMenuButton = new JButton("Return to Main Menu");
        //opportunityListPanel.add(mainMenuButton);
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
        panel.add(button);
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

//    private void initializeButtonsMenu() {
//        button1 = new JButton("See current posts");
//        button2 = new JButton("Add a post");
//        button3 = new JButton("Remove a post");
//        button4 = new JButton("Save your post");
//        button5 = new JButton("Load your post");
//        button6 = new JButton("Edit a post");
//        button7 = new JButton("Exit application");
//    }

//    private void addButtons(JButton button1, JButton button2, JButton button3, JButton button4,
//                            JButton button5, JButton button6, JButton button7) {
//        addButton(button1, mainMenu);
//        addButton(button2, mainMenu);
//        addButton(button3, mainMenu);
//        addButton(button4, mainMenu);
//        addButton(button5, mainMenu);
//        addButton(button6, mainMenu);
//        addButton(button7, mainMenu);
//    }

//    private void addButton(JButton button, JPanel panel) {
//        button.setFont(new Font("Serif", Font.BOLD, 13));
//        button.setBackground(Color.pink);
//        panel.add(button);
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//        setResizable(false);
//    }

   //new
    private void addActionToButton(JPanel buttonPanel) {
        for (int i = 0; i < buttonNames.length; i++) {
            addButton(buttonNames[i], buttonPanel, actionCommands[i]);
        }
    }

    //new
    private void addButton(String buttonName, JPanel panelButton, String actionCommand) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(new Dimension(300, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        panelButton.add(button);
    }


//        button1.addActionListener(this);
//        button1.setActionCommand("See current posts");
//
//        button2.addActionListener(this);
//        button2.setActionCommand("Create a post");
//
//        button3.addActionListener(this);
//        button3.setActionCommand("Remove a post");
//
//        button4.addActionListener(this);
//        button4.setActionCommand("Save your post");
//
//        button5.addActionListener(this);
//        button5.setActionCommand("Load your post");
//
//        button6.addActionListener(this);
//        button6.setActionCommand("Edit a post");
//
//        button7.addActionListener(this);
//        button7.setActionCommand("Exit application");
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("See current posts")) {
//            initializeOppPostsPanel();
//        } else if (e.getActionCommand().equals("Create a post")) {
//            initializeCreatePostPanel();
//        } else if (e.getActionCommand().equals("Remove a post")) {
//            removePost(post);
//        } else if (e.getActionCommand().equals("Save your post")) {
//            savePosts();
//        } else if (e.getActionCommand().equals("Load your post")) {
//            loadPosts();
//        } else if (e.getActionCommand().equals("Edit a post")) {
//            editPosts(post);
//        } else if (e.getActionCommand().equals("Exit application")) {
//            System.exit(0);
//        } else if (e.getActionCommand().equals("Return to Main menu")) {
//            returnToMainMenu();
//        } else if (e.getActionCommand().equals("Add Opportunity Post to the list")) {
//            addOppToList();
        //}
    }

    private void addOppToList() {
    }


    private void returnToMainMenu() {
    }


    private void editPosts(OpportunityPost post) {

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
        } catch (Exception e) {
            System.out.println("Please load the file before trying to save it");
        }
    }
}


//    private void removePost(OpportunityPost post) {
//    }

//    private void initializeCreatePostPanel() {
//
//    }
//
//    private void initializeOppPostsPanel() {
//
//    }
//}