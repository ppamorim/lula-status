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
package com.lula.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Lula implements Parcelable {

  public static final Parcelable.Creator<Lula> CREATOR
      = new Parcelable.Creator<Lula>() {
    public Lula createFromParcel(Parcel in) {
      return new Lula(in);
    }
    public Lula[] newArray(int size) {
      return new Lula[size];
    }
  };

  @JsonField(name = "arrested") private boolean arrested;
  @JsonField(name = "arrested") private boolean minister;

  public Lula() {
    super();
  }

  public Lula(Parcel in) {
    this.arrested = in.readInt() > 0;
    this.minister = in.readInt() > 0;
  }

  public boolean isArrested() {
    return arrested;
  }

  public void setArrested(boolean arrested) {
    this.arrested = arrested;
  }

  public boolean isMinister() {
    return minister;
  }

  public void setMinister(boolean minister) {
    this.minister = minister;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(arrested ? 1 : 0);
    parcel.writeInt(minister ? 1 : 0);
  }

}
