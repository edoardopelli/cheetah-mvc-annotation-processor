/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author edoardo
 */
@Entity
@Table(name = "Tavolo")
@NamedQueries({
    @NamedQuery(name = "Tavolo.findAll", query = "SELECT t FROM Tavolo t"),
    @NamedQuery(name = "Tavolo.findByIdTavolo", query = "SELECT t FROM Tavolo t WHERE t.idTavolo = :idTavolo"),
    @NamedQuery(name = "Tavolo.findByName", query = "SELECT t FROM Tavolo t WHERE t.name = :name")})
public class Tavolo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTavolo")
    private Long idTavolo;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "qrcode")
    private byte[] qrcode;
    @JoinColumn(name = "idRistorante", referencedColumnName = "idRistorante")
    @ManyToOne(optional = false)
    private Ristorante idRistorante;

    public Tavolo() {
    }

    public Tavolo(Long idTavolo) {
        this.idTavolo = idTavolo;
    }

    public Long getIdTavolo() {
        return idTavolo;
    }

    public void setIdTavolo(Long idTavolo) {
        this.idTavolo = idTavolo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getQrcode() {
        return qrcode;
    }

    public void setQrcode(byte[] qrcode) {
        this.qrcode = qrcode;
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
        hash += (idTavolo != null ? idTavolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tavolo)) {
            return false;
        }
        Tavolo other = (Tavolo) object;
        if ((this.idTavolo == null && other.idTavolo != null) || (this.idTavolo != null && !this.idTavolo.equals(other.idTavolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "example.domain.Tavolo[ idTavolo=" + idTavolo + " ]";
    }
    
}
