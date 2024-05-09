package common.utils;

/**
 * Интерфейс для объекта, который можно описывать
 * @author trikesh
 */
public interface Describable {
    /**
     * Получить имя.
     * @return имя
     */
    String getName();
    /**
     * Получить описание.
     * @return описание
     */
    String getDescription();
}
