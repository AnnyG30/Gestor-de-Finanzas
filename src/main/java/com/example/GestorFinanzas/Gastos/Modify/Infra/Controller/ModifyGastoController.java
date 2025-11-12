package com.example.GestorFinanzas.Gastos.Modify.Infra.Controller;

import com.example.GestorFinanzas.Gastos.Modify.App.ModifyGasto;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gastos/modify")
public class ModifyGastoController {

    private final ModifyGasto modifyGasto;

    public ModifyGastoController(ModifyGasto modifyGasto) {
        this.modifyGasto = modifyGasto;
    }

    @PutMapping("/{id}")
    public Gasto modificar(@PathVariable Long id, @RequestBody Gasto gasto) {
        return modifyGasto.ejecutar(id, gasto);
    }
}
