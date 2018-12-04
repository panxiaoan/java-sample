package com.panxiaoan.sample.java8.file;

import java.util.Map;
import org.junit.Test;

/**
 * @author <a href="mailto:xiaoan.pan@qq.com">潘小安</a>
 * @since 2018-12-03 16:09
 */
public class FileSampleTest {

    private FileSample fileSample = new FileSample();

    @Test
    public void testClear() {
        Map<String, String> evnMap = System.getenv();
        String m2RepoPath = evnMap.get("M2_REPO");
        fileSample.clearM2Repo(m2RepoPath);
    }
}
