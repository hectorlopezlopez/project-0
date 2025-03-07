package com.DEPI.Service;

import com.DEPI.DAO.UserDAO;
import com.DEPI.DTO.RequestUserDTO;
import com.DEPI.Model.User;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class UserServiceTest extends TestCase {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

//    TEST TO GET A USER------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testGetUserById(){
        User userMock = new User(1,"Hector","Lopesillo","7717412330","San","hll@mail.com","1234",1);
        when(userDAO.getUserById(1)).thenReturn(userMock);
        User userResult = userService.getUserById(1);
        assertNotNull(userResult);
        assertEquals(1,userResult.getId());
        assertEquals("Hector",userResult.getName());
        assertEquals("Lopesillo",userResult.getLastName());
        assertEquals("7717412330",userResult.getPhone());
        assertEquals("San",userResult.getAddress());
        assertEquals("hll@mail.com",userResult.getMail());
        assertEquals("1234",userResult.getPassword());
        assertEquals(1,userResult.getRol());
        verify(userDAO,times(1)).getUserById(1);


    }

    //    TEST TO GET A NOT EXISTING USER------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testGetUserDontExistById() {
        when(userDAO.getUserById(99)).thenReturn(null);
        User userResult = userService.getUserById(99);
        assertNull(userResult);
        verify(userDAO, times(1)).getUserById(99);
    }

    //    TEST TO UPDATE AN USER------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testUpdateUser(){
        User userMock = new User(1,"Hector","Lopesillo","7717412330","San","hll@mail.com","1234",1);
        when(userDAO.updateUser(userMock)).thenReturn(true);
        boolean userResult = userService.updateUser(userMock);
        assertNotNull(userResult);
        assertTrue(userResult);
        verify(userDAO,times(1)).updateUser(userMock);

    }















    //    TEST TO UPDATE MY USER------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void testUpdateMyUser(){
        User userMock = new User(1,"Hector","Lopesillo","7717412330","San","hll@mail.com","12346",1);
        RequestUserDTO userMockRequest = new RequestUserDTO(1,"Hector","Lopezillo","7717412330","San","hll@mail.com","12346",1);
        when(userDAO.updateMyUser(userMock)).thenReturn(true);
        String userResult = userService.updateMyUser(1,userMockRequest);
        assertNotNull(userResult);
        assertEquals("User updated",userResult);
        verify(userDAO,times(1)).updateMyUser(userMock);
    }



}