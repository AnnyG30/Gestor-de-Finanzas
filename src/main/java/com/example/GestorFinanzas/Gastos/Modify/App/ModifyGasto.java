package com.example.GestorFinanzas.Gastos.Modify.App;

import com.example.GestorFinanzas.Gastos.Modify.Domain.Services.ModifyGastoService;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.stereotype.Component;

@Component
public class ModifyGasto {

    private final ModifyGastoService service;

    public ModifyGasto(ModifyGastoService service) {
        this.service = service;
    }

    public Gasto ejecutar(Long id, Gasto gasto) {
        return service.modificarGasto(id, gasto);
    }
}
