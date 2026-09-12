package com.SpringBoot.AskNFix.service;

import com.SpringBoot.AskNFix.entity.Staff;
import com.SpringBoot.AskNFix.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long staffId) {

        return staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public Staff getStaffByEmail(String email) {

        return staffRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
    }

    public Staff updateStaff(Long staffId, Staff updatedStaff) {

        Staff staff = getStaffById(staffId);

        staff.setName(updatedStaff.getName());
        staff.setEmail(updatedStaff.getEmail());
        staff.setPhoneNumber(updatedStaff.getPhoneNumber());
        staff.setRole(updatedStaff.getRole());

        return staffRepository.save(staff);
    }

    public void deleteStaff(Long staffId) {

        Staff staff = getStaffById(staffId);

        staffRepository.delete(staff);
    }
}