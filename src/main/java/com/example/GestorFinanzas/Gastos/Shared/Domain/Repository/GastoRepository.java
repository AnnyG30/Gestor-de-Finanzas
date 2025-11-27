package com.example.GestorFinanzas.Gastos.Shared.Domain.Repository;

import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository  extends JpaRepository<Gasto, Long> {
}
