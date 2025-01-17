package com.rc.porms.controllers.dashboard;

import com.rc.porms.appl.facade.user.UserFacade;
import com.rc.porms.appl.facade.user.impl.UserFacadeImpl;
import com.rc.porms.appl.model.user.User;
import com.rc.porms.data.user.dao.UserDao;
import com.rc.porms.data.user.dao.impl.UserDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class TableViewController implements Initializable {

    @FXML
    private Button burgerButton;

    @FXML
    private ImageView burgerIcon;

    @FXML
    private AnchorPane sidebarPane;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<User> table;

    private UserFacade userFacade;

    private ObservableList<User> allUsers; // To store all users for filtering

    private boolean sidebarVisible = false;

    public TableViewController() {
        UserDao userDao = new UserDaoImpl();
        userFacade = new UserFacadeImpl(userDao);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        setupSearchFunctionality();
        refreshTable();
    }

    private void setupTable() {
        TableColumn<User, String> usernameColumn = new TableColumn<>("USERNAME");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        usernameColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, Timestamp> lastLoginColumn = new TableColumn<>("LAST LOGIN");
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLoginDate"));
        lastLoginColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        lastLoginColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, Timestamp> joinDateColumn = new TableColumn<>("JOIN DATE");
        joinDateColumn.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        joinDateColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        joinDateColumn.setCellFactory(getDateCellFactory());
        joinDateColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, String> roleColumn = new TableColumn<>("ROLE");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        roleColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<User, Integer> isLockedColumn = new TableColumn<>("IS LOCKED");
        isLockedColumn.setCellValueFactory(new PropertyValueFactory<>("isLocked"));
        isLockedColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
        isLockedColumn.setStyle("-fx-alignment: CENTER;");
        isLockedColumn.setCellFactory(column -> new TableCell<>() {
            private final Button lockButton = new Button();

            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                Image image = item == 1
                        ? new Image(getClass().getResourceAsStream("/assets/lock.png"))
                        : new Image(getClass().getResourceAsStream("/assets/unlock.png"));

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(16);
                imageView.setFitHeight(16);
                lockButton.setGraphic(imageView);

                lockButton.setOnAction(event -> {
                    User currentUser = getTableView().getItems().get(getIndex());
                    String username = currentUser.getUsername();

                    if (item == 1) {
                        userFacade.setUnLocked(username);
                    } else {
                        userFacade.setLocked(username);
                    }

                    refreshTable();
                });

                setGraphic(lockButton);
            }
        });

        table.getColumns().addAll(usernameColumn, lastLoginColumn, joinDateColumn, roleColumn, isLockedColumn);
    }

    private void refreshTable() {
        List<User> users = userFacade.getAllUsers();
        allUsers = FXCollections.observableArrayList(users);
        table.setItems(allUsers);
    }

    private void setupSearchFunctionality() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });
    }

    private void filterTable(String query) {
        if (query == null || query.isEmpty()) {
            table.setItems(allUsers);
            return;
        }

        String lowerCaseQuery = query.toLowerCase();

        ObservableList<User> filteredUsers = FXCollections.observableArrayList();
        for (User user : allUsers) {
            if (user.getUsername().toLowerCase().contains(lowerCaseQuery)) {
                filteredUsers.add(user);
            }
        }

        table.setItems(filteredUsers);
    }
     @FXML
     private void handleLogOut(ActionEvent event) throws IOException {
         Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         previousStage.close();

         Stage dashboardStage = new Stage();
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/views/MainView.fxml"));
         Parent root = loader.load();
         Scene scene = new Scene(root);
         dashboardStage.setScene(scene);

         dashboardStage.initStyle(StageStyle.UNDECORATED);

         dashboardStage.show();
     }
    @FXML
    private void toggleSidebarVisibility(ActionEvent event) {
        sidebarVisible = !sidebarVisible;
        sidebarPane.setVisible(sidebarVisible);

        if (sidebarVisible) {
            BorderPane.setMargin(sidebarPane, new Insets(0));
        } else {
            BorderPane.setMargin(sidebarPane, new Insets(0, -125.0, 0, 0));
        }
    }

    private Callback<TableColumn<User, Timestamp>, TableCell<User, Timestamp>> getDateCellFactory() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDate date = item.toLocalDateTime().toLocalDate();
                    setText(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            }
        };
    }

    @FXML
    protected void handleIconLogout (MouseEvent event) {
        try {
            Stage previousStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            previousStage.close();

            Stage dashboardStage = new Stage();
            dashboardStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/MainView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
