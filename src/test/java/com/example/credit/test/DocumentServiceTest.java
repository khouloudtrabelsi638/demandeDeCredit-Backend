package com.example.credit.test;
import com.example.credit.model.Document;
import com.example.credit.repository.DocumentRepository;
import com.example.credit.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocumentServiceTest {

    private DocumentRepository documentRepository;
    private DocumentService documentService;

    @BeforeEach
    public void setUp() {
        documentRepository = mock(DocumentRepository.class);
        documentService = new DocumentService(documentRepository);
    }

    @Test
    public void testGetAllDocuments_ReturnsDocuments() {
        Document doc1 = new Document();
        Document doc2 = new Document();
        when(documentRepository.findAll()).thenReturn(Arrays.asList(doc1, doc2));

        List<Document> result = documentService.getAllDocuments();

        assertEquals(2, result.size());
        verify(documentRepository).findAll();
    }

    @Test
    public void testGetDocumentById_ExistingId_ReturnsDocument() {
        Document doc = new Document();
        doc.setId(1L);
        when(documentRepository.findById(1L)).thenReturn(Optional.of(doc));

        Optional<Document> result = documentService.getDocumentById(1L);

        assertTrue(result.isPresent());
        assertEquals(doc, result.get());
        verify(documentRepository).findById(1L);
    }

    @Test
    public void testGetDocumentById_NonExistingId_ReturnsEmpty() {
        when(documentRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Document> result = documentService.getDocumentById(99L);

        assertFalse(result.isPresent());
        verify(documentRepository).findById(99L);
    }

    @Test
    public void testAddDocument_SavesDocument() {
        Document doc = new Document();
        documentService.addDocument(doc);

        verify(documentRepository).save(doc);
    }

    @Test
    public void testUpdateDocument_ExistingId_SavesDocument() {
        Document doc = new Document();
        doc.setId(1L);
        when(documentRepository.existsById(1L)).thenReturn(true);

        documentService.updateDocument(doc);

        verify(documentRepository).existsById(1L);
        verify(documentRepository).save(doc);
    }

    @Test
    public void testUpdateDocument_NonExistingId_Throws() {
        Document doc = new Document();
        doc.setId(2L);
        when(documentRepository.existsById(2L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            documentService.updateDocument(doc);
        });

        assertEquals("Document non trouvé", exception.getMessage());
        verify(documentRepository).existsById(2L);
        verify(documentRepository, never()).save(any());
    }

    @Test
    public void testDeleteDocumentById_ExistingId_DeletesDocument() {
        when(documentRepository.existsById(1L)).thenReturn(true);

        documentService.deleteDocumentById(1L);

        verify(documentRepository).existsById(1L);
        verify(documentRepository).deleteById(1L);
    }

    @Test
    public void testDeleteDocumentById_NonExistingId_Throws() {
        when(documentRepository.existsById(5L)).thenReturn(false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            documentService.deleteDocumentById(5L);
        });

        assertEquals("Document non trouvé", exception.getMessage());
        verify(documentRepository).existsById(5L);
        verify(documentRepository, never()).deleteById(anyLong());
    }
}
