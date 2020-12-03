package com.glen.glengen.entity;
/**
 * @author Glen
 * @create 2020-10-14 00:01:33
 * @Description 这是一个测试类
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Entity;
import java.util.Date;

@Data
@Entity
@Table(name= "test_success")
public class TestSuccess implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id;

    /**
     * 用户名
     */
    public String username;

    /**
     * 密码
     */
    public String password;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createTime;

    public TestSuccess(Integer id, String username) {
        this.id= id;
        this.username= username;
    }

    public TestSuccess(){
   }
}