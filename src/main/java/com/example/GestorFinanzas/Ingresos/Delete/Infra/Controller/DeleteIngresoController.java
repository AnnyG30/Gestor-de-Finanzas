package com.example.GestorFinanzas.Ingresos.Delete.Infra.Controller;


import com.example.GestorFinanzas.Ingresos.Delete.App.DeleteIngreso;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ingresos/delete")
public class DeleteIngresoController {

    private final DeleteIngreso deleteIngreso;

    public DeleteIngresoController(DeleteIngreso deleteIngreso) {
        this.deleteIngreso = deleteIngreso;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        deleteIngreso.ejecutar(id);
    }
}
