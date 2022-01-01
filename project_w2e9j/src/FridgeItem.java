package model.project_w2e9j.src;

import org.json.JSONObject;
import model.project_w2e9j.src.persistence.Writable;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

// Represents an item with a name, food type, price, expiry date, and the Fridge it is located in
public class FridgeItem implements Writable {
    private String name;            // name of item (e.g: bread, butter, eggs, etc...)
    private String foodType;        // change class to FoodType?
    private double price;           // price of item, change to float?
    private String expiresOn;       // expiry date of item in format: d/mm/yyyy

    // REQUIRES: itemName has a non-zero length, itemPrice >= 0
    // EFFECTS: creates a FridgeItem with
    //          name: itemName
    //          food type: itemType,
    //          price: itemPrice, and
    //          an expiry date: itemExpiresOn
    public FridgeItem(String itemName, String itemType, double itemPrice, String itemExpiresOn) {
        name = itemName;
        foodType = itemType;
        price = itemPrice;
        expiresOn = itemExpiresOn;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getFoodType() {
        return foodType;
    }

    public double getPrice() {
        return price;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    // EFFECTS: Converts expiry date from String to LocalDate format
    // this method references code from mkyong.com
    // URL: https://mkyong.com/java8/java-8-how-to-convert-string-to-localdate/
    public LocalDate getExpiresOnLocalDate() {
        DateTimeFormatter validFormat = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return LocalDate.parse(expiresOn, validFormat);
    }

    // EFFECTS: Returns days til item expires.
    //          If expires today: return 0
    //          if expires in the future: return positive integer representing days til item expires
    //          if expired already: return negative integer representing days since item has expired
    public int daysTilExpiry() {
        LocalDate expires = this.getExpiresOnLocalDate();
        LocalDate today = LocalDate.now();

        Period daysTilExpiring = Period.between(today, expires);
        return daysTilExpiring.getDays();
    }

    @Override
    // this method references code from JsonSerializationDemo, specifically code from the Thingy Class
    // URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemName", name);
        json.put("itemType", foodType);
        json.put("itemPrice", price);
        json.put("itemExpiresOn", expiresOn);
        return json;
    }
}


