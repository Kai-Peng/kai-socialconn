package com.kai.socialconn.service;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.pojo.DesignerFile;

import java.util.List;

public interface DesignerFileService {
    public DesignerFile getDesignerFileById(int id);

    public boolean insertFile(DesignerFile designerFile);
}
