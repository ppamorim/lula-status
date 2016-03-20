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
package com.lula.domain.interactor;

import com.bluelinelabs.logansquare.LoganSquare;
import com.github.ppamorim.threadexecutor.Interactor;
import com.github.ppamorim.threadexecutor.InteractorExecutor;
import com.github.ppamorim.threadexecutor.MainThread;
import com.lula.domain.model.Lula;
import com.lula.domain.model.Quotation;
import java.io.InputStream;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LulaInteractorImpl implements Interactor, LulaInteractor  {

  private final InteractorExecutor interactorExecutor;
  private final MainThread mainThread;
  private Callback callback;

  @Inject LulaInteractorImpl(InteractorExecutor interactorExecutor, MainThread mainThread) {
    this.interactorExecutor = interactorExecutor;
    this.mainThread = mainThread;
  }

  @Override public void execute(Callback callback) {
    if (callback == null) {
      throw new IllegalArgumentException("Callback parameter can't be null");
    }
    this.callback = callback;
    this.interactorExecutor.run(this);
  }

  @Override public void run() {
    try {

      Request request = new Request.Builder()
          .url("https://gist.githubusercontent.com/ppamorim/b863090cf8f041c93a1f/raw/3f71de74eddeef75da1d2f11fe96549f26f2f6a1/gistfile1.txt")
          .build();

      OkHttpClient okHttpClient = new OkHttpClient();

      Response response = okHttpClient.newCall(request).execute();

      InputStream inputStream = response.body().byteStream();

      if (inputStream != null) {
        Lula lula = LoganSquare.parse(inputStream, Lula.class);
        notifySuccess(lula);
      }

      Request moneyRequest = new Request.Builder()
          .url("http://developers.agenciaideias.com.br/cotacoes/json")
          .build();
      Response responseMoney = okHttpClient.newCall(moneyRequest).execute();
      InputStream moneyInputStream = responseMoney.body().byteStream();
      if (moneyInputStream != null) {
        Quotation quotation = LoganSquare.parse(moneyInputStream, Quotation.class);
        notifyMoneySuccess(quotation);
      }

    } catch (Exception e) {
      notifyError();
    }
  }

  private void notifySuccess(final Lula lula) {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onSuccessLoad(lula);
      }
    });
  }

  private void notifyMoneySuccess(final Quotation quotation) {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onSuccessMoneyLoad(quotation);
      }
    });
  }

  private void notifyError() {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onErrorLoad();
      }
    });
  }

}
