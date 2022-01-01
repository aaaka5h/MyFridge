package model.project_w2e9j.src.persistence;

import org.json.JSONObject;

// this interface references code from JsonSerializationDemo, specifically code from the Writable Interface
// URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
