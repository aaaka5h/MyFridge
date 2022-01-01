package persistence;

import model.Event;
import model.EventLog;
import model.Fridge;
import model.FridgeItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads Fridge from JSON data stored in file
// this class references code from JsonSerializationDemo, specifically code from the JsonReader Class
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads fridge from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Fridge read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFridge(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        EventLog.getInstance().logEvent(new Event("Loaded fridge from " + source));
        return contentBuilder.toString();
    }

    // EFFECTS: parses fridge from JSON object and returns it
    private Fridge parseFridge(JSONObject jsonObject) {
        ArrayList<FridgeItem> items = new ArrayList<>();
        String name = jsonObject.getString("name");
        Fridge f = new Fridge(name, items);
        addFridgeItems(f, jsonObject);
        return f;
    }

    // MODIFIES: f
    // EFFECTS: parses fridgeItems from JSON object and adds them to fridge
    private void addFridgeItems(Fridge f, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("fridgeItems");
        for (Object json : jsonArray) {
            JSONObject nextFridgeItem = (JSONObject) json;
            addFridgeItem(f, nextFridgeItem);
        }
    }

    // MODIFIES: f
    // EFFECTS: parses fridgeItem from JSON object and adds it to fridge
    private void addFridgeItem(Fridge f, JSONObject jsonObject) {
        String itemName = jsonObject.getString("itemName");
        String itemType = jsonObject.getString("itemType");
        Double itemPrice = jsonObject.getDouble("itemPrice");
        String itemExpiresOn = jsonObject.getString("itemExpiresOn");
        FridgeItem fi = new FridgeItem(itemName, itemType, itemPrice, itemExpiresOn);
        f.addItem(fi);
    }
}
