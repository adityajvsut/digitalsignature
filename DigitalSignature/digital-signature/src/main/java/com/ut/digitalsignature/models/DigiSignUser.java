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

import lombok.Data;

@Entity
@Table(name = "DigiSignUser")
@JsonIgnoreProperties(value = {"CreatedAt"}, allowGetters = true)
@Data
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

}