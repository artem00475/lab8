package person;

import exceptions.CoordinatesException;

import java.io.Serializable;

/**
 * Класс, описывающий координаты Х и Y
 */
public class Coordinates implements Serializable {
    private final Integer x; //Максимальное значение поля: 408, Поле не может быть null
    private final Integer y; //Значение поля должно быть больше -876, Поле не может быть null

    /**
     * Конструктор, задающий координаты Х и Y
     * @param x координата Х
     * @param y координата Y
     */
    public Coordinates(Integer x, Integer y){
        if (x>408){throw new CoordinatesException("Координата Х не должна превышать 408");}
        this.x=x;
        if (y<=-876){throw new CoordinatesException("Координата Y должна быть больше -876");}
        this.y=y;
    }
    @Override
    public String toString() {
        return String.format("%s,%s", x, y);
    }

    /**
     * Возвращает координату Х
     * @return x
     */
    public Integer getX(){return x;}

    /**
     * Возвращает координату Y
     * @return y
     */
    public Integer getY(){return y;}
}

