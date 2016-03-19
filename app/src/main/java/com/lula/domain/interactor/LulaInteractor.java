package com.lula.domain.interactor;

import com.lula.domain.model.Lula;

public interface LulaInteractor {
  void execute(Callback callback);
  interface Callback {
    void onSuccessLoad(Lula lula);
    void onErrorLoad();
  }
}
