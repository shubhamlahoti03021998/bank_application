package com.ProjectMicroservices.Cards.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDTO(String message, Map<String, String> contactDetails, List<String> onCallSupport) {

}
