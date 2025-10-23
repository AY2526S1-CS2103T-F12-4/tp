package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    @FXML
    private Circle statusIndicator;

    @FXML
    private Label statusLabel;

    public ResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);

        // Dynamically adjust height based on content length
        adjustHeightForContent(feedbackToUser);

        // Set status based on feedback content
        if (feedbackToUser.toLowerCase().contains("error")
                || feedbackToUser.toLowerCase().contains("invalid")
                || feedbackToUser.toLowerCase().contains("failed")) {
            setErrorStatus();
        } else if (feedbackToUser.toLowerCase().contains("added")
                || feedbackToUser.toLowerCase().contains("deleted")
                || feedbackToUser.toLowerCase().contains("updated")
                || feedbackToUser.toLowerCase().contains("success")) {
            setSuccessStatus();
        } else {
            setInfoStatus();
        }
    }

    /**
     * Adjusts the height of the result display based on content length.
     */
    private void adjustHeightForContent(String content) {
        // Ultra-aggressive approach - force large height
        String[] lines = content.split("\n");
        int totalLines = 0;

        for (String line : lines) {
            // Be very conservative - assume 40 characters per display line
            int displayLines = Math.max(1, (line.length() + 39) / 40);
            totalLines += displayLines;
        }

        // Force much larger height - be extremely generous
        int targetLines = Math.max(5, Math.min(15, totalLines + 2)); // Add buffer

        // Set very large height to ensure no scrolling
        double lineHeight = 22; // Even larger line height
        double targetHeight = targetLines * lineHeight;

        // Apply ultra-aggressive height settings
        resultDisplay.setPrefRowCount(targetLines);
        resultDisplay.setMinHeight(targetHeight);
        resultDisplay.setMaxHeight(targetHeight + 40); // Much more extra space
        resultDisplay.setPrefHeight(targetHeight);
    }

    private void setSuccessStatus() {
        statusIndicator.setFill(Color.LIMEGREEN);
        statusLabel.setText("Success");
        statusLabel.setStyle("-fx-text-fill: #4CAF50;");
    }

    private void setErrorStatus() {
        statusIndicator.setFill(Color.RED);
        statusLabel.setText("Error");
        statusLabel.setStyle("-fx-text-fill: #F44336;");
    }

    private void setInfoStatus() {
        statusIndicator.setFill(Color.DODGERBLUE);
        statusLabel.setText("Info");
        statusLabel.setStyle("-fx-text-fill: #2196F3;");
    }

}
