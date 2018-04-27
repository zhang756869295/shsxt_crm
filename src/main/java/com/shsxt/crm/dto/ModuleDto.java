package com.shsxt.crm.dto;

/**
 * Created by lp on 2018/1/12.
 */
public class ModuleDto {
    private Integer id;
    private Integer pId;
    private String name;
    private Boolean checked=false;// false-未选中  true-选中

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
