package com.example.GestorFinanzas.Gastos.Delete.Infra.Controller;


import com.example.GestorFinanzas.Gastos.Delete.App.DeleteGasto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gastos/delete")
public class DeleteGastoController {

    private final DeleteGasto deleteGasto;

    public DeleteGastoController(DeleteGasto deleteGasto) {
        this.deleteGasto = deleteGasto;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        deleteGasto.ejecutar(id);
    }

}
