package com.ProjectMicroservices.Accounts.repository;

import com.ProjectMicroservices.Accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

    /**
     *  @Transactional - Explicitly used because are using a custom method which is updating/modifying data
     * @Modifying - Indicates a method should be regarded as modifying query. And execute  query if this method inside
     *              a transaction (when my spring data JPA runs my query inside a transactionand if there is some error
     *              happens at the runtime, any partial change of data that is resulted due queries will be rolled back
     * @param customerId
     */
    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically=true)
    void deleteByCustomerId(Long customerId);
}
