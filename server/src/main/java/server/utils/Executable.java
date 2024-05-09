package server.utils;

import common.network.requests.Request;
import common.utils.ExecutionResponse;

/**
 * Интерфейс для всех выполняемых команд.
 * @author trikesh
 */
public interface Executable {
    /**
     * Выполнить команду.
     *
     * @param request Аргумент для выполнения
     * @return результат выполнения
     */
    ExecutionResponse apply(Request request);
}
