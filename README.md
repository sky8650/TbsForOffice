# TbsForOffice
腾讯TBS浏览服务应用内打开Office（Word，PPT，PDF）文件

## 前言
    众所周知由于墙的原因，利用Android系统的Webview是无法直接加载Office文件的，但是IOS可以，也是无奈呀！ 
    不过还好鹅厂给我们提供了X5浏览器，一般来说Android客户端想要加载文件，一般可有三种方案：
* 由后台开发人员把Office文件转换成Html文件
* 调用手机的第三方浏览器打开
* 利用X5浏览器在应用内打开



## 遇到的问题
   * 加载TbsReaderView的容器最好不要是LinearLayoutCompat，LinearLayout在滑动的时候会出现显示不全的情况。用FrameLayout较好
     （目前还不清楚原因，估计是X5的内核机制导致）
   * X5浏览器记得需要在Application中进行初始化
   * TbsReaderView 不能放在 layout 布局文件中，源码中只有TbsReaderView(Context var1, TbsReaderView.ReaderCallback var2) {}这一个构造方法进行初始化
    
    
