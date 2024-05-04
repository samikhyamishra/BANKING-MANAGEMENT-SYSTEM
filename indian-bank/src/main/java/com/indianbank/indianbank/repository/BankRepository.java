package com.indianbank.indianbank.repository;

import com.indianbank.indianbank.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {

}