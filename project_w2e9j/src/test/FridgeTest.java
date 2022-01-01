package model.project_w2e9j.src.test;

import model.project_w2e9j.src.Fridge;
import model.project_w2e9j.src.FridgeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FridgeTest {
    private Fridge testFridge;
    private ArrayList<FridgeItem> noItems = new ArrayList<>();
    private DateTimeFormatter localDateToString = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    FridgeItem soySauce = new FridgeItem("soySauce",
            "sauce",
            7.99,
            LocalDate.of(2021, 12, 25).format(localDateToString));

    FridgeItem eggs = new FridgeItem("eggs",
            "Meat",
            5.99,
            LocalDate.of(2021, 10, 30).format(localDateToString));

    FridgeItem butter = new FridgeItem("butter",
            "Dairy",
            6.99,
            LocalDate.of(2021, 10, 20).format(localDateToString));

    ArrayList<FridgeItem> eggsAndButter = new ArrayList<>();

    {
        eggsAndButter.add(eggs);
        eggsAndButter.add(butter);
    }

    @BeforeEach
    void runBefore() {
        testFridge = new Fridge("tester", noItems);
    }

    @Test
    void testConstructor() {
        assertEquals("tester", testFridge.getName());
        assertEquals(noItems, testFridge.getFridgeItems());
    }

    @Test
    void testFridgeWithItems() {
        testFridge = new Fridge("tester", eggsAndButter);

        assertEquals("tester", testFridge.getName());
        assertEquals(2, testFridge.getFridgeItems().size());
        assertTrue(eggsAndButter.contains(eggs));
        assertTrue(eggsAndButter.contains(butter));
    }

    @Test
    void testAddItem() {
        testFridge.addItem(soySauce);

        assertTrue(testFridge.getFridgeItems().contains(soySauce));
        assertEquals(1, testFridge.getFridgeItems().size());
    }

    @Test
    void testRemoveItemFromEmptyFridge() {
        testFridge.removeItem(eggs);

        assertEquals(0, testFridge.getFridgeItems().size());
        assertFalse(testFridge.getFridgeItems().contains(eggs));
        assertTrue(testFridge.getFridgeItems().isEmpty());
    }

    @Test
    void testRemoveItemNotInFridge() {
        testFridge.addItem(soySauce);
        testFridge.addItem(eggs);
        testFridge.removeItem(butter);

        assertEquals(2, testFridge.getFridgeItems().size());
        assertTrue(testFridge.getFridgeItems().contains(soySauce));
        assertTrue(testFridge.getFridgeItems().contains(eggs));
        assertFalse(testFridge.getFridgeItems().contains(butter));
    }

    @Test
    void testRemoveItem() {
        testFridge.addItem(eggs);
        testFridge.addItem(butter);
        testFridge.removeItem(eggs);

        assertEquals(1, testFridge.getFridgeItems().size());
        assertEquals(butter, testFridge.getFridgeItems().get(0));
        assertTrue(testFridge.getFridgeItems().contains(butter));
        assertFalse(testFridge.getFridgeItems().contains(eggs));

    }

    @Test
    void testEmptyFridgeWorth() {
        testFridge.fridgeWorth();

        assertEquals("$0.00", testFridge.fridgeWorth());
        assertTrue(testFridge.getFridgeItems().isEmpty());
    }

    @Test
    void testFridgeWithItemsWorth() {
        testFridge.addItem(soySauce);
        testFridge.addItem(eggs);
        testFridge.addItem(butter);
        testFridge.fridgeWorth();

        assertEquals("$20.97", testFridge.fridgeWorth());
        assertEquals(3, testFridge.getFridgeItems().size());
    }

    @Test
    void testListItemsInEmptyFridge() {
        testFridge.listItems();

        assertEquals(0, testFridge.listItems().size());
        assertTrue(testFridge.listItems().isEmpty());
    }

    @Test
    void testListItems() {
        testFridge.addItem(soySauce);
        testFridge.addItem(butter);
        testFridge.listItems();

        assertEquals(2, testFridge.listItems().size());
        assertTrue(testFridge.listItems().contains("soySauce"));
        assertTrue(testFridge.listItems().contains("butter"));
    }

    @Test
    void testFindItemInEmptyFridge() {
        assertNull(testFridge.findItem("soySauce"));
    }

    @Test
    void testFindItemNotInFridge() {
        testFridge.addItem(soySauce);
        testFridge.addItem(butter);
        testFridge.addItem(eggs);

        assertNull(testFridge.findItem("Eggs"));
        assertNull(testFridge.findItem("milk"));
    }

    @Test
    void testFindItemInFridge() {
        testFridge.addItem(soySauce);
        testFridge.addItem(butter);
        testFridge.addItem(eggs);

        assertEquals(soySauce, testFridge.findItem("soySauce"));
        assertEquals(butter, testFridge.findItem("butter"));
        assertEquals(eggs, testFridge.findItem(("eggs")));
    }

    @Test
    void testFindExpiryOnEmptyFridge() {
        assertEquals(-99999999, testFridge.checkExpiry("soySauce"));
    }

    @Test
    void testFindExpiryOnItemNotInFridge() {
        testFridge.addItem(eggs);

        assertEquals(-99999999, testFridge.checkExpiry("soySauce"));
    }

    @Test
    void testFindExpiryOnItemInFridge() {
        LocalDate threeDaysAfter = LocalDate.now().plusDays(3);
        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        FridgeItem expiresInThreeDays = new FridgeItem(
                "3",
                "",
                0,
                threeDaysAfter.format(localDateToString));
        FridgeItem expiredThreeDaysAgo = new FridgeItem("-3",
                "",
                0,
                threeDaysAgo.format(localDateToString));
        testFridge.addItem(expiresInThreeDays);
        testFridge.addItem(expiredThreeDaysAgo);

        assertEquals(3, testFridge.checkExpiry("3"));
        assertEquals(-3, testFridge.checkExpiry("-3"));
    }
}