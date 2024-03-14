package com.ProjectMicroservices.Accounts.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * Custom Exception : Whenever this Class Object is created it will call constructor and give "BAD_REQUEST"
 * */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerAlreadyExistsException extends RuntimeException{

  /**
   * @param message : Customer with this Mobile Number Already Exists
   */
  public CustomerAlreadyExistsException(String message){
   super(message);
  }

}
