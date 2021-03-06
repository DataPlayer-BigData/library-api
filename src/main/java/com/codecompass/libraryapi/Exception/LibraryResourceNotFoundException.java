package com.codecompass.libraryapi.Exception;

public class LibraryResourceNotFoundException extends Exception{

    private String traceId;

    public LibraryResourceNotFoundException(String traceId,String message) {

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
