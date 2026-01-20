package com.example.credit.test;

import com.example.credit.model.DossierCredit;
import com.example.credit.repository.CompteRepository;
import com.example.credit.repository.DossierCreditRepository;
import com.example.credit.service.DossierCreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DossierCreditServiceTest {

    private DossierCreditRepository dossierCreditRepository;
    private DossierCreditService dossierCreditService;

    @BeforeEach
    public void setUp() {
        
        dossierCreditRepository = mock(DossierCreditRepository.class);
        CompteRepository compteRepository= mock(CompteRepository.class);
        dossierCreditService = new DossierCreditService(dossierCreditRepository,compteRepository);
    }

    @Test
    public void testGetAllDossiers_ReturnsList() {
        DossierCredit d1 = new DossierCredit();
        DossierCredit d2 = new DossierCredit();
        when(dossierCreditRepository.findAll()).thenReturn(Arrays.asList(d1, d2));

        List<DossierCredit> result = dossierCreditService.getAllDossiers();

        assertEquals(2, result.size());
        verify(dossierCreditRepository).findAll();
    }

    @Test
    public void testGetDossierById_ExistingId_ReturnsOptional() {
        DossierCredit d = new DossierCredit();
        d.setId(1L);
        when(dossierCreditRepository.findById(1L)).thenReturn(Optional.of(d));

        Optional<DossierCredit> result = dossierCreditService.getDossierById(1L);

        assertTrue(result.isPresent());
        assertEquals(d, result.get());
        verify(dossierCreditRepository).findById(1L);
    }

    @Test
    public void testGetDossierById_NonExistingId_ReturnsEmpty() {
        when(dossierCreditRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<DossierCredit> result = dossierCreditService.getDossierById(99L);

        assertFalse(result.isPresent());
        verify(dossierCreditRepository).findById(99L);
    }

    @Test
    public void testAddDossier_SavesDossier() {
        DossierCredit d = new DossierCredit();
        dossierCreditService.addDossier(d);
        verify(dossierCreditRepository).save(d);
    }

    @Test
    public void testUpdateDossier_ExistingId_SavesDossier() {
        DossierCredit d = new DossierCredit();
        d.setId(1L);
        when(dossierCreditRepository.existsById(1L)).thenReturn(true);

        dossierCreditService.updateDossier(d);

        verify(dossierCreditRepository).existsById(1L);
        verify(dossierCreditRepository).save(d);
    }

    @Test
    public void testUpdateDossier_NonExistingId_Throws() {
        DossierCredit d = new DossierCredit();
        d.setId(2L);
        when(dossierCreditRepository.existsById(2L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            dossierCreditService.updateDossier(d);
        });

        assertEquals("DossierCredit non trouvé", exception.getMessage());
        verify(dossierCreditRepository).existsById(2L);
        verify(dossierCreditRepository, never()).save(any());
    }

    @Test
    public void testDeleteDossierById_ExistingId_DeletesDossier() {
        when(dossierCreditRepository.existsById(1L)).thenReturn(true);

        dossierCreditService.deleteDossierById(1L);

        verify(dossierCreditRepository).existsById(1L);
        verify(dossierCreditRepository).deleteById(1L);
    }

    @Test
    public void testDeleteDossierById_NonExistingId_Throws() {
        when(dossierCreditRepository.existsById(5L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            dossierCreditService.deleteDossierById(5L);
        });

        assertEquals("DossierCredit non trouvé", exception.getMessage());
        verify(dossierCreditRepository).existsById(5L);
        verify(dossierCreditRepository, never()).deleteById(anyLong());
    }
}
