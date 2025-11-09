package com.example.GestorFinanzas.Ingresos.Consult.App;


import com.example.GestorFinanzas.Ingresos.Consult.Domain.Services.ConsultIngresoService;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsultIngreso {

    private final ConsultIngresoService service;

    public ConsultIngreso(ConsultIngresoService service) {
        this.service = service;
    }

    public List<Ingreso> listarTodos() {
        return service.listarTodos();
    }

    public List<Ingreso> listarPorUsuario(Long idUsuario) {
        return service.listarPorUsuario(idUsuario);
    }

    public Ingreso buscarPorId(Long id) {
        return service.buscarPorId(id);
    }
}
