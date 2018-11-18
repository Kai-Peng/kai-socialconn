package com.kai.socialconn.controller;

import com.kai.socialconn.bean.Task;
import com.kai.socialconn.dao.DesignerFileDao;
import com.kai.socialconn.pojo.DesignerFile;
import com.kai.socialconn.service.DesignerFileService;
import com.kai.socialconn.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DesignerFileController {
    @Autowired
    private DesignerFileService designerFileService = null;

    @RequestMapping("/designerFile")
    public String designerFile(ModelMap model){
        DesignerFile designerFile = designerFileService.getDesignerFileById(1);
        model.addAttribute("designerFile",designerFile);
        return "designerFilePage";
    }
}
