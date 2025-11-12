package com.example.GestorFinanzas.Gastos.Consult.App;

import com.example.GestorFinanzas.Gastos.Consult.Domain.Services.ConsultGastoService;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultGasto {

    private final ConsultGastoService service;

    public ConsultGasto(ConsultGastoService service) {
        this.service = service;
    }

    public List<Gasto> listarTodos() {
        return service.listarTodos();
    }

    public List<Gasto> listarPorUsuario(Long idUsuario) {
        return service.listarPorUsuario(idUsuario);
    }

    public Gasto buscarPorId(Long id) {
        return service.buscarPorId(id);
    }
}
