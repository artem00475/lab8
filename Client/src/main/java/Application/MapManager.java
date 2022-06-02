package Application;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import person.Person;

import java.util.concurrent.BlockingQueue;

public class MapManager {
    private static ObservableList<Circle> circles;

    public static void setCircles(ObservableList<Circle> circles){MapManager.circles=circles;}

    public static void drawPersons(BlockingQueue<Person> people) {
        Platform.runLater(()-> {
            circles.clear();
            for (Person person : people) {
                Circle circle = new Circle(person.getCoordinateX(), person.getCoordinateY(), person.getHeight() / 2, Color.GRAY);
                circle.setFill(Color.WHITE);
                circle.setStroke(Color.GRAY);
                circle.setOnMouseClicked(event -> {
                    Stage stage = new Stage();
                    VBox vBox = new VBox();
                    Label id = new Label("Id: " + person.getID());
                    Label name = new Label("Name: " + person.getName());
                    Label coordinateX = new Label("CoordinateX: " + person.getCoordinateX());
                    Label coordinateY = new Label("CoordinateY: " + person.getCoordinateY());
                    Label date = new Label("Date: " + person.getDate());
                    Label height = new Label("Height: " + person.getHeight());
                    Label eye = new Label("EyeColor: " + person.getEyeColor().name());
                    Label hair = new Label("HairColor: " + person.getHairColor().name());
                    Label country = new Label("Country: " + person.getNationality().name());
                    Label locationX = new Label("LocationX: " + person.getLocationX());
                    Label locationY = new Label("LocationY: " + person.getLocationY());
                    Label locationZ = new Label("LocationZ: " + person.getLocationZ());
                    Label locationName = new Label("LocationName: " + person.getLocationName());
                    vBox.getChildren().addAll(id, name, coordinateX, coordinateY, date, height, eye, hair, country, locationX, locationY, locationZ, locationName);
                    vBox.setAlignment(Pos.CENTER);
                    stage.setScene(new Scene(vBox));
                    stage.show();
                });
                circles.add(circle);
            }
        });
    }
}
