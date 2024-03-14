package com.ProjectMicroservices.Loans.service;

import com.ProjectMicroservices.Loans.dto.Loansdto;
import org.springframework.stereotype.Service;

@Service
public interface LoansService {



    /**
     *
     * @param mobileNumber - Mobile Number of the Customer
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Loan Details based on a given mobileNumber
     */
    Loansdto fetchLoan(String mobileNumber);

    /**
     *
     * @param Loansdto - Loansdto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateLoan(Loansdto Loansdto);

    boolean updateLoan(Loansdto Loansdto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    boolean deleteLoan(String mobileNumber);
}
