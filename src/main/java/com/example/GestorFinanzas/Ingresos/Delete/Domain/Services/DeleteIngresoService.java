package com.example.GestorFinanzas.Ingresos.Delete.Domain.Services;


import com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository.IngresoRepository;
import com.example.GestorFinanzas.Ingresos.Shared.Infra.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteIngresoService {

    private final IngresoRepository repository;

    public DeleteIngresoService(IngresoRepository repository) {
        this.repository = repository;
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ingreso con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}
