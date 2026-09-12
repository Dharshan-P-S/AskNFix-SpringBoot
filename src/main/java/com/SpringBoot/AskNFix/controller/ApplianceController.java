package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.Appliance;
import com.SpringBoot.AskNFix.service.ApplianceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appliances")
public class ApplianceController {

    private final ApplianceService applianceService;

    public ApplianceController(ApplianceService applianceService) {
        this.applianceService = applianceService;
    }

    @PostMapping
    public Appliance createAppliance(@RequestBody Appliance appliance) {
        return applianceService.createAppliance(appliance);
    }

    @GetMapping
    public List<Appliance> getAllAppliances() {
        return applianceService.getAllAppliances();
    }

    @GetMapping("/{applianceId}")
    public Appliance getApplianceById(
            @PathVariable Long applianceId) {

        return applianceService.getApplianceById(applianceId);
    }

    @PutMapping("/{applianceId}")
    public Appliance updateAppliance(
            @PathVariable Long applianceId,
            @RequestBody Appliance appliance) {

        return applianceService.updateAppliance(applianceId, appliance);
    }

    @DeleteMapping("/{applianceId}")
    public String deleteAppliance(
            @PathVariable Long applianceId) {

        applianceService.deleteAppliance(applianceId);

        return "Appliance deleted successfully";
    }
}