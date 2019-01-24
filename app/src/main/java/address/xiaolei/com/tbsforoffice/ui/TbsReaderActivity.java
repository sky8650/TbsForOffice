package address.xiaolei.com.tbsforoffice.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.util.logging.Logger;

import address.xiaolei.com.tbsforoffice.FileVo;
import address.xiaolei.com.tbsforoffice.R;
import address.xiaolei.com.tbsforoffice.utils.DownloadUtil;
import address.xiaolei.com.tbsforoffice.utils.FileUtil;
import address.xiaolei.com.tbsforoffice.utils.RxUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class TbsReaderActivity extends Activity implements
        DownloadUtil.OnDownloadListener,TbsReaderView.ReaderCallback {

    DownloadUtil  downloadUtil;
    File file;
    TbsReaderView  tbsReaderView;
    LinearLayoutCompat  li_root;
    private String tbsReaderTemp = Environment.getExternalStorageDirectory() +
            "/TbsReaderTemp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbs_reader);
        initView();
    }

    /**
     * 初始化视图
     */
    private  void   initView(){
        tbsReaderView = new TbsReaderView(this, this);
        li_root=findViewById(R.id.li_root);
        li_root.addView(tbsReaderView);
        downloadUtil=DownloadUtil.get();
        downLoadFile();
    }


    /**
     * 下载文件
     */
    private  void   downLoadFile(){
        Observable.create(new ObservableOnSubscribe<FileVo>() {
          @Override
            public void subscribe(final ObservableEmitter<FileVo> e) throws Exception {
              final FileVo  fileVo=new FileVo();
               String path= FileUtil.getCachePath(TbsReaderActivity.this);
              // String  url="http://res.imtt.qq.com/TES/HowToLoadX5Core.doc";
              String  url="https://github.com/sky8650/TbsForOffice/raw/master/app/img/TBS_SDK.pdf";
                downloadUtil.download(url, path,
                        "aa.pdf",
                        new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(File file) {
                                fileVo.setFile(file);
                                e.onNext(fileVo);
                            }

                            @Override
                            public void onDownloading(int progress) {
                                fileVo.setProgress(progress+"");
                                e.onNext(fileVo);
                            }

                            @Override
                            public void onDownloadFailed(Exception e) {

                            }
                        });

            }

        }).compose(RxUtils.schedulersTransformer()).subscribe(new Consumer<FileVo>() {
            @Override
            public void accept(FileVo fileVo) {
                file=fileVo.getFile();
                if (file==null){
                    return;
                }
                //增加下面一句解决没有TbsReaderTemp文件夹不存在导致加载文件失败
                String bsReaderTemp = tbsReaderTemp;
                File bsReaderTempFile =new File(bsReaderTemp);
                if (!bsReaderTempFile.exists()) {
                    boolean mkdir = bsReaderTempFile.mkdir();
                    if(!mkdir){
                        Log.d("print","创建/TbsReaderTemp失败！！！！！");
                    }
                }
                //加载文件
                Bundle localBundle = new Bundle();
                localBundle.putString("filePath", file.toString());
                localBundle.putString("tempPath",
                        tbsReaderTemp);
                if (tbsReaderView == null){
                    tbsReaderView = getTbsView();
                }
                boolean result = tbsReaderView.preOpen(FileUtil.getFileType(file.toString()), false);
                if (result) {
                    tbsReaderView.openFile(localBundle);
                }

            }
        });

    }


    @Override
    public void onDownloadSuccess(File file) {
       this.file=file;
    }

    @Override
    public void onDownloading(int progress) {
        Log.d("下载进度：",""+progress);

    }

    @Override
    public void onDownloadFailed(Exception e) {

    }







    private   TbsReaderView  getTbsView(){
        return  new TbsReaderView(this,this);

    }


    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tbsReaderView.onStop();
    }
}
