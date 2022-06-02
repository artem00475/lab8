package Application;

import Client.ClientManager;
import Client.TableManager;
import Requests.RecieveManager;
import Requests.SendManager;
import exceptions.ConnectionException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import person.*;

import java.text.ParseException;


public class App extends Application {
    private Button reg;
    private Button log;
    private final Button helpCommand = new Button("Помощь");
    private final Button addCommand = new Button("Добавить");
    private final Button addIfMaxCommand = new Button("Добавить если максимальный");
    private final Button clearCommand = new Button("Очистить");
    private final Button countCommand = new Button("Количество больших чем расположение");
    private final Button scriptCommand = new Button("Скрипт");
    private final Button filterCommand = new Button("Отфильтровать меньшие чем цвет глаз");
    private final Button infoCommand = new Button("Информация");
    private final Button printCommand = new Button("Вывести поля по расположению");
    private final Button removeByIdCommand = new Button("Удалить по id");
    private final Button removeGreaterCommand = new Button("Удалить большие");
    private final Button removeHeadCommand = new Button("Удалить первый");
    private final Button updateCommand = new Button("Обновить");
    private TextField user;
    private PasswordField passwordField;
    private LoginManager loginManager;
    private static ClientManager clientManager;
    private CommandsManager commandsManager;
    private TableView<Person> tableView;
    private static ObservableList<Person> people;
    private Group map;
    private static ObservableList<Circle> circles;

    public static void setRequestManagers(RecieveManager recieveManager, SendManager sendManager, TableManager tableManager) {
        clientManager = new ClientManager(sendManager,recieveManager,tableManager);
    }


    @Override
    public void start(Stage primaryStage) {
        map = new Group();
        circles = FXCollections.observableArrayList();
        MapManager.setCircles(circles);
        circles.addListener(new ListChangeListener<Circle>() {
            @Override
            public void onChanged(Change<? extends Circle> c) {
                map.getChildren().clear();
                map.getChildren().addAll(circles);
            }
        });
        Stage stage =new Stage();
        PersonCreatorController personCreatorController = new PersonCreatorController(stage);
        commandsManager=new CommandsManager(clientManager, personCreatorController);
        loginManager = new LoginManager(clientManager,commandsManager);
        primaryStage.setTitle("Авторизация");
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
                        primaryStage.setTitle("Таблица и карта");
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
        addCommand.setOnAction(event -> {commandsManager.add();});
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
        reg = new Button("Регистрация");
        log = new Button("Вход");
        VBox vBox = new VBox();
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(new Label("Логин: "), user);
        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(new Label("Пароль: "), passwordField);
        HBox hBox3 = new HBox();
        hBox3.getChildren().addAll(reg, log);
        hBox3.setSpacing(20);
        hBox3.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(new Label("Добро пожаловать"),hBox1, hBox2, hBox3);
        VBox.setMargin(hBox2, new Insets(5, 0, 0, 0));
        VBox.setMargin(hBox3, new Insets(10, 0, 0, 0));
        VBox.setMargin(hBox1, new Insets(10, 0, 0, 0));
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 400, 300);
    }

    public Scene setMainWindowScene(String userName) throws ParseException {
        StackPane mainWindow = new StackPane();
        Label userN = new Label(userName);
        TabPane tabPane = new TabPane();
        Tab tab =new Tab();
        tab.setText("Таблица");
        tableView =new TableView<>();
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
        tab1.setText("Карта");
        tab1.setContent(map);
        tabPane.getTabs().addAll(tab,tab1);
        ObservableList<String> language = FXCollections.observableArrayList("Русский", "Dutch", "Lietuvių", "Español");
        ComboBox<String> languageField = new ComboBox<>(language);
        languageField.setValue("Русский");
        mainWindow.getChildren().addAll(tabPane,userN,languageField);
        StackPane.setMargin(userN,new Insets(5,0,0,0));
        StackPane.setMargin(languageField, new Insets(2,0,0,0));
        StackPane.setAlignment(languageField,Pos.TOP_RIGHT);
        VBox vBox1 = new VBox();
        vBox1.getChildren().add(mainWindow);
        FlowPane flowPane = new FlowPane();
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
