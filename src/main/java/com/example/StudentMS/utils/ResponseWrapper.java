package com.example.StudentMS.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper<T> {
    private boolean success;
    private T data;
    private String massage;

    public static <T> ResponseWrapper<T> success(T data) {
        return new ResponseWrapper<>(true, data, null);
    }

    public static <T> ResponseWrapper<T> fall(String massage){
        return new ResponseWrapper<>(false,null,massage);
    }
}
