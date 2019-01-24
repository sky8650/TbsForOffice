package address.xiaolei.com.tbsforoffice;

import java.io.File;

/**
 * author : XiaoLei
 * date   : 2019/1/241119
 * desc   :
 */
public class FileVo {
    private  String  progress;
    private File     file;


    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
