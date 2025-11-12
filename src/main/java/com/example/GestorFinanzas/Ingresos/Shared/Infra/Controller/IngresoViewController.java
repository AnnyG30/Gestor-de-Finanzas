package com.example.GestorFinanzas.Ingresos.Shared.Infra.Controller;

import org.springframework.ui.Model;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Shared.Domain.Services.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingresos")
public class IngresoViewController {

    @Autowired
    private IngresoService ingresoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ingresos", ingresoService.obtenerTodos());
        return "ingresos/list"; // templates/ingresos/list.html
    }

    @GetMapping("/add")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ingreso", new Ingreso());
        model.addAttribute("titulo", "Nuevo Ingreso");
        return "ingresos/form"; // templates/ingresos/form.html
    }

    @PostMapping("/save")
    public String guardar(@ModelAttribute Ingreso ingreso) {
        ingresoService.guardar(ingreso);
        return "redirect:/ingresos";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Ingreso ingreso = ingresoService.obtenerPorId(id);
        model.addAttribute("ingreso", ingreso);
        model.addAttribute("titulo", "Editar Ingreso");
        return "ingresos/form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable Long id) {
        ingresoService.eliminar(id);
        return "redirect:/ingresos";
    }
}
