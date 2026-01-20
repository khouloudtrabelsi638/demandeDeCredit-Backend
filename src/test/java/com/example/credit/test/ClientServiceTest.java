package com.example.credit.test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.credit.model.*;
import com.example.credit.repository.ClientRepository;
import com.example.credit.service.ClientService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ClientServiceTest {

    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    public void setup() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void testGetClients_ReturnsAll() {
        List<Client> mockClients = new ArrayList<>();
        mockClients.add(new ClientPersonnePhysique());
        mockClients.add(new ClientPersonneMorale());

        when(clientRepository.findAll()).thenReturn(mockClients);

        List<Client> clients = clientService.getClients();

        assertEquals(2, clients.size());
        verify(clientRepository).findAll();
    }

    @Test
    public void testRegisterClient_SetsRelationsAndSaves_ClientPersonneMorale() {
        ClientPersonneMorale client = new ClientPersonneMorale();
        client.setComptes(new ArrayList<>());
        client.setGerants(new ArrayList<>());
        client.setActionneurs(new ArrayList<>());

        // Add one compte with documents
        Compte compte = new Compte();
        List<Document> documents = new ArrayList<>();
        Document doc = new Document();
        documents.add(doc);
        compte.setDocuments(documents);
        client.getComptes().add(compte);

        // Gerants and Actionneurs
        Gerant gerant = new Gerant();
        client.getGerants().add(gerant);
        Actionneur actionneur = new Actionneur();
        client.getActionneurs().add(actionneur);

        clientService.registerClient(client);

        // Verify the bidirectional relations
        assertEquals(client, compte.getClient());
        assertEquals(compte, doc.getCompte());
        assertEquals(client, gerant.getClient());
        assertEquals(client, actionneur.getClient());

        verify(clientRepository).save(client);
    }

    @Test
    public void testRegisterClient_SetsRelationsAndSaves_ClientPersonnePhysique() {
        ClientPersonnePhysique client = new ClientPersonnePhysique();
        client.setComptes(new ArrayList<>());

        Compte compte = new Compte();
        List<Document> documents = new ArrayList<>();
        Document doc = new Document();
        documents.add(doc);
        compte.setDocuments(documents);
        client.getComptes().add(compte);


        clientService.registerClient(client);

        assertEquals(client, compte.getClient());
        assertEquals(compte, doc.getCompte());

        verify(clientRepository).save(client);
    }

    @Test
    public void testDeleteClient_CallsRepositoryDelete() {
        Long id = 5L;
        clientService.deleteClient(id);
        verify(clientRepository).deleteById(id);
    }

    @Test
    public void testUpdateClient_ClientPersonneMorale_Success() {
        Long id = 1L;

        ClientPersonneMorale existingClient = new ClientPersonneMorale();
        existingClient.setIdRelation(id);
        existingClient.setPay("OldPay");
        existingClient.setGouvernorat("OldGov");
        existingClient.setAdresse("OldAddress");
        existingClient.setInfoComplementaires("OldInfo");
        existingClient.setProfession("OldProfession");
        existingClient.setComptes(new ArrayList<>());
        existingClient.setGerants(new ArrayList<>());
        existingClient.setActionneurs(new ArrayList<>());

        DossierCredit existingDc = new DossierCredit();
        existingDc.setDateCreation(LocalDate.now());

        when(clientRepository.findById(id)).thenReturn(Optional.of(existingClient));

        ClientPersonneMorale updatedClient = new ClientPersonneMorale();
        updatedClient.setIdRelation(id);
        updatedClient.setPay("NewPay");
        updatedClient.setGouvernorat("NewGov");
        updatedClient.setAdresse("NewAddress");
        updatedClient.setInfoComplementaires("NewInfo");
        updatedClient.setProfession("NewProfession");

        List<Compte> updatedComptes = new ArrayList<>();
        updatedClient.setComptes(updatedComptes);

        DossierCredit updatedDc = new DossierCredit();
        updatedDc.setDateCreation(LocalDate.now());
        updatedDc.setAgence("NewAgence");

        updatedClient.setAcronyme("NewAcronyme");
        updatedClient.setFormeJuridique("NewForme");
        updatedClient.setGroupe("NewGroupe");
        updatedClient.setCapitalSocial(10000.0);
        updatedClient.setMatriculeFiscale("NewMatricule");
        updatedClient.setNbrEmployes(50);
        updatedClient.setSecteurActivite("NewSecteur");
        updatedClient.setDateVisite(LocalDate.now());
        updatedClient.setVisitePar("NewVisitePar");
        updatedClient.setPersonnesContactees("NewPersonnes");


        Gerant g = new Gerant();
        List<Gerant> gerants = new ArrayList<>();
        gerants.add(g);
        updatedClient.setGerants(gerants);

        Actionneur a = new Actionneur();
        List<Actionneur> actionneurs = new ArrayList<>();
        actionneurs.add(a);
        updatedClient.setActionneurs(actionneurs);

        clientService.updateClient(updatedClient);

        verify(clientRepository).findById(id);
        verify(clientRepository).save(existingClient);

        assertEquals("NewPay", existingClient.getPay());
        assertEquals("NewAcronyme", existingClient.getAcronyme());
        assertEquals(1, existingClient.getGerants().size());
        assertEquals(1, existingClient.getActionneurs().size());
        assertEquals(existingClient, existingClient.getGerants().get(0).getClient());
        assertEquals(existingClient, existingClient.getActionneurs().get(0).getClient());
    }

    @Test
    public void testUpdateClient_ClientPersonnePhysique_Success() {
        Long id = 2L;

        ClientPersonnePhysique existingClient = new ClientPersonnePhysique();
        existingClient.setIdRelation(id);
        existingClient.setPay("OldPay");
        existingClient.setGouvernorat("OldGov");
        existingClient.setAdresse("OldAddress");
        existingClient.setInfoComplementaires("OldInfo");
        existingClient.setProfession("OldProfession");
        existingClient.setComptes(new ArrayList<>());

        when(clientRepository.findById(id)).thenReturn(Optional.of(existingClient));

        ClientPersonnePhysique updatedClient = new ClientPersonnePhysique();
        updatedClient.setIdRelation(id);
        updatedClient.setPay("NewPay");
        updatedClient.setGouvernorat("NewGov");
        updatedClient.setAdresse("NewAddress");
        updatedClient.setInfoComplementaires("NewInfo");
        updatedClient.setProfession("NewProfession");

        updatedClient.setNom("NewNom");
        updatedClient.setPrenom("NewPrenom");
        updatedClient.setSexe("M");
        updatedClient.setSituationFamiliale("Single");
        updatedClient.setDateDeNaissance(LocalDate.now());
        updatedClient.setTypeContrat("CDI");
        updatedClient.setTypeEmployeur("Private");

        List<Compte> updatedComptes = new ArrayList<>();
        updatedClient.setComptes(updatedComptes);

        clientService.updateClient(updatedClient);

        verify(clientRepository).findById(id);
        verify(clientRepository).save(existingClient);

        assertEquals("NewPay", existingClient.getPay());
        assertEquals("NewNom", ((ClientPersonnePhysique) existingClient).getNom());
        assertEquals("NewPrenom", ((ClientPersonnePhysique) existingClient).getPrenom());
    }

    @Test
    public void testUpdateClient_ClientNotFound_Throws() {
        Long id = 999L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        ClientPersonnePhysique client = new ClientPersonnePhysique();
        client.setIdRelation(id);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clientService.updateClient(client);
        });

        assertEquals("Client not found", ex.getMessage());
        verify(clientRepository, never()).save(any());
    }

    @Test
    public void testGetClientById_Found() {
        Client client = new ClientPersonnePhysique();
        client.setIdRelation(10L);

        when(clientRepository.findById(10L)).thenReturn(Optional.of(client));

        Client result = clientService.getClientById(10L);

        assertEquals(client, result);
        verify(clientRepository).findById(10L);
    }

    @Test
    public void testGetClientById_NotFound_Throws() {
        when(clientRepository.findById(20L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clientService.getClientById(20L);
        });

        assertEquals("client not found", ex.getMessage());
        verify(clientRepository).findById(20L);
    }
}
