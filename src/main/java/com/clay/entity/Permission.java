package com.clay.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Data
@Table(name = "permission")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "user_actions_bitmask", columnDefinition = "BIGINT")
    private BigInteger userActionsBitmask = BigInteger.ZERO;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
