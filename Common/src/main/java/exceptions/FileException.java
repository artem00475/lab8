package exceptions;

/**
 * Исключение выбрасывается, если при работе с файлом ошибка
 */
public class FileException extends RuntimeException{
    /**
     *Конструктор создающий исключение с описанием
     * @param string описание
     */
    public FileException(String string){
        super(string);
        System.out.println(string);
    }
}
