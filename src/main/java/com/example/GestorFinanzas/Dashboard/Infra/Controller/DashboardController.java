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

    //  MÉTODO CALCULAR TOTAL
    private double calcularTotal(List<?> items) {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }

        double total = 0.0;
        for (Object item : items) {
            if (item instanceof Gasto) {
                Gasto gasto = (Gasto) item;
                if (gasto != null && gasto.getMonto() != null) {
                    total += gasto.getMonto();
                }
            } else if (item instanceof Ingreso) {
                Ingreso ingreso = (Ingreso) item;
                if (ingreso != null && ingreso.getMonto() != null) {
                    total += ingreso.getMonto();
                }
            }
        }
        return total;
    }

    //  MÉTODO PARA GASTOS
    private List<Gasto> obtenerUltimosGastos(List<Gasto> gastos, int limite) {
        if (gastos == null || gastos.isEmpty()) {
            return new ArrayList<>();
        }

        // Filtrar gastos con fecha null y ordenar por fechaGasto
        List<Gasto> gastosConFecha = new ArrayList<>();
        List<Gasto> gastosSinFecha = new ArrayList<>();

        for (Gasto gasto : gastos) {
            if (gasto != null) {
                if (gasto.getFechaGasto() != null) {
                    gastosConFecha.add(gasto);
                } else {
                    gastosSinFecha.add(gasto);
                }
            }
        }

        // Ordenar solo los que tienen fecha
        gastosConFecha.sort((g1, g2) -> g2.getFechaGasto().compareTo(g1.getFechaGasto()));

        // Combinar: primero los con fecha (ordenados), luego los sin fecha
        List<Gasto> todosGastos = new ArrayList<>();
        todosGastos.addAll(gastosConFecha);
        todosGastos.addAll(gastosSinFecha);

        // Limitar resultados
        if (todosGastos.size() > limite) {
            return todosGastos.subList(0, limite);
        }
        return todosGastos;
    }

    //  MÉTODO PARA INGRESOS
    private List<Ingreso> obtenerUltimosIngresos(List<Ingreso> ingresos, int limite) {
        if (ingresos == null || ingresos.isEmpty()) {
            return new ArrayList<>();
        }

        // Filtrar ingresos con fecha null y ordenar por fechaIngreso
        List<Ingreso> ingresosConFecha = new ArrayList<>();
        List<Ingreso> ingresosSinFecha = new ArrayList<>();

        for (Ingreso ingreso : ingresos) {
            if (ingreso != null) {
                if (ingreso.getFechaIngreso() != null) {
                    ingresosConFecha.add(ingreso);
                } else {
                    ingresosSinFecha.add(ingreso);
                }
            }
        }

        // Ordenar solo los que tienen fecha
        ingresosConFecha.sort((i1, i2) -> i2.getFechaIngreso().compareTo(i1.getFechaIngreso()));

        // Combinar: primero los con fecha (ordenados), luego los sin fecha
        List<Ingreso> todosIngresos = new ArrayList<>();
        todosIngresos.addAll(ingresosConFecha);
        todosIngresos.addAll(ingresosSinFecha);

        // Limitar resultados
        if (todosIngresos.size() > limite) {
            return todosIngresos.subList(0, limite);
        }
        return todosIngresos;
    }

    private Map<String, Double> calcularGastosPorCategoria(List<Gasto> gastos) {
        Map<String, Double> categorias = new HashMap<>();

        if (gastos == null || gastos.isEmpty()) {
            return categorias;
        }

        for (Gasto gasto : gastos) {
            //  VALIDACIÓN MÁS ROBUSTA PARA CATEGORÍAS NULL
            String categoria = gasto.getCategoria();
            if (categoria == null || categoria.trim().isEmpty()) {
                categoria = "Sin Categoría";
            }

            //  VALIDAR QUE MONTO NO SEA NULL
            Double monto = gasto.getMonto();
            if (monto == null) {
                monto = 0.0;
            }

            categorias.put(categoria, categorias.getOrDefault(categoria, 0.0) + monto);
        }

        return categorias;
    }
}