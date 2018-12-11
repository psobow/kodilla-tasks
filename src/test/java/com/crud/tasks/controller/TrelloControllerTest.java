package com.crud.tasks.controller;


import com.crud.tasks.client.TrelloClient;
import com.crud.tasks.dto.TrelloBoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloControllerTest {

    @Autowired
    TrelloClient trelloClient;

    @Test
    public void shouldPrintBoardsWithKodilla(){

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.stream()
                .filter(trelloBoardDto -> Objects.nonNull(trelloBoardDto.getId()))
                .filter(trelloBoardDto -> trelloBoardDto.getName() != null)
                .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                .forEach(System.out::println);

        // Operacje wykonane na streamie nie modyfikują obiektu.
        // trelloBoards.stream().forEach(System.out::println);
    }

}