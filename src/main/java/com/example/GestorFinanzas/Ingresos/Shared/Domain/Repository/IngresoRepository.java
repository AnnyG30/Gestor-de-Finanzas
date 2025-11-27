package com.example.GestorFinanzas.Ingresos.Shared.Domain.Repository;

import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngresoRepository  extends JpaRepository<Ingreso, Long> {
}
