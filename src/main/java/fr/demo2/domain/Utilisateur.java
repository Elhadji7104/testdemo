package fr.demo2.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "user_name")
    private String userName;

    @ManyToMany
    @JoinTable(name = "utilisateur_user",
               joinColumns = @JoinColumn(name = "utilisateurs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<Groupe> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "utilisateur_enprunter",
               joinColumns = @JoinColumn(name = "utilisateurs_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "enprunters_id", referencedColumnName = "id"))
    private Set<Produit> enprunters = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Utilisateur idUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public Utilisateur userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<Groupe> getUsers() {
        return users;
    }

    public Utilisateur users(Set<Groupe> groupes) {
        this.users = groupes;
        return this;
    }

    public Utilisateur addUser(Groupe groupe) {
        this.users.add(groupe);
        return this;
    }

    public Utilisateur removeUser(Groupe groupe) {
        this.users.remove(groupe);
        return this;
    }

    public void setUsers(Set<Groupe> groupes) {
        this.users = groupes;
    }

    public Set<Produit> getEnprunters() {
        return enprunters;
    }

    public Utilisateur enprunters(Set<Produit> produits) {
        this.enprunters = produits;
        return this;
    }

    public Utilisateur addEnprunter(Produit produit) {
        this.enprunters.add(produit);
        return this;
    }

    public Utilisateur removeEnprunter(Produit produit) {
        this.enprunters.remove(produit);
        return this;
    }

    public void setEnprunters(Set<Produit> produits) {
        this.enprunters = produits;
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
        Utilisateur utilisateur = (Utilisateur) o;
        if (utilisateur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), utilisateur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", idUser=" + getIdUser() +
            ", userName='" + getUserName() + "'" +
            "}";
    }
}
