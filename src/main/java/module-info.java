module com.example.aoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.common;


    opens com.example.aoc to javafx.fxml;
    exports com.example.aoc;
    exports com.example.aoc.day7;
    opens com.example.aoc.day7 to javafx.fxml;
    exports com.example.aoc.day8;
    opens com.example.aoc.day8 to javafx.fxml;
    exports com.example.aoc.day1;
    opens com.example.aoc.day1 to javafx.fxml;
    exports com.example.aoc.day4;
    opens com.example.aoc.day4 to javafx.fxml;
    exports com.example.aoc.day5;
    opens com.example.aoc.day5 to javafx.fxml;
    exports com.example.aoc.day6;
    opens com.example.aoc.day6 to javafx.fxml;
}