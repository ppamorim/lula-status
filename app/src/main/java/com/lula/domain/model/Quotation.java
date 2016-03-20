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
public class Quotation implements Parcelable {

  public static final Parcelable.Creator<Quotation> CREATOR
      = new Parcelable.Creator<Quotation>() {
    public Quotation createFromParcel(Parcel in) {
      return new Quotation(in);
    }
    public Quotation[] newArray(int size) {
      return new Quotation[size];
    }
  };

  @JsonField(name = "dolar") private QuotationStatus dollar;
  @JsonField(name = "euro") private QuotationStatus euro;

  public Quotation() {
    super();
  }

  public Quotation(Parcel in) {
    this.dollar = in.readParcelable(QuotationStatus.class.getClassLoader());
    this.euro = in.readParcelable(QuotationStatus.class.getClassLoader());
  }

  public QuotationStatus getDollar() {
    return dollar;
  }

  public void setDollar(QuotationStatus dollar) {
    this.dollar = dollar;
  }

  public QuotationStatus getEuro() {
    return euro;
  }

  public void setEuro(QuotationStatus euro) {
    this.euro = euro;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeParcelable(dollar, i);
    parcel.writeParcelable(euro, i);
  }

}
