package com.lula.domain.presenter;

import com.lula.domain.model.Lula;

public interface LulaPresenter {
  void requestStatus();
  void setView(View view);
  interface View {
    boolean isReady();
    void showLula(Lula lula);
    void showError();
  }
}
