package com.indianbank.indianbank.service;

import com.indianbank.indianbank.dto.BranchDto;

import java.util.List;

public interface BranchService {
    BranchDto createBranch(BranchDto branchDto);
    List<BranchDto> getAllBranches();
    BranchDto getBranchById(Integer branchId);
    BranchDto updateBranch(Integer branchId, BranchDto branchDto);
    void deleteBranch(Integer branchId);
    void lockBranch(Integer branchId);
}
