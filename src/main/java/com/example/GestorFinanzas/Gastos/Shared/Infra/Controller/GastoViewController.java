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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        try {
            List<Gasto> gastos = consultGastoService.listarTodos();

            // Calcular total de gastos
            Double totalGastos = gastos.stream()
                    .mapToDouble(gasto -> gasto.getMonto() != null ? gasto.getMonto() : 0.0)
                    .sum();

            // Calcular promedio mensual (puedes ajustar esta lógica según tus necesidades)
            Double promedioMensual = totalGastos / Math.max(gastos.size(), 1);

            model.addAttribute("gastos", gastos);
            model.addAttribute("totalGastos", totalGastos);
            model.addAttribute("promedioMensual", promedioMensual);

        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar gastos: " + e.getMessage());
            model.addAttribute("gastos", new ArrayList<Gasto>());
            model.addAttribute("totalGastos", 0.0);
            model.addAttribute("promedioMensual", 0.0);
        }

        return "gastos/list";
    }

    @GetMapping("/add")
    public String mostrarFormularioNuevo(Model model) {
        Gasto gasto = new Gasto();
        gasto.setFechaGasto(LocalDateTime.now());
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