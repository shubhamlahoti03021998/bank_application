package com.ProjectMicroservices.Accounts.service;

import com.ProjectMicroservices.Accounts.dto.AccountsDTO;
import com.ProjectMicroservices.Accounts.dto.CustomerDTO;
import com.ProjectMicroservices.Accounts.dto.ResponseDTO;

public interface AccountsService {
    void createNewAccount(CustomerDTO customerDTO);

    CustomerDTO getAccountDetails(String mobileNumber);

    boolean updateAccount(CustomerDTO customerDTO);


    boolean deleteAccount(String mobileNumber);
}
