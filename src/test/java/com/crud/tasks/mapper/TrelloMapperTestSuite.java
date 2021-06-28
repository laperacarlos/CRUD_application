package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrelloMapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoardsAndMapToLists() {
        //given
        List<TrelloListDto> trelloListsDto = new ArrayList<>();
        trelloListsDto.add(new TrelloListDto("1", "listDtoName", true));
        List<TrelloBoardDto> boardsDto = new ArrayList<>();
        boardsDto.add(new TrelloBoardDto("1", "boardName", trelloListsDto));

        //when
        List<TrelloBoard> boards = trelloMapper.mapToBoards(boardsDto);

        //then
        assertEquals(1, boards.size());
        assertEquals("1", boards.get(0).getId());
        assertEquals("boardName", boards.get(0).getName());
        assertEquals(1, boards.get(0).getLists().size());
        assertEquals("1", boards.get(0).getLists().get(0).getId());
        assertEquals("listDtoName", boards.get(0).getLists().get(0).getName());
        assertTrue(boards.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void testMapToBoardsDtoAndMapToListsDto() {
        //given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "listName", true));
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(new TrelloBoard("1", "boardName", trelloLists));

        //when
        List<TrelloBoardDto> boardsDto = trelloMapper.mapToBoardsDto(boards);

        //then
        assertEquals(1, boardsDto.size());
        assertEquals("1", boardsDto.get(0).getId());
        assertEquals("boardName", boardsDto.get(0).getName());
        assertEquals(1, boardsDto.get(0).getLists().size());
        assertEquals("1", boardsDto.get(0).getLists().get(0).getId());
        assertEquals("listName", boardsDto.get(0).getLists().get(0).getName());
        assertTrue(boardsDto.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "position", "listId");

        //when
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //then
        assertEquals("name", trelloCard.getName());
        assertEquals("description", trelloCard.getDescription());
        assertEquals("position", trelloCard.getPos());
        assertEquals("listId", trelloCard.getListId());
    }

    @Test
    public void testMapToCardDto() {
        //given
        TrelloCard trelloCard = new TrelloCard("name", "description", "position", "listId");

        //when
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //then
        assertEquals("name", trelloCardDto.getName());
        assertEquals("description", trelloCardDto.getDescription());
        assertEquals("position", trelloCardDto.getPos());
        assertEquals("listId", trelloCardDto.getListId());
    }
}
