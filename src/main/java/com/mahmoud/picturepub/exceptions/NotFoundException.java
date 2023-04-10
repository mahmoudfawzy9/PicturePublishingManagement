package com.mahmoud.picturepub.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Long id) {
        super("Could not find Entity with id " + id);
    }
}