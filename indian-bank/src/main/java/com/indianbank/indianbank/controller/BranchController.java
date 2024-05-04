package com.indianbank.indianbank.controller;

import com.indianbank.indianbank.dto.BranchDto;
import com.indianbank.indianbank.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/create")
    public ResponseEntity<BranchDto> createBranch(@RequestBody BranchDto branchDto) {
        BranchDto createdBranch = branchService.createBranch(branchDto);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        List<BranchDto> branches = branchService.getAllBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable Integer branchId) {
        BranchDto branch = branchService.getBranchById(branchId);
        if (branch != null) {
            return new ResponseEntity<>(branch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{branchId}")
    public ResponseEntity<BranchDto> updateBranch(@PathVariable Integer branchId, @RequestBody BranchDto branchDto) {
        BranchDto updatedBranch = branchService.updateBranch(branchId, branchDto);
        if (updatedBranch != null) {
            return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{branchId}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Integer branchId) {
        branchService.deleteBranch(branchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/lock/{branchId}")
    public ResponseEntity<Object> lockBranch(@PathVariable Integer branchId) {
        try {
            branchService.lockBranch(branchId);
            return ResponseEntity.ok("Branch locked successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to lock branch: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
