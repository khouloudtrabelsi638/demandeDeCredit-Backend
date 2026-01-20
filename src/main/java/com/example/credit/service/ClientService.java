package com.example.credit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.credit.model.Actionneur;
import com.example.credit.model.Client;
import com.example.credit.model.ClientPersonneMorale;
import com.example.credit.model.ClientPersonnePhysique;
import com.example.credit.model.Compte;
import com.example.credit.model.Document;
import com.example.credit.model.Gerant;
import com.example.credit.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public void registerClient(Client client) {
        for (Compte compte : client.getComptes()) {
            compte.setClient(client);
            for (Document doc : compte.getDocuments()) {
                doc.setCompte(compte);
            }
        }
        if (client instanceof ClientPersonneMorale) {
            ClientPersonneMorale clientM = (ClientPersonneMorale) client;
            for (Gerant gerant : clientM.getGerants()) {
                gerant.setClient(client);
            }

            for (Actionneur act : clientM.getActionneurs()) {
                act.setClient(client);
            }
        }
        this.clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }

    @Transactional
    public void updateClient(Client updatedClient) {
        Client existingClient = clientRepository.findById(updatedClient.getIdRelation())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Shared base fields
        existingClient.setPay(updatedClient.getPay());
        existingClient.setGouvernorat(updatedClient.getGouvernorat());
        existingClient.setAdresse(updatedClient.getAdresse());
        existingClient.setInfoComplementaires(updatedClient.getInfoComplementaires());
        existingClient.setProfession(updatedClient.getProfession());

        // Update comptes
        existingClient.getComptes().clear();
        if (updatedClient.getComptes() != null) {
            updatedClient.getComptes().forEach(compte -> {
                compte.setClient(existingClient);
                existingClient.getComptes().add(compte);
            });
        }

        // Subclass-specific fields
        if (existingClient instanceof ClientPersonnePhysique && updatedClient instanceof ClientPersonnePhysique) {
            ClientPersonnePhysique existing = (ClientPersonnePhysique) existingClient;
            ClientPersonnePhysique updated = (ClientPersonnePhysique) updatedClient;

            existing.setNom(updated.getNom());
            existing.setPrenom(updated.getPrenom());
            existing.setSexe(updated.getSexe());
            existing.setSituationFamiliale(updated.getSituationFamiliale());
            existing.setDateDeNaissance(updated.getDateDeNaissance());
            existing.setTypeContrat(updated.getTypeContrat());
            existing.setTypeEmployeur(updated.getTypeEmployeur());
        } else if (existingClient instanceof ClientPersonneMorale && updatedClient instanceof ClientPersonneMorale) {
            ClientPersonneMorale existing = (ClientPersonneMorale) existingClient;
            ClientPersonneMorale updated = (ClientPersonneMorale) updatedClient;

            existing.setAcronyme(updated.getAcronyme());
            existing.setFormeJuridique(updated.getFormeJuridique());
            existing.setGroupe(updated.getGroupe());
            existing.setCapitalSocial(updated.getCapitalSocial());
            existing.setMatriculeFiscale(updated.getMatriculeFiscale());
            existing.setNbrEmployes(updated.getNbrEmployes());
            existing.setSecteurActivite(updated.getSecteurActivite());
            existing.setDateVisite(updated.getDateVisite());
            existing.setVisitePar(updated.getVisitePar());
            existing.setPersonnesContactees(updated.getPersonnesContactees());

            // Update gerants
            existing.getGerants().clear();
            if (updated.getGerants() != null) {
                updated.getGerants().forEach(gerant -> {
                    gerant.setClient(existing);
                    existing.getGerants().add(gerant);
                });
            }

            // Update actionneurs
            existing.getActionneurs().clear();
            if (updated.getActionneurs() != null) {
                updated.getActionneurs().forEach(actionneur -> {
                    actionneur.setClient(existing);
                    existing.getActionneurs().add(actionneur);
                });
            }
        }

        clientRepository.save(existingClient);
    }

    public Client getClientById(Long id) {
        return this.clientRepository.findById(id).orElseThrow(() -> new RuntimeException("client not found"));
    }

    public Client getClientByIdCompte(long id) {
        return this.clientRepository.findByComptesId(id);
    }

}
