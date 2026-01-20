package com.example.credit.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.credit.model.Compte;
import com.example.credit.repository.CompteRepository;
import com.example.credit.service.CompteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CompteServiceTest {

    private CompteRepository compteRepository;
    private CompteService compteService;

    @BeforeEach
    public void setup() {
        compteRepository = Mockito.mock(CompteRepository.class);
        compteService = new CompteService(compteRepository);
    }

    @Test
    public void testGetAllComptes_ReturnsList() {
        List<Compte> comptes = Arrays.asList(new Compte(), new Compte());
        when(compteRepository.findAll()).thenReturn(comptes);

        List<Compte> result = compteService.getAllComptes();

        assertEquals(2, result.size());
        verify(compteRepository).findAll();
    }

    @Test
    public void testGetCompteById_Found() {
        Compte compte = new Compte();
        compte.setId(1L);
        when(compteRepository.findById(1L)).thenReturn(Optional.of(compte));

        Optional<Compte> result = compteService.getCompteById(1L);

        assertTrue(result.isPresent());
        assertEquals(compte, result.get());
        verify(compteRepository).findById(1L);
    }

    @Test
    public void testGetCompteById_NotFound() {
        when(compteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Compte> result = compteService.getCompteById(1L);

        assertFalse(result.isPresent());
        verify(compteRepository).findById(1L);
    }

    @Test
    public void testAddCompte_SavesCompte() {
        Compte compte = new Compte();
        compteService.addCompte(compte);

        verify(compteRepository).save(compte);
    }

    @Test
    public void testUpdateCompte_Success() {
        Compte compte = new Compte();
        compte.setId(5L);

        when(compteRepository.existsById(5L)).thenReturn(true);

        compteService.updateCompte(compte);

        verify(compteRepository).existsById(5L);
        verify(compteRepository).save(compte);
    }

    @Test
    public void testUpdateCompte_CompteNotFound_Throws() {
        Compte compte = new Compte();
        compte.setId(5L);

        when(compteRepository.existsById(5L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            compteService.updateCompte(compte);
        });

        assertEquals("Compte non trouvé", exception.getMessage());
        verify(compteRepository).existsById(5L);
        verify(compteRepository, never()).save(any());
    }

    @Test
    public void testUpdateCompte_InvalidId_Throws() {
        Compte compte = new Compte();
        compte.setId(0L);

        when(compteRepository.existsById(0L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            compteService.updateCompte(compte);
        });

        assertEquals("Compte non trouvé", exception.getMessage());
        verify(compteRepository).existsById(0L);  // expect this call
        verify(compteRepository, never()).save(any());
}

    @Test
    public void testDeleteCompteById_Success() {
        when(compteRepository.existsById(10L)).thenReturn(true);

        compteService.deleteCompteById(10L);

        verify(compteRepository).existsById(10L);
        verify(compteRepository).deleteById(10L);
    }

    @Test
    public void testDeleteCompteById_CompteNotFound_Throws() {
        when(compteRepository.existsById(10L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            compteService.deleteCompteById(10L);
        });

        assertEquals("Compte non trouvé", exception.getMessage());
        verify(compteRepository).existsById(10L);
        verify(compteRepository, never()).deleteById(any());
    }
}
