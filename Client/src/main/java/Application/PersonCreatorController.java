package Application;

import Language.Languages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import person.*;

import java.io.File;


public class PersonCreatorController {
    private final Stage stage;
    private Person person;
    private ColorE colorE;
    private Location location;

    public PersonCreatorController(Stage stage) {
        this.stage=stage;
    }

    public Person getPerson() {
        person=null;
        GridPane gridPane = new GridPane();
        Label name = new Label("Name");
        Label coordinateX = new Label("CoordinateX");
        Label coordinateY = new Label("CoordinateY");
        Label height = new Label("Height");
        Label eyeColor = new Label("EyeColor");
        Label hairColor = new Label("HairColor");
        Label country = new Label("Country");
        Label locationX = new Label("LocationX");
        Label locationY = new Label("LocationY");
        Label locationZ = new Label("LocationZ");
        Label locationName = new Label("LocationName");
        TextField nameField = new TextField();
        TextField coordinateXField = new TextField();
        TextField coordinateYField = new TextField();
        TextField heightField = new TextField();
        ObservableList<String> eye = FXCollections.observableArrayList("GREEN", "RED", "YELLOW", "BROWN");
        ComboBox<String> eyeColorField = new ComboBox<>(eye);
        eyeColorField.setValue("GREEN");
        ObservableList<String> hair = FXCollections.observableArrayList("RED", "BLACK", "ORANGE", "BROWN");
        ComboBox<String> hairColorField = new ComboBox<>(hair);
        hairColorField.setValue("RED");
        ObservableList<String> nationality = FXCollections.observableArrayList("USA", "SPAIN", "INDIA");
        ComboBox<String> countryField = new ComboBox<>(nationality);
        countryField.setValue("USA");
        TextField locationXField = new TextField();
        TextField locationYField = new TextField();
        TextField locationZField = new TextField();
        TextField locationNameField = new TextField();
        Button okButton = new Button("Ok");
        VBox vBox = new VBox();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(name, 0, 0);
        gridPane.add(coordinateX, 0, 1);
        gridPane.add(coordinateY, 0, 2);
        gridPane.add(height, 0, 3);
        gridPane.add(eyeColor, 0, 4);
        gridPane.add(hairColor, 0, 5);
        gridPane.add(country, 0, 6);
        gridPane.add(locationX, 0, 7);
        gridPane.add(locationY, 0, 8);
        gridPane.add(locationZ, 0, 9);
        gridPane.add(locationName, 0, 10);
        gridPane.add(nameField, 1, 0);
        gridPane.add(coordinateXField, 1, 1);
        gridPane.add(coordinateYField, 1, 2);
        gridPane.add(heightField, 1, 3);
        gridPane.add(eyeColorField, 1, 4);
        gridPane.add(hairColorField, 1, 5);
        gridPane.add(countryField, 1, 6);
        gridPane.add(locationXField, 1, 7);
        gridPane.add(locationYField, 1, 8);
        gridPane.add(locationZField, 1, 9);
        gridPane.add(locationNameField, 1, 10);
        vBox.getChildren().addAll(gridPane, okButton);
        VBox.setMargin(okButton, new Insets(20, 0, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        stage.setTitle("Person");
        stage.setScene(new Scene(vBox));
        okButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            try {
                StringBuilder stringBuilder = new StringBuilder();
                if (nameField.getText().equals("")) {
                    stringBuilder.append(Languages.getString("emptyName").get() +"\n");
                }if (Integer.parseInt(coordinateXField.getText())>408) {
                    stringBuilder.append(Languages.getString("coordinateXError").get()+"\n");
                }if (Integer.parseInt(coordinateYField.getText())<=-876) {
                    stringBuilder.append(Languages.getString("coordinateYError").get()+"\n");
                }if (Double.parseDouble(heightField.getText())<=0) {
                    stringBuilder.append(Languages.getString("heightError").get()+"\n");
                }if (locationNameField.getText().equals("")) {
                    stringBuilder.append(Languages.getString("emptyLocationName").get());
                }
                if (stringBuilder.length()==0) {
                    person = new Person(nameField.getText(), Integer.parseInt(coordinateXField.getText()), Integer.parseInt(coordinateYField.getText()), Double.parseDouble(heightField.getText()), ColorE.valueOf(eyeColorField.getValue()), ColorH.valueOf(hairColorField.getValue()), Country.valueOf(countryField.getValue()), Integer.parseInt(locationXField.getText()), Double.parseDouble(locationYField.getText()), Long.parseLong(locationZField.getText()), locationNameField.getText());
                    stage.close();
                }else {
                    alert.setContentText(stringBuilder.toString());
                    alert.showAndWait();
                }
            }catch (NumberFormatException e) {
                alert.setContentText(Languages.getString("numberFormatError").get());
                alert.showAndWait();
            }
        });
        stage.showAndWait();
        return person;
    }

    public Person updatePerson(Person person1) {
        person=null;
        GridPane gridPane = new GridPane();
        Label name = new Label("Name");
        Label coordinateX = new Label("CoordinateX");
        Label coordinateY = new Label("CoordinateY");
        Label height = new Label("Height");
        Label eyeColor = new Label("EyeColor");
        Label hairColor = new Label("HairColor");
        Label country = new Label("Country");
        Label locationX = new Label("LocationX");
        Label locationY = new Label("LocationY");
        Label locationZ = new Label("LocationZ");
        Label locationName = new Label("LocationName");
        TextField nameField = new TextField(person1.getName());
        TextField coordinateXField = new TextField(String.valueOf(person1.getCoordinateX()));
        TextField coordinateYField = new TextField(String.valueOf(person1.getCoordinateY()));
        TextField heightField = new TextField(String.valueOf(person1.getHeight()));
        ObservableList<String> eye = FXCollections.observableArrayList("GREEN", "RED", "YELLOW", "BROWN");
        ComboBox<String> eyeColorField = new ComboBox<>(eye);
        eyeColorField.setValue(person1.getEyeColor().name());
        ObservableList<String> hair = FXCollections.observableArrayList("RED", "BLACK", "ORANGE", "BROWN");
        ComboBox<String> hairColorField = new ComboBox<>(hair);
        hairColorField.setValue(person1.getHairColor().name());
        ObservableList<String> nationality = FXCollections.observableArrayList("USA", "SPAIN", "INDIA");
        ComboBox<String> countryField = new ComboBox<>(nationality);
        countryField.setValue(person1.getNationality().name());
        TextField locationXField = new TextField(String.valueOf(person1.getLocationX()));
        TextField locationYField = new TextField(String.valueOf(person1.getLocationY()));
        TextField locationZField = new TextField(String.valueOf(person1.getLocationZ()));
        TextField locationNameField = new TextField(person1.getLocationName());
        Button okButton = new Button("Ok");
        VBox vBox = new VBox();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(name, 0, 0);
        gridPane.add(coordinateX, 0, 1);
        gridPane.add(coordinateY, 0, 2);
        gridPane.add(height, 0, 3);
        gridPane.add(eyeColor, 0, 4);
        gridPane.add(hairColor, 0, 5);
        gridPane.add(country, 0, 6);
        gridPane.add(locationX, 0, 7);
        gridPane.add(locationY, 0, 8);
        gridPane.add(locationZ, 0, 9);
        gridPane.add(locationName, 0, 10);
        gridPane.add(nameField, 1, 0);
        gridPane.add(coordinateXField, 1, 1);
        gridPane.add(coordinateYField, 1, 2);
        gridPane.add(heightField, 1, 3);
        gridPane.add(eyeColorField, 1, 4);
        gridPane.add(hairColorField, 1, 5);
        gridPane.add(countryField, 1, 6);
        gridPane.add(locationXField, 1, 7);
        gridPane.add(locationYField, 1, 8);
        gridPane.add(locationZField, 1, 9);
        gridPane.add(locationNameField, 1, 10);
        vBox.getChildren().addAll(gridPane, okButton);
        VBox.setMargin(okButton, new Insets(20, 0, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        stage.setTitle("Person");
        stage.setScene(new Scene(vBox));
        okButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            try {
                StringBuilder stringBuilder = new StringBuilder();
                if (nameField.getText().equals("")) {
                    stringBuilder.append(Languages.getString("emptyName").get() +"\n");
                }if (Integer.parseInt(coordinateXField.getText())>408) {
                    stringBuilder.append(Languages.getString("coordinateXError").get()+"\n");
                }if (Integer.parseInt(coordinateYField.getText())<=-876) {
                    stringBuilder.append(Languages.getString("coordinateYError").get()+"\n");
                }if (Double.parseDouble(heightField.getText())<=0) {
                    stringBuilder.append(Languages.getString("heightError").get()+"\n");
                }if (locationNameField.getText().equals("")) {
                    stringBuilder.append(Languages.getString("emptyLocationName").get());
                }
                if (stringBuilder.length()==0) {
                    person = new Person(nameField.getText(), Integer.parseInt(coordinateXField.getText()), Integer.parseInt(coordinateYField.getText()), Double.parseDouble(heightField.getText()), ColorE.valueOf(eyeColorField.getValue()), ColorH.valueOf(hairColorField.getValue()), Country.valueOf(countryField.getValue()), Integer.parseInt(locationXField.getText()), Double.parseDouble(locationYField.getText()), Long.parseLong(locationZField.getText()), locationNameField.getText());
                    stage.close();
                }else {
                    alert.setContentText(stringBuilder.toString());
                    alert.showAndWait();
                }
            }catch (NumberFormatException e) {
                alert.setContentText(Languages.getString("numberFormatError").get());
                alert.showAndWait();
            }
        });
        stage.showAndWait();
        return person;
    }



    public int getId() {
        VBox vBox =new VBox();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Label id =new Label("Id");
        TextField idField = new TextField();
        hBox.getChildren().addAll(id,idField);
        Button button = new Button("Ok");
        vBox.getChildren().addAll(hBox,button);
        vBox.setAlignment(Pos.CENTER);
        stage.setTitle("Id");
        stage.setScene(new Scene(vBox));

        button.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            try {
                if (Integer.parseInt(idField.getText())<=0){
                    alert.setContentText(Languages.getString("idError").get());
                }else stage.close();
            }catch (NumberFormatException e) {
                alert.setContentText(Languages.getString("numberFormatError").get());
                alert.showAndWait();
            }
        });
        stage.showAndWait();
        return Integer.parseInt(idField.getText());
    }

    public ColorE getEyeColor() {
        VBox vBox =new VBox();
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        Label eyeColor =new Label("EyeColor");
        ObservableList<String> eye = FXCollections.observableArrayList("GREEN", "RED", "YELLOW", "BROWN");
        ComboBox<String> eyeColorField = new ComboBox<>(eye);
        hBox.getChildren().addAll(eyeColor,eyeColorField);
        Button button = new Button("Ok");
        vBox.getChildren().addAll(hBox,button);
        vBox.setAlignment(Pos.CENTER);
        stage.setTitle("EyeColor");
        stage.setScene(new Scene(vBox));
        colorE = null;

        button.setOnAction(event -> {
            try {
                colorE = ColorE.valueOf(eyeColorField.getValue());
                stage.close();
            }catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Eye Color");
                alert.setContentText(Languages.getString("colorError").get());
                alert.showAndWait();
            }
        });
        stage.showAndWait();
        return colorE;
    }

    public Location getLocation() {
        location=null;
        GridPane gridPane = new GridPane();
        Label locationX = new Label("LocationX");
        Label locationY = new Label("LocationY");
        Label locationZ = new Label("LocationZ");
        Label locationName = new Label("LocationName");
        TextField locationXField = new TextField();
        TextField locationYField = new TextField();
        TextField locationZField = new TextField();
        TextField locationNameField = new TextField();
        Button okButton = new Button("Ok");
        VBox vBox = new VBox();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(locationX, 0, 0);
        gridPane.add(locationY, 0, 1);
        gridPane.add(locationZ, 0, 2);
        gridPane.add(locationName, 0, 3);
        gridPane.add(locationXField, 1, 0);
        gridPane.add(locationYField, 1, 1);
        gridPane.add(locationZField, 1, 2);
        gridPane.add(locationNameField, 1, 3);
        vBox.getChildren().addAll(gridPane, okButton);
        VBox.setMargin(okButton, new Insets(20, 0, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        stage.setTitle("Location");
        stage.setScene(new Scene(vBox));
        okButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Location");
            try {
                if (locationNameField.getText().equals("")) {
                    alert.setContentText(Languages.getString("emptyLocationName").get());
                    alert.showAndWait();
                }else {
                    location = new Location(Integer.parseInt(locationXField.getText()), Double.parseDouble(locationYField.getText()), Long.parseLong(locationZField.getText()), locationNameField.getText());
                    stage.close();
                }
            }catch (NumberFormatException e) {
                alert.setContentText(Languages.getString("numberFormatError").get());
                alert.showAndWait();
            }
        });
        stage.showAndWait();
        return location;
    }

    public File getFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Script File");
        return fileChooser.showOpenDialog(stage);
    }
}
