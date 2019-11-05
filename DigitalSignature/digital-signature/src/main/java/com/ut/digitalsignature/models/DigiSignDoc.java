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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "DigiSignDoc")
@JsonIgnoreProperties(value = {"CreatedAt","UpdatedAt"}, allowGetters = true)
@Data
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

    @Column(name = "mobile_number", nullable = true)
    private String mobile_number;

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
    
    @Column(name="CreatedAt",nullable = false)
    @CreationTimestamp
    private Date CreatedAt;


}