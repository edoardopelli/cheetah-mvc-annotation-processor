/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example.domain;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edoardo
 */
@Entity
@Table(name = "Login")
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Login l"),
    @NamedQuery(name = "Login.findByIdLogin", query = "SELECT l FROM Login l WHERE l.idLogin = :idLogin"),
    @NamedQuery(name = "Login.findByUsername", query = "SELECT l FROM Login l WHERE l.username = :username"),
    @NamedQuery(name = "Login.findByTokenAccesso", query = "SELECT l FROM Login l WHERE l.tokenAccesso = :tokenAccesso"),
    @NamedQuery(name = "Login.findByDataUltimoAccesso", query = "SELECT l FROM Login l WHERE l.dataUltimoAccesso = :dataUltimoAccesso"),
    @NamedQuery(name = "Login.findByDataPrimoAccesso", query = "SELECT l FROM Login l WHERE l.dataPrimoAccesso = :dataPrimoAccesso")})
public class Login implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLogin")
    private Long idLogin;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Lob
    @Column(name = "password")
    private byte[] password;
    @Basic(optional = false)
    @Lob
    @Column(name = "salt")
    private byte[] salt;
    @Column(name = "tokenAccesso")
    private String tokenAccesso;
    @Column(name = "dataUltimoAccesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimoAccesso;
    @Column(name = "dataPrimoAccesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrimoAccesso;
    @JoinColumn(name = "idUtente", referencedColumnName = "idUtente")
    @ManyToOne(optional = false)
    private Utente idUtente;

    public Login() {
    }

    public Login(Long idLogin) {
        this.idLogin = idLogin;
    }

    public Login(Long idLogin, String username, byte[] password, byte[] salt) {
        this.idLogin = idLogin;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public String getTokenAccesso() {
        return tokenAccesso;
    }

    public void setTokenAccesso(String tokenAccesso) {
        this.tokenAccesso = tokenAccesso;
    }

    public Date getDataUltimoAccesso() {
        return dataUltimoAccesso;
    }

    public void setDataUltimoAccesso(Date dataUltimoAccesso) {
        this.dataUltimoAccesso = dataUltimoAccesso;
    }

    public Date getDataPrimoAccesso() {
        return dataPrimoAccesso;
    }

    public void setDataPrimoAccesso(Date dataPrimoAccesso) {
        this.dataPrimoAccesso = dataPrimoAccesso;
    }

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogin != null ? idLogin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.idLogin == null && other.idLogin != null) || (this.idLogin != null && !this.idLogin.equals(other.idLogin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "example.domain.Login[ idLogin=" + idLogin + " ]";
    }
    
}
