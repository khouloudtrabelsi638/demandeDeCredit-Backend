package com.example.credit.controller;

import com.example.credit.model.Document;
import com.example.credit.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public List<Document> getAll() {
        return documentService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public Document getById(@PathVariable long id) {
        Optional<Document> document = documentService.getDocumentById(id);
        return document.orElse(null);
    }

    @PostMapping
    public String add(@RequestBody Document document) {
        documentService.addDocument(document);
        return "Document ajouté";
    }

    @PutMapping
    public String update(@RequestBody Document document) {
        documentService.updateDocument(document);
        return "Mise à jour avec succès";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        documentService.deleteDocumentById(id);
        return "Document supprimé";
    }
}
