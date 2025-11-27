package com.example.GestorFinanzas.Gastos.Consult.Domain.Services;


import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import com.example.GestorFinanzas.Gastos.Shared.Domain.Repository.GastoRepository;
import com.example.GestorFinanzas.Gastos.Shared.Infra.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultGastoService {

    private final GastoRepository repository;

    public ConsultGastoService(GastoRepository repository) {
        this.repository = repository;
    }

    public List<Gasto> listarTodos() {
        return repository.findAll();
    }

    public List<Gasto> listarPorUsuario(Long idUsuario) {
        List<Gasto> gastos = repository.findAll()
                .stream()
                .filter(g -> g.getIdUsuario().equals(idUsuario))
                .toList();

        if (gastos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron gastos para el usuario " + idUsuario);
        }
        return gastos;
    }

    public Gasto buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gasto con id " + id + " no encontrado"));
    }
}
