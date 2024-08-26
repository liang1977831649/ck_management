package com.cw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Storage {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    private String remark;
}
