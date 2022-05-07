package exceptions;

/**
 * Исключение выбрасывается, если в полях класса {@link ru.itmo.lab5.person.Coordinates} ошибка
 */
public class CoordinatesException extends RuntimeException{
    /**
     * Конструктор создающий исключение с описанием
     * @param string описание
     */
    public CoordinatesException(String string){
        super(string);
        System.out.println(string);
    }
}
