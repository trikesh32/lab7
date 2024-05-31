package client.locales;

import java.util.ListResourceBundle;

public class gui_es_DO extends ListResourceBundle {
    public Object[][] getContents(){
        return contents;
    }
    private Object[][] contents = {{"Error", "Error"},
            {"Timeout", "El tiempo de espera del servidor ha expirado"},
            {"UnavailableError", "Servidor no disponible"},
            {"ServerDoesntResponse", "El servidor no responde"},
            {"ServerIOException", "No tengo idea de cómo llamar a este error"},
            {"InvalidCredentials", "Verifique sus datos"},
            {"AuthTitle", "Autorización"},
            {"LoginField", "Usuario"},
            {"PasswordField", "Contraseña"},
            {"SignUpButton", "Registrarse"},
            {"UserNotFound", "Usuario no encontrado"},
            {"PasswordIncorrect", "Contraseña incorrecta"},
            {"UserAlreadyExists", "El usuario ya existe"},
            {"RegisterSuccess", "Registro completado"},
            {"AuthSuccess", "Autorización completada"},
            {"Info", "Información"},
            {"NotValidData", "Datos no válidos"},
            {"DBError", "Error de la base de datos"},
            {"ObjectNotFound", "Objeto no encontrado"},
            {"Forbidden", "Acceso denegado"},
            {"InfoResult", "Tipo: {0}\nCantidad de elementos: {1}\nFecha de la última guardada: {2}\nFecha de la última inicialización: {3}"},
            {"EditTitle", "Edición"},
            {"Cancel", "Cancelar"},
            {"NumberFormatException", "Error de formato numérico"},
            {"UserLabel", "Usuario"},
            {"Exit", "Salir"},
            {"LogOut", "Cerrar sesión"},
            {"Help", "Ayuda"},
            {"Add", "Agregar"},
            {"Update", "Actualizar"},
            {"RemoveByID", "Eliminar por ID"},
            {"Clear", "Limpiar"},
            {"ExecuteScript", "Ejecutar script"},
            {"RemoveLower", "Eliminar menores"},
            {"SumOfCapacity", "Suma de capacidades"},
            {"FilterByCapacity", "Filtrar por capacidad"},
            {"FilterLessThanType", "Filtrar por tipo"},
            {"TableTab", "Tabla"},
            {"VisualTab", "Visualización"},
            {"Owner", "Propietario"},
            {"Name", "Nombre"},
            {"CreationDate", "Fecha de creación"},
            {"EnginePower", "Potencia"},
            {"Capacity", "Capacidad"},
            {"VehicleType", "Tipo de vehículo"},
            {"FuelType", "Tipo de combustible"},
            {"EditTitle", "Edición"},
            {"Cancel", "Cancelar"},
            {"CommandNotFound", "Comando no encontrado"},
            {"CheckScriptErr", "Verifique su script"},
            {"ScriptExecutionErr", "Error al ejecutar el script"},
            {"HelpResult", "add {element}: añade un nuevo elemento a la colección\n" +
                    "clear: limpia la colección\n" +
                    "execute_script filename: ejecuta el script del archivo especificado\n" +
                    "exit: cierra el programa sin guardar\n" +
                    "filter_by_capacity: muestra los elementos cuyo campo capacidad es igual al valor especificado\n" +
                    "filter_less_than_type: muestra los elementos cuyo valor del campo tipo de vehículo es menor que el valor especificado\n" +
                    "help: muestra información sobre los comandos disponibles\n" +
                    "info: muestra información sobre la colección\n" +
                    "remove_by_id id: elimina un elemento por ID\n" +
                    "remove_last: elimina el último elemento\n" +
                    "remove_lower {element}: elimina todos los elementos menores que el especificado\n" +
                    "show: muestra información sobre la colección\n" +
                    "sort: ordena la colección por los nombres de los elementos\n" +
                    "sum_of_capacity: muestra la suma de todos los campos capacidad\n" +
                    "update id {element}: actualiza el valor del elemento"},
            {"Success", "Éxito"},
            {"UpdateSuc", "Actualización exitosa"},
            {"ClearSuc", "Limpieza exitosa"},
            {"RemoveById", "Eliminación por ID"},
            {"RemoveByIDSuc", "Éxito"},
            {"ScriptExecutionSuc", "Éxito"},
            {"Result", "Resultado"},
            {"WrongType", "Tipo incorrecto"},
            {"FilterLessThanTypeResult", "Objetos encontrados"},
            {"WrongNumber", "Número incorrecto"}};
}
