package org.python.bouncycastle.dvcs;

import org.python.bouncycastle.asn1.dvcs.Data;

public abstract class DVCSRequestData {
   protected Data data;

   protected DVCSRequestData(Data var1) {
      this.data = var1;
   }

   public Data toASN1Structure() {
      return this.data;
   }
}
