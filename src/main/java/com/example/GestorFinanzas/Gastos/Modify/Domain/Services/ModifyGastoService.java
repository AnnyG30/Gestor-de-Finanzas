package com.example.GestorFinanzas.Gastos.Modify.Domain.Services;


import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import com.example.GestorFinanzas.Gastos.Shared.Domain.Repository.GastoRepository;
import com.example.GestorFinanzas.Gastos.Shared.Infra.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ModifyGastoService {

    private final GastoRepository repository;

    public ModifyGastoService(GastoRepository repository) {
        this.repository = repository;
    }

    public Gasto modificarGasto(Long id, Gasto gastoActualizado) {
        Gasto gastoExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto con id " + id + " no encontrado"));

        gastoExistente.setCategoria(gastoActualizado.getCategoria());
        gastoExistente.setMonto(gastoActualizado.getMonto());
        gastoExistente.setDescripcion(gastoActualizado.getDescripcion());
        gastoExistente.setFechaIngreso(gastoActualizado.getFechaIngreso());
        gastoExistente.setIdUsuario(gastoActualizado.getIdUsuario());

        return repository.save(gastoExistente);
    }
}
