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
