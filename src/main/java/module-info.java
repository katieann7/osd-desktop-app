module osd.records.mgt.desktop.app {
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires org.slf4j;
    requires org.apache.logging.log4j.slf4j2.impl;
    requires jbcrypt;
    requires com.oracle.database.jdbc;

    opens com.rc.porms to javafx.fxml;
    opens com.rc.porms.controllers.main to javafx.fxml;
    opens com.rc.porms.controllers to javafx.fxml;
    opens com.rc.porms.appl.model.user to javafx.fxml;
    opens com.rc.porms.appl.model.offense to javafx.fxml;
    opens com.rc.porms.controllers.dashboard to javafx.fxml;
    opens com.rc.porms.controllers.modal to javafx.fxml;
    opens com.rc.porms.controllers.search to javafx.fxml;
    opens com.rc.porms.appl.model.violation to javafx.fxml;

    exports com.rc.porms.controllers;
    exports com.rc.porms;
    exports com.rc.porms.controllers.main;
    exports com.rc.porms.appl.model.user;
    exports com.rc.porms.appl.model.offense;
    exports com.rc.porms.controllers.dashboard;
    exports com.rc.porms.controllers.modal;
    exports com.rc.porms.controllers.search;
    exports com.rc.porms.appl.model.violation;

}