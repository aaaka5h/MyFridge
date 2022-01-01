package model.project_w2e9j.src;

import model.project_w2e9j.src.persistence.JsonReader;
import model.project_w2e9j.src.persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the graphical user interface of the MyFridge application
public class MyFridgeGUI extends JFrame {
    private Fridge myFridge;
    private static final String JSON_STORE = "./data/fridge.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JDesktopPane mainFrame;
    private JMenuBar menuBar;
    private JInternalFrame fridgeImage;
    private JInternalFrame fridgeItems;
    private JInternalFrame options;

    private final JButton addItemButton = new JButton(new AddItemAction());
    private final JButton removeItemButton = new JButton(new RemoveItemAction());
    private final JButton checkExpiryButton = new JButton(new CheckExpiryAction());
    private final JButton fridgeWorthButton = new JButton(new CheckFridgeWorthAction());
    private final JButton saveFridgeButton = new JButton(new SaveFridgeAction());
    private final JButton loadFridgeButton = new JButton(new LoadFridgeAction());

    public static final Color DESKTOP_COLOR = Color.BLACK;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private final String fridgeImageFile = "./data/fridgeVectorBlack.jpeg";
    private final int imageLabelHeight = 100;

    private JTextPane items = new JTextPane();
    private final int textLabelHeight = 462;

    private EventLog el = EventLog.getInstance();

    // EFFECTS: Creates an instance of the MyFridge graphical interface with a JsonWriter and JsonReader (jsonWriter
    //          and jsonReader) to store data, a JDesktopPane desktop to display application, and three JInternalFrames
    //          within desktop: options (buttons users can click to perform actions on their fridge), fridgeImage
    //          (a black/white image of a fridge), and fridgeItems (displays items currently in a user's fridge)
    public MyFridgeGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mainFrame = new JDesktopPane();
        mainFrame.setBackground(DESKTOP_COLOR);
        options = new JInternalFrame("Options");
        options.setLayout(new BorderLayout());
        fridgeImage = new JInternalFrame();
        fridgeItems = new JInternalFrame(null, true, false, false, false);

        setContentPane(mainFrame);
        setTitle("MyFridge");
        setSize(WIDTH, HEIGHT);
        doTasks();

        setVisibleAndAddToDesktop(options);
        setVisibleAndAddToDesktop(fridgeImage);
        centreRightAndHideLabel(fridgeImage);
        setVisibleAndAddToDesktop(fridgeItems);
        centreMidAndHideLabel(fridgeItems);
        setVisible(true);
    }

    // EFFECTS: Initializes fridge with no items, displays: options, fridge image, fridge items text pane, and menu bar
    // MODIFIES: this
    private void doTasks() {
        initialize();
        displayOptions();
        displayFridgeImage();
        displayFridgeItems();
        addMenu();
    }

    // EFFECTS: Makes JInternalFrame visible and adds to desktop
    // MODIFIES: this
    private void setVisibleAndAddToDesktop(JInternalFrame ji) {
        ji.pack();
        ji.setVisible(true);
        mainFrame.add(ji);
    }

    // EFFECTS: initializes Fridge with name myFridge and no items within it
    // MODIFIES: this
    private void initialize() {
        ArrayList<FridgeItem> emptyFridge = new ArrayList<>();
        myFridge = new Fridge("myFridge", emptyFridge);
    }

    // EFFECTS: Adds menu bar to application
    // MODIFIES: this
    private void addMenu() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new LoadFridgeAction());
        fileMenu.add(new SaveFridgeAction());
        fileMenu.add(new QuitAction());
        menuBar.add(fileMenu);

        JMenu addOrRemoveMenu = new JMenu("Add/Remove");
        addOrRemoveMenu.add(new AddItemAction());
        addOrRemoveMenu.add(new RemoveItemAction());
        menuBar.add(addOrRemoveMenu);

        JMenu checkStatsMenu = new JMenu("Check");
        checkStatsMenu.add(new CheckExpiryAction());
        checkStatsMenu.add(new CheckFridgeWorthAction());
        menuBar.add(checkStatsMenu);

        setJMenuBar(menuBar);
        menuBar.setVisible(true);
    }

    // EFFECTS: Adds buttons for user to perform actions
    // MODIFIES: this
    public void displayOptions() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(3, 2));
        buttons.add(addItemButton);
        buttons.add(removeItemButton);
        buttons.add(checkExpiryButton);
        buttons.add(fridgeWorthButton);
        buttons.add(saveFridgeButton);
        buttons.add(loadFridgeButton);

        options.add(buttons, BorderLayout.EAST);
    }

    // EFFECTS: Adds a fridge image to desktop
    // MODIFIES: this
    private void displayFridgeImage() {
        fridgeImage.add(new JLabel(new ImageIcon(fridgeImageFile)));
    }

    // EFFECTS: puts fridge icon on desktop, hides label
    // MODIFIES: this
    private void centreRightAndHideLabel(JInternalFrame ji) {
        ji.setLocation(WIDTH - fridgeImage.getWidth(), (HEIGHT - fridgeImage.getHeight()) / 2 - imageLabelHeight);
    }

    // EFFECTS: puts list of items on desktop, hides label
    // MODIFIES: this
    private void centreMidAndHideLabel(JInternalFrame ji) {
        ji.setLocation((WIDTH - fridgeItems.getWidth()) / 2, HEIGHT - fridgeItems.getHeight() - textLabelHeight);
    }

    // EFFECTS: displays list of FridgeItems currently in myFridge on desktop
    // MODIFIES: this
    private void displayFridgeItems() {
        items = new JTextPane();
        items.setText("The items in your fridge are:" + myFridge.listItems().toString());
        items.setSelectedTextColor(Color.RED);
        items.setBackground(Color.WHITE);
        items.setVisible(true);
        fridgeItems.add(items);
    }

    // EFFECTS: updates item list by resetting JTextPane
    // MODIFIES: this
    private void updateItems() {
        items.setText("The items in your fridge are: " + myFridge.listItems().toString());
    }

    // Represents the action of clicking on the "Quit" menu option
    private class QuitAction extends AbstractAction {

        // Creates an instance of QuitAction with displayable name "Quit"
        QuitAction() {
            super("Quit");
        }

        @Override
        // EFFECTS: prints all events performed to the console and quits the application
        // MODIFIES: none
        public void actionPerformed(ActionEvent evt) {
            for (Event e : el) {
                System.out.println(e.toString() + "\n");
            }
            System.exit(0);
        }
    }

    // Represents the action of clicking on the "Add Item" button
    private class AddItemAction extends AbstractAction {

        // Creates an instance of AddItemAction with displayable name "Add Item"
        AddItemAction() {
            super("Add Item");
        }

        @Override
        // EFFECTS: Creates a popup JOptionPane with four JTextFields where a user enters the name, type, price, and
        // expiry date of the item they are adding, once a user has filled out the JOptionPane, the item is added to
        // myFridge and items is updated
        // MODIFIES: myFridge, this
        public void actionPerformed(ActionEvent e) {
            JTextField itemName = new JTextField();
            JTextField itemType = new JTextField();
            JTextField itemPrice = new JTextField();
            JTextField itemExpiry = new JTextField();

            Object[] fields = {
                    "Name", itemName,
                    "Type", itemType,
                    "Price", itemPrice,
                    "Expiry date (dd/mm/yyyy)", itemExpiry
            };

            JOptionPane.showConfirmDialog(null, fields, "Item", JOptionPane.OK_CANCEL_OPTION);

            String name = itemName.getText();
            String type = itemType.getText();
            double price = Double.parseDouble(itemPrice.getText());
            String expiry = itemExpiry.getText();

            FridgeItem itemToAdd = new FridgeItem(name, type, price, expiry);
            myFridge.addItem(itemToAdd);
            updateItems();
        }
    }

    // Represents the action of clicking on the "Remove Item" button
    private class RemoveItemAction extends AbstractAction {

        // EFFECTS: Creates an instance of RemoveItemAction with displayable name "Remove Item"
        RemoveItemAction() {
            super("Remove Item");
        }

        @Override
        // EFFECTS: Creates a popup JOptionPane with one JTextField where a user enters the name of the item they are
        // removing, once a user has filled out the JOptionPane, the item is removed from myFridge and items is updated
        // MODIFIES: myFridge, this
        public void actionPerformed(ActionEvent e) {
            JTextField itemName = new JTextField();

            JOptionPane.showConfirmDialog(null,
                    itemName,
                    "Item Name",
                    JOptionPane.OK_CANCEL_OPTION);

            String name = itemName.getText();

            myFridge.removeItem(myFridge.findItem(name));
            updateItems();
        }
    }

    // Represents the action of clicking on the "Check Expiry" button
    private class CheckExpiryAction extends AbstractAction {

        // Creates an instance of CheckExpiryAction with displayable name "Check Expiry"
        CheckExpiryAction() {
            super("Check Expiry");
        }

        @Override
        // EFFECTS: Creates a popup JOptionPane with one JTextField where a user enters the name of the item they are
        // checking the expiry of, once a user has filled out the JOptionPane, the item's expiry date is returned
        // MODIFIES: None
        public void actionPerformed(ActionEvent e) {
            JTextField itemName = new JTextField();

            JOptionPane.showConfirmDialog(null,
                    itemName,
                    "Check Expiry of:",
                    JOptionPane.OK_CANCEL_OPTION);

            String name = itemName.getText();

            JOptionPane.showConfirmDialog(null,
                    checkExpiryMessage(name),
                    name,
                    JOptionPane.OK_CANCEL_OPTION);
        }
    }

    // Represents the action of clicking on the "Check Fridge Worth" button
    private class CheckFridgeWorthAction extends AbstractAction {

        // Creates an instance of CheckFridgeWorthAction with displayable name "Check Fridge Worth"
        CheckFridgeWorthAction() {
            super("Check Fridge Worth");
        }

        @Override
        // EFFECTS: Creates a popup JOptionPane displaying the sum of the prices of each FridgeItem in a user's fridge
        // MODIFIES: None
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showConfirmDialog(null,
                    "Your fridge is worth " + myFridge.fridgeWorth(),
                    "Fridge Worth",
                    JOptionPane.OK_CANCEL_OPTION);
        }
    }

    // Represents the action of clicking on the "Save Fridge" button
    private class SaveFridgeAction extends AbstractAction {

        // Creates an instance of SaveFridgeAction with displayable name "Save Fridge"
        SaveFridgeAction() {
            super("Save Fridge");
        }

        @Override
        // EFFECTS: Saves fridge data to a json file so user can access their fridge upon reopening the application,
        // creates a popup JOptionPane alerting the user that their fridge has been saved
        // MODIFIES: JSON_STORE
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(myFridge);
                jsonWriter.close();
                JOptionPane.showConfirmDialog(null,
                        "Saved " + myFridge.getName() + " to " + JSON_STORE,
                        "Save Fridge",
                        JOptionPane.OK_CANCEL_OPTION);
            } catch (FileNotFoundException e) {
                JOptionPane.showConfirmDialog(null,
                        "Unable to write to file: " + JSON_STORE,
                        "Save Fridge",
                        JOptionPane.OK_CANCEL_OPTION);
            }
        }
    }

    // Represents the action of clicking on the "Load Fridge" button
    private class LoadFridgeAction extends AbstractAction {

        // Creates an instance of LoadFridgeAction with displayable name "Load Fridge"
        LoadFridgeAction() {
            super("Load Fridge");
        }

        @Override
        // EFFECTS: Loads fridge data from json file, updates fridgeItems in myFridge and items, also creates a popup
        // JOptionPane alerting the user that their fridge has been loaded
        // MODIFIES: JSON_STORE, this
        public void actionPerformed(ActionEvent evt) {
            try {
                myFridge = jsonReader.read();
                JOptionPane.showConfirmDialog(null,
                        "Loaded " + myFridge.getName() + " from " + JSON_STORE,
                        "Load Fridge",
                        JOptionPane.OK_CANCEL_OPTION);
                updateItems();
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(null,
                        "Unable to read from file: " + JSON_STORE,
                        "Load Fridge",
                        JOptionPane.OK_CANCEL_OPTION);
            }
        }
    }

    // EFFECTS: returns message that corresponds to expiry date of item
    // MODIFIES: None
    private String checkExpiryMessage(String itemName) {
        if (myFridge.checkExpiry(itemName) == -99999999) {
            return "Error: could not find item in fridge";
        } else if (myFridge.checkExpiry(itemName) > 0) {
            return itemName + " expires in " + myFridge.checkExpiry(itemName) + " days!";
        } else if (myFridge.checkExpiry(itemName) < 0) {
            return itemName + " expired " + myFridge.checkExpiry(itemName) + " days ago!";
        } else {
            return itemName + " expires today!";
        }
    }

    // EFFECTS: Runs the program
    // MODIFIES: None
    public static void main(String[] args) {
        JFrame frame = new MyFridgeGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
