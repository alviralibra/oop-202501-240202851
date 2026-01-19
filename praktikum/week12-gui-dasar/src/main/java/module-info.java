module com.upb.agripos {
    requires javafx.controls;
    requires java.sql;

    opens com.upb.agripos to javafx.graphics;
    opens com.upb.agripos.view to javafx.graphics;
    exports com.upb.agripos;
}