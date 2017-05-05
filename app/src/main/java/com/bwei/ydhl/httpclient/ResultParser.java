package com.bwei.ydhl.httpclient;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;


/**
 * Created by muhanxi on 17/4/16.
 */

public class ResultParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {

        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setResult(result);
        //返回ResponseEntity对象
        return responseEntity;
    }
}
