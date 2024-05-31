package client.locales;

import java.util.ListResourceBundle;

public class gui_ru_RU extends ListResourceBundle {
    public Object[][] getContents(){
        return contents;
    }
    private Object[][] contents = {
            {"Error", "Ошибка"},
            {"Timeout", "Время ожидания сервера вышло"},
            {"UnavailableError", "Сервер недоступен"},
            {"ServerDoesntResponse", "Сервер не отвечает"},
            {"ServerIOException", "Да понятия как эту ошибку обозвать"},
            {"InvalidCredentials", "Перепроверьте данные"},
            {"AuthTitle", "Авторизация"},
            {"LoginField", "Логин"},
            {"PasswordField", "Пароль"},
            {"SignUpButton", "Зарегистрироваться"},
            {"UserNotFound", "Пользователь не найден"},
            {"PasswordIncorrect", "Неверный пароль"},
            {"UserAlreadyExists", "Пользователь уже существует"},
            {"RegisterSuccess", "Регистрация завершена"},
            {"AuthSuccess", "Авторизация завершена"},
            {"Info", "Информация"},
            {"NotValidData", "Данные не валидны"},
            {"DBError", "Ошибка работы базы данных"},
            {"ObjectNotFound", "Объект не найден"},
            {"Forbidden", "Отказано в доступе"},
            {"InfoResult", "Тип: {0}\nКоличество элементов: {1}\nДата последнего сохранения: {2}\nДата последней инициализации: {3}"},
            {"EditTitle", "Редактирование"},
            {"Cancel", "Отмена"},
            {"NumberFormatException", "С числом беда("},
            {"UserLabel", "Юзер"},
            {"Exit", "Выход"},
            {"LogOut", "Логаут"},
            {"Help", "Помощь"},
            {"Add", "Добавить"},
            {"Update", "Обновить"},
            {"RemoveByID", "Удалить по id"},
            {"Clear", "Очистка"},
            {"ExecuteScript", "Выполнить скрипт"},
            {"RemoveLower", "Удалить ниже"},
            {"SumOfCapacity", "Сумма вместимостей"},
            {"FilterByCapacity", "Фильтр по вместимости"},
            {"FilterLessThanType", "Фильтр по типу"},
            {"TableTab", "Таблица"},
            {"VisualTab", "Визуализация"},
            {"Owner", "Владелец"},
            {"Name", "Имя"},
            {"CreationDate", "Дата создания"},
            {"EnginePower", "Мощность"},
            {"Capacity", "Вместимость"},
            {"VehicleType", "Вид транспорта"},
            {"FuelType", "Вид топлива"},
            {"EditTitle", "Редактирование"},
            {"Cancel", "Отмена"},
            {"CommandNotFound", "Команда не найдена"},
            {"CheckScriptErr", "Проверьте свой скрипт"},
            {"ScriptExecutionErr", "Ошибка при выполнении скрипта"},
            {"HelpResult", "add {element}: добавляет новый элемент в коллекцию\n" +
                    "clear: очищает коллекцию\n" +
                    "execute_script filename: выполнить скрипт из указанного файла\n" +
                    "exit: завершить программу без сохранений\n" +
                    "filter_by_capacity: выводит элементы, у которых поле capacity равно заданному\n" +
                    "filter_less_than_type: выводит элементы, значения поля VehicleType меньше заданного\n" +
                    "help: выводит информацию о доступных командах\n" +
                    "info: выводит информацию о коллекции\n" +
                    "remove_by_id id: удаляет элемент по id\n" +
                    "remove_last: удаляет последний элемент\n" +
                    "remove_lower {element}: удаляет все элементы, меньшие чем заданный\n" +
                    "show: выводит информацию о коллекции\n" +
                    "sort: сортирует коллекцию по именам элементов\n" +
                    "sum_of_capacity: выводит сумму всех полей capacity\n" +
                    "update id {element}: обновляет значение элемента"},
            {"Success", "Успех"},
            {"UpdateSuc", "Обновление успешно"},
            {"ClearSuc", "Удаление успешно"},
            {"RemoveById", "Удаление по ID"},
            {"RemoveByIDSuc", "Успех"},
            {"ScriptExecutionSuc", "Успех"},
            {"Result", "Результат"},
            {"WrongType", "Не верный тип"},
            {"FilterLessThanTypeResult", "Найденные объекты"},
            {"WrongNumber", "Не верное число"}
    };
}
