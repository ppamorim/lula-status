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
package com.lula.domain.presenter;

import android.os.Bundle;
import com.lula.domain.interactor.LulaInteractor;
import com.lula.domain.model.Lula;
import com.lula.domain.model.Quotation;
import com.lula.util.Tags;
import javax.inject.Inject;

public class LulaPresenterImpl implements LulaPresenter {

  private View view;
  private LulaInteractor lulaInteractor;
  private Lula lula;
  private Quotation quotation;

  @Inject LulaPresenterImpl(LulaInteractor lulaInteractor) {
    this.lulaInteractor = lulaInteractor;
  }

  @Override public void requestStatus(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      this.lula = savedInstanceState.getParcelable(Tags.LULA);
      this.quotation = savedInstanceState.getParcelable(Tags.QUOTATION);
      savedInstanceState.remove(Tags.LULA);
      savedInstanceState.remove(Tags.QUOTATION);
      notifySuccess(lula);
      notifySuccess(quotation);
    } else {
      notifyLoading();
      lulaInteractor.execute(new LulaInteractor.Callback() {
        @Override public void onSuccessLoad(Lula lula) {
          notifySuccess(lula);
        }

        @Override public void onSuccessMoneyLoad(Quotation quotation) {
          notifySuccess(quotation);
        }

        @Override public void onErrorLoad() {
          notifyError();
        }
      });
    }
  }

  @Override public Bundle saveInstanceState(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      if (!savedInstanceState.containsKey(Tags.LULA)) {
        savedInstanceState.putParcelable(Tags.LULA, lula);
      }
      if (!savedInstanceState.containsKey(Tags.QUOTATION)) {
        savedInstanceState.putParcelable(Tags.QUOTATION, quotation);
      }
    }
    return savedInstanceState;
  }

  @Override public void setView(View view) {
    this.view = view;
  }

  private void notifySuccess(Lula lula) {
    this.lula = lula;
    if (view.isReady()) {
      view.showLula(lula);
    }
  }

  private void notifySuccess(Quotation quotation) {
    if (view.isReady()) {
      view.showMoney(quotation);
    }
  }

  private void notifyLoading() {
    if (view.isReady()) {
      view.showLoading();
    }
  }

  private void notifyError() {
    if (view.isReady()) {
      view.showError();
    }
  }

}
