package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import  static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloFacadeTest {

    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        //given
        List<TrelloListDto> trelloListsDto =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloListsDto));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenCallRealMethod();

        //when
        List<TrelloBoardDto> trelloBoardsDto = trelloFacade.fetchTrelloBoards();

        //then
        assertNotNull(trelloBoardsDto);
        assertEquals(0, trelloBoardsDto.size());
    }

    @Test
    void shouldFetchTrelloBoards() {
        //given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "newList", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "newList", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenCallRealMethod();
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);

        //when
        List<TrelloBoardDto> trelloBoardsDto = trelloFacade.fetchTrelloBoards();

        //then
        assertNotNull(trelloBoardsDto);
        assertEquals(1, trelloBoardsDto.size());

        trelloBoardsDto.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("newList", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("test_list", trelloListDto.getName());
                assertFalse(trelloListDto.isClosed());
            });
        });
    }

    @Test
    void testCreateCard() {
        //given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "description", "pos", "listId");
        TrelloCard trelloCard = new TrelloCard("test", "description", "pos", "listId");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("id" ,"test", "shortUrl");

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdCard);
        doCallRealMethod().when(trelloValidator).validateCard(trelloCard);

        //when
        CreatedTrelloCardDto createdTrelloCard = trelloFacade.createCard(trelloCardDto);

        //then
        assertEquals("test", createdTrelloCard.getName());
        assertEquals("id", createdTrelloCard.getId());
        assertEquals("shortUrl", createdTrelloCard.getShortUrl());
    }







}
