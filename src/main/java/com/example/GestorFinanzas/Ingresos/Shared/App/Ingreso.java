package com.example.GestorFinanzas.Ingresos.Shared.App;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name ="ingresos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingreso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_ingreso")
    private Long idIngreso;

    @Column(name = "id_usuario")
    private Long idUsuario = 1L; //  VALOR POR DEFECTO

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "archivo_adjunto")
    private String archivoAdjunto;

    private String categoria;

    private Double monto;

    private String descripcion;

    //  CONSTRUCTOR PARA CREAR NUEVOS INGRESOS
    public Ingreso(LocalDate fechaIngreso, String categoria, Double monto, String descripcion) {
        this.idUsuario = 1L;
        this.fechaIngreso = fechaIngreso;
        this.categoria = categoria;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    //  CONSTRUCTOR PARA CUANDO LA CATEGORÍA PUEDE SER NULL
    public Ingreso(LocalDate fechaIngreso, Double monto, String descripcion) {
        this.idUsuario = 1L;
        this.fechaIngreso = fechaIngreso;
        this.categoria = null; // Categoría puede ser null
        this.monto = monto;
        this.descripcion = descripcion;
    }
}