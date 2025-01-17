package com.rc.porms.data.prefect.offense.dao.impl;

import com.rc.porms.appl.model.offense.Offense;
import com.rc.porms.data.prefect.offense.OffenseDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OffenseDaoImplTest {
    private OffenseDao offenseDao;
    private List<Offense> offenses;

    @BeforeEach
    public void setUp() {
        offenses = new ArrayList<>();
        Offense offense1 = new Offense();
        Offense offense2 = new Offense();
        offenses.add(offense1);
        offenses.add(offense2);

        offenseDao = mock(OffenseDao.class);
    }

    @Test
    public void testGetAllOffense() {
        Offense offense1 = new Offense();
        offense1.setDescription("major");
        when(offenseDao.getAllOffense()).thenReturn(Arrays.asList(offense1));

        List<Offense> offenses = offenseDao.getAllOffense();

        assertNotNull(offenses, "The offense list should not be null.");
        assertEquals(1, offenses.size(), "There should be 1 offense.");
        assertEquals("major", offenses.get(0).getDescription(), "The first offense should have description of major.");

        verify(offenseDao, times(1)).getAllOffense();
    }

    @Test
    public void testGetAllOffenseByType() {
        Offense offense1 = new Offense();
        offense1.setDescription("minor");
        when(offenseDao.getAllOffenseByType("minor")).thenReturn(Arrays.asList(offense1));

        List<Offense> offenses = offenseDao.getAllOffenseByType("minor");

        assertNotNull(offenses, "The offense list should not be null.");
        assertEquals(1, offenses.size(), "There should be 1 offense.");
        assertEquals("minor", offenses.get(0).getDescription(), "The first offense should have description of major.");

        verify(offenseDao, times(1)).getAllOffenseByType("minor");
    }

    @Test
    public void testGetOffenseById() {
        Offense offense1 = new Offense();
        offense1.setId(1);

        when(offenseDao.getOffenseByID(1)).thenReturn(offense1);

        Offense expectedOffense = offenseDao.getOffenseByID(1);

        assertEquals(expectedOffense, offense1);
        assertEquals(expectedOffense.getId(), offense1.getId());
    }

    @Test
    public void testGetOffenseByName() {
        Offense offense1 = new Offense();
        offense1.setId(1);
        offense1.setDescription("minor");
        when(offenseDao.getOffenseByName("minor")).thenReturn(offense1);

        Offense offenses = offenseDao.getOffenseByName("minor");

        assertNotNull(offenses, "The offense should not be null.");
        assertEquals("minor", offenses.getDescription(), "The offense description should be 'minor'.");
        assertEquals(1, offenses.getId(), "The offense ID should be 1.");

        verify(offenseDao, times(1)).getOffenseByName("minor");
    }

    @Test
    public void testAddOffense() {
        Offense offense = new Offense();
        offense.setId(1);

        when(offenseDao.addOffense(offense)).thenReturn(true);
        when(offenseDao.getOffenseByID(1)).thenReturn(offense);

        Offense expectedOffense = offenseDao.getOffenseByID(1);
        assertEquals(expectedOffense.getId(), offense.getId());
    }

    @Test
    public void testUpdateOffense() {
        Offense offense = new Offense();
        offense.setId(1);

        when(offenseDao.addOffense(offense)).thenReturn(true);
        when(offenseDao.updateOffense(offense)).thenReturn(true);
        when(offenseDao.getOffenseByID(1)).thenReturn(offense);

        Offense expectedOffense = offenseDao.getOffenseByID(1);
        assertEquals(expectedOffense.getId(), offense.getId());
    }
}
