package com.ProjectMicroservices.Cards.service;

import com.ProjectMicroservices.Cards.dto.CardsDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CardsService {
    void createCard(CardsDTO cardsDTO);

    void createNewCard(String mobileNumber);

    CardsDTO getCardDetailsbyMobile(String mobileNumber);

    boolean updateCardDetails(CardsDTO cardsDTO);

    boolean deleteCardDetails(String mobileNumber);

    List<CardsDTO> fetchAllExistingCards();
}
