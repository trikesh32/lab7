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
            {"Info", "Информация"}

    };
}
