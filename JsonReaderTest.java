package persistence;

import model.Fridge;
import model.FridgeItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// this class references code from JsonSerializationDemo, specifically code from the JsonReaderTest Class
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Fridge f = reader.read();
            fail("Expected IOException");
        } catch (IOException e) {
            // successfully caught IOException
        }
    }

    @Test
    void testReaderEmptyFridge() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyFridge.json");
        try {
            Fridge f = reader.read();
            assertEquals("MyFridge", f.getName());
            assertEquals(0, f.getFridgeItems().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFridgeWithEggsAndButter() {
        JsonReader reader = new JsonReader("./data/testReaderFridgeWithEggsAndButter.json");
        try {
            Fridge f = reader.read();
            assertEquals("MyFridge", f.getName());
            List<FridgeItem> items = f.getFridgeItems();
            assertEquals(2, items.size());
            checkFridgeItem("eggs", "meat", 5.99, "30/10/2021", items.get(0));
            checkFridgeItem("butter", "dairy", 6.99, "20/10/2021", items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


//    @Test
//    void testAddFridgeItems() {
//        JsonReader reader = new JsonReader("./data/testReaderFridgeWithEggsAndButter.json");
//        JSONObject jsonObject = new JSONObject("./data/testReaderFridgeWithEggsAndButter.json");
//        JSONArray jsonArray = jsonObject.getJSONArray("fridgeItems");
//        reader.
//    }
}