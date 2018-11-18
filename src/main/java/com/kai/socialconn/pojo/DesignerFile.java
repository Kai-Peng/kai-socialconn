package com.kai.socialconn.pojo;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Repository;

@Alias(value = "designerfile")
public class DesignerFile {
    private Long id;
    private byte[] file;
    private String designer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }
}
