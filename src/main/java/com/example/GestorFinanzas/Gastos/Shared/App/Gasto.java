package com.example.GestorFinanzas.Gastos.Shared.App;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Long idUsuario;

    @Column(name = "fecha_ingreso")
    private LocalDateTime fechaIngreso;

    private String categoria;

    private Double monto;

    private String descripcion;
}
