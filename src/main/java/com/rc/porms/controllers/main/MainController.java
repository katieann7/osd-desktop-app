package com.rc.porms.controllers.main;

import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.user.dao.UserDao;
import com.rc.porms.data.user.dao.impl.UserDaoImpl;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class MainController {

    public Text error;

    public Text error1;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordShown;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ToggleButton toggleButton;

    @FXML
    private Button logButton;

    private User user;

    UserDao userFacade = new UserDaoImpl();

    @FXML
    protected void initialize() {
        // Add listeners to text fields to enable/disable the login button
        ChangeListener<String> textFieldListener = (observable, oldValue, newValue) -> {
            logButton.setDisable(usernameField.getText().isEmpty() || passwordField.getText().isEmpty());
        };

        usernameField.textProperty().addListener(textFieldListener);
        passwordField.textProperty().addListener(textFieldListener);

        // Initially disable the login button
        logButton.setDisable(true);
    }
    @FXML
    protected void logButtonOnAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getCharacters().toString();
        String password2 = passwordShown.getText();

        try {
            User currentUser = userFacade.getUserByUsername(username);

            if (username.isEmpty()) {
                error1.setText("Username is required.");
                error1.setFill(Color.RED);
            } else if (username != null) {
                error1.setText("");
            }
            if (currentUser == null && !username.isEmpty()) {
                error1.setText("Username does not exist");
                error1.setFill(Color.RED);
            }
            if (password.isEmpty()){
                error.setText("Password is required.");
                error.setFill(Color.RED);
            }else if (username != null ) {
                error1.setText("");
            }

            if(currentUser != null && BCrypt.checkpw(password, currentUser.getPassword())) {
                if(checkRoleAdmin(currentUser).equals("admin")){
                    showAlert("Login Successful", "Welcome " + username + "!", Alert.AlertType.INFORMATION);
                    openAdminDashboardWindow(event);
                } else if((checkRoleAdmin(currentUser).equals("prefect"))){
                    showAlert("Login Successful", "Welcome " + username + "!", Alert.AlertType.INFORMATION);
                    openPrefectDashboardWindow(event);
                }
            }if (!password.isEmpty()){
                error.setText("Incorrect Password");
                error.setFill(Color.RED);
            }else if (!password.isEmpty()){
                error.setText("");
            }
        } catch (Exception ex) {
            showAlert("Error", "An error occurred during login: " + ex.getMessage(), Alert.AlertType.ERROR);
            ex.printStackTrace();
        }
    }
    public String checkRoleAdmin(User currentUser){
        try{
            if(currentUser.getRole().equals("ROLE_ADMIN")){
                return "admin";
            } else if(currentUser.getRole().equals("ROLE_PREFECT")) {
                return "prefect";
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred during validating role: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return "user";
    }

    private static boolean isTestMode = false;

    public static void setTestMode(boolean testMode) {
        isTestMode = testMode;
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        if (isTestMode) {
            alert.show();
        } else {
            alert.showAndWait();
        }
    }

    private void openAdminDashboardWindow(ActionEvent event) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();

            Stage dashboardStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/UserList.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);

            dashboardStage.initStyle(StageStyle.UNDECORATED);

            dashboardStage.show();
        } catch (IOException e) {
            System.err.println("Error opening dashboard window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void openPrefectDashboardWindow(ActionEvent event) {
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
        } catch (IOException e) {
            System.err.println("Error opening dashboard window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void changeVisibility(ActionEvent event){
        if(toggleButton.isSelected()){
            passwordShown.setText(passwordField.getText());
            passwordShown.setVisible(true);
            passwordField.setVisible(false);
            toggleButton.setVisible(false);
            return;
        }
        passwordField.setText(passwordShown.getText());
        passwordField.setVisible(true);
        passwordShown.setVisible(false);
        toggleButton.setVisible(true);
    }

    @FXML
    void changeVisibility2(ActionEvent event){
        if(toggleButton.isSelected()){
            passwordField.setText(passwordShown.getText());
            passwordField.setVisible(true);
            passwordShown.setVisible(false);
            toggleButton.setVisible(true);
            return;
        }
        passwordShown.setText(passwordField.getText());
        passwordShown.setVisible(true);
        passwordField.setVisible(false);
        toggleButton.setVisible(false);
    }


    @FXML
    protected void quitApp(MouseEvent event) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
