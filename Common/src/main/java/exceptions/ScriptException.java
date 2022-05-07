package exceptions;

/**
 * Исключение выбрасывается, если при работе со скриптом ошибка
 */
public class ScriptException extends  RuntimeException{
    /**
     * Конструктор создающий исключение с описанием
     * @param string описание
     */
    public ScriptException(String string){
        super(string);
        System.out.println(string);
    }
}
