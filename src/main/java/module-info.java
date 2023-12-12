module myshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports myshop.Controller to javafx.fxml;

    opens myshop.Controller to javafx.fxml;
    opens myshop to javafx.fxml;

    exports myshop;
}
