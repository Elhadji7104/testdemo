package fr.demo2.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Groupe.
 */
@Entity
@Table(name = "groupe")
public class Groupe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupe_name")
    private String groupeName;

    @ManyToMany
    @JoinTable(name = "groupe_user",
               joinColumns = @JoinColumn(name = "groupes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<Utilisateur> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public Groupe groupeName(String groupeName) {
        this.groupeName = groupeName;
        return this;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public Set<Utilisateur> getUsers() {
        return users;
    }

    public Groupe users(Set<Utilisateur> utilisateurs) {
        this.users = utilisateurs;
        return this;
    }

    public Groupe addUser(Utilisateur utilisateur) {
        this.users.add(utilisateur);
        return this;
    }

    public Groupe removeUser(Utilisateur utilisateur) {
        this.users.remove(utilisateur);
        return this;
    }

    public void setUsers(Set<Utilisateur> utilisateurs) {
        this.users = utilisateurs;
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
        Groupe groupe = (Groupe) o;
        if (groupe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), groupe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Groupe{" +
            "id=" + getId() +
            ", groupeName='" + getGroupeName() + "'" +
            "}";
    }
}
