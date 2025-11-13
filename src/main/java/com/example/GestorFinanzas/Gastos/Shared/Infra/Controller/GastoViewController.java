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

    // Página principal - Lista todos los gastos
    @GetMapping
    public String listarGastos(Model model) {
        model.addAttribute("gastos", consultGastoService.listarTodos());
        return "gastos/list";
    }

    // Mostrar formulario para NUEVO gasto
    @GetMapping("/add")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("gasto", new Gasto());
        model.addAttribute("titulo", "Nuevo Gasto");
        return "gastos/form";
    }

    // Procesar NUEVO gasto
    @PostMapping("/add")
    public String procesarNuevoGasto(@ModelAttribute Gasto gasto, RedirectAttributes redirectAttributes) {
        try {
            // Aquí deberías obtener el ID del usuario desde la sesión o autenticación
            // Por ahora, lo hardcodeamos o lo pasamos como campo oculto en el formulario
            if (gasto.getIdUsuario() == null) {
                gasto.setIdUsuario(1L); // Usuario por defecto - CAMBIAR ESTO
            }

            addGastoService.agregarGasto(gasto);
            redirectAttributes.addFlashAttribute("success", "Gasto agregado correctamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/gastos/add";
        }
        return "redirect:/gastos";
    }

    // Mostrar formulario para EDITAR gasto
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

    // Procesar ACTUALIZACIÓN de gasto
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
            // CAMBIA ESTO:
            deleteGastoService.eliminar(id); // ← Usa "eliminar" en lugar de "eliminarGasto"

            redirectAttributes.addFlashAttribute("success", "Gasto eliminado correctamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/gastos";
    }

    // Listar gastos por usuario (opcional)
    @GetMapping("/usuario/{idUsuario}")
    public String listarGastosPorUsuario(@PathVariable Long idUsuario, Model model) {
        try {
            model.addAttribute("gastos", consultGastoService.listarPorUsuario(idUsuario));
            model.addAttribute("titulo", "Gastos del Usuario " + idUsuario);
            return "gastos/list";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "gastos/list";
        }
    }
}