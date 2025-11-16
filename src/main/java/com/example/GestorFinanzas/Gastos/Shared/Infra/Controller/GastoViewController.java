package com.example.GestorFinanzas.Gastos.Shared.Infra.Controller;

import org.springframework.ui.Model;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import com.example.GestorFinanzas.Gastos.Add.Domain.Services.AddGastoService;
import com.example.GestorFinanzas.Gastos.Consult.Domain.Services.ConsultGastoService;
import com.example.GestorFinanzas.Gastos.Modify.Domain.Services.ModifyGastoService;
import com.example.GestorFinanzas.Gastos.Delete.Domain.Services.DeleteGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime; // ← Asegúrate de importar esto

@Controller
@RequestMapping("/gastos")
public class GastoViewController {

    @Autowired
    private AddGastoService addGastoService;

    @Autowired
    private ConsultGastoService consultGastoService;

    @Autowired
    private ModifyGastoService modifyGastoService;

    @Autowired
    private DeleteGastoService deleteGastoService;

    @GetMapping
    public String listarGastos(Model model) {
        model.addAttribute("gastos", consultGastoService.listarTodos());
        return "gastos/list";
    }

    @GetMapping("/add")
    public String mostrarFormularioNuevo(Model model) {
        Gasto gasto = new Gasto();
        gasto.setFechaGasto(LocalDateTime.now()); // ← Fecha por defecto
        model.addAttribute("gasto", gasto);
        model.addAttribute("titulo", "Nuevo Gasto");
        return "gastos/form";
    }

    @PostMapping("/add")
    public String procesarNuevoGasto(@ModelAttribute Gasto gasto, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("💾 Guardando gasto con fecha: " + gasto.getFechaGasto());

            if (gasto.getIdUsuario() == null) {
                gasto.setIdUsuario(1L);
            }

            // Si no hay fecha, establecer la actual
            if (gasto.getFechaGasto() == null) {
                gasto.setFechaGasto(LocalDateTime.now());
            }

            addGastoService.agregarGasto(gasto);
            redirectAttributes.addFlashAttribute("success", "Gasto agregado correctamente!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/gastos/add";
        }
        return "redirect:/gastos";
    }

    // ... (los otros métodos se mantienen igual)
    @GetMapping("/edit/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Gasto gasto = consultGastoService.buscarPorId(id);
            model.addAttribute("gasto", gasto);
            model.addAttribute("titulo", "Editar Gasto");
            return "gastos/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/gastos";
        }
    }

    @PostMapping("/edit/{id}")
    public String procesarEdicionGasto(@PathVariable Long id, @ModelAttribute Gasto gasto, RedirectAttributes redirectAttributes) {
        try {
            modifyGastoService.modificarGasto(id, gasto);
            redirectAttributes.addFlashAttribute("success", "Gasto actualizado correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/gastos";
    }

    @GetMapping("/delete/{id}")
    public String eliminarGasto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            deleteGastoService.eliminar(id);
            redirectAttributes.addFlashAttribute("success", "Gasto eliminado correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/gastos";
    }
}