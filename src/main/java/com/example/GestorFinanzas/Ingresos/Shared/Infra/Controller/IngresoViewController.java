package com.example.GestorFinanzas.Ingresos.Shared.Infra.Controller;

import com.example.GestorFinanzas.Gastos.Consult.Domain.Services.ConsultGastoService;
import org.springframework.ui.Model;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import com.example.GestorFinanzas.Ingresos.Add.Domain.Services.AddIngresoService;
import com.example.GestorFinanzas.Ingresos.Consult.Domain.Services.ConsultIngresoService;
import com.example.GestorFinanzas.Ingresos.Modify.Domain.Services.ModifyIngresoService;
import com.example.GestorFinanzas.Ingresos.Delete.Domain.Services.DeleteIngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ingresos")
public class IngresoViewController {

    @Autowired
    private AddIngresoService addIngresoService;

    @Autowired
    private ConsultIngresoService consultIngresoService;

    @Autowired
    private ModifyIngresoService modifyIngresoService;

    @Autowired
    private ConsultGastoService consultGastoService;

    @Autowired
    private DeleteIngresoService deleteIngresoService;

    @GetMapping
    public String listarIngresos(Model model) {
        List<Ingreso> ingresos = consultIngresoService.listarTodos();

        // Calcular total de ingresos
        Double totalIngresos = ingresos.stream()
                .mapToDouble(Ingreso::getMonto)
                .sum();

        // Aquí necesitas obtener los gastos para calcular el saldo total
        // Si no tienes este servicio, puedes omitir el saldo por ahora
        Double totalGastos = 0.0; // Reemplaza con tu lógica para obtener gastos
        Double saldoTotal = totalIngresos - totalGastos;

        // Agregar datos al modelo
        model.addAttribute("ingresos", ingresos);
        model.addAttribute("totalIngresos", totalIngresos);
        model.addAttribute("saldoTotal", saldoTotal);

        return "ingresos/list";
    }



    @GetMapping("/add")
    public String mostrarFormularioNuevo(Model model) {
        Ingreso ingreso = new Ingreso();
        ingreso.setFechaIngreso(LocalDateTime.now()); // ← Fecha por defecto
        model.addAttribute("ingreso", ingreso);
        model.addAttribute("titulo", "Nuevo Ingreso");
        return "ingresos/form";
    }

    @PostMapping("/add")
    public String procesarNuevoIngreso(@ModelAttribute Ingreso ingreso, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("💾 Guardando ingreso con fecha: " + ingreso.getFechaIngreso());

            if (ingreso.getIdUsuario() == null) {
                ingreso.setIdUsuario(1L);
            }

            // Si no hay fecha, establecer la actual
            if (ingreso.getFechaIngreso() == null) {
                ingreso.setFechaIngreso(LocalDateTime.now());
            }

            addIngresoService.agregarIngreso(ingreso);
            redirectAttributes.addFlashAttribute("success", "Ingreso agregado correctamente!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ingresos/add";
        }
        return "redirect:/ingresos";
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Ingreso ingreso = consultIngresoService.buscarPorId(id);
            model.addAttribute("ingreso", ingreso);
            model.addAttribute("titulo", "Editar Ingreso");
            return "ingresos/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/ingresos";
        }
    }

    @PostMapping("/edit/{id}")
    public String procesarEdicionIngreso(@PathVariable Long id, @ModelAttribute Ingreso ingreso, RedirectAttributes redirectAttributes) {
        try {
            modifyIngresoService.modificarIngreso(id, ingreso);
            redirectAttributes.addFlashAttribute("success", "Ingreso actualizado correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/ingresos";
    }

    @GetMapping("/delete/{id}")
    public String eliminarIngreso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            deleteIngresoService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Ingreso eliminado correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/ingresos";
    }
}