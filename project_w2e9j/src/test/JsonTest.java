package model.project_w2e9j.src.test;

import model.project_w2e9j.src.FridgeItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

// this class references code from JsonSerializationDemo, specifically code from the JsonTest Class
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected void checkFridgeItem(String name,
                                   String itemType,
                                   double itemPrice,
                                   String itemExpiresOn,
                                   FridgeItem fridgeItem) {
        assertEquals(name, fridgeItem.getName());
        assertEquals(itemType, fridgeItem.getFoodType());
        assertEquals(itemPrice, fridgeItem.getPrice());
        assertEquals(itemExpiresOn, fridgeItem.getExpiresOn());
    }
}