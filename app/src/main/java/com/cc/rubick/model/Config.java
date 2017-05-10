package com.cc.rubick.model;

/**
 * Created by bigcong on 2017/2/28.
 */

public class Config {
    private Long configId;

    /**

     */
    private String type;

    /**
     * type  中文描述
     */
    private String typeName;

    /**
     * value 中文描述
     */
    private String valueName;

    /**

     */
    private String value;
    private Page page;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
