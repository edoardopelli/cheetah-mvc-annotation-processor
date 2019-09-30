/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnp.fastbook.domain;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edoardo
 */
@Entity
@Table(name = "Ristorante")
@NamedQueries({
    @NamedQuery(name = "Ristorante.findAll", query = "SELECT r FROM Ristorante r"),
    @NamedQuery(name = "Ristorante.findByIdRistorante", query = "SELECT r FROM Ristorante r WHERE r.idRistorante = :idRistorante"),
    @NamedQuery(name = "Ristorante.findByName", query = "SELECT r FROM Ristorante r WHERE r.name = :name"),
    @NamedQuery(name = "Ristorante.findByAddress", query = "SELECT r FROM Ristorante r WHERE r.address = :address"),
    @NamedQuery(name = "Ristorante.findByCity", query = "SELECT r FROM Ristorante r WHERE r.city = :city"),
    @NamedQuery(name = "Ristorante.findByLongitudine", query = "SELECT r FROM Ristorante r WHERE r.longitudine = :longitudine"),
    @NamedQuery(name = "Ristorante.findByLatitudine", query = "SELECT r FROM Ristorante r WHERE r.latitudine = :latitudine")})
public class Ristorante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRistorante")
    private Long idRistorante;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "longitudine")
    private String longitudine;
    @Column(name = "latitudine")
    private String latitudine;
    @OneToMany(mappedBy = "idRistorante")
    private Set<Utente> utenteSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRistorante")
    private Set<MenuPranzo> menuPranzoSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRistorante")
    private Set<Menu> menuSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRistorante")
    private Set<Tavolo> tavoloSet;

    public Ristorante() {
    }

    public Ristorante(Long idRistorante) {
        this.idRistorante = idRistorante;
    }

    public Long getIdRistorante() {
        return idRistorante;
    }

    public void setIdRistorante(Long idRistorante) {
        this.idRistorante = idRistorante;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public Set<Utente> getUtenteSet() {
        return utenteSet;
    }

    public void setUtenteSet(Set<Utente> utenteSet) {
        this.utenteSet = utenteSet;
    }

    public Set<MenuPranzo> getMenuPranzoSet() {
        return menuPranzoSet;
    }

    public void setMenuPranzoSet(Set<MenuPranzo> menuPranzoSet) {
        this.menuPranzoSet = menuPranzoSet;
    }

    public Set<Menu> getMenuSet() {
        return menuSet;
    }

    public void setMenuSet(Set<Menu> menuSet) {
        this.menuSet = menuSet;
    }

    public Set<Tavolo> getTavoloSet() {
        return tavoloSet;
    }

    public void setTavoloSet(Set<Tavolo> tavoloSet) {
        this.tavoloSet = tavoloSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRistorante != null ? idRistorante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ristorante)) {
            return false;
        }
        Ristorante other = (Ristorante) object;
        if ((this.idRistorante == null && other.idRistorante != null) || (this.idRistorante != null && !this.idRistorante.equals(other.idRistorante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dnp.fastbook.domain.Ristorante[ idRistorante=" + idRistorante + " ]";
    }
    
}
