package Application;

import Client.ClientManager;
import Client.TableManager;
import Requests.RecieveManager;
import Requests.SendManager;
import exceptions.ConnectionException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import person.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public class App extends Application {
    private Button reg;
    private Button log;
    private final Button helpCommand = new Button("Help");
    private final Button addCommand = new Button("Add");
    private final Button addIfMaxCommand = new Button("Add If Max");
    private final Button clearCommand = new Button("Clear");
    private final Button countCommand = new Button("Count Greater Than Location");
    private final Button scriptCommand = new Button("Script");
    private final Button filterCommand = new Button("Filter Less Than Eye Color");
    private final Button infoCommand = new Button("Info");
    private final Button printCommand = new Button("Print Field Ascending Location");
    private final Button removeByIdCommand = new Button("Remove By Id");
    private final Button removeGreaterCommand = new Button("Remove Greater");
    private final Button removeHeadCommand = new Button("Remove Head");
    private final Button updateCommand = new Button("Update");
    private TextField user;
    private PasswordField passwordField;
    private LoginManager loginManager;
    private static ClientManager clientManager;
    private CommandsManager commandsManager;
    private TableView<Person> tableView;
    private static ObservableList<Person> people;

    public static void setRequestManagers(RecieveManager recieveManager, SendManager sendManager, TableManager tableManager) {
        clientManager = new ClientManager(sendManager,recieveManager,tableManager);
    }

    @Override
    public void start(Stage primaryStage) {
        Stage stage =new Stage();
        PersonCreatorController personCreatorController = new PersonCreatorController(stage);
        commandsManager=new CommandsManager(clientManager, personCreatorController);
        loginManager = new LoginManager(clientManager,commandsManager);
        primaryStage.setTitle("Login in");
        primaryStage.setScene(setLoginWindowScene());
        primaryStage.show();



        reg.setOnAction(event -> {
            try {
                loginManager.signUp(user.getText(), passwordField.getText());
            }catch (ConnectionException ignored) {}
        });
        log.setOnAction(event -> {
            try {
                if (loginManager.signIn(user.getText(), passwordField.getText())) {
                    try {
                        primaryStage.setScene(setMainWindowScene(loginManager.getUserName()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }catch (ConnectionException ignored) {}
        });
        helpCommand.setTooltip(new Tooltip("Click the button\nto see commands help"));
        helpCommand.setOnAction(event -> commandsManager.help());
        infoCommand.setTooltip(new Tooltip("Click the button\nto see info about collection"));
        infoCommand.setOnAction(event -> commandsManager.info());
        printCommand.setTooltip(new Tooltip("Click the button\nto print fields ascending Location"));
        printCommand.setOnAction(event -> commandsManager.print());
        addCommand.setTooltip(new Tooltip("Click the button\nto add element"));
        addCommand.setOnAction(event -> {
            //commandsManager.setPersonStage();
            commandsManager.add();
//                tableView.setItems(people);
            //people.add(new Person(3,"artem",10,10, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").parse("12:23:46 24.03.2022"),140.0, ColorE.BROWN, ColorH.BLACK, Country.INDIA,10,10.0,Long.parseLong("10"),"1231"));
        });
        addIfMaxCommand.setTooltip(new Tooltip("Click the button\nto add element that is more then max"));
        addIfMaxCommand.setOnAction(event -> commandsManager.addIfMax());
        removeByIdCommand.setTooltip(new Tooltip("Click the button\nto remove element by id"));
        removeByIdCommand.setOnAction(event -> commandsManager.removeById());
        updateCommand.setTooltip(new Tooltip("Click the button\nto update element by id"));
        updateCommand.setOnAction(event -> commandsManager.updateById());
        removeHeadCommand.setTooltip(new Tooltip("Click the button\nto remove first element"));
        removeHeadCommand.setOnAction(event -> commandsManager.removeHead());
        removeGreaterCommand.setTooltip(new Tooltip("Click the button\nto remove elements that greater than it"));
        removeGreaterCommand.setOnAction(event -> commandsManager.removeGreater());
        clearCommand.setTooltip(new Tooltip("Click the button\nto clear collection"));
        clearCommand.setOnAction(event -> commandsManager.clear());
        filterCommand.setTooltip(new Tooltip("Click the button\nto filter less than EyeColor"));
        filterCommand.setOnAction(event -> commandsManager.filterEyeColor());
        countCommand.setTooltip(new Tooltip("Click the button\nto count elements with greater Location"));
        countCommand.setOnAction(event -> commandsManager.countLocation());
        scriptCommand.setTooltip(new Tooltip("Click the button\nto execute script from file"));
        scriptCommand.setOnAction(event -> commandsManager.script());




    }

    public static void run(String[] args, ObservableList<Person> people) {
        App.people=people;
        App.launch(args);
    }

    public static void exit() {clientManager.exit();}

    public Scene setLoginWindowScene() {
        user = new TextField();
        passwordField = new PasswordField();
        reg = new Button("Sign Up");
        log = new Button("Sign in");
        VBox vBox = new VBox();
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(new Label("UserName: "), user);
        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(new Label("Password: "), passwordField);
        HBox hBox3 = new HBox();
        hBox3.getChildren().addAll(reg, log);
        hBox3.setSpacing(20);
        hBox3.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(new Label("Welcome"),hBox1, hBox2, hBox3);
        VBox.setMargin(hBox2, new Insets(5, 0, 0, 0));
        VBox.setMargin(hBox3, new Insets(10, 0, 0, 0));
        VBox.setMargin(hBox1, new Insets(10, 0, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 400, 300);
    }

    public Scene setMainWindowScene(String userName) throws ParseException {
        StackPane mainWindow = new StackPane();
        //HBox hBox = new HBox();
        Label userN = new Label(userName);
        TabPane tabPane = new TabPane();
        Tab tab =new Tab();
        tab.setText("Table");
        tableView =new TableView<>();
        //people.addAll(new Person(1,"artem",10,10, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").parse("12:23:46 24.03.2022"),140.0, ColorE.BROWN, ColorH.BLACK, Country.INDIA,10,10.0,Long.parseLong("10"),"1231"),
                //new Person(2,"artem",10,10, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").parse("12:23:46 24.03.2022"),150.0, ColorE.BROWN, ColorH.BLACK, Country.INDIA,10,10.0,Long.parseLong("10"),"1231"));
        TableColumn<Person,Integer> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Person,String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person,Integer> coordinateX = new TableColumn<>("CoordinateX");
        coordinateX.setCellValueFactory(new PropertyValueFactory<>("coordinateX"));
        TableColumn<Person,Integer> coordinateY = new TableColumn<>("CoordinateY");
        coordinateY.setCellValueFactory(new PropertyValueFactory<>("coordinateY"));
        TableColumn<Person,String> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn<Person,Double> height = new TableColumn<>("Height");
        height.setCellValueFactory(new PropertyValueFactory<>("height"));
        TableColumn<Person,String> eyeColor = new TableColumn<>("EyeColor");
        eyeColor.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        TableColumn<Person,String> hairColor = new TableColumn<>("HairColor");
        hairColor.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        TableColumn<Person,String> country = new TableColumn<>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        TableColumn<Person,Integer> locationX = new TableColumn<>("LocationX");
        locationX.setCellValueFactory(new PropertyValueFactory<>("locationX"));
        TableColumn<Person,Double> locationY = new TableColumn<>("LocationY");
        locationY.setCellValueFactory(new PropertyValueFactory<>("locationY"));
        TableColumn<Person,Long> locationZ = new TableColumn<>("LocationZ");
        locationZ.setCellValueFactory(new PropertyValueFactory<>("locationZ"));
        TableColumn<Person,String> locationName = new TableColumn<>("LocationName");
        locationName.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        tableView.getColumns().addAll(id,name,coordinateX,coordinateY,date,height,eyeColor,hairColor,country,locationX,locationY,locationZ,locationName);
        tableView.setItems(people);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefHeight(600);
        BorderPane pane =new BorderPane();
        pane.setCenter(tableView);
        tab.setContent(pane);
        Tab tab1 =new Tab();
        tab1.setText("Map");
        tab1.setContent(new Label("map"));
        tabPane.getTabs().addAll(tab,tab1);
        //hBox.getChildren().addAll(tabPane,userN);
        //hBox.setAlignment(Pos.TOP_CENTER);
        mainWindow.getChildren().addAll(tabPane,userN);
        StackPane.setMargin(userN,new Insets(5,0,0,0));
        VBox vBox1 = new VBox();
        vBox1.getChildren().add(mainWindow);
        FlowPane flowPane = new FlowPane();
//        Button helpCommand = new Button("Help");
//        Button addCommand = new Button("Add");
//        Button addIfMaxCommand = new Button("Add If Max");
//        Button clearCommand = new Button("Clear");
//        Button countCommand = new Button("Count Greater Than Location");
//        Button scriptCommand = new Button("Script");
//        Button filterCommand = new Button("Filter Less Than Eye Color");
//        Button infoCommand = new Button("Info");
//        Button printCommand = new Button("Print Field Ascending Location");
//        Button removeByIdCommand = new Button("Remove By Id");
//        Button removeGreaterCommand = new Button("Remove Greater");
//        Button removeHeadCommand = new Button("Remove Head");
//        Button showCommand = new Button("Show");
//        Button updateCommand = new Button("Update");
        flowPane.getChildren().addAll(helpCommand,addCommand,addIfMaxCommand,clearCommand,countCommand,scriptCommand,filterCommand,infoCommand,printCommand,removeByIdCommand,removeGreaterCommand,removeHeadCommand,updateCommand);
        vBox1.getChildren().add(flowPane);
        VBox.setMargin(flowPane,new Insets(15.0,50,10,50));
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(5);
        flowPane.setVgap(10);
        StackPane.setAlignment(userN,Pos.TOP_CENTER);
        return new Scene(vBox1,1000,700);
    }

}
