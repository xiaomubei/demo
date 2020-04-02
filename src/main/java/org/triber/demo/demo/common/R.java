package org.triber.demo.demo.common;

import java.io.Serializable;

/**
 * @param <T>
 * @ClassName: R
 * @Description:TODO(响应主体包装)
 * @author: wl
 * @date: 2018年10月9日 下午2:39:20
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int NO_LOGIN = -1; // 未登陆code

    public static final int SUCCESS = 0; // 成功code

    public static final int FAIL = 1; // 失败code

    public static final int NO_PERMISSION = 2; // 没有权限code

    private String msg = "success";

    private int code = SUCCESS;

    private long count; // 针对分页 的总条数

    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public R(T data, long count) {
        super();
        this.data = data;
        this.count = count;
    }

    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

}