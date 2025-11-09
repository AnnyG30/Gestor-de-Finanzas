package com.example.GestorFinanzas.Ingresos.Delete.App;

import com.example.GestorFinanzas.Ingresos.Delete.Domain.Services.DeleteIngresoService;

public class DeleteIngreso {

    private final DeleteIngresoService service;

    public DeleteIngreso(DeleteIngresoService service) {
        this.service = service;
    }

    public void ejecutar(Long id) {
        service.eliminar(id);
    }
}
