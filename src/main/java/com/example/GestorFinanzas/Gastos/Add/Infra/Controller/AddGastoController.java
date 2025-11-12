package com.example.GestorFinanzas.Gastos.Add.Infra.Controller;

import com.example.GestorFinanzas.Gastos.Add.App.AddGasto;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gastos/add")
public class AddGastoController {


    private final AddGasto addGasto;

    public AddGastoController(AddGasto addGasto) {
        this.addGasto = addGasto;
    }

    @PostMapping
    public Gasto agregar(@RequestBody Gasto gasto) {
        return addGasto.ejecutar(gasto);
    }
}
