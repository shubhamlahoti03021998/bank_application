package com.ProjectMicroservices.Accounts.service;

import com.ProjectMicroservices.Accounts.constants.AccountsConstants;
import com.ProjectMicroservices.Accounts.dto.AccountsDTO;
import com.ProjectMicroservices.Accounts.dto.CustomerDTO;
import com.ProjectMicroservices.Accounts.entity.Accounts;
import com.ProjectMicroservices.Accounts.entity.Customer;
import com.ProjectMicroservices.Accounts.exceptionHandling.CustomerAlreadyExistsException;
import com.ProjectMicroservices.Accounts.exceptionHandling.ResourceNotFoundException;
import com.ProjectMicroservices.Accounts.mapper.AccountsMapper;
import com.ProjectMicroservices.Accounts.mapper.CustomerMapper;
import com.ProjectMicroservices.Accounts.repository.AccountsRepository;
import com.ProjectMicroservices.Accounts.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Transactional
public class AccountsServiceImpl implements AccountsService{


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    @Override
    public void createNewAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.INSTANCE.convertCustomerDTOToEntity(customerDTO);

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Shubham");
        Optional<Customer> byMobileNumber = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if(byMobileNumber.isPresent()){
            throw new CustomerAlreadyExistsException("Customer Already Exists with this given Mobile Number : " + customerDTO.getMobileNumber());
        }
        Customer save = customerRepository.save(customer);
        // create a bank account for the saved customer
        accountsRepository.save(createAccount(customer));

    }

    private Accounts createAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomnumber = 1000000000L + new Random().nextLong(900000000);
        accounts.setAccountNumber(randomnumber);
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy("Shubham");
        accounts.setBranchAddress(AccountsConstants.ADDRESS);
        accounts.setAccountType(AccountsConstants.SAVINGS);
        return accounts;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDTO getAccountDetails(String mobileNumber){
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
       Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account","CustomerId",customer.getCustomerId().toString())
        );
        CustomerDTO customerDTO = CustomerMapper.INSTANCE.convertCustomerEntityToDTO(customer);

        AccountsDTO accountsDTO = AccountsMapper.INSTANCE.convertAccountsEntityToDTO(accounts);
        CustomerDTO accountDetailsFromCustomerMobileNumber = getAccountDetailsFromCustomerMobileNumber(accountsDTO,customerDTO,customer);
        return accountDetailsFromCustomerMobileNumber;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDTO customerDTO) {
        boolean isUpdated=false;
        /**
         * we are checking if account details are there and not null
         */
        AccountsDTO accountsDTO = customerDTO.getAccountsDTO();
        if(accountsDTO!=null){
            Accounts accounts = accountsRepository.findById(accountsDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDTO.getAccountNumber().toString())
            );

            AccountsMapper.INSTANCE.convertAccountsDTOToEntity(accountsDTO);
            Accounts save = accountsRepository.save(accounts);

            /**
             * Now we are checking if customer exists with the given customerId
             */
            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
            );
            CustomerMapper.INSTANCE.convertCustomerDTOToEntity(customerDTO);
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );

        if(customer!=null){
            customerRepository.deleteById(customer.getCustomerId());
            /**
             * could have deleted by passing accountId which is primary key, but then we would have unnecessarily
             * fired an account fetch query, since here customer fetch query is already executed better of using it
             */
            accountsRepository.deleteByCustomerId(customer.getCustomerId());
            return true;
        }
        return false;
    }

    private CustomerDTO getAccountDetailsFromCustomerMobileNumber(AccountsDTO accountsDTO,CustomerDTO customerDTO, Customer customer) {
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setMobileNumber(customer.getMobileNumber());
        customerDTO.setAccountsDTO(accountsDTO);
        return customerDTO;
    }


}
