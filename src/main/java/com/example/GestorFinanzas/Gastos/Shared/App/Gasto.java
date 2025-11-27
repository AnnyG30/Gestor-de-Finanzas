package com.example.GestorFinanzas.Gastos.Shared.App;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name = "gastos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto")
    private Long idGasto;

    @Column(name = "id_usuario")
    private Long idUsuario = 1L; // ✅ VALOR POR DEFECTO

    @Column(name = "fecha_gasto")
    private LocalDate fechaGasto;

    @Column(name = "archivo_adjunto")
    private String archivoAdjunto;

    private String categoria;

    private Double monto;

    private String descripcion;

    // CONSTRUCTOR PARA CREAR NUEVOS GASTOS
    public Gasto(LocalDate fechaGasto, String categoria, Double monto, String descripcion) {
        this.idUsuario = 1L;
        this.fechaGasto = fechaGasto;
        this.categoria = categoria;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    // CONSTRUCTOR PARA CUANDO LA CATEGORÍA PUEDE SER NULL
    public Gasto(LocalDate fechaGasto, Double monto, String descripcion) {
        this.idUsuario = 1L;
        this.fechaGasto = fechaGasto;
        this.categoria = null; // Categoría puede ser null
        this.monto = monto;
        this.descripcion = descripcion;
    }
}