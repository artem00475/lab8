package exceptions;

/**
 * Исключение выбрасывается, если при вводе даты ошибка
 */
public class DateException extends RuntimeException{
    /**
     * Конструктор создающий исключение с описанием
     * @param string описание
     */
    public DateException(String string){
        super(string);
        System.out.println(string);
    }
}
