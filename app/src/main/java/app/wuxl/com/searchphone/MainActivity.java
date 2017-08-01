package app.wuxl.com.searchphone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.wuxl.com.searchphone.model.Phone;
import app.wuxl.com.searchphone.mvp.MvpMainView;
import app.wuxl.com.searchphone.mvp.impl.MainPresenter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MvpMainView {

    @Bind(R.id.input_phone)
    EditText inputPhone;
    @Bind(R.id.button_search)
    Button buttonSearch;
    @Bind(R.id.result_phone)
    TextView resultPhone;
    @Bind(R.id.result_province)
    TextView resultProvince;
    @Bind(R.id.result_type)
    TextView resultType;
    @Bind(R.id.result_carrier)
    TextView resultCarrier;

    MainPresenter mainPresenter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this);
        mainPresenter.attach(this);
    }

    @OnClick(R.id.button_search)
    public void onClick() {
        //查询
        mainPresenter.searchPhoneInfo(inputPhone.getText().toString());
    }

    @Override
    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", "正在查询...", true, false);
        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle("");
            progressDialog.setMessage("正在查询...");
        }
        progressDialog.show();
    }

    @Override
    public void hidenLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView() {
        Phone phone = mainPresenter.getPhoneInfo();
        resultPhone.setText("手机号：" + inputPhone.getText().toString());
        resultProvince.setText("省和市：" + phone.getResult().getProvince() + phone.getResult().getCity());
        resultType.setText("运营商：" + phone.getResult().getCompany());
        resultCarrier.setText("卡类型：" + phone.getResult().getCardtype());
    }
}
