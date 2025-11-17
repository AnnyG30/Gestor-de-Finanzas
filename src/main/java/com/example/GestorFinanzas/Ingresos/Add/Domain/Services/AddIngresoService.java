package com.example.GestorFinanzas.Ingresos.Add.Domain.Services;

import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository.IngresoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddIngresoService {

    @Autowired
    private IngresoRepository repository;

    public Ingreso agregarIngreso(Ingreso ingreso) {
        if (ingreso.getMonto() == null || ingreso.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }
        if (ingreso.getDescripcion() == null || ingreso.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        return repository.save(ingreso);
    }
}