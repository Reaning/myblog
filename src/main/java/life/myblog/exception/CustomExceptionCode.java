package life.myblog.exception;

public enum CustomExceptionCode implements ICustomExceptionCode{
    ARTICLE_NOT_FOUND(1001,"这篇文章找不到了！"),
    ;

    private Integer code;
    private String message;

    CustomExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
