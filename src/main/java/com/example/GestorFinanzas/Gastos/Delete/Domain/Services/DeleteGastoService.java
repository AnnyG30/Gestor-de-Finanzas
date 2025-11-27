package com.example.GestorFinanzas.Gastos.Delete.Domain.Services;

import com.example.GestorFinanzas.Gastos.Shared.Domain.Repository.GastoRepository;
import com.example.GestorFinanzas.Gastos.Shared.Infra.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteGastoService {

    private final GastoRepository repository;

    public DeleteGastoService(GastoRepository repository) {
        this.repository = repository;
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Gasto con id " + id + " no encontrado");
        }
        repository.deleteById(id);
    }
}
