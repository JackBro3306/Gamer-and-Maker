package net.typedef.gam.bean;

import com.google.gson.annotations.Expose;

public class ResponseModel<T> {
    public static final int CODE_OK = 200;
    public static final int CODE_ERROR_PARAMETERS = 501;
    @Expose
    private int code;
    @Expose
    private String msg;
    @Expose
    private T data;

    public ResponseModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static <T> ResponseModel<T> buildOk(T result) {
        return new ResponseModel<>(CODE_OK,"success",result);
    }

    public static <T>  ResponseModel<T> buildParameterError() {
        return new ResponseModel<T>(CODE_ERROR_PARAMETERS, "Parameters Error.",null);
    }
}
