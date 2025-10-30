package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;

    @FXML
    private Label totalRecordsStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path}.
     */
    public StatusBarFooter(Path saveLocation, ObservableList<Person> filteredPersonList) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        // initialize total records text
        updateTotalRecords(filteredPersonList.size());

        // listen for changes to the filtered list and update label
        filteredPersonList.addListener((ListChangeListener<Person>) change -> {
            updateTotalRecords(filteredPersonList.size());
        });
    }

    private void updateTotalRecords(int size) {
        totalRecordsStatus.setText(String.format("Total patients: %d", size));
    }

}
