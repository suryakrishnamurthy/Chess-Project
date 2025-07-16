module com.example.chessui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chessui to javafx.fxml;
    exports com.example.chessui;
}