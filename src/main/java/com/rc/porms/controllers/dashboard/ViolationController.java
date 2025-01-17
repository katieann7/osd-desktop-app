package com.rc.porms.controllers.dashboard;

import com.rc.porms.PrefectInfoMgtApplication;
import com.rc.porms.StudentInfoMgtApplication;
import com.rc.porms.appl.facade.employee.EmployeeFacade;
import com.rc.porms.appl.facade.prefect.violation.ViolationFacade;
import com.rc.porms.appl.facade.student.StudentFacade;
import com.rc.porms.appl.model.communityservice.CommunityService;
import com.rc.porms.appl.model.student.Student;
import com.rc.porms.appl.model.violation.Violation;
import com.rc.porms.controllers.modal.EditViolationController;
import com.rc.porms.controllers.search.SearchViolationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViolationController implements Initializable {
    @FXML
    private Button burgerButton;

    @FXML
    private ImageView burgerIcon;

    @FXML
    private AnchorPane sidebarPane;

    private boolean sidebarVisible = false;

    @FXML
    private ComboBox<String> filterBox;
    @FXML
    private TextField searchField;

    @FXML
    private ToggleButton searchButton;

    @FXML
    TableView tableView;

    private ViolationFacade violationFacade;

    private StudentFacade studentFacade;

    private EmployeeFacade employeeFacade;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PrefectInfoMgtApplication app = new PrefectInfoMgtApplication();
        violationFacade = app.getViolationFacade();

        initializeTable();
        setupSearchFieldListener();

        filterBox.getItems().addAll("All", "CETE", "CBAM");
        filterBox.setValue("All");

        filterBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if ("All".equals(newValue)) {
                populateTableWithAllViolations();
            } else {
                populateTableWithFilteredViolations(newValue);
            }
        });
    }
    private void populateTableWithAllViolations() {
        List<Violation> violations = violationFacade.getAllViolation();
        ObservableList<Violation> data = FXCollections.observableArrayList(violations);
        tableView.setItems(data);
    }

    private void populateTableWithFilteredViolations(String clusterName) {
        List<Violation> violations = violationFacade.getAllViolationByClusterName(clusterName);
        ObservableList<Violation> data = FXCollections.observableArrayList(violations);
        tableView.setItems(data);
    }


    private void initializeTable() {
        tableView.getItems().clear();
        List<Violation> violations = violationFacade.getAllViolation();
        ObservableList<Violation> data = FXCollections.observableArrayList(violations);
        tableView.setItems(data);

        TableColumn<Violation, String> studColumn = new TableColumn<>("STUDENT NAME");
        studColumn.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getStudent().getFirstName();
            String lastName = cellData.getValue().getStudent().getLastName();
            return new SimpleStringProperty(firstName + " " + lastName);
        });

        TableColumn<Violation, String> offenseColumn = new TableColumn<>("OFFENSE");
        offenseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffense().getDescription()));

        TableColumn<Violation, Integer> warningColumn = new TableColumn<>("WARNING NO.");
        warningColumn.setCellValueFactory(new PropertyValueFactory<>("warningNum"));

        TableColumn<Violation, Integer> csHoursColumn = new TableColumn<>("CS HOURS");
        csHoursColumn.setCellValueFactory(new PropertyValueFactory<>("commServHours"));

        TableColumn<Violation, String> disciplinaryColumn = new TableColumn<>("DISCIPLINARY ACTION");
        disciplinaryColumn.setCellValueFactory(new PropertyValueFactory<>("disciplinaryAction"));

        TableColumn<Violation, Timestamp> dateColumn = new TableColumn<>("DATE OF NOTICE");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfNotice"));
        dateColumn.setCellFactory(getDateCellFactory());

        TableColumn<Violation, String> approvedByColumn = new TableColumn<>("APPROVED BY");
        approvedByColumn.setCellValueFactory(cellData -> {
            String firstName = cellData.getValue().getApprovedBy().getFirstName();
            String lastName = cellData.getValue().getApprovedBy().getLastName();
            return new SimpleStringProperty(firstName + " " + lastName);
        });

        tableView.getColumns().addAll(studColumn, offenseColumn, warningColumn, csHoursColumn, disciplinaryColumn, dateColumn, approvedByColumn);
    }

    private void setupSearchFieldListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTableData(newValue);
        });
    }

    private void filterTableData(String searchTerm) {
        String selectedFilter = filterBox.getValue();
        List<Violation> violations;

        if ("All".equals(selectedFilter)) {
            violations = violationFacade.getAllViolation();
        } else {
            violations = violationFacade.getAllViolationByClusterName(selectedFilter);
        }

        ObservableList<Violation> filteredData = FXCollections.observableArrayList();

        for (Violation violation : violations) {
            String studentName = violation.getStudent().getFirstName().toLowerCase() + " " + violation.getStudent().getLastName().toLowerCase();
            if (studentName.contains(searchTerm.toLowerCase())) {
                filteredData.add(violation);
            }
        }

        tableView.setItems(filteredData);
    }


    private Callback<TableColumn<Violation, Timestamp>, TableCell<Violation, Timestamp>> getDateCellFactory() {
        return column -> new TableCell<>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            protected void updateItem(Timestamp item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    LocalDate date = item.toLocalDateTime().toLocalDate();
                    setText(formatter.format(date));
                }
            }
        };
    }

    @FXML
    protected void handleIconOffense(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/OffenseList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconViolationList(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ViolationList.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIconCommunityService(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CommunityService.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    protected void handleSubmitAddViolationButton(ActionEvent event) {
        Stage stage2 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage2.hide();
        showDashboard2();
    }

    private void showDashboard2() {
        try {
            Stage dashboardStage2 = new Stage();
            dashboardStage2.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(getClass().getResource("/views/AddViolation.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2);
            dashboardStage2.setScene(scene2);
            dashboardStage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showEditViolation(Violation violation, ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            Stage editStage = new Stage();
            editStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/EditViolation.fxml"));
            AnchorPane editLayout = new AnchorPane();
            editLayout = loader.load();
            EditViolationController editViolationController = loader.getController();
            editViolationController.setViolation(violation);
            Scene scene = new Scene(editLayout);
            editStage.setScene(scene);
            editStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @FXML
    private void handleSearchButton(ActionEvent event) {
        String searchTerm = searchField.getText().trim();

        StudentInfoMgtApplication app = new StudentInfoMgtApplication();
        StudentFacade studentFacade = app.getStudentFacade();

        List<Student> allStudents = studentFacade.getAllStudents();

        List<Student> matchingStudents = new ArrayList<>();
        for (Student student : allStudents) {
            String fullName = student.getFirstName().toLowerCase() + " " + student.getLastName().toLowerCase();

            if (fullName.contains(searchTerm.toLowerCase())) {
                matchingStudents.add(student);
            }
        }

        if (!matchingStudents.isEmpty()) {
            Student selectedStudent = matchingStudents.get(0);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SearchViolation.fxml"));

                SearchViolationController searchController = new SearchViolationController();
                searchController.initData(selectedStudent);
                loader.setController(searchController);
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Student Not Found", "No student found with the name: " + searchTerm);
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
