module com.railway {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.railway.controller to javafx.fxml;
    opens com.railway.model to javafx.base;
    exports com.railway;
}
