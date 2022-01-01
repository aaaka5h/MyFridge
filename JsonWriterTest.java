package persistence;

import model.FridgeItem;
import model.Fridge;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// this class references code from JsonSerializationDemo, specifically code from the JsonWriterTest Class
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
class JsonWriterTest extends JsonTest {
    private FridgeItem butter;
    private ArrayList<FridgeItem> eggsAndButter;
    private ArrayList<FridgeItem> noItems;

    @BeforeEach
    void runBefore() {
        FridgeItem eggs;
        noItems = new ArrayList<>();
        DateTimeFormatter localDateToString = DateTimeFormatter.ofPattern("d/MM/yyyy");

        eggs = new FridgeItem("eggs",
                "meat",
                5.99,
                LocalDate.of(2021, 10, 30).format(localDateToString));

        butter = new FridgeItem("butter",
                "dairy",
                6.99,
                LocalDate.of(2021, 10, 20).format(localDateToString));

        eggsAndButter = new ArrayList<FridgeItem>();
        {
            eggsAndButter.add(eggs);
            eggsAndButter.add(butter);
        }
    }

    @Test
    void testConstructor() {
        JsonWriter testJsonWriter = new JsonWriter("./data/testWriterEmptyFridge.json");
        assertEquals("./data/testWriterEmptyFridge.json", testJsonWriter.getDestination());
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Fridge f = new Fridge("MyFridge", eggsAndButter);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException was expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFridge() {
        try {
            Fridge f = new Fridge("MyFridge", noItems);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyFridge.json");
            writer.open();
            assertEquals(writer.getDestination(), "./data/testWriterEmptyFridge.json");
            writer.write(f);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyFridge.json");
            f = reader.read();
            assertEquals("MyFridge", f.getName());
            assertEquals(0, f.getFridgeItems().size());
        } catch (IOException e) {
            fail("Exception should not have been caught");
        }
    }

    @Test
    void testWriterFridgeWithEggsAndButter() {
        try {
            Fridge f = new Fridge("MyFridge", eggsAndButter);
            JsonWriter writer = new JsonWriter("./data/testWriterFridgeWithEggsAndButter.json");
            writer.open();
            writer.write(f);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFridgeWithEggsAndButter.json");
            f = reader.read();
            assertEquals("MyFridge", f.getName());
            List<FridgeItem> items = f.getFridgeItems();
            assertEquals(2, items.size());
            checkFridgeItem("eggs", "meat", 5.99, "30/10/2021", items.get(0));
            checkFridgeItem("butter", "dairy", 6.99, "20/10/2021", items.get(1));

        } catch (IOException e) {
            fail("Exception should not have been caught");
        }
    }

    @Test
    void testWrite() {
        Fridge f = new Fridge("MyFridge", eggsAndButter);
        JsonWriter writer = new JsonWriter("./data/testWriterFridgeWithEggsAndButter.json");
        JSONObject json = new JSONObject();
        json.put("name", f.getName());
        json.put("fridgeItems", f.fridgeItemsToJson());

        assertTrue(json.get("name").equals(f.getName()));
    }
}
