package collection;

import person.Person;

import java.util.Comparator;

/**
 * Класс, реализующий сравнение двух объектов класса Person по координате Х местоположения
 */
public class LocationComporator implements Comparator<Person> {
    /**
     * Сравненивает два объекта класса Person по координате Х местоположения
     * @param p1 объект 1
     * @param p2 объект 2
     * @return результат сравнения
     */
    public int compare(Person p1, Person p2) {
        if (p1.getLocation().getX() < p2.getLocation().getX())
            return -1;
        else if (p1.getLocation().getX() > p2.getLocation().getX())
            return 1;
        return 0;
    }
}