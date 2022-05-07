package exceptions;

/**
 * Исключение выбрасывается, если при вводе роста ошибка
 */
public class HeightException extends RuntimeException{
    /**
     *Конструктор создающий исключение с описанием
     * @param string описание
     */
    public HeightException(String string){
        super(string);
        System.out.println(string);
    }
}
