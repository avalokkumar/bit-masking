package com.clay.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "action")
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "ordinal", columnDefinition = "INT")
    private Integer ordinal;

    @ManyToOne(cascade = CascadeType.ALL)
    private Permission permission;
}
