package com.indianbank.indianbank.mapper;

import com.indianbank.indianbank.dto.BranchDto;
import com.indianbank.indianbank.entity.Branch;

public class BranchMapper {
    public static Branch mapToBranch(BranchDto branchDto) {
        Branch branch = new Branch();
        branch.setBranchId(branchDto.getBranchId());
        branch.setBranchName(branchDto.getBranchName());
        branch.setBranchAddress(branchDto.getBranchAddress());
        branch.setActive(branchDto.getActive());
        branch.setCustomerId(branchDto.getCustomerId());
        branch.setAccountNo(branchDto.getAccountNo());
        branch.setCreatedBy(branchDto.getCreatedBy());
        branch.setCreatedOn(branchDto.getCreatedOn());
        branch.setUpdatedBy(branchDto.getUpdatedBy());
        branch.setUpdatedOn(branchDto.getUpdatedOn());
        return branch;
    }

    public static BranchDto mapToBranchDto(Branch branch) {
        BranchDto branchDto = new BranchDto();
        branchDto.setBranchId(branch.getBranchId());
        branchDto.setBranchName(branch.getBranchName());
        branchDto.setBranchAddress(branch.getBranchAddress());
        branchDto.setActive(branch.getActive());
        branchDto.setCustomerId(branch.getCustomerId());
        branchDto.setAccountNo(branch.getAccountNo());
        branchDto.setCreatedOn(branch.getCreatedOn());
        branchDto.setCreatedBy(branch.getCreatedBy());
        branchDto.setUpdatedOn(branch.getUpdatedOn());
        branchDto.setUpdatedBy(branch.getUpdatedBy());
        return branchDto;
    }
}
