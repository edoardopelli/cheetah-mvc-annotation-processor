/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.domain;

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
@Table(name = "Portata")
@NamedQueries({
    @NamedQuery(name = "Portata.findAll", query = "SELECT p FROM Portata p"),
    @NamedQuery(name = "Portata.findByIdPortata", query = "SELECT p FROM Portata p WHERE p.idPortata = :idPortata"),
    @NamedQuery(name = "Portata.findByDescrizione", query = "SELECT p FROM Portata p WHERE p.descrizione = :descrizione"),
    @NamedQuery(name = "Portata.findByPrezzo", query = "SELECT p FROM Portata p WHERE p.prezzo = :prezzo"),
    @NamedQuery(name = "Portata.findBySenzaGlutine", query = "SELECT p FROM Portata p WHERE p.senzaGlutine = :senzaGlutine")})
public class Portata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPortata")
    private Long idPortata;
    @Column(name = "descrizione")
    private String descrizione;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prezzo")
    private Double prezzo;
    @Column(name = "senzaGlutine")
    private Boolean senzaGlutine;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPortata")
    private Set<MenuPranzoDettaglio> menuPranzoDettaglioSet;
    @JoinColumn(name = "idTipoPortata", referencedColumnName = "idTipoPortata")
    @ManyToOne(optional = false)
    private TipoPortata idTipoPortata;
    @JoinColumn(name = "idMenu", referencedColumnName = "idMenu")
    @ManyToOne(optional = false)
    private Menu idMenu;

    public Portata() {
    }

    public Portata(Long idPortata) {
        this.idPortata = idPortata;
    }

    public Long getIdPortata() {
        return idPortata;
    }

    public void setIdPortata(Long idPortata) {
        this.idPortata = idPortata;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public Boolean getSenzaGlutine() {
        return senzaGlutine;
    }

    public void setSenzaGlutine(Boolean senzaGlutine) {
        this.senzaGlutine = senzaGlutine;
    }

    public Set<MenuPranzoDettaglio> getMenuPranzoDettaglioSet() {
        return menuPranzoDettaglioSet;
    }

    public void setMenuPranzoDettaglioSet(Set<MenuPranzoDettaglio> menuPranzoDettaglioSet) {
        this.menuPranzoDettaglioSet = menuPranzoDettaglioSet;
    }

    public TipoPortata getIdTipoPortata() {
        return idTipoPortata;
    }

    public void setIdTipoPortata(TipoPortata idTipoPortata) {
        this.idTipoPortata = idTipoPortata;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPortata != null ? idPortata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Portata)) {
            return false;
        }
        Portata other = (Portata) object;
        if ((this.idPortata == null && other.idPortata != null) || (this.idPortata != null && !this.idPortata.equals(other.idPortata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "example.domain.Portata[ idPortata=" + idPortata + " ]";
    }
    
}
