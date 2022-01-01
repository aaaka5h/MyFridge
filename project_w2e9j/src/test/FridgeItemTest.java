package model.project_w2e9j.src.test;

import model.project_w2e9j.src.FridgeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FridgeItemTest {
    private FridgeItem testFridgeItem;
    private DateTimeFormatter localDateToString = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeEach
    void runBefore() {
        testFridgeItem = new FridgeItem("tester",
                "unknown type",
                5.99,
                LocalDate.now().format(localDateToString));
    }

    @Test
    void testConstructor() {
        assertEquals("tester", testFridgeItem.getName());
        assertEquals("unknown type", testFridgeItem.getFoodType());
        assertEquals(5.99, testFridgeItem.getPrice());
        assertEquals(LocalDate.now().format(localDateToString), testFridgeItem.getExpiresOn());
    }

    @Test
    void testItemExpiringToday() {
        assertEquals(0, testFridgeItem.daysTilExpiry());
    }

    @Test
    void testItemExpiringTomorrow() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        testFridgeItem = new FridgeItem(
                "tester",
                "unknown type",
                5.99,
                tomorrow.format(localDateToString));

        assertEquals(1, testFridgeItem.daysTilExpiry());
        assertEquals(tomorrow, testFridgeItem.getExpiresOnLocalDate());
    }

    @Test
    void testItemExpiringInFuture() {
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        testFridgeItem = new FridgeItem("tester",
                "unknown type",
                5.99,
                nextWeek.format(localDateToString));

        assertEquals(7, testFridgeItem.daysTilExpiry());
        assertEquals(nextWeek, testFridgeItem.getExpiresOnLocalDate());
    }

    @Test
    void testItemExpiredYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        testFridgeItem = new FridgeItem(
                "tester",
                "unknown type",
                5.99,
                yesterday.format(localDateToString));

        assertEquals(-1, testFridgeItem.daysTilExpiry());
        assertEquals(yesterday, testFridgeItem.getExpiresOnLocalDate());

    }

    @Test
    void testItemExpiredLastWeek() {
        LocalDate lastWeek = LocalDate.now().minusDays(7);
        testFridgeItem = new FridgeItem("tester",
                "unknown type",
                5.99,
                lastWeek.format(localDateToString));

        assertEquals(-7, testFridgeItem.daysTilExpiry());
        assertEquals(lastWeek, testFridgeItem.getExpiresOnLocalDate());

    }


}
