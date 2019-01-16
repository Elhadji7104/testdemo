package fr.demo2.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "cas")
    private String cas;

    @Column(name = "quantite")
    private String quantite;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "nom")
    private String nom;

    @Column(name = "mm")
    private String mm;

    @Column(name = "risque")
    private String risque;

    @Column(name = "molecule")
    private String molecule;

    @ManyToMany
    @JoinTable(name = "produit_user",
               joinColumns = @JoinColumn(name = "produits_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<Utilisateur> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "produit_achat",
               joinColumns = @JoinColumn(name = "produits_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "achats_id", referencedColumnName = "id"))
    private Set<Fournisseur> achats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCas() {
        return cas;
    }

    public Produit cas(String cas) {
        this.cas = cas;
        return this;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getQuantite() {
        return quantite;
    }

    public Produit quantite(String quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getLieu() {
        return lieu;
    }

    public Produit lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNom() {
        return nom;
    }

    public Produit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMm() {
        return mm;
    }

    public Produit mm(String mm) {
        this.mm = mm;
        return this;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getRisque() {
        return risque;
    }

    public Produit risque(String risque) {
        this.risque = risque;
        return this;
    }

    public void setRisque(String risque) {
        this.risque = risque;
    }

    public String getMolecule() {
        return molecule;
    }

    public Produit molecule(String molecule) {
        this.molecule = molecule;
        return this;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public Set<Utilisateur> getUsers() {
        return users;
    }

    public Produit users(Set<Utilisateur> utilisateurs) {
        this.users = utilisateurs;
        return this;
    }

    public Produit addUser(Utilisateur utilisateur) {
        this.users.add(utilisateur);
        return this;
    }

    public Produit removeUser(Utilisateur utilisateur) {
        this.users.remove(utilisateur);
        return this;
    }

    public void setUsers(Set<Utilisateur> utilisateurs) {
        this.users = utilisateurs;
    }

    public Set<Fournisseur> getAchats() {
        return achats;
    }

    public Produit achats(Set<Fournisseur> fournisseurs) {
        this.achats = fournisseurs;
        return this;
    }

    public Produit addAchat(Fournisseur fournisseur) {
        this.achats.add(fournisseur);
        return this;
    }

    public Produit removeAchat(Fournisseur fournisseur) {
        this.achats.remove(fournisseur);
        return this;
    }

    public void setAchats(Set<Fournisseur> fournisseurs) {
        this.achats = fournisseurs;
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
        Produit produit = (Produit) o;
        if (produit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", cas='" + getCas() + "'" +
            ", quantite='" + getQuantite() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", nom='" + getNom() + "'" +
            ", mm='" + getMm() + "'" +
            ", risque='" + getRisque() + "'" +
            ", molecule='" + getMolecule() + "'" +
            "}";
    }
}
