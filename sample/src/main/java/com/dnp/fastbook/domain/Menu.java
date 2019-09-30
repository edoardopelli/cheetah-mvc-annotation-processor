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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edoardo
 */
@Entity
@Table(name = "Menu")
@NamedQueries({
    @NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m"),
    @NamedQuery(name = "Menu.findByIdMenu", query = "SELECT m FROM Menu m WHERE m.idMenu = :idMenu")})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMenu")
    private Long idMenu;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMenu")
    private Set<Portata> portataSet;
    @JoinColumn(name = "idRistorante", referencedColumnName = "idRistorante")
    @ManyToOne(optional = false)
    private Ristorante idRistorante;

    public Menu() {
    }

    public Menu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Set<Portata> getPortataSet() {
        return portataSet;
    }

    public void setPortataSet(Set<Portata> portataSet) {
        this.portataSet = portataSet;
    }

    public Ristorante getIdRistorante() {
        return idRistorante;
    }

    public void setIdRistorante(Ristorante idRistorante) {
        this.idRistorante = idRistorante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dnp.fastbook.domain.Menu[ idMenu=" + idMenu + " ]";
    }
    
}
