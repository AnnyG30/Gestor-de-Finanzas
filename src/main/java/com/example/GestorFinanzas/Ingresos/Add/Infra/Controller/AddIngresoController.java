package com.example.GestorFinanzas.Ingresos.Add.Infra.Controller;


import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/ingresos") // ✅ Cambia el prefijo
public class AddIngresoController {

    @GetMapping("/add")
    public String mostrarFormularioAgregar(Model model) {
        // ... mismo código
        return "ingresos/form";
    }

    @PostMapping("/save")
    public String guardarIngreso(@ModelAttribute Ingreso ingreso, Model model) {
        // ... mismo código
        return "redirect:/ingresos"; // ✅ Redirige al listado principal
    }
}