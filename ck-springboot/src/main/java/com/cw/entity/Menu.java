package com.cw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    private Integer id;
    private String menuCode;
    private String menuName;
    private String menuLevel;
    private String menuParentCode;
    private String menuIndex;
    private String menuComponent;
    private String menuIcon;
    private String menuRight;
}
