package com.rc.porms.appl.facade.prefect.offense.impl;

import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.data.prefect.offense.OffenseDao;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

        when(mockOffenseDao.getOffenseByID(1)).thenReturn(null);

        Offense invalidOffense = offenseFacade.getOffenseByID(1);

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

        when(mockOffenseDao.getOffenseByID(1)).thenReturn(null);
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
    }

    @Test
    public void testGetOffenseByName() {
        Offense offense1 = new Offense();
        offense1.setId(1);
        offense1.setDescription("major");

        List<Offense> mockOffensesByName = Arrays.asList(offense1);

        when(mockOffenseDao.getOffenseByName("major")).thenReturn(offense1);

        Offense offenses = offenseFacade.getOffenseByName("major");

        assertNotNull(offenses, "The offense list should not be null.");
        assertEquals("major", offenses.getDescription(), "The first offense should have ID 1.");
 
        verify(mockOffenseDao, times(1)).getOffenseByName("major");
    }

}
