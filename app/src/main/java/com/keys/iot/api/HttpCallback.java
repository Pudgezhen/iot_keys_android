package com.keys.iot.api;

public interface HttpCallback {

    void onSuccess(String res);

    void onFailure(Exception e);
}
