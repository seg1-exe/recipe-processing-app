package presentation;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Arrays;
import java.util.List;

public class ResultWindow extends Stage {
    public ResultWindow(String results) {
        setTitle("Résultats");

        TextArea textArea = new TextArea(results);
        textArea.setEditable(false);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(textArea);
        layout.setPrefSize(600, 400);
        layout.setPadding(new Insets(10));

        Scene scene = new Scene(layout);
        setScene(scene);
    }
}
