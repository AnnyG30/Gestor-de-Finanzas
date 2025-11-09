package com.example.GestorFinanzas.Ingresos.Modify.App;


import com.example.GestorFinanzas.Ingresos.Modify.Domain.Services.ModifyIngresoService;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.stereotype.Component;

@Component
public class ModifyIngreso {
    private final ModifyIngresoService service;

    public ModifyIngreso(ModifyIngresoService service) {
        this.service = service;
    }

    public Ingreso ejecutar(Long id, Ingreso ingreso) {
        return service.modificarIngreso(id, ingreso);
    }
}
