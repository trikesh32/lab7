package common.models;

import java.io.Serializable;

/**
 * Перечисление всех типов FuelType
 * @author trikesh
 */
public enum FuelType implements Serializable {
    GASOLINE,
    KEROSENE,
    ALCOHOL,
    MANPOWER;

    /**
     * @return возвращает строку со всеми значениями через запятую
     */
    public static String names() {
        StringBuilder fuelNames = new StringBuilder();
        for (var fuel : values())
            fuelNames.append(fuel.name()).append(", ");
        return fuelNames.substring(0, fuelNames.length() - 2);
    }
}
