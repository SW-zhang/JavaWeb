package com.framework.upload.service;

import com.framework.service.GenericCrudService;
import com.framework.upload.FileStatusEnum;
import com.framework.upload.entity.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UploadFileService {
    private final Logger logger = LoggerFactory.getLogger(UploadFileService.class);

    @Autowired
    private GenericCrudService crudService;

    @Transactional
    public void save(UploadFile file) {
        crudService.save(file);
    }

    public List<UploadFile> getListByEntryCode(String entryCode) {
        return crudService.hql(UploadFile.class, "from UploadFile where entryCode = ?", entryCode);
    }

    public UploadFile findOne(Long id) {
        return crudService.get(UploadFile.class, id);
    }

    @Transactional
    public void delete(Long id) {
        UploadFile uploadFile = findOne(id);
        // 获得下载文件全路径
        String downLoadPath = uploadFile.getSaveFilePath() + File.separator + uploadFile.getSaveFileName();
        File file = new File(downLoadPath);
        if (file.exists()) {
            file.delete();
        }
        logger.info("物理删除附件：******************** [ " + uploadFile.getRealFileName() + " ] **************");
        crudService.delete(uploadFile);
    }

    /**
     * 更改文件状态
     *
     * @param status
     * @param entryCodes
     */
    @Transactional
    public void updateStatus(FileStatusEnum status, String... entryCodes) {
        for (String entryCode : entryCodes) {
            List<UploadFile> files = getListByEntryCode(entryCode);
            for (UploadFile file : files) {
                if (file != null) {
                    file.setState(status.getValue());
                }
                crudService.save(file);
            }
        }
    }

    /**
     * 更改文件状态
     *
     * @param status
     * @param ids
     */
    @Transactional
    public void updateStatus(FileStatusEnum status, Long... ids) {
        for (Long id : ids) {
            UploadFile file = findOne(id);
            if (file != null) {
                file.setState(status.getValue());
            }
            crudService.save(file);
        }
    }

}
