package com.example.GestorFinanzas.Gastos.Consult.Infra.Controller;


import com.example.GestorFinanzas.Gastos.Consult.App.ConsultGasto;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gastos/consult")
public class ConsultGastoController {

    private final ConsultGasto consultGasto;

    public ConsultGastoController(ConsultGasto consultGasto) {
        this.consultGasto = consultGasto;
    }

    @GetMapping("/all")
    public List<Gasto> listarTodos() {
        return consultGasto.listarTodos();
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Gasto> listarPorUsuario(@PathVariable Long idUsuario) {
        return consultGasto.listarPorUsuario(idUsuario);
    }

    @GetMapping("/{id}")
    public Gasto buscarPorId(@PathVariable Long id) {
        return consultGasto.buscarPorId(id);
    }
}
