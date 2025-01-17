package com.rc.porms.controllers.modal;

import com.rc.porms.PrefectInfoMgtApplication;
import com.rc.porms.appl.facade.prefect.offense.OffenseFacade;
import com.rc.porms.appl.model.offense.Offense;
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

public class EditOffenseController implements Initializable {
    public Text error;
    public Text error1;
    @FXML
    private TextField offenseField;

    @FXML
    private ComboBox<String> comboBox;

    private Offense offense;

    private OffenseFacade offenseFacade;

    @FXML
    protected void saveEditOffenseClicked(ActionEvent event) {
        PrefectInfoMgtApplication app = new PrefectInfoMgtApplication();
        offenseFacade = app.getOffenseFacade();

        String offenseValue = offenseField.getText();
        String valOffense = comboBox.getValue();

        if (comboBox.getValue() == null || offenseField.getText().isEmpty()) {
            error.setText( "Error Missing Information: Please fill in all required fields.");
            error.setFill(Color.RED);
            return;
        }else{
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


        Offense editOffense = new Offense();
        editOffense.setId(offense.getId());
        editOffense.setType(comboBox.getValue());
        editOffense.setDescription(offenseField.getText());

//        System.out.println(offense.getId());
//        System.out.println(offense.getType());
//        System.out.println(offense.getDescription());
        try {
            offenseFacade.updateOffense(editOffense);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Offense Updated", "Offense successfully updated.");
        } catch(Exception ex) {
            error1.setText( "Error Update Failed: Failed to update offense. Please try again later.");
            error1.setFill(Color.RED);
            ex.printStackTrace();
        }
        finally {
            try {
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
        comboBox.getItems().addAll("Minor", "Major");
    }

    public void setOffense(Offense offense) {
        this.offense = offense;
        offense.getId();
        comboBox.setValue(offense.getType());
        offenseField.setText(offense.getDescription());
    }

    @FXML
    protected void handleCancelEditOffenseClicked(MouseEvent event) {
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
    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}