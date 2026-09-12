package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Appliance;
import com.SpringBoot.AskNFix.repository.ApplianceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplianceService {

    private final ApplianceRepository applianceRepository;

    public ApplianceService(ApplianceRepository applianceRepository) {
        this.applianceRepository = applianceRepository;
    }

    public Appliance createAppliance(Appliance appliance) {
        return applianceRepository.save(appliance);
    }

    public List<Appliance> getAllAppliances() {
        return applianceRepository.findAll();
    }

    public Appliance getApplianceById(Long applianceId) {
        return applianceRepository.findById(applianceId)
                .orElseThrow(() -> new RuntimeException("Appliance not found"));
    }

    public Appliance updateAppliance(Long applianceId, Appliance updatedAppliance) {

        Appliance appliance = getApplianceById(applianceId);

        appliance.setName(updatedAppliance.getName());
        appliance.setDescription(updatedAppliance.getDescription());

        return applianceRepository.save(appliance);
    }

    public void deleteAppliance(Long applianceId) {

        Appliance appliance = getApplianceById(applianceId);

        applianceRepository.delete(appliance);
    }
}