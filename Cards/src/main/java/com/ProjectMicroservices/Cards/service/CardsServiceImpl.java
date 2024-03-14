package com.ProjectMicroservices.Cards.service;

import com.ProjectMicroservices.Cards.constants.CardConstants;
import com.ProjectMicroservices.Cards.dto.CardsDTO;
import com.ProjectMicroservices.Cards.entity.Cards;
import com.ProjectMicroservices.Cards.exception.CardsAlreadyExistsException;
import com.ProjectMicroservices.Cards.exception.ResourceNotFoundException;
import com.ProjectMicroservices.Cards.mapper.CardsMapper;
import com.ProjectMicroservices.Cards.repository.CardsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CardsServiceImpl implements CardsService{

    private Logger logger = LoggerFactory.getLogger(CardsServiceImpl.class);

    @Autowired
    private CardsRepository cardsRepository;
    @Override
    public void createCard(CardsDTO cardsDTO) {
        Cards cards = CardsMapper.INSTANCE.convertCardsDTOToEntity(cardsDTO);
        cardsRepository.save(cards);
    }

    @Override
    public void createNewCard(String mobileNumber) {
        Optional<Cards> byMobileNumber = cardsRepository.findByMobileNumber(mobileNumber);

        if(byMobileNumber.isPresent()){
            throw new CardsAlreadyExistsException("Card with this Mobile number already Exists : " + mobileNumber);
        }
        else cardsRepository.save(createCardwith(mobileNumber));
    }

    /**
     * @param mobileNumber
     */
    @Override
    public CardsDTO getCardDetailsbyMobile(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        CardsDTO cardsDTO = CardsMapper.INSTANCE.convertCardsEntityToDTO(cards);
        cardsDTO.setCardNumber(cards.getCardNumber());
        cardsDTO.setCardType(cards.getCardType());
        cardsDTO.setMobileNumber(cards.getMobileNumber());
        cardsDTO.setTotalLimit(cards.getTotalLimit());
        cardsDTO.setAvailableAmount(cards.getAvailableAmount());
        cardsDTO.setAmountUsed(cards.getAmountUsed());
        logger.info("Cards info is", cardsDTO.toString());
        return cardsDTO;
    }

    /**
     * @param cardsDTO
     * @return
     */
    @Override
    public boolean updateCardDetails(CardsDTO cardsDTO) {
        Cards cards = cardsRepository.findByCardNumber(cardsDTO.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "Card Number", cardsDTO.getCardNumber())
        );
        cardsRepository.save(cards);
        return true;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteCardDetails(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

    /**
     * @return
     */
    @Override
    public List<CardsDTO> fetchAllExistingCards() {
        List<Cards> all = cardsRepository.findAll();
        List<CardsDTO> allCardsDTO = all.stream().map(CardsDTO::new).collect(Collectors.toList());
        return allCardsDTO;
    }

    private Cards createCardwith(String mobileNumber) {
        Cards cards = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        cards.setMobileNumber(mobileNumber);
        cards.setCardNumber(String.valueOf(randomCardNumber));
        cards.setCardType(CardConstants.CREDIT_CARD);
        cards.setAmountUsed(0);
        cards.setTotalLimit(CardConstants.NEW_CARD_LIMIT);
        cards.setAvailableAmount(CardConstants.NEW_CARD_LIMIT);
        return cards;
    }
}
