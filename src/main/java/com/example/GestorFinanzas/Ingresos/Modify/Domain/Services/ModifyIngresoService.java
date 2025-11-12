package com.example.GestorFinanzas.Ingresos.Modify.Domain.Services;


import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository.IngresoRepository;
import com.example.GestorFinanzas.Ingresos.Shared.Infra.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ModifyIngresoService {

    private final IngresoRepository repository;

    public ModifyIngresoService(IngresoRepository repository) {
        this.repository = repository;
    }

    public Ingreso modificarIngreso(Long id, Ingreso ingresoActualizado) {
        Ingreso ingresoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso con id " + id + " no encontrado"));

        ingresoExistente.setCategoria(ingresoActualizado.getCategoria());
        ingresoExistente.setMonto(ingresoActualizado.getMonto());
        ingresoExistente.setDescripcion(ingresoActualizado.getDescripcion());
        ingresoExistente.setFechaIngreso(ingresoActualizado.getFechaIngreso());
        ingresoExistente.setIdUsuario(ingresoActualizado.getIdUsuario());

        return repository.save(ingresoExistente);
    }

}
