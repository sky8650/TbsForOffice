package address.xiaolei.com.tbsforoffice;

import java.io.File;

/**
 * author : XiaoLei
 * date   : 2019/1/241119
 * desc   :
 */
public class FileVo {
    private  int  progress;
    private File     file;
    private  boolean isSucess;

    public boolean isSucess() {
        return isSucess;
    }

    public void setSucess(boolean sucess) {
        isSucess = sucess;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
