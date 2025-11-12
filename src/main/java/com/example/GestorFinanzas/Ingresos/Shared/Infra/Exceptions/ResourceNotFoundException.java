package com.example.GestorFinanzas.Ingresos.Shared.Infra.Exceptions;

public class ResourceNotFoundException extends RuntimeException  {
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
