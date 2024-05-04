package com.indianbank.indianbank.repository;

import com.indianbank.indianbank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Integer> {

}