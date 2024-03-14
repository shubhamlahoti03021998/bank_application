package com.ProjectMicroservices.Loans.mapper;

import com.ProjectMicroservices.Loans.dto.Loansdto;
import com.ProjectMicroservices.Loans.entity.Loans;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoansMapper {

    LoansMapper INSTANCE = Mappers.getMapper(LoansMapper.class);

    Loansdto convertEntityToDTO(Loans loans);

    Loans convertDTOToEntity(Loansdto loansdto);

}
