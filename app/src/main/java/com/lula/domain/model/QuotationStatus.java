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
public class QuotationStatus implements Parcelable {

  public static final Parcelable.Creator<Quotation> CREATOR
      = new Parcelable.Creator<Quotation>() {
    public Quotation createFromParcel(Parcel in) {
      return new Quotation(in);
    }
    public Quotation[] newArray(int size) {
      return new Quotation[size];
    }
  };

  @JsonField(name = "cotacao") private Float quotation;
  @JsonField(name = "variacao") private Float variation;

  public QuotationStatus() {
    super();
  }

  public QuotationStatus(Parcel in) {
    this.quotation = in.readFloat();
    this.variation = in.readFloat();
  }

  public Float getVariation() {
    return variation;
  }

  public void setVariation(Float variation) {
    this.variation = variation;
  }

  public Float getQuotation() {
    return quotation;
  }

  public void setQuotation(Float quotation) {
    this.quotation = quotation;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeFloat(quotation);
    parcel.writeFloat(variation);
  }

}
