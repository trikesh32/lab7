package common.network.responses;

public class ResponseInteger extends Response{
    private Integer res;
    public ResponseInteger(boolean exitCode, String message, Integer res){
        super(exitCode, message);
        this.res = res;
    }
    public Integer getRes(){
        return res;
    }
}
