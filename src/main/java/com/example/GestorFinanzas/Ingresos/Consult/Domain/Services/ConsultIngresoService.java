package com.example.GestorFinanzas.Ingresos.Consult.Domain.Services;


import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository.IngresoRepository;
import com.example.GestorFinanzas.Ingresos.Shared.Infra.Exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultIngresoService {

    private final IngresoRepository repository;

    public ConsultIngresoService(IngresoRepository repository) {
        this.repository = repository;
    }

    public List<Ingreso> listarTodos() {
        return repository.findAll();
    }

    public List<Ingreso> listarPorUsuario(Long idUsuario) {
        List<Ingreso> ingresos = repository.findAll()
                .stream()
                .filter(i -> i.getIdUsuario().equals(idUsuario))
                .toList();

        if (ingresos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron ingresos para el usuario " + idUsuario);
        }
        return ingresos;
    }

    public Ingreso buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingreso con id " + id + " no encontrado"));
    }

}
