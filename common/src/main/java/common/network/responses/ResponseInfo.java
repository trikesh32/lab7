package common.network.responses;

import common.models.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Stack;

public class ResponseInfo extends Response{
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private Class<? extends List> type;
    private Integer size;
    public ResponseInfo(boolean exitCode, String message, LocalDateTime lastInitTime, LocalDateTime lastSaveTime, Class<? extends List> type, Integer size){
        super(exitCode, message);
        this.lastInitTime = lastInitTime;
        this.lastSaveTime = lastSaveTime;
        this.type = type;
        this.size = size;
    }

    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    public Class<? extends List> getType() {
        return type;
    }

    public Integer getSize() {
        return size;
    }
}
