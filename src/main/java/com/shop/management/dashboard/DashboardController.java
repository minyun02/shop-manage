package com.shop.management.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/dashboard";
    }
}
