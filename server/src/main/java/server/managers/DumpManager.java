package server.managers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import server.App;
import common.models.Coordinates;
import common.models.Vehicle;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * Менеджер для загрузки из файла в формате xml, и сохранения
 * @author trikesh
 */
public class DumpManager {
    private final String filename;
    private final Logger console;
    private final XStream xstream;

    public DumpManager(String filename) {
        this.filename = filename;
        this.console = App.logger;
        xstream = new XStream();
        xstream.alias("vehicle", Vehicle.class);
        xstream.alias("coordinates", Coordinates.class);
        xstream.alias("vehicles", CollectionManager.class);
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.addPermission(NullPermission.NULL);
        xstream.addPermission(NoTypePermission.NONE);
        xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        xstream.allowTypeHierarchy(List.class);
        xstream.allowTypeHierarchy(String.class);
        xstream.ignoreUnknownElements();
    }

    /**
     * Записывает коллекцию в файл
     * @param collection коллекция
     */
    public void writeCollection(Stack<Vehicle> collection) {
        if (!filename.isEmpty()) {
            try (FileOutputStream collectionFileWriter = new FileOutputStream(filename)) {
                if (!collection.isEmpty()){
                String xml = xstream.toXML(new ArrayList<>(collection));
                collectionFileWriter.write(xml.getBytes());
                } else {
                    collectionFileWriter.write("".getBytes());
                }
            } catch (IOException exception) {
                console.severe("Файл не может быть открыт");
            }
        } else console.severe("Беда с названием файла");
    }

    /**
     * Считывает коллекцию из файла
     * @return коллекцию
     */
    public Stack<Vehicle> readCollection() {
        if (!filename.isEmpty()) {
            try (FileReader fileReader = new FileReader(filename)) {
                xstream.allowTypes(new Class[]{List.class, Vehicle.class});
                BufferedReader reader = new BufferedReader(fileReader);
                if (new File(filename).length() == 0) {
                    return new Stack<>();
                }
                StringBuilder xml = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        xml.append(line);
                    }
                }
                List<Vehicle> list = (List<Vehicle>) xstream.fromXML(xml.toString());
                Stack<Vehicle> vehicleStack = new Stack<>();
                for (var v : list) {
                    if (v.check_validity()) {
                        vehicleStack.push(v);
                    } else {
                        console.warning("Файл содержит некорректный объект");
                    }
                }
                return vehicleStack;
            } catch (FileNotFoundException e) {
                console.severe("Файл не найден");
                return new Stack<>();
            } catch (IOException e) {
                console.severe("Ошибка ввода-вывода");
            } catch (NullPointerException e) {
                console.severe("Невозможно найти коллекцию в файле");
            } catch (IllegalStateException e) {
                console.severe("Непредвиденная ошибка");
            } catch (NoSuchElementException e) {
                console.severe("Файл пустой");
            } catch (Exception e) {
                console.severe("Ошибка десериализации");
            }
        } else
            console.warning("Беда с названием файла");
        return null;
    }

}
