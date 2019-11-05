package com.ut.digitalsignature.models;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;


@Entity
@Table(name = "emailotp")
@EntityListeners(AuditingEntityListener.class)
@Data
public class emailotp implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotBlank
    @Column(name="email")
    private String email;

    @NotBlank
    @Column(name="otp")
    private String otp;

}