package persistence;

import java.io.PrintWriter;

// Represents data that can be saved to file
public interface Saveable { // taken largely from TellerApp
    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to
    void save(PrintWriter printWriter);
}
