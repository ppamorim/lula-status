package com.lula.domain.presenter;

import com.lula.domain.interactor.LulaInteractor;
import com.lula.domain.model.Lula;
import javax.inject.Inject;

public class LulaPresenterImpl implements LulaPresenter {

  private View view;
  private LulaInteractor lulaInteractor;
  private Lula lula;

  @Inject LulaPresenterImpl(LulaInteractor lulaInteractor) {
    this.lulaInteractor = lulaInteractor;
  }

  @Override public void requestStatus() {
    lulaInteractor.execute(new LulaInteractor.Callback() {
      @Override public void onSuccessLoad(Lula lula) {
        notifySuccess(lula);
      }

      @Override public void onErrorLoad() {
        notifyError();
      }
    });
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

  private void notifyError() {
    if (view.isReady()) {
      view.showError();
    }
  }

}
