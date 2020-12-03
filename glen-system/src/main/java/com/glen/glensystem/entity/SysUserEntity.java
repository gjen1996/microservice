package com.glen.glensystem.entity;/**
 * @author Glen
 * @create 2020-09-07 15:19:34
 * @Description
 */

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glen.glensystem.group.AddGroup;
import com.glen.glensystem.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 系统用户
 */
@TableName("sys_user")
@Data
@ApiModel(value = "User对象", description = "用户表")
public class SysUserEntity implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "主键id", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * y用户id
     */
    @ApiModelProperty(value = "用户id", example = "12145")
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    @ApiModelProperty(value = "用户名", example = "admin")
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "123456")
    @TableField("password")
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
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
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
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
    @TableField(exist = false)
    private List<Long> roleIdList;

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
    @NotNull(message = "部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;
    @TableField("recommen_person")
    private String recommenPerson;
    @TableField("role_name")
    private String roleName;
    @TableField("wx_no")
    private String wxNo;
    @TableField("qq_no")
    private String qqNo;
    @TableField("is_delete")
    private String isDelete;

    @TableField("is_jasper_user")
    private String isJasperUser;

    /**
     * 公司名称
     */
    @TableField("cust_Name")
    private String custName;


    @TableField("user_role")
    private String userRole;

    /**
     * 微信唯一标识
     */
    @TableField("open_id")
    private String openId;


    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 微信登陆态
     */
    @TableField("wx_status")
    private String wxStatus;


    /**
     * 获取：用户名
     *
     * @return String
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
