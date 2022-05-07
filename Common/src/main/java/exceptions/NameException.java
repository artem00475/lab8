package exceptions;

/**
 * Исключение выбрасывается, если при вводе имени ошибка
 */
public class NameException extends RuntimeException{
    /**
     * Конструктор создаещий исключение с описанием
     * @param string описание
     */
    public NameException(String string){
        super(string);
        System.out.println(string);
    }
}
