package com.example.GestorFinanzas.Ingresos.Shared.Domain.Services;


import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoService {

    @Autowired
    private IngresoRepository ingresoRepository;

    public List<Ingreso> obtenerTodos() {
        return ingresoRepository.findAll();
    }

    public Ingreso obtenerPorId(Long id) {
        return ingresoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado con id " + id));
    }

    public Ingreso guardar(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }

    public void eliminar(Long id) {
        ingresoRepository.deleteById(id);
    }

}
