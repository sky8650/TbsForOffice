package address.xiaolei.com.tbsforoffice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import address.xiaolei.com.tbsforoffice.ui.TbsReaderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button  btn_pdf,btn_word,btn_ppt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(MainActivity.this,
                TbsReaderActivity.class);
        switch (v.getId()){
            case R.id.btn_pdf:
                String  pdf_url="https://github.com/sky8650/TbsForOffice/raw/master/app/img/TBS_SDK.pdf";
                intent.putExtra("URL",pdf_url);
                startActivity(intent);
                break;
            case  R.id.btn_ppt:
                String  ppt_url="";
                intent.putExtra("URL",ppt_url);
                startActivity(intent);
                break;

            case  R.id.btn_word:
                String word_url="";
                intent.putExtra("URL",word_url);
                startActivity(intent);
                break;
        }

    }


    /**
     * 初始化视图
     */
    private   void  initView(){
        btn_pdf=findViewById(R.id.btn_pdf);
        btn_pdf.setOnClickListener(this);

        btn_word=findViewById(R.id.btn_word);
        btn_word.setOnClickListener(this);

        btn_ppt=findViewById(R.id.btn_ppt);
        btn_ppt.setOnClickListener(this);


    }


}
