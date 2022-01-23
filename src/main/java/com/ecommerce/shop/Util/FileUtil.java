package com.ecommerce.shop.Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("fileUtil")
public class FileUtil {
    
    @Value("${base.path}")
    public String basePath;
    
    public boolean createBaseDirIfNotExist() {
        File baseDir = new File(basePath);
        boolean existsBaseDir = baseDir.exists();
        if (!existsBaseDir) {
            if (baseDir.mkdir()) {
                existsBaseDir = true;
            }
        }
        return existsBaseDir;
    }
    
    public String createDirectoryIfNotExist(String path) {
        try {
            String out = null;
            if (createBaseDirIfNotExist()) {
                out =  path + File.separator;
                // System.err.println("out::" + out);
                File test = new File(out);
                if (!test.exists()) {
                    test.mkdir();
                    // System.err.println(out + " u krijua");
                }
            } else {
                System.err.println("Directory of basepath failed to create");
            }
            return out;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
