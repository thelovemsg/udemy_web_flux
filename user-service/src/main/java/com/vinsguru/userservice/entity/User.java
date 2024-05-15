package com.vinsguru.userservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("usersA")
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer balance;
}
