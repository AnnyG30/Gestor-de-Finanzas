package com.example.GestorFinanzas.Gastos.Shared.Infra.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
