package com.lula.di;

import com.lula.di.scopes.ActivityScope;
import com.lula.domain.interactor.LulaInteractor;
import com.lula.domain.interactor.LulaInteractorImpl;
import com.lula.domain.presenter.LulaPresenter;
import com.lula.domain.presenter.LulaPresenterImpl;
import dagger.Module;
import dagger.Provides;

@Module public class LulaModule {

  @Provides @ActivityScope LulaPresenter provideLulaPresenter(LulaPresenterImpl presenter) {
    return presenter;
  }

  @Provides @ActivityScope LulaInteractor provideLulaInteractor(LulaInteractorImpl interactor) {
    return interactor;
  }

}
