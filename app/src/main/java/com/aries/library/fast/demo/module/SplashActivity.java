package com.aries.library.fast.demo.module;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.aries.library.fast.demo.R;
import com.aries.library.fast.demo.module.main.MainActivity;
import com.aries.library.fast.manager.RxJavaManager;
import com.aries.library.fast.module.activity.FastTitleActivity;
import com.aries.library.fast.util.FastUtil;
import com.aries.ui.util.StatusBarUtil;
import com.aries.ui.view.title.TitleBarView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import butterknife.BindView;

/**
 * Created: AriesHoo on 2017/8/7 21:41
 * Function:
 * Desc:
 */
public class SplashActivity extends FastTitleActivity {

    @BindView(R.id.tv_appSplash) TextView tvApp;
    @BindView(R.id.tv_versionSplash) TextView tvVersion;
    @BindView(R.id.tv_copyRightSplash) TextView tvCopyRight;

    @Override
    public boolean isLightStatusBarEnable() {
        return false;
    }

    @Override
    public void beforeSetContentView() {
        if (!isTaskRoot()) {//防止应用后台后点击桌面图标造成重启的假象---MIUI及Flyme上发现过(原生未发现)
            finish();
            return;
        }
        super.beforeSetContentView();
    }

    @Override
    public void beforeInitView() {
        super.beforeInitView();
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setVisibility(View.GONE);
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (!isTaskRoot()) {
            return;
        }
        if (!StatusBarUtil.isSupportStatusBarFontChange() && isLightStatusBarEnable()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }
        Drawable drawable = getResources().getDrawable(R.drawable.ic_launcher);
        FastUtil.getTintDrawable(drawable, Color.WHITE);
        tvApp.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        mContentView.setBackgroundResource(R.drawable.img_bg_login);
        tvVersion.setText("V" + FastUtil.getVersionName(mContext));
        tvVersion.setTextColor(Color.WHITE);
        tvCopyRight.setTextColor(Color.WHITE);
        RxJavaManager.getInstance().setTimer(2000, new RxJavaManager.TimerListener() {
            @Override
            public void timeEnd() {
                FastUtil.startActivity(mContext, MainActivity.class);
                finish();
            }
        }).compose(bindUntilEvent(ActivityEvent.DESTROY));
    }
}
