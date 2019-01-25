package address.xiaolei.com.tbsforoffice.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.daimajia.numberprogressbar.NumberProgressBar;
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
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TbsReaderActivity extends Activity implements
        TbsReaderView.ReaderCallback {

    DownloadUtil  downloadUtil;
    File file;
    TbsReaderView  tbsReaderView;
    FrameLayout li_root;
    NumberProgressBar progressBar;
    private  String  officeUrl="";
    private  String  officeSaveName="";
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
        officeUrl=getIntent().getStringExtra("URL");
        officeSaveName="aa."+FileUtil.getFileType(officeUrl);
        progressBar=findViewById(R.id.number_progress_bar);
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
                downloadUtil.download(officeUrl, path,
                        officeSaveName,
                        new DownloadUtil.OnDownloadListener() {
                            @Override
                            public void onDownloadSuccess(File file) {
                                fileVo.setFile(file);
                                e.onNext(fileVo);
                                e.onComplete();
                            }
                            @Override
                            public void onDownloading(int progress) {
                                Log.d("当前下载的进度",""+progress);
                                showProgress(progress);
                            }
                            @Override
                            public void onDownloadFailed(Exception e) {
                            }
                        });
            }

        }).compose(RxUtils.schedulersTransformer()).subscribe(new Consumer<FileVo>() {
            @Override
            public void accept(FileVo fileVo) {
                 showOffice(fileVo);
            }
        });

    }


    /**
     * 加载文件
     */
    private   void   showOffice(FileVo fileVo){
         progressBar.setProgress(fileVo.getProgress());
         file=fileVo.getFile();
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


    /**
     * 显示进度
     * @param progress
     */
   private   void   showProgress(final int  progress){
        Observable.just(progress)
               .compose(RxUtils.schedulersTransformer())
               . subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                progressBar.setProgress(progress);
            }
        });

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
