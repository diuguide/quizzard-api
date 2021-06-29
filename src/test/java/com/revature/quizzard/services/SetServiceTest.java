package com.revature.quizzard.services;

import com.revature.quizzard.dtos.*;
import com.revature.quizzard.exceptions.ResourceNotFoundException;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.user.RoleEntity;
import com.revature.quizzard.models.user.UserEntity;
import com.revature.quizzard.repositories.*;
import com.revature.quizzard.security.JWTokenUtil;
import org.junit.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@ActiveProfiles("test")
public class SetServiceTest {

    private SetService sut;
    private SetRepository mockSetRepo;
    private AccountRepository mockAccountRepo;
    private SetEntity mockSetEntity;
    private List<SetEntity> mockSetList;
    private AccountEntity mockAccount;
    private Set<CardEntity> mockCards;
    private JWTokenUtil mockTokenUtil;
    private CardRepository mockCardRepo;

    @Before
    public void setupTest() {
        mockSetRepo = mock(SetRepository.class);
        mockAccountRepo = mock(AccountRepository.class);
        mockTokenUtil = mock(JWTokenUtil.class);
        sut = new SetService(mockSetRepo, mockAccountRepo, mockCardRepo, mockTokenUtil);
    }

    @After
    public void teardownTest() {
        mockSetRepo = null;
        mockAccountRepo = null;
        sut = null;
        mockSetEntity = null;
        mockAccount = null;
        mockSetList = null;
        mockCards = null;
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndSet() {

        mockAccount = new AccountEntity();
        mockSetList = new ArrayList<>();
        mockCards = new HashSet<>();
        mockSetEntity = new SetEntity(1, mockCards, mockAccount, "test", true);
        mockSetList.add(mockSetEntity);
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(1, result.size());
    }

    @Test
    public void test_getCreatedSetsWithValidUsernameAndNoSet() {

        mockAccount = new AccountEntity();
        mockSetList = new ArrayList<>();
        when(mockAccountRepo.findByUsername(any())).thenReturn(mockAccount);
        when(mockSetRepo.findAllCreatedByAccount(any())).thenReturn(mockSetList);

        List<SetDTO> result = sut.getCreatedSets("test");

        assertEquals(0, result.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void test_getCreatedSetsWithInvalidUsername() {

        when(mockAccountRepo.findByUsername(any())).thenReturn(null);

        List<SetDTO> result = sut.getCreatedSets("test");

    }

    @Test
    public void test_getPublicSetsResultIsNotEmpty(){

        //Arrange
        UserEntity mockUser = new UserEntity(1,"fN","lN","fn.ln@email.com");

        RoleEntity mockRole = new RoleEntity(1,"ADMIN");

        AccountEntity mockAccount = new AccountEntity();
        mockAccount.setId(1);
        mockAccount.setUser(mockUser);

        CardEntity mockCard = new CardEntity();
        mockCard.setId(1);
        mockCard.setCreator(mockAccount);

        SetEntity mockSet = new SetEntity();
        mockSet.setId(1);
        mockSet.setName("Public Set 1");
        mockSet.setIsPublic(true);
        mockSet.setCreator(mockAccount);
        Set<CardEntity> mockSetOfCards = new HashSet<CardEntity>();
        mockSetOfCards.add(mockCard);
        mockSet.setCards(mockSetOfCards);

        List<SetEntity> mockPublicSets = new ArrayList<SetEntity>();
        mockPublicSets.add(mockSet);
        when(mockSetRepo.findAllByIsPublic(anyBoolean())).thenReturn(mockPublicSets);

        List<SetEntity> expectedResult = new ArrayList<>();
        expectedResult.add(mockSet);

        //Act
        List<SetEntity> actualResult= sut.getPublicSets();

        //Assert
        Assert.assertEquals(expectedResult.stream().findFirst().get().getId(), actualResult.stream().findFirst().get().getId());


    }

    @Test
    public void test_getSetById(){
        //Arrange
        UserEntity mockUser = new UserEntity(1,"fN","lN","fn.ln@email.com");

        RoleEntity mockRole = new RoleEntity(1,"ADMIN");

        AccountEntity mockAccount = new AccountEntity();
        mockAccount.setId(1);
        mockAccount.setUser(mockUser);

        CardEntity mockCard = new CardEntity();
        mockCard.setId(1);
        mockCard.setCreator(mockAccount);

        SetEntity mockSet = new SetEntity();
        mockSet.setId(1);
        mockSet.setName("Public Set 1");
        mockSet.setIsPublic(true);
        mockSet.setCreator(mockAccount);
        Set<CardEntity> mockSetOfCards = new HashSet<CardEntity>();
        mockSetOfCards.add(mockCard);
        mockSet.setCards(mockSetOfCards);

        when(mockSetRepo.findById(anyInt())).thenReturn(java.util.Optional.of(mockSet));

        SetEntity expectedResult = new SetEntity();
        expectedResult.setId(mockSet.getId());
        //Act

        Optional<SetEntity> actualResult = sut.getSetById(1);

        //Assert
        Assert.assertEquals(expectedResult.getId(), actualResult.get().getId());

    }

//    @Test
//    public void test_createStudySet(){
//        SetDTO setDTO = new SetDTO();
//        setDTO.setSetName("mockName");
////        setDTO.setCreator(mockAccount);
//        setDTO.setPublic(true);
//        setDTO.setSetId(2);
//
//        //Expected
//        doReturn(new SetEntity()).when(mockSetRepo).save(any());
//        //Act
//        SetDTO result = sut.createStudySets(setDTO);
//
//        //Assert
//        verify(mockSetRepo, times(1)).save(any());
//
//        assertEquals(setDTO, result);
//
//    }
}
