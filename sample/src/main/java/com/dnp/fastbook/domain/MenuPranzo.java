/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dnp.fastbook.domain;

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
@Table(name = "MenuPranzo")
@NamedQueries({
    @NamedQuery(name = "MenuPranzo.findAll", query = "SELECT m FROM MenuPranzo m"),
    @NamedQuery(name = "MenuPranzo.findByIdMenuPranzo", query = "SELECT m FROM MenuPranzo m WHERE m.idMenuPranzo = :idMenuPranzo"),
    @NamedQuery(name = "MenuPranzo.findByPrezzo", query = "SELECT m FROM MenuPranzo m WHERE m.prezzo = :prezzo"),
    @NamedQuery(name = "MenuPranzo.findByDate", query = "SELECT m FROM MenuPranzo m WHERE m.date = :date")})
public class MenuPranzo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMenuPranzo")
    private Long idMenuPranzo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prezzo")
    private Double prezzo;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMenuPranzo")
    private Set<MenuPranzoDettaglio> menuPranzoDettaglioSet;
    @JoinColumn(name = "idRistorante", referencedColumnName = "idRistorante")
    @ManyToOne(optional = false)
    private Ristorante idRistorante;

    public MenuPranzo() {
    }

    public MenuPranzo(Long idMenuPranzo) {
        this.idMenuPranzo = idMenuPranzo;
    }

    public Long getIdMenuPranzo() {
        return idMenuPranzo;
    }

    public void setIdMenuPranzo(Long idMenuPranzo) {
        this.idMenuPranzo = idMenuPranzo;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<MenuPranzoDettaglio> getMenuPranzoDettaglioSet() {
        return menuPranzoDettaglioSet;
    }

    public void setMenuPranzoDettaglioSet(Set<MenuPranzoDettaglio> menuPranzoDettaglioSet) {
        this.menuPranzoDettaglioSet = menuPranzoDettaglioSet;
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
        hash += (idMenuPranzo != null ? idMenuPranzo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MenuPranzo)) {
            return false;
        }
        MenuPranzo other = (MenuPranzo) object;
        if ((this.idMenuPranzo == null && other.idMenuPranzo != null) || (this.idMenuPranzo != null && !this.idMenuPranzo.equals(other.idMenuPranzo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dnp.fastbook.domain.MenuPranzo[ idMenuPranzo=" + idMenuPranzo + " ]";
    }
    
}
