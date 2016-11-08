package com.veslabs.jetlinklibrary.messaging;

/**
 * Created by Burhan Aras on 10/26/2016.
 */

public interface AsyncResponse {
    void onSuccess(Object response);

    void onFailure(String message);
}
