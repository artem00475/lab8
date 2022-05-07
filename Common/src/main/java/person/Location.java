package person;

import java.io.Serializable;

/**
 * Класс, описывающий местоположение
 */
public class Location implements Serializable {
    private Integer x; //Поле не может быть null
    private Double y; //Поле не может быть null
    private Long z; //Поле не может быть null
    private String name; //Длина строки не должна быть больше 374, Поле не может быть null

    /**
     * Конструктор, задающий местоположение
     * @param x координата Х местоположения
     * @param y координата Y местоположения
     * @param z координата Z местоположения
     * @param name название местоположения
     */
    public Location(Integer x, Double y, Long z, String name){
        this.x=x;
        this.y=y;
        this.z=z;
        this.name=name;
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", x, y, z, name);
    }

    /**
     * Возвращает координату Х местоположения
     * @return х
     */
    public Integer getX(){return x;}

    /**
     * Возвращает координату Y местоположения
     * @return y
     */
    public Double getY(){return y;}

    /**
     * Возвращает координату Z местоположения
     * @return z
     */
    public Long getZ(){return z;}

    /**
     * Возвращает название местоположения местоположения
     * @return name
     */
    public String getName(){return name;}

    /**
     * Сравнивает значение местоположание по координате Х с заданным местоположением
     * @param location заданное местоположение
     * @return boolean
     */
    public boolean compare(Location location){
        return x > location.getX();
    }


}
