package fr.demo2.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fournisseur_name")
    private String fournisseurName;

    @ManyToMany
    @JoinTable(name = "fournisseur_achat",
               joinColumns = @JoinColumn(name = "fournisseurs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "achats_id", referencedColumnName = "id"))
    private Set<Produit> achats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFournisseurName() {
        return fournisseurName;
    }

    public Fournisseur fournisseurName(String fournisseurName) {
        this.fournisseurName = fournisseurName;
        return this;
    }

    public void setFournisseurName(String fournisseurName) {
        this.fournisseurName = fournisseurName;
    }

    public Set<Produit> getAchats() {
        return achats;
    }

    public Fournisseur achats(Set<Produit> produits) {
        this.achats = produits;
        return this;
    }

    public Fournisseur addAchat(Produit produit) {
        this.achats.add(produit);
        return this;
    }

    public Fournisseur removeAchat(Produit produit) {
        this.achats.remove(produit);
        return this;
    }

    public void setAchats(Set<Produit> produits) {
        this.achats = produits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fournisseur fournisseur = (Fournisseur) o;
        if (fournisseur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fournisseur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", fournisseurName='" + getFournisseurName() + "'" +
            "}";
    }
}
