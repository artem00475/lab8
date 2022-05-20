package DataBase;

import exceptions.ConnectionException;
import org.apache.commons.codec.digest.DigestUtils;
import person.ColorE;
import person.ColorH;
import person.Country;
import person.Person;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;

public class DBManager {
    private Connection connection=null;
    private Queue<Person> collection;
    private String login;
    private String password;

    public boolean connect() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден!");
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://pg/studs");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void setCollection(Queue<Person> queue){collection=queue;}

    public short initializeCollection(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PERSONS;");
            short found=0;
            while (resultSet.next()){
                found=1;
                try {
                    collection.add(readPerson(resultSet));
                }catch (NullPointerException e){
                    System.out.println("Объект не добавлен");
                }
            }
            return found;
        } catch (SQLException e) {
            return -1;
        }
    }

    public synchronized Person readPerson(ResultSet resultSet){
        int id;
        String name;
        int coordinatesX;
        int coordinatesY;
        Date date;
        double height;
        ColorE eyeColor;
        ColorH hairColor;
        Country nationality;
        int locationX;
        double locationY;
        long locationZ;
        String locationName;
        try {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            coordinatesX = resultSet.getInt("coordinatesX");
            coordinatesY = resultSet.getInt("coordinatesY");
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(resultSet.getString("date"));
            height = resultSet.getDouble("height");
            eyeColor = ColorE.valueOf(resultSet.getString("eyeColor"));
            hairColor = ColorH.valueOf(resultSet.getString("hairColor"));
            nationality = Country.valueOf(resultSet.getString("Country"));
            locationX = resultSet.getInt("locationX");
            locationY = resultSet.getDouble("locationY");
            locationZ = resultSet.getLong("locationZ");
            locationName = resultSet.getString("locationName");
            return new Person(id,name,coordinatesX,coordinatesY,date,height,eyeColor,hairColor,nationality,locationX,locationY,locationZ,locationName);
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        } catch (ParseException e){ return null;}
    }

    public void setUser(String login1,String password1) {
        this.login=login1;
        this.password=password1;
    }

    public Person addPerson(Person person){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PERSONS VALUES(nextval('ID'),?,?,?,current_timestamp,?,?,?,?,?,?,?,?,?);");
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getCoordinates().getX());
            preparedStatement.setInt(3,person.getCoordinates().getY());
            preparedStatement.setDouble(4,person.getHeight());
            preparedStatement.setString(5,person.getEyeColor().name());
            preparedStatement.setString(6,person.getHairColor().name());
            preparedStatement.setString(7,person.getNationality().name());
            preparedStatement.setInt(8,person.getLocation().getX());
            preparedStatement.setDouble(9,person.getLocation().getY());
            preparedStatement.setLong(10,person.getLocation().getZ());
            preparedStatement.setString(11,person.getLocation().getName());
            preparedStatement.setString(12,login);
            preparedStatement.execute();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PERSONS WHERE id = currval('id');");
            resultSet.next();
            return readPerson(resultSet);
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }

    public synchronized boolean deletePerson(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PERSONS WHERE id =? and userName=?;");
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,login);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }else {return false;}
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }

    public boolean clear() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSONS WHERE userNAme=?;");
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            Queue<Person> people = new PriorityQueue<>();
            while (resultSet.next()){
                people.add(readPerson(resultSet));
            }
            if (!people.isEmpty()) {
                people.stream().forEach(person -> {
                    deletePerson(person.getID());
                    collection.remove(collection.stream().filter(person1 -> person1.getID()==person.getID()).findFirst().orElse(null));
                });
                return true;
            }else return false;
        }catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа БД");
        }
    }

    public Person deleteFirstPerson(){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PERSONS WHERE userNAme=?;");
            preparedStatement.setString(1,login);
            ResultSet resultSet = preparedStatement.executeQuery();
            Queue<Person> people = new PriorityQueue<>();
            while (resultSet.next()){
                people.add(readPerson(resultSet));
            }
            if (!people.isEmpty()) {
                deletePerson(people.peek().getID());
                collection.remove(collection.stream().filter(person -> person.getID()==people.peek().getID()).findFirst().orElse(null));
                return people.peek();
            }else return null;
        }catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа БД");
        }
    }

    public boolean updatePerson(int id,Person person) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PERSONS SET name=?,coordinatesX=?,coordinatesY=?,height=?,eyeColor=?,hairColor=?,country=?,locationX=?,locationY=?,locationZ=?,locationName=? WHERE id = ? and userName=?;");
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getCoordinates().getX());
            preparedStatement.setInt(3,person.getCoordinates().getY());
            preparedStatement.setDouble(4,person.getHeight());
            preparedStatement.setString(5,person.getEyeColor().name());
            preparedStatement.setString(6,person.getHairColor().name());
            preparedStatement.setString(7,person.getNationality().name());
            preparedStatement.setInt(8,person.getLocation().getX());
            preparedStatement.setDouble(9,person.getLocation().getY());
            preparedStatement.setLong(10,person.getLocation().getZ());
            preparedStatement.setString(11,person.getLocation().getName());
            preparedStatement.setInt(12,id);
            preparedStatement.setString(13,login);
            if (preparedStatement.executeUpdate()>0){
                return true;
            }else {return false;}
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }

    public boolean checkLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE userNAme = ?;");
            preparedStatement.setString(1,login);
            preparedStatement.execute();
            return (preparedStatement.getResultSet().next());
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }

    public boolean checkPass(String login, String  password) {
        password = hashPass(password);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE userNAme = ? and password = ?;");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            preparedStatement.execute();
            return (preparedStatement.getResultSet().next());
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }

    public synchronized short checkUser(String login, String password) {
        if (checkLogin(login)) {
            if (checkPass(login,password)) {
                this.login = login;
                this.password=password;
                return 1;
            } else return -1;
        }  else {
            if (addUser(login,password)) {
                this.login = login;
                this.password = password;
                return 0;
            }else return -2;
        }
    }

    public boolean addUser(String login,String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS VALUES(?,?);");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,hashPass(password));
            if (preparedStatement.executeUpdate()>0){
                return true;
            }else {return false;}
        } catch (SQLException e) {
            throw new ConnectionException("Ошибка доступа к БД");
        }
    }

    public String hashPass(String password) {
        return DigestUtils.sha1Hex(password);
    }
}
