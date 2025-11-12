package com.example.GestorFinanzas.Ingresos.Shared.App;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long idUsuario;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    private String categoria;

    private Double monto;

    private String descripcion;
}
