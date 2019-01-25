# TbsForOffice
腾讯TBS浏览服务应用内打开Office（Word，PPT，PDF）文件

## 前言

众所周知利用Android系统的Webview是无法直接加载Office文件的，但是IOS可以，也是无奈呀！ 
不过还好鹅厂给我们提供了X5浏览器，一般来说Android客户端想要加载文件，一般可有三种方案:
    
* 由后台开发人员把Office文件转换成Html文件
* 调用手机的第三方浏览器打开
* 利用X5浏览器在应用内打开

## 图例
<image src="https://github.com/sky8650/TbsForOffice/blob/master/app/img/device-2019-01-25-141307.png" width="260px"/>   <image src="https://github.com/sky8650/TbsForOffice/blob/master/app/img/device-2019-01-25-141409.png" width="260px"/>    <image 
src="https://github.com/sky8650/TbsForOffice/blob/master/app/img/GIF.gif" width="260px"/>

## 文件下载(OKhttp)
```
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
```

### 文件加载
```
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

```



## 遇到的问题
   * 加载TbsReaderView的容器最好不要是LinearLayoutCompat，LinearLayout在滑动的时候会出现显示不全的情况。用FrameLayout较好
     （目前还不清楚原因，估计是X5的内核机制导致）
   * X5浏览器记得需要在Application中进行初始化
   * TbsReaderView 不能放在 layout 布局文件中，因为源码中只有TbsReaderView(Context var1, TbsReaderView.ReaderCallback var2) {}这一个构造方法进行初始化
   * 只能加载本地的文件，因此先下载到sd卡中再进行加载
   * 需要在onDestroy中停用TbsReaderView：tbsReaderView.onStop();
   
   ## PS:如果本文对你有帮助请点个star或者Fork，如有问题可在Issues中进行讨论
    
    
