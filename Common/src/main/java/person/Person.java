package person;

import exceptions.IdException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Класс, описывающий человека
 * Объекты класса являются элементами коллекции
 */
public class Person implements Serializable,Comparable<Person> {
    private static Integer uniqueID=1;
    private final int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private final Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double height; //Поле не может быть null, Значение поля должно быть больше 0
    private ColorE eyeColor; //Поле не может быть null
    private ColorH hairColor; //Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле может быть null
    private static final LinkedList<Integer> idArray = new LinkedList<>();


    /**
     * Конструктор, задающий параметры для создания человека (id и date задаются)
     * @param id  id
     * @param name  имя
     * @param coordinatesX  координата Х
     * @param coordinatesY  координата Y
     * @param date  дата создания
     * @param height  рост
     * @param eyeColor  цвет глаз
     * @param hairColor  цвет волос
     * @param nationality  национальность
     * @param locationX координата Х местоположения
     * @param locationY координата Y местоположения
     * @param locationZ координата Z местоположения
     * @param locationName название местоположения
     */
    public Person(int id, String name, Integer coordinatesX, Integer coordinatesY, Date date, Double height, ColorE eyeColor, ColorH hairColor, Country nationality, Integer locationX, Double locationY, Long locationZ, String locationName ){
        if (idArray.isEmpty()){
           idArray.add(id);
        }else {
            for (Integer integer : idArray) {
                if (integer.equals(id)) {
                    throw new IdException("Человек с таким id уже есть в файле.");
                }
            }
            idArray.add(id);
        }
        if (id<=0){throw new IdException("Id должен быть больше 0");}
        this.id  = id;
        if (id >= uniqueID){uniqueID=id+1;}
        this.name=name;
        this.coordinates=new Coordinates(coordinatesX,coordinatesY);
        this.creationDate= date;
        this.height=height;
        this.eyeColor=eyeColor;
        this.hairColor=hairColor;
        this.nationality=nationality;
        this.location=new Location(locationX,locationY,locationZ,locationName);
    }

    /**
     * Конструктор, задающий параметры для создания человека (id и date создаются автоматически)
     * @param name имя
     * @param coordinatesX координата Х
     * @param coordinatesY координата Y
     * @param height рост
     * @param eyeColor цвет глаз
     * @param hairColor цвет волос
     * @param nationality национальность
     * @param locationX координата Х местоположения
     * @param locationY координата Y местоположения
     * @param locationZ координата Z местоположения
     * @param locationName название местоположения
     */
    public Person(String name,Integer coordinatesX,Integer coordinatesY, Double height, ColorE eyeColor, ColorH hairColor, Country nationality, Integer locationX, Double locationY, Long locationZ, String locationName){
        this.id  = uniqueID;
        idArray.add(id);
        uniqueID++;
        this.name=name;
        this.coordinates=new Coordinates(coordinatesX,coordinatesY);
        this.creationDate=new Date(System.currentTimeMillis());
        this.height=height;
        this.eyeColor=eyeColor;
        this.hairColor=hairColor;
        this.nationality=nationality;
        this.location=new Location(locationX,locationY,locationZ,locationName);
    }

    /**
     * Возвращает id еловека
     * @return id
     */
    public int getID(){return id;}
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                id, name, coordinates, new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(creationDate), height, eyeColor, hairColor, nationality, location);
    }

    /**
     * Возвращает имя человека
     * @return name
     */
    public String getName(){return name;}

    /**
     * Возвращает координаты человека
     * @return coordinates
     * @see Coordinates
     */
    public Coordinates getCoordinates(){return coordinates;}

    /**
     * Возвращает рост человека
     * @return height
     */
    public Double getHeight(){return height;}

    /**
     * Возвращает цвет глаз человека
     * @return eyeColor
     * @see ColorE
     */
    public ColorE getEyeColor(){return eyeColor;}

    /**
     * Возвращает цвет волос человека
     * @return hairColor
     * @see ColorH
     */
    public ColorH getHairColor(){return hairColor;}

    /**
     * Возвращает национальность человека
     * @return nationality
     * @see Country
     */
    public Country getNationality(){return nationality;}

    /**
     * Возвращает местоположение человека
     * @return location
     * @see Location
     */
    public Location getLocation(){return location;}

    /**
     * Устанавливает новое имя человека
     * @param name новое имя
     */
    public void setName(String name){ this.name=name;}

    /**
     * Устанавливает новые координаты человека
     * @param coordinates новые координаты
     * @see Coordinates
     */
    public void setCoordinates(Coordinates coordinates){this.coordinates= coordinates;}

    /**
     * Устанавливает  новый рост человека
     * @param height  новый рост
     */
    public void setHeight(Double height){this.height= height;}

    /**
     * Устанавливает новый цвет глаз человека
     * @param eyeColor новый цвет глаз
     * @see ColorE
     */
    public void setEyeColor(ColorE eyeColor){this.eyeColor= eyeColor;}

    /**
     * Устанавливает новй цвет волос человека
     * @param hairColor новый цвет волос
     * @see ColorH
     */
    public void setHairColor(ColorH hairColor){this.hairColor= hairColor;}

    /**
     * Устанавливает новую национальность человека
     * @param nationality новая национальность
     * @see Country
     */
    public void setNationality(Country nationality){this.nationality= nationality;}

    /**
     * Устанавливает новое мкстоположение человека
     * @param location новое местопложение
     * @see Location
     */
    public void setLocation(Location location){this.location= location;}

    /**
     * Возвращает список со всеми полями человека
     * @return fields список с полями
     */
    public LinkedList<String> getPersonFields(){
        LinkedList<String> fields= new LinkedList<>();
        fields.add(String.valueOf(id));
        fields.add(name);
        fields.add(String.valueOf(coordinates.getX()));
        fields.add(String.valueOf(coordinates.getY()));
        fields.add(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(creationDate));
        fields.add(String.valueOf(height));
        fields.add(eyeColor.name());
        fields.add(hairColor.name());
        fields.add(nationality.name());
        fields.add(String.valueOf(location.getX()));
        fields.add(String.valueOf(location.getY()));
        fields.add(String.valueOf(location.getZ()));
        fields.add(location.getName());
        return fields;
    }

    /**
     * Возвращает список со всеми id коллекции
     * @return idArray список со всеми id коллекции
     */
    public static LinkedList<Integer> getIdArray(){
        return idArray;
    }

    /**
     * Удаляет id из списка всех id
     * @param id id удаленного элемента
     */
    public static void removeFromIdArray(int id){idArray.remove((Integer) id);}

    /**
     * Очищает список всех id
     */
    public static void removeAllFromIdArray(){idArray.clear();}

    @Override
    public int compareTo(Person person) {
        return this.name.compareTo(person.getName());
    }
}


