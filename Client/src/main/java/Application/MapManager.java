package Application;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import person.Person;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class MapManager {
    private static AnchorPane group;
    private static Map<String,Color> colorMap = new HashMap<>();
    private static final Random random = new Random();
    private static Map<Integer, Shape> shapeMap = new HashMap<>();
    private static Map<Integer, Text> textMap = new HashMap<>();

    public static void setCircles(AnchorPane group){MapManager.group=group;}

    public static Color getColor() {
        Color color;
        while (true) {
            color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
            if (!colorMap.containsValue(color)) {
                return color;
            }
        }
    }

    public static void drawPersons(BlockingQueue<Person> people) {
        for (Person person : people) {
                if (!colorMap.containsKey(person.getUser())) {
                    colorMap.put(person.getUser(), getColor());
                }
                Circle circle = new Circle(0,colorMap.get(person.getUser()));
                circle.setId(String.valueOf(person.getID()));
                circle.setStroke(Color.GRAY);

            Text text = new Text(String.valueOf(person.getID()));
            text.setFont(Font.font(person.getHeight()/6));
            text.translateXProperty().bind(circle.centerXProperty().subtract(text.getLayoutBounds().getWidth()/2));
            text.translateYProperty().bind(circle.centerYProperty().add(text.getLayoutBounds().getHeight()/4));

            KeyValue keyValueX = new KeyValue(circle.centerXProperty(),person.getCoordinateX());
            KeyValue keyValueY = new KeyValue(circle.centerYProperty(),person.getCoordinateY());
            KeyValue keyValueR = new KeyValue(circle.radiusProperty(),person.getHeight()/2);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1),keyValueX,keyValueY,keyValueR);
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(keyFrame);

            text.setFill(colorMap.get(person.getUser()));


            KeyValue keyValueT = new KeyValue(text.fillProperty(),Color.BLACK);
            KeyFrame keyFrame1 = new KeyFrame(Duration.millis(1),keyValueT);
            Timeline timeline1 = new Timeline();
            timeline1.getKeyFrames().add(keyFrame1);

            RotateTransition transition = new RotateTransition(Duration.seconds(0.5),text);
            transition.setToAngle(360);
            transition.setCycleCount(1);
            transition.setAutoReverse(true);


            timeline.play();
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    timeline1.play();
                    transition.play();
                }
            });
            Tooltip tooltip = new Tooltip();
            tooltip.setText("Id: " + person.getID()+"\n"+
                    "Name: " + person.getName()+"\n"+
                    "CoordinateX: " + person.getCoordinateX()+"\n"+
                    "CoordinateY: " + person.getCoordinateY()+"\n"+
                    "Date: " + person.getDate()+"\n"+
                    "Height: " + person.getHeight()+"\n"+
                    "EyeColor: " + person.getEyeColor().name()+"\n"+
                    "HairColor: " + person.getHairColor().name()+"\n"+
                    "Country: " + person.getNationality().name()+"\n"+
                    "LocationX: " + person.getLocationX()+"\n"+
                    "LocationY: " + person.getLocationY()+"\n"+
                    "LocationZ: " + person.getLocationZ()+"\n"+
                    "LocationName: " + person.getLocationName()+"\n"+
                    "User: " + person.getUser());
            Tooltip.install(circle,tooltip);
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
                    Label user = new Label("User: " + person.getUser());
                    vBox.getChildren().addAll(id, name, coordinateX, coordinateY, date, height, eye, hair, country, locationX, locationY, locationZ, locationName,user);
                    vBox.setAlignment(Pos.CENTER);
                    stage.setScene(new Scene(vBox));
                    stage.show();
                });
                shapeMap.put(person.getID(),circle);
                textMap.put(person.getID(),text);
                group.getChildren().add(circle);
                group.getChildren().add(text);
                //circles.add(label);
        }
    }

    public static void removeCircle(BlockingQueue<Person> people) {
            for (Person person : people) {
                group.getChildren().remove(shapeMap.get(person.getID()));
                group.getChildren().remove(textMap.get(person.getID()));
            }
    }

    public static void updateCircle(BlockingQueue<Person> people) {
        for (Person person : people) {
            Circle circle = (Circle) shapeMap.get(person.getID());
            Text text = textMap.get(person.getID());
            circle.setCenterX(Double.parseDouble(person.getCoordinateX().toString()));
            circle.setCenterY(Double.parseDouble(person.getCoordinateY().toString()));
            circle.setRadius(person.getHeight()/2);
            text.setFont(Font.font(person.getHeight()/6));
            text.translateXProperty().bind(circle.centerXProperty().subtract(text.getLayoutBounds().getWidth()/2));
            text.translateYProperty().bind(circle.centerYProperty().add(text.getLayoutBounds().getHeight()/4));
        }
    }
}
