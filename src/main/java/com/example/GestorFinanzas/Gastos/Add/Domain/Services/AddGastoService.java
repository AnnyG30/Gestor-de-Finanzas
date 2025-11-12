package com.example.GestorFinanzas.Gastos.Add.Domain.Services;


import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import com.example.GestorFinanzas.Gastos.Shared.Domain.Repository.GastoRepository;
import org.springframework.stereotype.Service;

@Service
public class AddGastoService {


    private final GastoRepository repository;

    public AddGastoService(GastoRepository repository) {
        this.repository = repository;
    }

    public Gasto agregarGasto(Gasto gasto) {
        if (gasto.getMonto() == null || gasto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que 0");
        }
        return repository.save(gasto);
    }
}
