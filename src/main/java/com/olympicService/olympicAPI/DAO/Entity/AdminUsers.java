package com.olympicService.olympicAPI.DAO.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin_users")
@Data
@NoArgsConstructor
public class AdminUsers {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    public  Integer adId;
	
	@Column(name = "level")
    public  Integer level;
	
    @Column(name = "name")
    public  String name;

    @Column (name = "email")
    public String email;
    
    @Column (name = "password")
    public String password;
    
    @Column (name = "creater")
    public String creater;
}
