package exceptions;

public class ConnectionException extends RuntimeException{
        /**
         * Конструктор создающий исключение с описанием
         * @param string описание
         */
        public ConnectionException(String string){
            super(string);
            System.out.println(string);
        }
    }

