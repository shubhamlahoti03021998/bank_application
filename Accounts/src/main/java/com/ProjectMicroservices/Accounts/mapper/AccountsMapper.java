package com.ProjectMicroservices.Accounts.mapper;

import com.ProjectMicroservices.Accounts.dto.AccountsDTO;
import com.ProjectMicroservices.Accounts.entity.Accounts;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountsMapper {

    AccountsMapper INSTANCE = Mappers.getMapper(AccountsMapper.class);

    //Convert DTO to Entity
    Accounts convertAccountsDTOToEntity(AccountsDTO accountsDTO);

    //Convert Entity to DTO
    AccountsDTO convertAccountsEntityToDTO(Accounts accounts);
}
