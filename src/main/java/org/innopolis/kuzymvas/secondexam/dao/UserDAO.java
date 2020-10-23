package org.innopolis.kuzymvas.secondexam.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", unique = true, nullable = false)
    private String name;
}
