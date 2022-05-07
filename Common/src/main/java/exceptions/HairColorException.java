package exceptions;

/**
 * Исключение выбрасывается, если при вводе цвета волос ошибка
 */
public class HairColorException extends RuntimeException{
    /**
     *Конструктор создающий исключение с описанием
     * @param string описание
     */
    public HairColorException(String string){
        super(string);
        System.out.println(string);
    }
}
