package com.example.GestorFinanzas.Ingresos.Add.Infra.Controller;


import com.example.GestorFinanzas.Ingresos.Add.App.AddIngreso;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingresos/add")
public class AddIngresoController {

    private final AddIngreso addIngreso;

    public AddIngresoController(AddIngreso addIngreso) {
        this.addIngreso = addIngreso;
    }

    @PostMapping
    public Ingreso agregar(@RequestBody Ingreso ingreso) {
        return addIngreso.ejecutar(ingreso);
    }
}
