package com.glen.glengen.entity;/**
 * @author Glen
 * @create 2020-09-07 15:19:34
 * @Description
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * 系统用户
 */

@ApiModel(value = "User对象", description = "用户表")
@Data
@Entity
@Table(name="s_user")
public class SysUserEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */

    @ApiModelProperty(value = "主键id", example = "1")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    /**
     * y用户id
     */
    @ApiModelProperty(value = "用户id", example = "12145")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;

    /**
     * 盐
     */
    @ApiModelProperty(value = "加盐", example = "456893")
    private String salt;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "用户邮箱", example = "805596378@qq.com")
    private String email;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "18605910879")
    private String mobile;

    /**
     * 状态 0：禁用 1：正常
     */
    @ApiModelProperty(value = "用户状态", example = "1")
    private Integer status;

    /**
     * 角色ID列表
     */
    @ApiModelProperty(value = "用户角色", example = "1")
    private Integer roleId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "用户创建时间", example = "1")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "用户部门", example = "1")
    private Long deptId;

    /**
     * 部门名称
     */

    private String deptName;

    private String recommenPerson;

    private String roleName;

    private String wxNo;

    private String qqNo;

    private String isDelete;


    private String isJasperUser;

    /**
     * 公司名称
     */

    private String custName;


    private String userRole;

    /**
     * 微信唯一标识
     */

    private String openId;


    /**
     * 微信登陆态
     */

    private String wxStatus;


    /**
     * 获取：用户名
     *
     * @return String
     */

}
