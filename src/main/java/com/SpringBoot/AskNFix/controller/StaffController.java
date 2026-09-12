package com.SpringBoot.AskNFix.controller;

import com.SpringBoot.AskNFix.entity.Staff;
import com.SpringBoot.AskNFix.service.StaffService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public Staff createStaff(@RequestBody Staff staff) {
        return staffService.createStaff(staff);
    }

    @GetMapping
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{staffId}")
    public Staff getStaffById(
            @PathVariable Long staffId) {

        return staffService.getStaffById(staffId);
    }

    @GetMapping("/email/{email}")
    public Staff getStaffByEmail(
            @PathVariable String email) {

        return staffService.getStaffByEmail(email);
    }

    @PutMapping("/{staffId}")
    public Staff updateStaff(
            @PathVariable Long staffId,
            @RequestBody Staff staff) {

        return staffService.updateStaff(staffId, staff);
    }

    @DeleteMapping("/{staffId}")
    public String deleteStaff(
            @PathVariable Long staffId) {

        staffService.deleteStaff(staffId);

        return "Staff deleted successfully";
    }
}