package com.indianbank.indianbank.service.impl;
import com.indianbank.indianbank.dto.EmployeeDto;
import com.indianbank.indianbank.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private MapSqlParameterSource mapSqlParameterSource;
    private JavaMailSender javaMailSender;

    @Autowired
    public EmployeeServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JavaMailSender javaMailSender) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.javaMailSender=javaMailSender;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        String sql = "INSERT INTO employee (employee_name, employee_address, employee_phone_number, employee_email) " +
                "VALUES (:employeeName, :employeeAddress, :employeePhoneNumber, :employeeEmail)";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employeeName", employeeDto.getEmployeeName());
        parameters.put("employeeAddress", employeeDto.getEmployeeAddress());
        parameters.put("employeePhoneNumber", employeeDto.getEmployeePhoneNumber());
        parameters.put("employeeEmail", employeeDto.getEmployeeEmail());
        namedParameterJdbcTemplate.update(sql, parameters);
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        List<EmployeeDto> employees = namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeId(rs.getInt("employee_id"));
            employeeDto.setEmployeeName(rs.getString("employee_name"));
            employeeDto.setEmployeeAddress(rs.getString("employee_address"));
            employeeDto.setEmployeePhoneNumber(rs.getString("employee_phone_number"));
            employeeDto.setEmployeeEmail(rs.getString("employee_email"));
            return employeeDto;
        });
        return employees;
    }

    @Override
    public EmployeeDto getEmployeeById(Integer employeeId) {
        String sql = "SELECT * FROM employee WHERE employee_id = :employeeId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employeeId", employeeId);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployeeId(rs.getInt("employee_id"));
            employeeDto.setEmployeeName(rs.getString("employee_name"));
            employeeDto.setEmployeeAddress(rs.getString("employee_address"));
            employeeDto.setEmployeePhoneNumber(rs.getString("employee_phone_number"));
            employeeDto.setEmployeeEmail(rs.getString("employee_email"));
            return employeeDto;
        });
    }

    @Override
    public EmployeeDto updateEmployee(Integer employeeId, EmployeeDto employeeDto) {
        String sql = "UPDATE employee SET employee_name = :employeeName, " +
                "employee_address = :employeeAddress, employee_phone_number = :employeePhoneNumber, employee_email = :employeeEmail " +
                "WHERE employee_id = :employeeId";

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employeeName", employeeDto.getEmployeeName());
        parameters.put("employeeAddress", employeeDto.getEmployeeAddress());
        parameters.put("employeePhoneNumber", employeeDto.getEmployeePhoneNumber());
        parameters.put("employeeEmail", employeeDto.getEmployeeEmail());
        parameters.put("employeeId", employeeId);
        namedParameterJdbcTemplate.update(sql, parameters);

        return employeeDto;
    }

    @Override
    public void deleteEmployee(Integer employeeId) {
        String sql = "DELETE FROM employee WHERE employee_id = :employeeId";
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("employeeId", employeeId);
        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public boolean isValidUser(Integer employeeId, String password){

        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("employeeId",employeeId);
        mapSqlParameterSource.addValue("password",password);
        String sql = "SELECT COUNT(*) FROM employee WHERE employee_id = :employeeId AND password = :password";
        Integer loginEmp= namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource,Integer.class);
        if(loginEmp>0) {
            String updateSql = "UPDATE employee SET is_active = true WHERE employee_id= :employeeId";
            int rowsUpdated = namedParameterJdbcTemplate.update(updateSql,mapSqlParameterSource);
            return rowsUpdated>0;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean logout(Integer employeeId){
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("employeeId", employeeId);
            String sql = "UPDATE employee SET is_active = false WHERE employee_id = :employeeId";
            Integer rowsUpdated = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
//           check if the update operation was successful
            if(rowsUpdated>0){
                System.out.println("Logout Successful!");
                return true;
            }
            else {
                System.out.println("User not found or already logged out");
                return false;
            }
        }
        catch(Exception e){
            System.err.println("Logout Failed! Error: " +e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void registerEmp(String employeeName, String employeeAddress, String employeePhoneNumber, String employeeEmail, String password){
        String sql = "INSERT INTO employee(employee_name, employee_address, employee_phone_number, employee_email, password) " +
                "VALUES (:employeeName, :employeeAddress, :employeePhoneNumber, :employeeEmail, :password)";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("employeeName", employeeName);
        mapSqlParameterSource.addValue("employeeAddress", employeeAddress);
        mapSqlParameterSource.addValue("employeePhoneNumber", employeePhoneNumber);
        mapSqlParameterSource.addValue("employeeEmail",employeeEmail);
        mapSqlParameterSource.addValue("password",password);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public String generateToken(){
        return UUID.randomUUID().toString();
    }

    @Override
    public Boolean saveToken(String employeeEmail, String token){
        try {
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("employeeEmail", employeeEmail);
            mapSqlParameterSource.addValue("token", token);
            String sql = "INSERT INTO employee (employeeEmail, token) VALUES (:employee_email, :token)";
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sendPasswordResetEmail(String employeeEmail, String token){
//        creating email message
        try{
            SimpleMailMessage message= new SimpleMailMessage();
            message.setTo(employeeEmail);
            message.setSubject("Password Reset Request");
            message.setText("Dear User, \n\n As per your request to reset the password, click the following link to reset your password:\n\n" +
                    "http://yourdomain.com/reset-password?token=" + token);
            javaMailSender.send(message);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Map<String, Object> forgotPassword(String employeeEmail) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = generateToken();
            boolean saveToken = saveToken(employeeEmail, token);
            if (saveToken) {
                boolean emailSent = sendPasswordResetEmail(employeeEmail, token);
                if (emailSent) {
                    response.put("status", 1);
                    response.put("message","Password reset email sent");
                } else {
                    response.put("status", 0);
                    response.put("error", "Failed to send password reset email");
                }
            } else {
                response.put("status", 0);
                response.put("error", "Failed to save reset token");
            }
        } catch (Exception e) {
            response.put("status", 0);
            response.put("error", "Error in processing the password reset request. Error: " + e.getMessage());
            e.printStackTrace();
          }
        return response;
    }


    @Override
    public Boolean resetPassword(String token, String newPassword){
        try{
            String sql= "UPDATE employee SET password = :newPassword WHERE token = :token";
            mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("token", token);
            mapSqlParameterSource.addValue("newPassword", newPassword);
            int rowsUpdated = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            // Delete the token from the database after successful password reset
            deleteToken(token);
            return rowsUpdated > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteToken(String token) {
        String sql = "DELETE FROM employee WHERE token = :token";
        mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("token", token);
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

    }

    @Override
    public void lockEmployee(Integer employeeId) {
        String lockEmployeeSql = "UPDATE employee SET is_active = false WHERE employee_id = :employeeId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("employeeId", employeeId);
        namedParameterJdbcTemplate.update(lockEmployeeSql, params);
    }
}
