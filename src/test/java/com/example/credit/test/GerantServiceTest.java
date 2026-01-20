package com.example.credit.test;

import com.example.credit.model.Gerant;
import com.example.credit.repository.GerantRepository;
import com.example.credit.service.GerantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GerantServiceTest {

    private GerantRepository gerantRepository;
    private GerantService gerantService;

    @BeforeEach
    public void setUp() {
        gerantRepository = mock(GerantRepository.class);
        gerantService = new GerantService(gerantRepository);
    }

    @Test
    public void testGetAllGerants_ReturnsList() {
        Gerant g1 = new Gerant();
        Gerant g2 = new Gerant();
        when(gerantRepository.findAll()).thenReturn(Arrays.asList(g1, g2));

        List<Gerant> result = gerantService.getAllGerants();

        assertEquals(2, result.size());
        verify(gerantRepository).findAll();
    }

    @Test
    public void testGetGerantById_ExistingId_ReturnsOptional() {
        Gerant g = new Gerant();
        g.setId(1L);
        when(gerantRepository.findById(1L)).thenReturn(Optional.of(g));

        Optional<Gerant> result = gerantService.getGerantById(1L);

        assertTrue(result.isPresent());
        assertEquals(g, result.get());
        verify(gerantRepository).findById(1L);
    }

    @Test
    public void testGetGerantById_NonExistingId_ReturnsEmpty() {
        when(gerantRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Gerant> result = gerantService.getGerantById(99L);

        assertFalse(result.isPresent());
        verify(gerantRepository).findById(99L);
    }

    @Test
    public void testAddGerant_SavesGerant() {
        Gerant g = new Gerant();
        gerantService.addGerant(g);
        verify(gerantRepository).save(g);
    }

    @Test
    public void testUpdateGerant_ExistingId_SavesGerant() {
        Gerant g = new Gerant();
        g.setId(1L);
        when(gerantRepository.existsById(1L)).thenReturn(true);

        gerantService.updateGerant(g);

        verify(gerantRepository).existsById(1L);
        verify(gerantRepository).save(g);
    }

    @Test
    public void testUpdateGerant_NonExistingId_Throws() {
        Gerant g = new Gerant();
        g.setId(2L);
        when(gerantRepository.existsById(2L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gerantService.updateGerant(g);
        });

        assertEquals("Gerant non trouvé", exception.getMessage());
        verify(gerantRepository).existsById(2L);
        verify(gerantRepository, never()).save(any());
    }

    @Test
    public void testDeleteGerantById_ExistingId_DeletesGerant() {
        when(gerantRepository.existsById(1L)).thenReturn(true);

        gerantService.deleteGerantById(1L);

        verify(gerantRepository).existsById(1L);
        verify(gerantRepository).deleteById(1L);
    }

    @Test
    public void testDeleteGerantById_NonExistingId_Throws() {
        when(gerantRepository.existsById(5L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gerantService.deleteGerantById(5L);
        });

        assertEquals("Gerant non trouvé", exception.getMessage());
        verify(gerantRepository).existsById(5L);
        verify(gerantRepository, never()).deleteById(anyLong());
    }
}
