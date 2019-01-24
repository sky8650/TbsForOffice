package address.xiaolei.com.tbsforoffice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import address.xiaolei.com.tbsforoffice.ui.TbsReaderActivity;

public class MainActivity extends AppCompatActivity {
    Button  btn_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化视图
     */
    private   void  initView(){
        btn_file=findViewById(R.id.btn_file);
        btn_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,
                        TbsReaderActivity.class);
                startActivity(intent);
            }
        });

    }




}
