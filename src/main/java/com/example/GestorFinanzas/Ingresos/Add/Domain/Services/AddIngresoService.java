package com.example.GestorFinanzas.Ingresos.Add.Domain.Services;


import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository.IngresoRepository;
import org.springframework.stereotype.Service;

@Service
public class AddIngresoService {

    private final IngresoRepository repository;

    public AddIngresoService(IngresoRepository repository) {
        this.repository = repository;
    }

    public Ingreso agregarIngreso(Ingreso ingreso) {
        if (ingreso.getMonto() == null || ingreso.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }
        return repository.save(ingreso);
    }
}
