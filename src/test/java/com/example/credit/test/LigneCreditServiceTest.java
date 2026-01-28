package com.example.credit.test;

import com.example.credit.model.LigneCredit;
import com.example.credit.repository.LigneCreditRepository;
import com.example.credit.service.LigneCreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LigneCreditServiceTest {

    private LigneCreditRepository ligneCreditRepository;
    private LigneCreditService ligneCreditService;

    @BeforeEach
    public void setUp() {
        ligneCreditRepository = mock(LigneCreditRepository.class);
        ligneCreditService = new LigneCreditService(ligneCreditRepository);
    }

    @Test
    public void testGetAllLignes_ReturnsList() {
        LigneCredit l1 = new LigneCredit();
        LigneCredit l2 = new LigneCredit();
        when(ligneCreditRepository.findAll()).thenReturn(Arrays.asList(l1, l2));

        List<LigneCredit> result = ligneCreditService.getAllLignes();

        assertEquals(2, result.size());
        verify(ligneCreditRepository).findAll();
    }

    @Test
    public void testGetLigneById_ExistingId_ReturnsOptional() {
        LigneCredit l = new LigneCredit();
        l.setId(1L);
        when(ligneCreditRepository.findById(1L)).thenReturn(Optional.of(l));

        Optional<LigneCredit> result = ligneCreditService.getLigneById(1L);

        assertTrue(result.isPresent());
        assertEquals(l, result.get());
        verify(ligneCreditRepository).findById(1L);
    }

    @Test
    public void testGetLigneById_NonExistingId_ReturnsEmpty() {
        when(ligneCreditRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<LigneCredit> result = ligneCreditService.getLigneById(99L);

        assertFalse(result.isPresent());
        verify(ligneCreditRepository).findById(99L);
    }

    @Test
    public void testAddLigne_SavesLigne() {
        LigneCredit l = new LigneCredit();
        ligneCreditService.addLigne(l);
        verify(ligneCreditRepository).save(l);
    }

    @Test
    public void testUpdateLigne_ExistingId_SavesLigne() {
        LigneCredit l = new LigneCredit();
        l.setId(1L);

        // IMPORTANT : mock findById pour éviter NoSuchElementException
        when(ligneCreditRepository.existsById(1L)).thenReturn(true);
        when(ligneCreditRepository.findById(1L)).thenReturn(Optional.of(l));
        when(ligneCreditRepository.save(l)).thenReturn(l);

        ligneCreditService.updateLigne(l);

        verify(ligneCreditRepository).existsById(1L);
        verify(ligneCreditRepository).findById(1L);
        verify(ligneCreditRepository).save(l);
    }

    @Test
    public void testUpdateLigne_NonExistingId_Throws() {
        LigneCredit l = new LigneCredit();
        l.setId(2L);
        when(ligneCreditRepository.existsById(2L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ligneCreditService.updateLigne(l);
        });

        assertEquals("LigneCredit non trouvée", exception.getMessage());
        verify(ligneCreditRepository).existsById(2L);
        verify(ligneCreditRepository, never()).findById(anyLong());
        verify(ligneCreditRepository, never()).save(any());
    }

    @Test
    public void testDeleteLigneById_ExistingId_DeletesLigne() {
        when(ligneCreditRepository.existsById(1L)).thenReturn(true);

        ligneCreditService.deleteLigneById(1L);

        verify(ligneCreditRepository).existsById(1L);
        verify(ligneCreditRepository).deleteById(1L);
    }

    @Test
    public void testDeleteLigneById_NonExistingId_Throws() {
        when(ligneCreditRepository.existsById(5L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ligneCreditService.deleteLigneById(5L);
        });

        assertEquals("LigneCredit non trouvée", exception.getMessage());
        verify(ligneCreditRepository).existsById(5L);
        verify(ligneCreditRepository, never()).deleteById(anyLong());
    }
}
