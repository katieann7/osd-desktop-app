package com.rc.porms.appl.facade.prefect.offense.impl;

import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.data.prefect.offense.OffenseDao;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OffenseFacadeImplTest {

    private OffenseDao mockOffenseDao;
    private OffenseFacadeImpl offenseFacade;

    @Before
    public void setUp() {
        mockOffenseDao = mock(OffenseDao.class);
        offenseFacade = new OffenseFacadeImpl(mockOffenseDao);
    }

    @Test
    public void testGetOffenseByID() {
        Offense mockOffense = new Offense();
        mockOffense.setId(1);
        when(mockOffenseDao.getOffenseByID(1)).thenReturn(mockOffense);

        Offense actualOffense = offenseFacade.getOffenseByID(1);

        assertNotNull(actualOffense);
        assertEquals(1, actualOffense.getId());

        when(mockOffenseDao.getOffenseByID(999)).thenReturn(null);

        Offense invalidOffense = offenseFacade.getOffenseByID(999);

        assertNull(invalidOffense);
    }

    @Test
    public void testAddOffense() {
        Offense offenseToAdd = new Offense();
        when(mockOffenseDao.addOffense(offenseToAdd)).thenReturn(true);

        boolean result = offenseFacade.addOffense(offenseToAdd);

        assertTrue(result);
        verify(mockOffenseDao, times(1)).addOffense(offenseToAdd);
    }

    @Test
    public void testUpdateOffense() {

        Offense offenseToUpdate = new Offense();
        offenseToUpdate.setId(1);
        when(mockOffenseDao.getOffenseByID(1)).thenReturn(offenseToUpdate);
        when(mockOffenseDao.updateOffense(offenseToUpdate)).thenReturn(true);

        boolean result = offenseFacade.updateOffense(offenseToUpdate);

        assertTrue(result);
        verify(mockOffenseDao, times(1)).updateOffense(offenseToUpdate);

        Offense nonExistentOffense = new Offense();
        nonExistentOffense.setId(2);
        when(mockOffenseDao.getOffenseByID(2)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            offenseFacade.updateOffense(nonExistentOffense);
        });

        assertEquals("Offense to update not found. ", exception.getMessage());
    }


    @Test
    public void testGetAllOffense() {
        Offense offense1 = new Offense();
        offense1.setId(1);
        Offense offense2 = new Offense();
        offense2.setId(2);
        List<Offense> mockOffenses = Arrays.asList(offense1, offense2);
        when(mockOffenseDao.getAllOffense()).thenReturn(mockOffenses);

        List<Offense> offenses = offenseFacade.getAllOffense();

        assertNotNull(offenses);
        assertEquals(2, offenses.size());
        verify(mockOffenseDao, times(1)).getAllOffense();
    }

    @Test
    public void testGetAllOffenseByType() {

        String type = "Minor";
        Offense offense1 = new Offense();
        offense1.setType(type);
        Offense offense2 = new Offense();
        offense2.setType(type);
        List<Offense> mockOffensesByType = Arrays.asList(offense1, offense2);
        when(mockOffenseDao.getAllOffenseByType(type)).thenReturn(mockOffensesByType);

        List<Offense> offenses = offenseFacade.getAllOffenseByType(type);

        assertNotNull(offenses);
        assertEquals(2, offenses.size());
        verify(mockOffenseDao, times(1)).getAllOffenseByType(type);

        when(mockOffenseDao.getAllOffenseByType("NonExistentType")).thenThrow(new RuntimeException("Failed to retrieve offenses by type"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            offenseFacade.getAllOffenseByType("NonExistentType");
        });
        assertEquals("Failed to retrieve all Offense by type: Failed to retrieve offenses by type", exception.getMessage());
    }
}
