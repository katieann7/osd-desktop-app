package com.rc.porms.controllers;

import com.rc.porms.controllers.main.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.TestFx;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.util.NodeQueryUtils.isVisible;


class MainTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/views/MainView.fxml"));

        BorderPane mainLayout = new BorderPane();
        mainLayout = loader.load();

        Scene scene = new Scene(mainLayout);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }


    @TestFx
    public void testLoginValidation() {
        MainController.setTestMode(true);


        //testing kung ang makaka log in if walang ilagay sa username and password
        clickOn("#logButton");
        sleep(2000);


        //testing kung ang makaka log in if walang ilagay sa username
        clickOn("#passwordField");
        write("Password@123");
        clickOn("#logButton");
        sleep(2000);
        clickOn("#passwordField");
        eraseText(12);


        //testing kung ang makaka log in if walang ilagay sa password
        clickOn("#usernameField");
        write("conrad");
        clickOn("#logButton");
        sleep(2000);
        clickOn("#usernameField");
        eraseText(6);


        //testing kung makaka login if mali yung username tas tama yung password
        clickOn("#usernameField");
        write("wrongusername");
        clickOn("#passwordField");
        write("Password@123");
        clickOn("#logButton");
        sleep(2000);
        clickOn("#usernameField");
        eraseText(13);
        clickOn("#passwordField");
        eraseText(12);

        //testing kung makaka log in kahit mali yung password
        clickOn("#usernameField");
        write("conrad");
        clickOn("#passwordField");
        write("Wrongpass@123");
        clickOn("#eyeBrow");
        sleep(1000);
        clickOn("#eyeBrow");
        clickOn("#logButton");
        sleep(2000);
        clickOn("#usernameField");
        eraseText(6);
        clickOn("#passwordField");
        eraseText(13);

        //test kung makaka log in if tama yung credentials
        clickOn("#usernameField");
        write("conrad");
        clickOn("#passwordField");
        write("Password@123");
        clickOn("#logButton");
        sleep(1000);


        verifyThat("#table", isVisible());
        sleep(500);

        //test if makakapaglagay ng Minor and major offense kung walang description
        clickOn("#addOffenseButton");
        sleep(1000);
        verifyThat(".dialog-pane", isVisible());

        clickOn("#offenseField");
        write("");
        clickOn("#comboBox");
        sleep(1000);
        clickOn("Minor");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);


        clickOn("#comboBox");
        sleep(1000);
        clickOn("Major");
        sleep(1000);
        clickOn("#saveAddButton");


        clickOn("#cancelAddButton");
        sleep(2000);

        //test if makakapag input ng offense if walang major or minor
        clickOn("#addOffenseButton");
        sleep(1000);
        verifyThat(".dialog-pane", isVisible());

        clickOn("#offenseField");
        write("Test");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);
        clickOn("#cancelAddButton");

        //Test if pwede mag lagay ng alpha numeric sa offense
        clickOn("#addOffenseButton");
        sleep(1000);
        verifyThat(".dialog-pane", isVisible());

        clickOn("#offenseField");
        write("Smoking & Drinking");
        clickOn("#comboBox");
        sleep(1000);
        clickOn("Major");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);
        clickOn("#cancelAddButton");

        //test para makapag add ng offense minor
        clickOn("#addOffenseButton");
        sleep(1000);
        verifyThat(".dialog-pane", isVisible());

        clickOn("#offenseField");
        write("MinorTest");
        clickOn("#comboBox");
        sleep(1000);
        clickOn("Minor");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);

        //test para makapag add ng offense Major
        clickOn("#addOffenseButton");
        sleep(1000);
        verifyThat(".dialog-pane", isVisible());

        clickOn("#offenseField");
        write("MajorTest");
        clickOn("#comboBox");
        sleep(1000);
        clickOn("Major");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(2000);
        verifyThat("#table", isVisible());

        //Test para sa search ng offense pati filter

        clickOn("#searchField");
        sleep(1000);
        write("MajorTest");
        sleep(1000);
        eraseText(9);
        sleep(1000);
        write("MinorTest");
        sleep(1000);
        eraseText(9);
        sleep(1000);


        clickOn("#filterBox");
        sleep(500);
        clickOn("Minor");
        sleep(1000);


        clickOn("#filterBox");
        sleep(500);
        clickOn("Major");
        sleep(1000);


        clickOn("#filterBox");
        sleep(500);
        clickOn("All");
        sleep(1000);

        //test para sa edit ng offense

        clickOn("#editButton-0");
        sleep(1000);
        eraseText(1);
        sleep(500);
        write("EditMinor");
        sleep(1000);
        clickOn("#comboBox");
        sleep(1000);
        clickOn("Minor");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);


        moveTo(800, 330);
        clickOn(800, 330);
        sleep(1000);


        clickOn("#editButton-0");
        sleep(1000);
        eraseText(1);
        sleep(500);
        write("EditMajor");
        sleep(1000);
        clickOn("#comboBox");
        clickOn("Major");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);

        moveTo(800, 330);
        clickOn(800, 330);
        sleep(1000);



        clickOn("#burgerButton");
        sleep(1000);
        clickOn("#violationlistButton");
        sleep(1000);

        clickOn("#addviolationButton");
        sleep(1000);

        //test if makakakapag save ng violation kahit student lang ilalagay

        clickOn("#studentIdField");
        eraseText(8);
        sleep(500);
        write("CT21-0122");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);
        clickOn("#cancelAddButton");
        sleep(1000);

        //check if makakapag add ng violation kahit walang student na ilagay


        clickOn("#addviolationButton");
        sleep(1000);
        clickOn("#studentIdField");
        sleep(1000);
        write("CT210122");
        sleep(1000);
        clickOn("#offenseComboBox");
        sleep(1000);
        clickOn("Haircut");
        sleep(1000);
        clickOn("#warningNumField");
        write("1");
        sleep(1000);
        clickOn("#csHoursField");
        write("4");
        sleep(1000);
        clickOn("#disciplinaryField");
        write("Cleaning");
        sleep(1000);
        clickOn(820, 490);
        sleep(1000);
        clickOn(590, 590);
        sleep(1000);
        clickOn("#employeeIdField");
        write("111");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);

        //check if makakapag add ng violation kahit future na date ang ilagay


        clickOn("#addviolationButton");
        sleep(1000);
        clickOn("#studentIdField");
        sleep(1000);
        write("CT21-0122");
        sleep(1000);
        clickOn("#offenseComboBox");
        sleep(1000);
        clickOn("Haircut");
        sleep(1000);
        clickOn("#warningNumField");
        write("1");
        sleep(1000);
        clickOn("#csHoursField");
        write("4");
        sleep(1000);
        clickOn("#disciplinaryField");
        write("Cleaning");
        sleep(1000);
        clickOn(820, 490);
        sleep(1000);
        clickOn(590, 590);
        clickOn(675, 490);
        eraseText(4);
        write("2030");
        sleep(1000);
        clickOn("#employeeIdField");
        write("111");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);

        //check kung makakapag add ng violation kung may letter sa occurence at sa cs hours

        clickOn("#addviolationButton");
        sleep(1000);
        clickOn("#studentIdField");
        sleep(1000);
        write("CT21-0122");
        sleep(1000);
        clickOn("#offenseComboBox");
        sleep(1000);
        clickOn("Haircut");
        sleep(1000);
        clickOn("#warningNumField");
        write("one");
        sleep(1000);
        clickOn("#csHoursField");
        write("4");
        sleep(1000);
        clickOn("#disciplinaryField");
        write("Cleaning");
        sleep(1000);
        clickOn(820, 490);
        sleep(1000);
        clickOn(590, 590);
        sleep(1000);
        clickOn("#employeeIdField");
        write("111");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);
        clickOn("#warningNumField");
        eraseText(4);
        write("1");
        clickOn("#csHoursField");
        eraseText(1);
        write("four");
        clickOn("#saveAddButton");
        sleep(1000);
        clickOn("#csHoursField");
        eraseText(4);
        write("4");
        sleep(1000);
        clickOn("#saveAddButton");
        sleep(1000);

        //test if nagana ang search sa violation


        clickOn("#searchField");
        sleep(1000);
        write("Conrad");
        sleep(1000);

        clickOn("#searchButton");
        sleep(3000);



    }
}