package com.example.GestorFinanzas.Gastos.Add.App;


import com.example.GestorFinanzas.Gastos.Add.Domain.Services.AddGastoService;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.stereotype.Component;

@Component
public class AddGasto {


    private final AddGastoService service;

    public AddGasto(AddGastoService service) {
        this.service = service;
    }

    public Gasto ejecutar(Gasto gasto) {
        return service.agregarGasto(gasto);
    }
}
