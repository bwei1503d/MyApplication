package com.bwei.ydhl.httpclient;

import org.xutils.http.annotation.HttpResponse;

/**
 * Created by muhanxi on 17/4/16.
 */

@HttpResponse(parser = ResultParser.class)
public class ResponseEntity {

    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
