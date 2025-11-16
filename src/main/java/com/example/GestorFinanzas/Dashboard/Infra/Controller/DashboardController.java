package com.example.GestorFinanzas.Dashboard.Infra.Controller;

import com.example.GestorFinanzas.Gastos.Consult.Domain.Services.ConsultGastoService;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import com.example.GestorFinanzas.Ingresos.Consult.Domain.Services.ConsultIngresoService;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private ConsultGastoService consultGastoService;

    @Autowired
    private ConsultIngresoService consultIngresoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        System.out.println("🎯 DashboardController con datos reales ejecutado");

        try {
            // 1. Obtener datos de la base de datos
            List<Gasto> gastos = consultGastoService.listarTodos();
            List<Ingreso> ingresos = consultIngresoService.listarTodos();

            System.out.println("📊 Gastos encontrados: " + gastos.size());
            System.out.println("📊 Ingresos encontrados: " + ingresos.size());

            // 2. Cálculos básicos
            double totalIngresos = calcularTotal(ingresos);
            double totalGastos = calcularTotal(gastos);
            double saldoTotal = totalIngresos - totalGastos;

            // 3. Preparar datos para el template (EVITAR NULLS)
            model.addAttribute("saldoTotal", saldoTotal);
            model.addAttribute("totalIngresos", totalIngresos);
            model.addAttribute("totalGastos", totalGastos);
            model.addAttribute("totalTransacciones", gastos.size() + ingresos.size());

            // Listas - asegurar que nunca sean null
            model.addAttribute("gastos", gastos != null ? gastos : new ArrayList<>());
            model.addAttribute("ingresos", ingresos != null ? ingresos : new ArrayList<>());

            // Últimos registros (máximo 5) - ORDENADOS POR FECHA
            model.addAttribute("ultimosGastos", obtenerUltimosGastos(gastos, 5));
            model.addAttribute("ultimosIngresos", obtenerUltimosIngresos(ingresos, 3));

            // Gastos por categoría
            model.addAttribute("gastosPorCategoria", calcularGastosPorCategoria(gastos));

            System.out.println("✅ Todos los datos cargados en el modelo");

        } catch (Exception e) {
            System.out.println("❌ Error en dashboard: " + e.getMessage());
            e.printStackTrace();

            // En caso de error, poner valores por defecto
            model.addAttribute("saldoTotal", 0.0);
            model.addAttribute("totalIngresos", 0.0);
            model.addAttribute("totalGastos", 0.0);
            model.addAttribute("totalTransacciones", 0);
            model.addAttribute("gastos", new ArrayList<>());
            model.addAttribute("ingresos", new ArrayList<>());
            model.addAttribute("ultimosGastos", new ArrayList<>());
            model.addAttribute("ultimosIngresos", new ArrayList<>());
            model.addAttribute("gastosPorCategoria", new HashMap<>());
            model.addAttribute("error", "Error cargando datos: " + e.getMessage());
        }

        return "dashboard/dashboard";
    }

    private double calcularTotal(List<?> items) {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Object item : items) {
            if (item instanceof Gasto) {
                total += ((Gasto) item).getMonto();
            } else if (item instanceof Ingreso) {
                total += ((Ingreso) item).getMonto();
            }
        }
        return total;
    }

    // MÉTODO ESPECÍFICO PARA GASTOS - ORDENADO POR FECHA_GASTO
    private List<Gasto> obtenerUltimosGastos(List<Gasto> gastos, int limite) {
        if (gastos == null || gastos.isEmpty()) {
            return new ArrayList<>();
        }

        // Ordenar por fechaGasto (los más recientes primero)
        List<Gasto> gastosOrdenados = new ArrayList<>(gastos);
        gastosOrdenados.sort((g1, g2) -> g2.getFechaGasto().compareTo(g1.getFechaGasto()));

        // Limitar resultados
        if (gastosOrdenados.size() > limite) {
            return gastosOrdenados.subList(0, limite);
        }
        return gastosOrdenados;
    }

    // MÉTODO ESPECÍFICO PARA INGRESOS - ORDENADO POR FECHA_INGRESO
    private List<Ingreso> obtenerUltimosIngresos(List<Ingreso> ingresos, int limite) {
        if (ingresos == null || ingresos.isEmpty()) {
            return new ArrayList<>();
        }

        // Ordenar por fechaIngreso (los más recientes primero)
        List<Ingreso> ingresosOrdenados = new ArrayList<>(ingresos);
        ingresosOrdenados.sort((i1, i2) -> i2.getFechaIngreso().compareTo(i1.getFechaIngreso()));

        // Limitar resultados
        if (ingresosOrdenados.size() > limite) {
            return ingresosOrdenados.subList(0, limite);
        }
        return ingresosOrdenados;
    }

    private Map<String, Double> calcularGastosPorCategoria(List<Gasto> gastos) {
        Map<String, Double> categorias = new HashMap<>();

        if (gastos == null || gastos.isEmpty()) {
            return categorias;
        }

        for (Gasto gasto : gastos) {
            // ✅ VALIDACIÓN MÁS ROBUSTA PARA CATEGORÍAS NULL
            String categoria = gasto.getCategoria();
            if (categoria == null || categoria.trim().isEmpty()) {
                categoria = "Sin Categoría";
            }

            // ✅ VALIDAR QUE MONTO NO SEA NULL
            Double monto = gasto.getMonto();
            if (monto == null) {
                monto = 0.0;
            }

            categorias.put(categoria, categorias.getOrDefault(categoria, 0.0) + monto);
        }

        return categorias;
    }
}