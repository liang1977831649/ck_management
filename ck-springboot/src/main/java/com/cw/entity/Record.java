package com.cw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author baomidou
 * @since 2024-08-24
 */
@Data
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;
    @NotNull
    private Integer goodsId;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer adminId;
    @NotNull
    private Integer count;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createtime;
    @NotNull
    private String operation;

    private String remark;

    //名称
    @TableField(exist = false)
    private String adminName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String goodstypeName;
    @TableField(exist = false)
    private String storageName;

}
