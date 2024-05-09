package common.models;

import common.utils.Validatable;

import java.io.Serializable;

/**
 * Класс координат
 * @author trikesh
 */
public class Coordinates implements Validatable, Serializable {
    private int x; //Максимальное значение поля: 636
    private float y;

    public Coordinates(Integer x, Float y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x: " + x +
                ", y: " + y +
                '}';
    }

    /**
     * Проверяет валидность полей
     * @return true если валидны, иначе false
     */
    public boolean check_validity() {
        return x <= 636;
    }
}