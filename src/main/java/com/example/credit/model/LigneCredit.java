    package com.example.credit.model;

    import java.time.LocalDate;

    import com.fasterxml.jackson.annotation.JsonBackReference;

    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.JoinColumn;
    import jakarta.persistence.ManyToOne;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class LigneCredit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String famille;
        private String nature;
        private String type;
        private String devise;
        private int montant;
        private LocalDate dateEcheance;
        private String typetaux;
        private int taux;
        private int marge;
        private int montantcontreValeur;
        @ManyToOne
        @JoinColumn(name="id_dossier")
        @JsonBackReference
        private DossierCredit dossier;
    }
