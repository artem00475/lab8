package collection;


import file.FileManager;
import person.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Класс, работающий с коллекцией
 */
public class CollectionManager {
    private String scanPath = "ish";
    private final Queue<Person> collection;
    private final String initDate;
    private final FileManager fileManager;

    /**
     * Конструктор, задающий параметры объекта
     * Создается коллекция, сохраняется дата создания
     */
    public CollectionManager() throws FileNotFoundException {
        collection = new PriorityQueue<>();
        initDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
        this.fileManager= new FileManager();
        File file = new File(scanPath);
        Scanner scanner = new Scanner(file);
        parseFileToCollection(scanner,scanPath);
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
     * Заполнение коллекции из файла
     * @param scanner консоль
     * @param string путь к файле
     * @throws FileNotFoundException если файл, не найден
     */
    public void parseFileToCollection(Scanner scanner, String string) {
        fileManager.parseFile(collection,scanner,string);
    }

    /**
     * Добавляет объект класса {@link Person} в коллекцию
     * @param person объект класса {@link Person}
     */
    public void addElement(Person person){
        collection.add(person);
    }

    /**
     * Обновляет поля объекта класса {@link Person} по id
     * @param id id элемента
     * @param p объект класса {@link Person}
     */
    public boolean updateElement(int id, Person p){
        Person person1 = collection.stream().filter(person -> person.getID()==id).findFirst().orElse(null);
        try {
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
        }catch (NullPointerException e){return false;}
    }

    /**
     * Удаляет элемент из коллекции по id
     * @param id id элемента
     */
    public boolean removeElementByID(int id){
        return collection.remove(collection.stream().filter(person -> person.getID()==id).findFirst().orElse(null));
    }

    /**
     * Удаляет все элементы из коллекции
     */
    public void removeAll(){
        Person.removeAllFromIdArray();
        collection.clear();

    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        fileManager.saveToFile(collection);
        //System.out.println("Коллекция сохранена");
    }

    /**
     * Удаляет первый элемент из очереди
     * @return объект класса {@link Person}
     */
    public Person removeFirstElement(){
        Person person = collection.remove();
        Person.removeFromIdArray(person.getID());
        return person;
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
        return collection.removeAll(collection.stream().filter(person1 -> person1.getHeight()>person.getHeight()).collect(Collectors.toCollection(PriorityQueue<Person>::new)));
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
