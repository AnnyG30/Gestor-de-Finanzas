package com.example.GestorFinanzas.Dashboard.Infra.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {

        return "dashboard/dashboard"; // apunta a templates/dashboard/dashboard.html
    }

}
