package com.rc.porms.controllers.modal;

import com.rc.porms.PrefectInfoMgtApplication;
import com.rc.porms.appl.facade.prefect.offense.OffenseFacade;
import com.rc.porms.appl.model.offense.Offense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AddOffenseController implements Initializable {
    public Text error;

    public Text error1;
    @FXML
    private TextField offenseField;

    @FXML
    private ComboBox<String> comboBox;

    private OffenseFacade offenseFacade;

    ObservableList <String> subjects;

    @FXML
    protected void saveAddOffenseClicked(ActionEvent event) {
        PrefectInfoMgtApplication app = new PrefectInfoMgtApplication();
        offenseFacade = app.getOffenseFacade();

        String offenseValue = offenseField.getText();
        String valOffense = comboBox.getValue();

        // Validate if the offense value is empty
        if (offenseValue.isEmpty()) {
            error.setText("Offense is empty. Please input Offense");
            error.setFill(Color.RED);
            return;
        } else {
            error.setText("");
        }

// Validate the length of the offense value
        if (offenseValue.length() > 32) {
            error.setText("Character must be 32 characters only");
            error.setFill(Color.RED);
            return;
        } else {
            error.setText("");
        }

// Validate if the offense value is alphanumeric
        if (!offenseValue.matches("[a-zA-Z0-9]+")) {
            error.setText("Alphanumeric characters only");
            error.setFill(Color.RED);
            return;
        } else {
            error.setText("");
        }

// Validate if the offense type is selected
        if (valOffense == null || valOffense.equals("Select offense type")) {
            error.setText("Error: Select an offense");
            error.setFill(Color.RED);
            return;
        } else {
            error.setText("");
        }

// Validate if the selected offense is either "major" or "minor"
        if (!valOffense.equalsIgnoreCase("major") && !valOffense.equalsIgnoreCase("minor")) {
            error1.setText("Error: Please select either 'major' or 'minor' offense");
            error1.setFill(Color.RED);
            return;
        } else {
            error1.setText("");
        }

        Offense addOffense = new Offense();
        addOffense.setType(comboBox.getValue());
        addOffense.setDescription(offenseField.getText());


        try {
            offenseFacade.addOffense(addOffense);
        } catch(Exception ex) {
            ex.printStackTrace();;
        }
        finally {
            try {
                //back to list after adding
                Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                previousStage.close();

                Stage dashboardStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/views/OffenseList.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);

                dashboardStage.initStyle(StageStyle.UNDECORATED);

                dashboardStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subjects = FXCollections.observableArrayList("Select a offense","Minor", "Major");
        comboBox.setItems(subjects);
        comboBox.getSelectionModel().select(0);
    }


    @FXML
    protected void handleCancelAddOffenseClicked(MouseEvent event) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();

            Stage dashboardStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/OffenseList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

