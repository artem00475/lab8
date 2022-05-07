package collection;

import person.Person;

import java.util.Comparator;

/**
 * Класс, реализующий сравнение двух объектов класса Person по росту
 */
public class PersonComporator implements Comparator<Person> {
    /**
     * Сраавнивает два объекта класса Person по росту
     * @param p1 объект 1
     * @param p2 объект 2
     * @return результат сравнения
     */
    public int compare(Person p1, Person p2) {
        if (p1.getHeight() < p2.getHeight())
            return -1;
        else if (p1.getHeight() > p2.getHeight())
            return 1;
        return 0;
    }
}
