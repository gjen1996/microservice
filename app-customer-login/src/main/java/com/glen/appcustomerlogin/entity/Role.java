package com.glen.appcustomerlogin.entity;/**
 * @author Glen
 * @create 2019- 06-2019/6/28-10:35
 * @Description
 */

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Glen
 * @create 2019/6/28 10:35 
 * @Description
 */
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
