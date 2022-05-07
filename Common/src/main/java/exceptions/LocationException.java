package exceptions;

/**
 * Исключение выбрасывается, если в полях класса {@link person.Location} ошибка
 */
public class LocationException extends RuntimeException{
    /**
     *Конструктор создающий исключение с описанием
     *@param string описание
     */
    public LocationException(String string){
        super(string);
        System.out.println(string);
    }
}
