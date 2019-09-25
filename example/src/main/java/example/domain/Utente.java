/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edoardo
 */
@Entity
@Table(name = "Utente")
@NamedQueries({
    @NamedQuery(name = "Utente.findAll", query = "SELECT u FROM Utente u"),
    @NamedQuery(name = "Utente.findByIdUtente", query = "SELECT u FROM Utente u WHERE u.idUtente = :idUtente"),
    @NamedQuery(name = "Utente.findByName", query = "SELECT u FROM Utente u WHERE u.name = :name"),
    @NamedQuery(name = "Utente.findBySurname", query = "SELECT u FROM Utente u WHERE u.surname = :surname"),
    @NamedQuery(name = "Utente.findByBirthday", query = "SELECT u FROM Utente u WHERE u.birthday = :birthday"),
    @NamedQuery(name = "Utente.findByHasRestaurant", query = "SELECT u FROM Utente u WHERE u.hasRestaurant = :hasRestaurant")})
public class Utente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUtente")
    private Long idUtente;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(name = "hasRestaurant")
    private Boolean hasRestaurant;
    @JoinColumn(name = "idRistorante", referencedColumnName = "idRistorante")
    @ManyToOne
    private Ristorante idRistorante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUtente")
    private Set<Login> loginSet;

    public Utente() {
    }

    public Utente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getHasRestaurant() {
        return hasRestaurant;
    }

    public void setHasRestaurant(Boolean hasRestaurant) {
        this.hasRestaurant = hasRestaurant;
    }

    public Ristorante getIdRistorante() {
        return idRistorante;
    }

    public void setIdRistorante(Ristorante idRistorante) {
        this.idRistorante = idRistorante;
    }

    public Set<Login> getLoginSet() {
        return loginSet;
    }

    public void setLoginSet(Set<Login> loginSet) {
        this.loginSet = loginSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUtente != null ? idUtente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utente)) {
            return false;
        }
        Utente other = (Utente) object;
        if ((this.idUtente == null && other.idUtente != null) || (this.idUtente != null && !this.idUtente.equals(other.idUtente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "example.domain.Utente[ idUtente=" + idUtente + " ]";
    }
    
}
