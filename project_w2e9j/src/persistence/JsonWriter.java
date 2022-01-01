package model.project_w2e9j.src.persistence;

import model.project_w2e9j.src.Event;
import model.project_w2e9j.src.EventLog;
import model.project_w2e9j.src.Fridge;
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes JSON representation of fridge to file
// this class references code from JsonSerializationDemo, specifically code from the JsonWriter Class
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Simple destinator getter
    // EFFECTS: gets this destination
    public String getDestination() {
        return destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of fridge to file
    public void write(Fridge f) {
        JSONObject json = f.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
        EventLog.getInstance().logEvent(new Event("Saved fridge to " + destination));
    }
}
