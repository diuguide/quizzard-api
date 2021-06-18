package com.revature.quizzard.repositories;

import com.revature.quizzard.models.user.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    AccountEntity findByUsername(String username);

    AccountEntity findByUsernameAndPassword(String name, String password);

    boolean existsByUsername(String username);

    boolean existsUserByUsernameAndPassword(String name, String password);

}