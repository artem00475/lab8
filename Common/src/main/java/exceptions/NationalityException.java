package exceptions;

/**
 * Исключение выбрасывается, если при вводе национальности ошибка
 */
public class NationalityException extends RuntimeException{
    /**
     * Конструктор создающий исключение с описанием
     * @param string описание
     */
    public NationalityException(String string){
        super(string);
        System.out.println(string);
    }
}
