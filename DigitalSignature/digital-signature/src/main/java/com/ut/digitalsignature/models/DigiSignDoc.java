package com.ut.digitalsignature.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ut.digitalsignature.dto.Request.DocumentReqSign;
import com.ut.digitalsignature.repositories.IGenericDao;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "DigiSignDoc")
@JsonIgnoreProperties(value = {"CreatedAt","UpdatedAt"}, allowGetters = true)
@Component
public class DigiSignDoc implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id", nullable = true)
    private String user_id;

    @Column(name = "email", nullable = true)
    private String user_email;

    @Column(name = "name", nullable = true)
    private String user_name;

    @Column(name = "file_path", nullable = true)
    private String file_path;

    @Column(name = "page", nullable = true)
    private String page;

    @Column(name = "llx", nullable = true)
    private String llx;

    @Column(name = "lly", nullable = true)
    private String lly;

    @Column(name = "urx", nullable = true)
    private String urx;

    @Column(name = "ury", nullable = true)
    private String ury;

    @Column(name = "sign_status", nullable = true)
    private Boolean sign_status;

    @Column(name = "sign_type", nullable = true)
    private String sign_type;
    
    @Column(name="CreatedAt",nullable = false)
    @CreationTimestamp
    private Date CreatedAt;

    @Transient
    IGenericDao<DigiSignDoc> dao;

    @Autowired
    public void setDao(IGenericDao<DigiSignDoc> daoToSet) {
      dao = daoToSet;
      dao.setClazz(DigiSignDoc.class);
    }

    public void setDocumentData(String userId,DocumentReqSign users,String filename){
        setFile_path(filename);
        setUser_id(userId);
        setUser_email(users.getEmail());
        setUser_name(users.getName());
        setPage(users.getPage());
        setLlx(users.getLlx());
        setLly(users.getLly());
        setUrx(users.getUrx());
        setUry(users.getUry());
        setSign_type(users.getAksi_ttd());
        setSign_status(false);
        dao.save(this);
    }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getUser_email() {
    return user_email;
  }

  public void setUser_email(String user_email) {
    this.user_email = user_email;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getFile_path() {
    return file_path;
  }

  public void setFile_path(String file_path) {
    this.file_path = file_path;
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public String getLlx() {
    return llx;
  }

  public void setLlx(String llx) {
    this.llx = llx;
  }

  public String getLly() {
    return lly;
  }

  public void setLly(String lly) {
    this.lly = lly;
  }

  public String getUrx() {
    return urx;
  }

  public void setUrx(String urx) {
    this.urx = urx;
  }

  public String getUry() {
    return ury;
  }

  public void setUry(String ury) {
    this.ury = ury;
  }

  public Boolean getSign_status() {
    return sign_status;
  }

  public void setSign_status(Boolean sign_status) {
    this.sign_status = sign_status;
  }

  public String getSign_type() {
    return sign_type;
  }

  public void setSign_type(String sign_type) {
    this.sign_type = sign_type;
  }

  public Date getCreatedAt() {
    return CreatedAt;
  }

  public void setCreatedAt(Date createdAt) {
    CreatedAt = createdAt;
  }

  public IGenericDao<DigiSignDoc> getDao() {
    return dao;
  }

}