package com.ProjectMicroservices.Accounts.mapper;

import com.ProjectMicroservices.Accounts.dto.CustomerDTO;
import com.ProjectMicroservices.Accounts.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    //Convert DTO to Entity
    Customer convertCustomerDTOToEntity(CustomerDTO customerDTO);

    //Convert Entity to DTO
    CustomerDTO convertCustomerEntityToDTO(Customer customer);

}
