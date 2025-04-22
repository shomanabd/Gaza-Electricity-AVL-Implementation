module com.example.datastructureproject3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.datastructureproject3 to javafx.fxml;
    exports com.example.datastructureproject3;
}