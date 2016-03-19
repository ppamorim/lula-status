package com.lula.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lula.LulaApplication;
import com.lula.R;
import com.lula.di.LulaModule;
import com.lula.di.components.DaggerLulaComponent;
import com.lula.di.components.LulaComponent;
import com.lula.domain.model.Lula;
import com.lula.domain.presenter.LulaPresenter;
import javax.inject.Inject;

public class LulaActivity extends AppCompatActivity implements LulaPresenter.View {

  private LulaComponent lulaComponent;

  @Inject LulaPresenter lulaPresenter;

  @Bind(R.id.progress_bar) ProgressBar progressBar;
  @Bind(R.id.try_again) Button tryAgainButton;
  @Bind(R.id.arrested) TextView arrestedTextView;
  @Bind(R.id.minister) TextView ministerTextView;
  @Bind(R.id.fallback_view) FrameLayout fallback;

  @OnClick(R.id.try_again) void onTryAgainClick() {
    progressBar.setVisibility(View.VISIBLE);
    lulaPresenter.requestStatus();
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    lulaComponent().inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lula);
    ButterKnife.bind(this);
    lulaPresenter.setView(this);
    progressBar.setVisibility(View.VISIBLE);
    lulaPresenter.requestStatus();
  }

  @Override public boolean isReady() {
    return !isFinishing();
  }

  @Override public void showLula(Lula lula) {
    fallback.setVisibility(View.GONE);
    progressBar.setVisibility(View.GONE);
    arrestedTextView.setVisibility(View.VISIBLE);
    ministerTextView.setVisibility(View.VISIBLE);
    arrestedTextView.setText(lula.isArrested() ? R.string.yes : R.string.no);
    ministerTextView.setText(lula.isMinister() ? R.string.yes : R.string.no);
  }

  @Override public void showError() {
    fallback.setVisibility(View.VISIBLE);
    tryAgainButton.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
    arrestedTextView.setVisibility(View.GONE);
    ministerTextView.setVisibility(View.GONE);
  }

  private LulaComponent lulaComponent() {
    if (lulaComponent == null) {
      lulaComponent = DaggerLulaComponent.builder()
          .applicationComponent(((LulaApplication) getApplication()).component())
          .lulaModule(new LulaModule())
          .build();
    }
    return lulaComponent;
  }

}
