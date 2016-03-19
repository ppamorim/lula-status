package com.lula.di.components;

import android.app.Application;
import com.github.ppamorim.threadexecutor.InteractorExecutor;
import com.github.ppamorim.threadexecutor.MainThread;
import com.lula.LulaApplication;
import com.lula.di.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
  void inject(LulaApplication lulaApplication);
  Application application();
  InteractorExecutor executor();
  MainThread mainThread();
}