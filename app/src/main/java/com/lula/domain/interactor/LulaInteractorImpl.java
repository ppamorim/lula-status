package com.lula.domain.interactor;

import com.bluelinelabs.logansquare.LoganSquare;
import com.github.ppamorim.threadexecutor.Interactor;
import com.github.ppamorim.threadexecutor.InteractorExecutor;
import com.github.ppamorim.threadexecutor.MainThread;
import com.lula.domain.model.Lula;
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
          .url("")
          .build();

      Response response = new OkHttpClient().newCall(request).execute();
      InputStream inputStream = response.body().byteStream();

      if (inputStream != null) {
        Lula lula = LoganSquare.parse(inputStream, Lula.class);
        notifySuccess(lula);
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

  private void notifyError() {
    mainThread.post(new Runnable() {
      @Override public void run() {
        callback.onErrorLoad();
      }
    });
  }

}
