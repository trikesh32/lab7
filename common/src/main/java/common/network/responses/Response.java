package common.network.responses;

import java.io.Serializable;

public class Response implements Serializable {
    private final boolean exitCode;
    private final String message;

    public Response(boolean exitCode, String message){
        this.exitCode = exitCode;
        this.message = message;
    }
    public Response(String message){
        this.exitCode = true;
        this.message = message;
    }

    public boolean getExitCode() {
        return exitCode;
    }
    public String getMessage() {
        return message;
    }

}
