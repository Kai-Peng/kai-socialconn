package com.kai.socialconn.dao;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.pojo.DesignerFile;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerFileDao {
    public DesignerFile getDesignerFileById(int id);

    public boolean insertFile(DesignerFile designerFile);
}
