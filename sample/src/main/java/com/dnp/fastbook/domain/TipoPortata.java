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
@Table(name = "TipoPortata")
@NamedQueries({
    @NamedQuery(name = "TipoPortata.findAll", query = "SELECT t FROM TipoPortata t"),
    @NamedQuery(name = "TipoPortata.findByIdTipoPortata", query = "SELECT t FROM TipoPortata t WHERE t.idTipoPortata = :idTipoPortata"),
    @NamedQuery(name = "TipoPortata.findByDescrizione", query = "SELECT t FROM TipoPortata t WHERE t.descrizione = :descrizione")})
public class TipoPortata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTipoPortata")
    private Long idTipoPortata;
    @Column(name = "descrizione")
    private String descrizione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPortata")
    private Set<Portata> portataSet;

    public TipoPortata() {
    }

    public TipoPortata(Long idTipoPortata) {
        this.idTipoPortata = idTipoPortata;
    }

    public Long getIdTipoPortata() {
        return idTipoPortata;
    }

    public void setIdTipoPortata(Long idTipoPortata) {
        this.idTipoPortata = idTipoPortata;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<Portata> getPortataSet() {
        return portataSet;
    }

    public void setPortataSet(Set<Portata> portataSet) {
        this.portataSet = portataSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPortata != null ? idTipoPortata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPortata)) {
            return false;
        }
        TipoPortata other = (TipoPortata) object;
        if ((this.idTipoPortata == null && other.idTipoPortata != null) || (this.idTipoPortata != null && !this.idTipoPortata.equals(other.idTipoPortata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dnp.fastbook.domain.TipoPortata[ idTipoPortata=" + idTipoPortata + " ]";
    }
    
}
