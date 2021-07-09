package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService simpleEmailService;


    @Test
    void testFetchTrelloBoards() {
        //given
        List<TrelloListDto> trelloListsDto =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloListsDto));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);

        //when
        List<TrelloBoardDto> fetchedBoards = trelloService.fetchTrelloBoards();

        //then
        assertNotNull(fetchedBoards);
        assertEquals(1, fetchedBoards.size());
    }

    @Test
    void createCard() {
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "description", "pos", "listId");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("id" ,"test", "shortUrl");

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        doNothing().when(simpleEmailService).send(any(Mail.class));

        //when
        CreatedTrelloCardDto createdTrelloCard = trelloService.createTrelloCard(trelloCardDto);

        //then
        assertEquals("id", createdTrelloCard.getId());
    }
}
