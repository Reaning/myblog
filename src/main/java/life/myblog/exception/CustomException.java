package life.myblog.exception;

public class CustomException extends RuntimeException {
    private Integer code;
    private String message;

    public CustomException(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    public CustomException(ICustomExceptionCode iCustomExceptionCode){
        this.code = iCustomExceptionCode.getCode();
        this.message = iCustomExceptionCode.getMessage();
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
