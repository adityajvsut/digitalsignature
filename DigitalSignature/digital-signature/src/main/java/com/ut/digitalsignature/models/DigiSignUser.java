package com.ut.digitalsignature.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ut.digitalsignature.dto.Request.RegisterUser;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "DigiSignUser")
@JsonIgnoreProperties(value = {"CreatedAt"}, allowGetters = true)
public class DigiSignUser implements Serializable{

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
    private String email;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "mobile_number", nullable = true)
    private String mobile_number;

    @Column(name = "ttd_path", nullable = true)
    private String ttd_path;

    @Column(name = "sign_path", nullable = true)
    private String sign_path;

    @Column(name="CreatedAt",nullable = false)
    @CreationTimestamp
    private Date CreatedAt;

    public void setUserDetails(RegisterUser userDetails,String path){
        this.setName(userDetails.getName());
        this.setEmail(userDetails.getEmail());
        this.setMobile_number(userDetails.getMobile_number());
        this.setUser_id(userDetails.getUser_id());
        this.setTtd_path(path);      
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getTtd_path() {
        return ttd_path;
    }

    public void setTtd_path(String ttd_path) {
        this.ttd_path = ttd_path;
    }

    public String getSign_path() {
        return sign_path;
    }

    public void setSign_path(String sign_path) {
        this.sign_path = sign_path;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

}