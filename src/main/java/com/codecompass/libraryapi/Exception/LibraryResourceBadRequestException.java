package com.codecompass.libraryapi.Exception;

public class LibraryResourceBadRequestException extends Exception{

    private String traceId;

    public LibraryResourceBadRequestException(String traceId, String message) {

        super(message);
        this.traceId=traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTraceId() {
        return traceId;
    }
}
