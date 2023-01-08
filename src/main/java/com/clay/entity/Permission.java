package com.clay.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Data
@Table(name = "permission")
@ToString(exclude = "user")
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "user_actions_bitmask", columnDefinition = "BIGINT")
    private BigInteger userActionsBitmask = BigInteger.ZERO;

    @ManyToOne
    @JoinTable(name = "user_permission_mapping")
    private User user;
}
