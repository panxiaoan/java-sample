package com.panxiaoan.sample.java8.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-12-03 15:34
 */
public class FileSample {

    /** 删除 Maven 仓库无效文件 */
    public void clearM2Repo(String path) {
        try {
            Files.list(Paths.get(path)).forEach(pathItem -> {
                try {
                    File file = pathItem.toFile();
                    if (file.isDirectory()) {
                        this.clearM2Repo(file.getAbsolutePath());
                    } else {
                        if (file.getName().endsWith(".jar") || file.getName().endsWith(".pom")) {
                            return;
                        } else {
                            System.out.println(file.getAbsolutePath());
                            file.delete();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
