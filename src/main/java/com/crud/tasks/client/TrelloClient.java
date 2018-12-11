package com.crud.tasks.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.dto.CreatedTrelloCard;
import com.crud.tasks.dto.TrelloBoardDto;
import com.crud.tasks.dto.TrelloCardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;


    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloLogin() + "/boards")
                .queryParam("key",     trelloConfig.getTrelloAppKey())
                .queryParam("token",   trelloConfig.getTrelloToken())
                .queryParam("fields", "name,id")
                .queryParam("lists",  "all")
                .build().encode().toUri();

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }


    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key",    trelloConfig.getTrelloAppKey())
                .queryParam("token",  trelloConfig.getTrelloToken())
                .queryParam("name",   trelloCardDto.getName())
                .queryParam("desc",   trelloCardDto.getDescription())
                .queryParam("pos",    trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }
}