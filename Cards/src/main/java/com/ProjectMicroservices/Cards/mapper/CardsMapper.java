package com.ProjectMicroservices.Cards.mapper;

import com.ProjectMicroservices.Cards.dto.CardsDTO;
import com.ProjectMicroservices.Cards.entity.Cards;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CardsMapper {

    CardsMapper INSTANCE = Mappers.getMapper(CardsMapper.class);

    Cards convertCardsDTOToEntity(CardsDTO cardsDTO);

    CardsDTO convertCardsEntityToDTO(Cards cards);
}
