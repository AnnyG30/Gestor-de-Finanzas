package com.example.GestorFinanzas.Gastos.Delete.App;

import com.example.GestorFinanzas.Gastos.Delete.Domain.Services.DeleteGastoService;
import org.springframework.stereotype.Component;

@Component
public class DeleteGasto {

    private final DeleteGastoService service;

    public DeleteGasto(DeleteGastoService service) {
        this.service = service;
    }

    public void ejecutar(Long id) {
        service.eliminar(id);
    }
}
