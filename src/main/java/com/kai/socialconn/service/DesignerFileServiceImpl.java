package com.kai.socialconn.service;

import com.kai.socialconn.dao.DesignerFileDao;
import com.kai.socialconn.pojo.DesignerFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignerFileServiceImpl implements DesignerFileService{
    @Autowired
    private DesignerFileDao designerFileDao;

    @Override
    public DesignerFile getDesignerFileById(int id) {
        return designerFileDao.getDesignerFileById(id);
    }

    @Override
    public boolean insertFile(DesignerFile designerFile) {
        return designerFileDao.insertFile(designerFile);
    }
}
