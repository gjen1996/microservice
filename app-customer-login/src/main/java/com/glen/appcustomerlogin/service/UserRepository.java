package com.glen.appcustomerlogin.service;

import com.glen.appcustomerlogin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Glen
 * @create 2019/6/28 10:32 
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

