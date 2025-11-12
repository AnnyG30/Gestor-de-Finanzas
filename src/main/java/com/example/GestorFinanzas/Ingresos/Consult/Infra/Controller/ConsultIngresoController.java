package com.example.GestorFinanzas.Ingresos.Consult.Infra.Controller;


import com.example.GestorFinanzas.Ingresos.Consult.App.ConsultIngreso;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingresos/consult")
public class ConsultIngresoController {

    private final ConsultIngreso consultIngreso;

    public ConsultIngresoController(ConsultIngreso consultIngreso) {
        this.consultIngreso = consultIngreso;
    }

    @GetMapping("/all")
    public List<Ingreso> listarTodos() {
        return consultIngreso.listarTodos();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Ingreso> listarPorUsuario(@PathVariable Long idUsuario) {
        return consultIngreso.listarPorUsuario(idUsuario);
    }

    @GetMapping("/{id}")
    public Ingreso buscarPorId(@PathVariable Long id) {
        return consultIngreso.buscarPorId(id);
    }
}
