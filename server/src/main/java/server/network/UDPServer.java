package server.network;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.SerializationException;
import server.App;
import server.handlers.CommandHandler;
import common.network.requests.Request;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.SerializationUtils;
import common.network.responses.Response;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;


public abstract class UDPServer implements Runnable{
    private final InetSocketAddress addr;
    private final CommandHandler commandHandler;
    private Runnable afterHook;
    private Logger logger = App.logger;
    private boolean running = true;
    public UDPServer(InetSocketAddress addr, CommandHandler commandHandler){
        this.addr = addr;
        this.commandHandler = commandHandler;
    }

    public InetSocketAddress getAddr() {
        return addr;
    }
    public abstract Pair<Byte[], SocketAddress> receiveData() throws IOException;
    public abstract void sendData(byte[] data, SocketAddress addr) throws IOException;
    public abstract void connectToClient(SocketAddress addr) throws SocketException;
    public abstract void disconnectFromClient();
    public abstract void close();
    public void run() {
        logger.info("Сервер запущен по адресу " + addr);

        while (running) {
            Pair<Byte[], SocketAddress> dataPair;
            try {
                dataPair = receiveData();
            } catch (Exception e) {
                logger.severe("Ошибка получения данных : " + e.toString());
                disconnectFromClient();
                continue;
            }

            var dataFromClient = dataPair.getKey();
            var clientAddr = dataPair.getValue();

            try {
                connectToClient(clientAddr);
                logger.info("Соединено с " + clientAddr);
            } catch (Exception e) {
                logger.severe("Ошибка соединения с клиентом : " + e.toString());
            }

            Request request;
            try {
                request = SerializationUtils.deserialize(ArrayUtils.toPrimitive(dataFromClient));
                logger.info("Обработка " + request + " из " + clientAddr);
            } catch (SerializationException e) {
                logger.severe("Невозможно десериализовать объект запроса.");
                System.out.println(e);
                disconnectFromClient();
                continue;
            }

            Response response = null;
            try {
                response = commandHandler.handle(request);
                if (afterHook != null) afterHook.run();
            } catch (Exception e) {
                logger.severe("Ошибка выполнения команды : " + e.toString());
            }
            if (response == null) response = new Response(false, "Нет команды " + request.getCommandName());

            var data = SerializationUtils.serialize(response);
            logger.info("Ответ: " + response);

            try {
                sendData(data, clientAddr);
                logger.info("Отправлен ответ клиенту " + clientAddr);
            } catch (Exception e) {
                logger.severe("Ошибка ввода-вывода : " + e.toString());
            }

            disconnectFromClient();
            logger.info("Отключение от клиента " + clientAddr);
        }

        close();
    }
    public void setAfterHook(Runnable afterHook){
        this.afterHook = afterHook;
    }
    public void stop(){
        running = false;
    }
}
