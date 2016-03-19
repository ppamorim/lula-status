package com.lula.di.components;

import com.lula.di.LulaModule;
import com.lula.di.scopes.ActivityScope;
import com.lula.domain.interactor.LulaInteractor;
import com.lula.domain.presenter.LulaPresenter;
import com.lula.ui.activity.LulaActivity;
import dagger.Component;

@SuppressWarnings("unused")
@ActivityScope @Component(dependencies = ApplicationComponent.class,
    modules = { LulaModule.class})
public interface LulaComponent {
  void inject(LulaActivity lulaActivity);
  LulaPresenter getPresenter();
  LulaInteractor getInteractor();
}