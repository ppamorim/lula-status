/*
* Copyright (C) 2016 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.lula.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import com.lula.domain.model.Quotation;
import com.lula.domain.presenter.LulaPresenter;
import javax.inject.Inject;

public class LulaActivity extends AppCompatActivity implements LulaPresenter.View {

  private LulaComponent lulaComponent;

  @Inject LulaPresenter lulaPresenter;

  @Bind(R.id.progress_bar) ProgressBar progressBar;
  @Bind(R.id.try_again) Button tryAgainButton;
  @Bind(R.id.arrested_title) TextView arrestedTitleTextView;
  @Bind(R.id.arrested) TextView arrestedTextView;
  @Bind(R.id.minister_title) TextView ministerTitleTextView;
  @Bind(R.id.minister) TextView ministerTextView;
  @Bind(R.id.quotation) TextView quotationTextView;

  @OnClick(R.id.try_again) void onTryAgainClick() {
    progressBar.setVisibility(View.VISIBLE);
    lulaPresenter.requestStatus(null);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    lulaComponent().inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lula);
    ButterKnife.bind(this);
    lulaPresenter.setView(this);
    lulaPresenter.requestStatus(savedInstanceState);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    lulaPresenter.saveInstanceState(outState);
  }

  @Override public boolean isReady() {
    return !isFinishing();
  }

  @Override public void showLula(Lula lula) {
    progressBar.setVisibility(View.GONE);
    arrestedTitleTextView.setVisibility(View.VISIBLE);
    ministerTitleTextView.setVisibility(View.VISIBLE);
    arrestedTextView.setVisibility(View.VISIBLE);
    ministerTextView.setVisibility(View.VISIBLE);
    arrestedTextView.setText(lula.isArrested() ? R.string.yes : R.string.no);
    ministerTextView.setText(lula.isMinister() ? R.string.yes : R.string.no);
  }

  @Override public void showMoney(Quotation quotation) {

    Float dollar = quotation.getDollar().getQuotation();
    Float euro = quotation.getEuro().getQuotation();

    String formattedQuotation = String.format(getResources().getString(R.string.quotation),
        dollar, euro);

    quotationTextView.setText(formattedQuotation);
    quotationTextView.setVisibility(View.VISIBLE);
  }

  @Override public void showLoading() {
    progressBar.setVisibility(View.VISIBLE);
    tryAgainButton.setVisibility(View.GONE);
    arrestedTitleTextView.setVisibility(View.GONE);
    ministerTitleTextView.setVisibility(View.GONE);
    arrestedTextView.setVisibility(View.GONE);
    ministerTextView.setVisibility(View.GONE);
  }

  @Override public void showError() {
    tryAgainButton.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
    arrestedTitleTextView.setVisibility(View.GONE);
    ministerTitleTextView.setVisibility(View.GONE);
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
