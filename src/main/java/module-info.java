module tech.octopusdragon.helloworld {
    requires javafx.controls;
    requires javafx.fxml;


    opens tech.octopusdragon.helloworld to javafx.fxml;
    exports tech.octopusdragon.helloworld;
}