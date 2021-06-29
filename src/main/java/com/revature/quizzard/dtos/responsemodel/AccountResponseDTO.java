package com.revature.quizzard.dtos.responsemodel;

import com.revature.quizzard.dtos.responsemodel.lists.AccountCardDTO;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.composites.AccountCardId;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
public @Data class AccountResponseDTO {

    private Set<RoleEntity> roles;

//    private Set<AccountCardDTO> cards;

    private String username;

    private int points;

    public AccountResponseDTO(AccountEntity accountEntity) {
        this.roles = accountEntity.getRoles();
        this.username = accountEntity.getUsername();
//        this.cards = accountEntity.getAccountCards();
        this.points = accountEntity.getPoints();
    }
}
