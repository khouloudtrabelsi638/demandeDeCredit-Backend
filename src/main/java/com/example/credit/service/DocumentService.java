package com.example.credit.service;

import com.example.credit.model.Document;
import com.example.credit.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(long id) {
        return documentRepository.findById(id);
    }

    public void addDocument(Document document) {
        documentRepository.save(document);
    }

    public void updateDocument(Document document) {
        long id = document.getId();
        if (!documentRepository.existsById(id)) {
            throw new IllegalStateException("Document non trouvé");
        }
        documentRepository.save(document);
    }

    public void deleteDocumentById(long id) {
        if (!documentRepository.existsById(id)) {
            throw new IllegalStateException("Document non trouvé");
        }
        documentRepository.deleteById(id);
    }
}
