package com.example.credit.test;

import com.example.credit.model.Actionneur;
import com.example.credit.model.ClientPersonneMorale;
import com.example.credit.repository.ActionneurRepository;
import com.example.credit.service.ActionneurService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActionneurServiceTest {

    private ActionneurRepository actionneurRepository;
    private ActionneurService actionneurService;

    @BeforeEach
    void setUp() {
        actionneurRepository = mock(ActionneurRepository.class);
        actionneurService = new ActionneurService(actionneurRepository);
    }
    @Test
    void testAddActionneur() {
        Actionneur a = new Actionneur("123", "Ali", "Ben", LocalDate.of(1990, 1, 1), "Tunisienne", 5000.0,new ClientPersonneMorale());

        actionneurService.addActionneur(a);

        verify(actionneurRepository).save(a);
    }

    @Test
    void testGetActionneurById() {
        Actionneur a = new Actionneur("123", "Ali", "Ben", LocalDate.of(1990, 1, 1), "Tunisienne", 5000.0,new ClientPersonneMorale());
        when(actionneurRepository.findById("123")).thenReturn(Optional.of(a));

        Optional<Actionneur> result = actionneurService.getActionneurById("123");

        assertTrue(result.isPresent());
        assertEquals("Ali", result.get().getNom());
    }

    @Test
    void testGetAllActionneurs() {
        when(actionneurRepository.findAll()).thenReturn(Arrays.asList(
                new Actionneur("1", "Nom1", "Prenom1", LocalDate.now(), "TN", 1000.0,new ClientPersonneMorale()),
                new Actionneur("2", "Nom2", "Prenom2", LocalDate.now(), "TN", 2000.0,new ClientPersonneMorale())
        ));

        var result = actionneurService.getAllActionneurs();

        assertEquals(2, result.size());
    }


    @Test
    void testDeleteActionneurById() {
        actionneurService.deleteActionneurById("123");

        verify(actionneurRepository).deleteById("123");
    }

    @Test
    void testUpdateActionneur_successful() {
        Actionneur existing = new Actionneur("123", "Old", "Name", LocalDate.of(1990, 1, 1), "FR", 1000.0,new ClientPersonneMorale());
        Actionneur updated = new Actionneur("123", "New", "Name", LocalDate.of(1990, 1, 1), "FR", 1000.0,new ClientPersonneMorale());

        when(actionneurRepository.findById("123")).thenReturn(Optional.of(existing));

        actionneurService.updateActionneur(updated);

        ArgumentCaptor<Actionneur> captor = ArgumentCaptor.forClass(Actionneur.class);
        verify(actionneurRepository).save(captor.capture());

        Actionneur saved = captor.getValue();
        assertEquals("New", saved.getNom());
    }

    @Test
    void testUpdateActionneur_notFound() {
        Actionneur a = new Actionneur("999", "Test", "User", LocalDate.now(), "TN", 2000.0,new ClientPersonneMorale());

        when(actionneurRepository.findById("999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalStateException.class, () ->
                actionneurService.updateActionneur(a)
        );

        assertEquals("actionneur non existant", exception.getMessage());
    }
}
