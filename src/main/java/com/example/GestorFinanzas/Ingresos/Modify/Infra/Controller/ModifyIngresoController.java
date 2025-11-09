package com.example.GestorFinanzas.Ingresos.Modify.Infra.Controller;


import com.example.GestorFinanzas.Ingresos.Modify.App.ModifyIngreso;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingresos/modify")
public class ModifyIngresoController {

    private final ModifyIngreso modifyIngreso;

    public ModifyIngresoController(ModifyIngreso modifyIngreso) {
        this.modifyIngreso = modifyIngreso;
    }

    @PutMapping("/{id}")
    public Ingreso modificar(@PathVariable Long id, @RequestBody Ingreso ingreso) {
        return modifyIngreso.ejecutar(id, ingreso);
    }

}
