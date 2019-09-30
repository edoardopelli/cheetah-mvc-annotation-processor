/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnp.fastbook.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author edoardo
 */
@Entity
@Table(name = "MenuPranzoDettaglio")
@NamedQueries({
    @NamedQuery(name = "MenuPranzoDettaglio.findAll", query = "SELECT m FROM MenuPranzoDettaglio m"),
    @NamedQuery(name = "MenuPranzoDettaglio.findByIdMenuPranzoDettaglio", query = "SELECT m FROM MenuPranzoDettaglio m WHERE m.idMenuPranzoDettaglio = :idMenuPranzoDettaglio")})
public class MenuPranzoDettaglio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMenuPranzoDettaglio")
    private Long idMenuPranzoDettaglio;
    @JoinColumn(name = "idMenuPranzo", referencedColumnName = "idMenuPranzo")
    @ManyToOne(optional = false)
    private MenuPranzo idMenuPranzo;
    @JoinColumn(name = "idPortata", referencedColumnName = "idPortata")
    @ManyToOne(optional = false)
    private Portata idPortata;

    public MenuPranzoDettaglio() {
    }

    public MenuPranzoDettaglio(Long idMenuPranzoDettaglio) {
        this.idMenuPranzoDettaglio = idMenuPranzoDettaglio;
    }

    public Long getIdMenuPranzoDettaglio() {
        return idMenuPranzoDettaglio;
    }

    public void setIdMenuPranzoDettaglio(Long idMenuPranzoDettaglio) {
        this.idMenuPranzoDettaglio = idMenuPranzoDettaglio;
    }

    public MenuPranzo getIdMenuPranzo() {
        return idMenuPranzo;
    }

    public void setIdMenuPranzo(MenuPranzo idMenuPranzo) {
        this.idMenuPranzo = idMenuPranzo;
    }

    public Portata getIdPortata() {
        return idPortata;
    }

    public void setIdPortata(Portata idPortata) {
        this.idPortata = idPortata;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenuPranzoDettaglio != null ? idMenuPranzoDettaglio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPranzoDettaglio)) {
            return false;
        }
        MenuPranzoDettaglio other = (MenuPranzoDettaglio) object;
        if ((this.idMenuPranzoDettaglio == null && other.idMenuPranzoDettaglio != null) || (this.idMenuPranzoDettaglio != null && !this.idMenuPranzoDettaglio.equals(other.idMenuPranzoDettaglio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dnp.fastbook.domain.MenuPranzoDettaglio[ idMenuPranzoDettaglio=" + idMenuPranzoDettaglio + " ]";
    }
    
}
