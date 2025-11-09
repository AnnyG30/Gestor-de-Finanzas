package com.example.GestorFinanzas.Ingresos.Add.App;


import com.example.GestorFinanzas.Ingresos.Add.Domain.Services.AddIngresoService;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.stereotype.Component;

@Component
public class AddIngreso {

    private final AddIngresoService service;

    public AddIngreso(AddIngresoService service){
        this.service = service;
    }

    public Ingreso ejecutar(Ingreso ingreso) {
        return service.agregarIngreso(ingreso);
    }
}
