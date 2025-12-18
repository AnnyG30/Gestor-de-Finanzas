package com.example.GestorFinanzas.Dashboard.Infra.Controller;

import com.example.GestorFinanzas.Gastos.Consult.Domain.Services.ConsultGastoService;
import com.example.GestorFinanzas.Gastos.Shared.App.Gasto;
import com.example.GestorFinanzas.Ingresos.Consult.Domain.Services.ConsultIngresoService;
import com.example.GestorFinanzas.Ingresos.Shared.App.Ingreso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            System.out.println("📊 Gastos encontrados: " + (gastos != null ? gastos.size() : 0));
            System.out.println("📊 Ingresos encontrados: " + (ingresos != null ? ingresos.size() : 0));

            // Asegurar que nunca sean null
            gastos = (gastos != null) ? gastos : new ArrayList<>();
            ingresos = (ingresos != null) ? ingresos : new ArrayList<>();

            // 2. Cálculos básicos
            double totalIngresos = calcularTotalIngresos(ingresos);
            double totalGastos = calcularTotalGastos(gastos);
            double saldoTotal = totalIngresos - totalGastos;

            // 3. Preparar datos para el template
            model.addAttribute("saldoTotal", saldoTotal);
            model.addAttribute("totalIngresos", totalIngresos);
            model.addAttribute("totalGastos", totalGastos);
            model.addAttribute("totalTransacciones", gastos.size() + ingresos.size());

            model.addAttribute("gastos", gastos);
            model.addAttribute("ingresos", ingresos);

            // Últimos registros (máximo 5 / 3)
            model.addAttribute("ultimosGastos", obtenerUltimosGastos(gastos, 5));
            model.addAttribute("ultimosIngresos", obtenerUltimosIngresos(ingresos, 3));

            // Gastos por categoría
            model.addAttribute("gastosPorCategoria", calcularGastosPorCategoria(gastos));

            // =========================
            // ✅ DATOS REALES PARA GRÁFICO (por mes, año actual)
            // =========================
            int anio = java.time.Year.now().getValue();

            List<String> labels = Arrays.asList("Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic");
            List<Double> ingresosPorMes = new ArrayList<>(Collections.nCopies(12, 0.0));
            List<Double> gastosPorMes   = new ArrayList<>(Collections.nCopies(12, 0.0));

            // Ingresos por mes
            for (Ingreso i : ingresos) {
                if (i != null && i.getFechaIngreso() != null && i.getMonto() != null) {
                    if (i.getFechaIngreso().getYear() == anio) {
                        int mesIndex = i.getFechaIngreso().getMonthValue() - 1; // 0..11
                        ingresosPorMes.set(mesIndex, ingresosPorMes.get(mesIndex) + i.getMonto());
                    }
                }
            }

            // Gastos por mes
            for (Gasto g : gastos) {
                if (g != null && g.getFechaGasto() != null && g.getMonto() != null) {
                    if (g.getFechaGasto().getYear() == anio) {
                        int mesIndex = g.getFechaGasto().getMonthValue() - 1; // 0..11
                        gastosPorMes.set(mesIndex, gastosPorMes.get(mesIndex) + g.getMonto());
                    }
                }
            }

            model.addAttribute("chartLabels", labels);
            model.addAttribute("chartIngresos", ingresosPorMes);
            model.addAttribute("chartGastos", gastosPorMes);
            model.addAttribute("chartAnio", anio);

            System.out.println("✅ Todos los datos cargados en el modelo (incluye gráfico)");

        } catch (Exception e) {
            System.out.println("❌ Error en dashboard: " + e.getMessage());
            e.printStackTrace();

            // Valores por defecto
            model.addAttribute("saldoTotal", 0.0);
            model.addAttribute("totalIngresos", 0.0);
            model.addAttribute("totalGastos", 0.0);
            model.addAttribute("totalTransacciones", 0);
            model.addAttribute("gastos", new ArrayList<>());
            model.addAttribute("ingresos", new ArrayList<>());
            model.addAttribute("ultimosGastos", new ArrayList<>());
            model.addAttribute("ultimosIngresos", new ArrayList<>());
            model.addAttribute("gastosPorCategoria", new HashMap<>());

            // Defaults del gráfico
            model.addAttribute("chartLabels", Arrays.asList("Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"));
            model.addAttribute("chartIngresos", new ArrayList<>(Collections.nCopies(12, 0.0)));
            model.addAttribute("chartGastos", new ArrayList<>(Collections.nCopies(12, 0.0)));
            model.addAttribute("chartAnio", java.time.Year.now().getValue());

            model.addAttribute("error", "Error cargando datos: " + e.getMessage());
        }

        return "dashboard/dashboard";
    }

    // =========================
    // TOTALES
    // =========================
    private double calcularTotalGastos(List<Gasto> gastos) {
        if (gastos == null || gastos.isEmpty()) return 0.0;

        double total = 0.0;
        for (Gasto gasto : gastos) {
            if (gasto != null && gasto.getMonto() != null) {
                total += gasto.getMonto();
            }
        }
        return total;
    }

    private double calcularTotalIngresos(List<Ingreso> ingresos) {
        if (ingresos == null || ingresos.isEmpty()) return 0.0;

        double total = 0.0;
        for (Ingreso ingreso : ingresos) {
            if (ingreso != null && ingreso.getMonto() != null) {
                total += ingreso.getMonto();
            }
        }
        return total;
    }

    // =========================
    // ÚLTIMOS GASTOS
    // =========================
    private List<Gasto> obtenerUltimosGastos(List<Gasto> gastos, int limite) {
        if (gastos == null || gastos.isEmpty()) {
            return new ArrayList<>();
        }

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

        gastosConFecha.sort((g1, g2) -> g2.getFechaGasto().compareTo(g1.getFechaGasto()));

        List<Gasto> todosGastos = new ArrayList<>();
        todosGastos.addAll(gastosConFecha);
        todosGastos.addAll(gastosSinFecha);

        if (todosGastos.size() > limite) {
            return todosGastos.subList(0, limite);
        }
        return todosGastos;
    }

    // =========================
    // ÚLTIMOS INGRESOS
    // =========================
    private List<Ingreso> obtenerUltimosIngresos(List<Ingreso> ingresos, int limite) {
        if (ingresos == null || ingresos.isEmpty()) {
            return new ArrayList<>();
        }

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

        ingresosConFecha.sort((i1, i2) -> i2.getFechaIngreso().compareTo(i1.getFechaIngreso()));

        List<Ingreso> todosIngresos = new ArrayList<>();
        todosIngresos.addAll(ingresosConFecha);
        todosIngresos.addAll(ingresosSinFecha);

        if (todosIngresos.size() > limite) {
            return todosIngresos.subList(0, limite);
        }
        return todosIngresos;
    }

    // =========================
    // GASTOS POR CATEGORÍA
    // =========================
    private Map<String, Double> calcularGastosPorCategoria(List<Gasto> gastos) {
        Map<String, Double> categorias = new HashMap<>();

        if (gastos == null || gastos.isEmpty()) {
            return categorias;
        }

        for (Gasto gasto : gastos) {
            if (gasto == null) continue;

            String categoria = gasto.getCategoria();
            if (categoria == null || categoria.trim().isEmpty()) {
                categoria = "Sin Categoría";
            }

            Double monto = gasto.getMonto();
            if (monto == null) {
                monto = 0.0;
            }

            categorias.put(categoria, categorias.getOrDefault(categoria, 0.0) + monto);
        }

        return categorias;
    }
}
