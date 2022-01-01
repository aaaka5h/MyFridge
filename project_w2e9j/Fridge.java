package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

// Represents a Fridge having a name and containing a list of FridgeItems
public class Fridge implements Writable {
    private String name;                          // Fridge name
    private ArrayList<FridgeItem> fridgeItems;    // List of all items in fridge

    // REQUIRES: fridgeName is non-zero length
    // EFFECTS: creates a Fridge with a name a list of items
    //          if items = 0, set list of FridgeItems to empty list, else items
    public Fridge(String fridgeName, ArrayList<FridgeItem> items) {
        name = fridgeName;
        if (items.isEmpty()) {
            fridgeItems = new ArrayList<>();
        } else {
            fridgeItems = items;
        }
    }

    // getters

    public String getName() {
        return name;
    }

    public ArrayList<FridgeItem> getFridgeItems() {
        return fridgeItems;
    }

    // MODIFIES: this
    // EFFECTS: adds item to a Fridge
    public void addItem(FridgeItem item) {
        fridgeItems.add(item);
        EventLog.getInstance().logEvent(new Event("Added " + item.getName() + " to fridge"));
    }

    // MODIFIES: this
    // EFFECTS: removes item from a Fridge, do nothing if item does not exist in Fridge
    public void removeItem(FridgeItem item) {
        if (fridgeItems.contains(item)) {
            fridgeItems.remove(item);
            EventLog.getInstance().logEvent(new Event("Removed " + item.getName() + " from fridge"));
        }
    }

    // EFFECTS: returns the dollar value of all items in Fridge
    public String fridgeWorth() {
        double sum = 0.00;
        for (FridgeItem i : fridgeItems) {
            sum += i.getPrice();
        }
        EventLog.getInstance().logEvent(new Event("Checked fridge worth"));
        return "$" + String.format("%.2f", sum);
    }

    // EFFECTS: returns a list of names of every FridgeItem in Fridge
    public ArrayList<String> listItems() {
        ArrayList<String> items = new ArrayList<>();
        for (FridgeItem i : fridgeItems) {
            items.add(i.getName());
        }
        return items;
    }

    // EFFECTS: searches for a specific item in your Fridge, returns that item if found
    public FridgeItem findItem(String item) {
        ArrayList<FridgeItem> itemList = this.fridgeItems;
        for (FridgeItem i : itemList) {
            if (i.getName().equals(item)) {
                return i;
            }
        }
        return null;
    }

    // EFFECTS: searches for a specific item in fridge and if found, returns days til it expires,
    //          else, return null
    public int checkExpiry(String itemName) {
        if (findItem(itemName) == null) {
            return -99999999;
        } else {
            Period daysTilExpiry = Period.between(LocalDate.now(), findItem(itemName).getExpiresOnLocalDate());
            EventLog.getInstance().logEvent(new Event("Checked expiry date of " + itemName));
            return daysTilExpiry.getDays();
        }
    }

    @Override
    // this method references code from JsonSerializationDemo, specifically code from the Workroom Class
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("fridgeItems", fridgeItemsToJson());
        return json;
    }

    // EFFECTS: converts fridgeItems in this Fridge to a JSON array
    // this method references code from JsonSerializationDemo, specifically code from the Workroom Class
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JSONArray fridgeItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (FridgeItem fi : fridgeItems) {
            jsonArray.put(fi.toJson());
        }

        return jsonArray;
    }

}