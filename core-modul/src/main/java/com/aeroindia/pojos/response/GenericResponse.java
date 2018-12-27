package com.aeroindia.pojos.response;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by PSQ on 11/4/2017.
 */
/*Request
{
    "status": false,
    "operation": "User Login",
    "errorMessage": "Password does not match",
}
 */

public class GenericResponse implements Serializable {

    private boolean status;
    private String operation;
    private String errorMessage;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    static public GenericResponse create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, GenericResponse.class);
    }
    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
