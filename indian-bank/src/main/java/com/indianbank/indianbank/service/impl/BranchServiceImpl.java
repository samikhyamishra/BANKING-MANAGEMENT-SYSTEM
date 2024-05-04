package com.indianbank.indianbank.service.impl;

import com.indianbank.indianbank.dto.BranchDto;
import com.indianbank.indianbank.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BranchServiceImpl implements BranchService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;

    @Autowired
    public BranchServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public BranchDto createBranch(BranchDto branchDto) {
        Integer branchId = generateBranchId();
        branchDto.setBranchId(branchId);
        String sql = "INSERT INTO branch (branch_id, branch_name, branch_address, is_active, created_on, created_by) " +
                "VALUES (:branchId, :branchName, :branchAddress, :active, :createdOn, :createdBy)";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("branchId", branchDto.getBranchId());
        parameters.put("branchName", branchDto.getBranchName());
        parameters.put("branchAddress", branchDto.getBranchAddress());
        parameters.put("active", true);
        parameters.put("createdOn", branchDto.getCreatedOn());
        parameters.put("createdBy", branchDto.getCreatedBy());

        namedParameterJdbcTemplate.update(sql, parameters);
        return branchDto;
    }
    private Integer generateBranchId() {
        return (int) (Math.random() * 900000000) + 100000000;
    }

    @Override
    public List<BranchDto> getAllBranches() {
        String sql = "SELECT * FROM branch";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            BranchDto branchDto = new BranchDto();
            branchDto.setBranchId(rs.getInt("branch_id"));
            branchDto.setBranchName(rs.getString("branch_name"));
            branchDto.setBranchAddress(rs.getString("branch_address"));
            branchDto.setCreatedOn(rs.getDate("created_on"));
            branchDto.setCreatedBy(rs.getInt("created_by"));
            branchDto.setUpdatedOn(rs.getDate("updated_on"));
            branchDto.setUpdatedBy(rs.getInt("updated_by"));
            return branchDto;
        });
    }

   @Override
   public BranchDto getBranchById(Integer branchId) {
       String sql = "SELECT * FROM branch WHERE branch_id = :branchId";
       Map<String, Object> parameters = new HashMap<>();
       parameters.put("branchId", branchId);
       return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
           BranchDto branchDto = new BranchDto();
           branchDto.setBranchId(rs.getInt("branch_id"));
           branchDto.setBranchAddress(rs.getString("branch_address"));
           branchDto.setBranchName(rs.getString("branch_name"));
           return branchDto;
       });
   }

    @Override
    public BranchDto updateBranch(Integer branchId, BranchDto branchDto) {
        String sql = "UPDATE branch SET branch_name = :branchName, branch_address = :branchAddress, is_active = :active WHERE branch_id = :branchId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("branchName", branchDto.getBranchName());
        parameters.put("branchAddress", branchDto.getBranchAddress());
        parameters.put("active",branchDto.getActive());
        parameters.put("branchId", branchId);

        namedParameterJdbcTemplate.update(sql, parameters);

        return branchDto;
    }

   @Override
    public void deleteBranch(Integer branchId) {
        String sql = "DELETE FROM branch WHERE branch_id = :branchId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("branchId", branchId);

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public void lockBranch(Integer branchId) {
        // Update branch status
        String updateBranchSql = "UPDATE branch SET is_active = false WHERE branch_id = :branchId";
        MapSqlParameterSource branchParams = new MapSqlParameterSource();
        branchParams.addValue("branchId", branchId);
        namedParameterJdbcTemplate.update(updateBranchSql, branchParams);

        // Lock related customers
//        String updateCustomersSql = "UPDATE customers SET is_active = false WHERE branch_id = :branchId";
//        MapSqlParameterSource customerParams = new MapSqlParameterSource();
//        customerParams.addValue("branchId", branchId);
//        namedParameterJdbcTemplate.update(updateCustomersSql, customerParams);

        // Lock related accounts
        String updateAccountsSql = "UPDATE accounts SET is_active = false WHERE branch_id = :branchId";
        MapSqlParameterSource accountParams = new MapSqlParameterSource();
        accountParams.addValue("branchId", branchId);
        namedParameterJdbcTemplate.update(updateAccountsSql, accountParams);
    }


}