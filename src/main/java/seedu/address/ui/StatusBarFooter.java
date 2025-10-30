package seedu.address.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Platform;
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
    private Label totalDisplayedStatus;

    @FXML
    private Label totalPresentStatus;

    /**
     * Creates a {@code StatusBarFooter} with the given {@code Path} and lists to observe.
     *
     * @param saveLocation path used to display save location.
     * @param filteredPersonList observable filtered list used to show number of displayed records.
     * @param allPersonList observable full list used to show total records present.
     */
    public StatusBarFooter(Path saveLocation, ObservableList<Person> filteredPersonList,
                           ObservableList<Person> allPersonList) {
        super(FXML);
        saveLocationStatus.setText(Paths.get(".").resolve(saveLocation).toString());
        // initialize the counts
        updateCounts(filteredPersonList.size(), allPersonList.size());

        // Listen for changes in both filtered and all person lists and update their counts
        ListChangeListener<Person> listener = change -> updateCounts(filteredPersonList.size(),
                allPersonList.size());

        filteredPersonList.addListener(listener);
        allPersonList.addListener(listener);
    }

    /**
     * Updates the displayed counts in the status bar footer in a thread-safe manner.
     *
     * @param displayedCount The number of patients currently shown in the filtered list view
     * @param presentCount The total number of patients stored in CLInic
     */
    private void updateCounts(int displayedCount, int presentCount) {
        if (Platform.isFxApplicationThread()) {
            totalDisplayedStatus.setText(String.format("Displayed: %d", displayedCount));
            totalPresentStatus.setText(String.format("Total Patients: %d", presentCount));
        } else {
            Platform.runLater(() -> {
                totalDisplayedStatus.setText(String.format("Displayed: %d", displayedCount));
                totalPresentStatus.setText(String.format("Total Patients: %d", presentCount));
            });
        }
    }

}
