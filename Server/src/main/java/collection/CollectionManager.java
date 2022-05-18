package collection;


import DataBase.DBManager;
import person.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Класс, работающий с коллекцией
 */
public class CollectionManager {
    private final Queue<Person> collection;
    private final String initDate;
    private DBManager dbManager;

    /**
     * Конструктор, задающий параметры объекта
     * Создается коллекция, сохраняется дата создания
     */
    public CollectionManager(DBManager dbManager) {
        collection = new PriorityQueue<>();
        initDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
        this.dbManager=dbManager;
        dbManager.setCollection(collection);
    }

    /**
     * Возвращает коллекцию
     * @return коллекция
     */
    public Queue<Person> getCollection(){
        return collection;
    }

    /**
     * Возвращает дату создания коллекции
     * @return дата создания коллекции
     */
    public String getInitDate() {
        return initDate;
    }

    @Override
    public String toString() {
        StringBuilder personList = new StringBuilder();
        for (Person person : collection) {
            personList.append("\n").append(person);
        }
        return personList.toString();
    }

    /**
     * Добавляет объект класса {@link Person} в коллекцию
     * @param person объект класса {@link Person}
     */
    public boolean addElement(Person person) {
        Person person1 = dbManager.addPerson(person);
        if (!(person1==null)) {
            collection.add(person1);
            return true;
        }else return false;
    }

    /**
     * Обновляет поля объекта класса {@link Person} по id
     * @param id id элемента
     * @param p объект класса {@link Person}
     */
    public boolean updateElement(int id, Person p){
        if (dbManager.updatePerson(id,p)){
        Person person1 = collection.stream().filter(person -> person.getID()==id).findFirst().orElse(null);
        collection.remove(person1);
        person1.setName(p.getName());
        person1.setCoordinates(p.getCoordinates());
        person1.setHeight(p.getHeight());
        person1.setEyeColor(p.getEyeColor());
        person1.setHairColor(p.getHairColor());
        person1.setNationality(p.getNationality());
        person1.setLocation(p.getLocation());
        collection.add(person1);
        return true;
        }else {return false;}
    }

    /**
     * Удаляет элемент из коллекции по id
     * @param id id элемента
     */
    public boolean removeElementByID(int id){
        if (dbManager.deletePerson(id)) {
            return collection.remove(collection.stream().filter(person -> person.getID() == id).findFirst().orElse(null));
        }else return false;
    }

    /**
     * Удаляет все элементы из коллекции
     */
    public void removeAll(){
        Person.removeAllFromIdArray();
        collection.clear();

    }

    /**
     * Удаляет первый элемент из очереди
     * @return объект класса {@link Person}
     */
    public Person removeFirstElement(){
        dbManager.deletePerson(collection.peek().getID());
        return collection.remove();
    }

    /**
     * Сравнивает значение height элементов коллекции со значением заданного объекта
     * @param person объект класса {@link Person}
     * @return {@code true} если больше, иначе {@code false}
     */
    public boolean ifMore(Person person){
        if (collection.isEmpty()){return true;}
         return collection.stream().max(new PersonComporator()).get().getHeight().compareTo(person.getHeight())<0;
    }

    /**
     * Удаляет из коллекции все элементы превышающие заданный
     * @param person объект класса {@link Person}
     * @return {@code true} если нужные элементы есть, иначе {@code false}
     */
    public boolean removeGreater(Person person){
        PriorityQueue<Person> people = collection.stream().filter(person1 -> person1.getHeight()>person.getHeight()).collect(Collectors.toCollection(PriorityQueue<Person>::new));
        people.stream().forEach(person1 -> dbManager.deletePerson(person1.getID()));
        return collection.removeAll(people);
    }

    /**
     * Выводит количество, значение поле location которых больше заданного
     * @param location объект класса {@link Location}
     * @return количество элементов
     */
    public int countGreaterLocation(Location location){
        return Integer.parseInt(Long.toString(collection.stream().filter(person -> person.getLocation().compare(location)).count()));
    }

    /**
     * Сортирует коллекцию по местоположению
     * @return отсортированная коллекция
     */
    public Queue<Person> sortByLocation(){
        return collection.stream().sorted(new LocationComporator()).collect(Collectors.toCollection(PriorityQueue<Person> :: new));
    }

    /**
     * Возвращает коллекцию с элементами значение поля eyeColor меньше заданного
     * @param eyeColor заданное значение {@link ColorE}
     * @return коллекция с элементами
     */
    public Queue<Person> filterLessThanEyeColor(ColorE eyeColor){
        return collection.stream().filter(person -> person.getEyeColor().compareTo(eyeColor)<0).collect(Collectors.toCollection(PriorityQueue<Person>::new));
    }
}
