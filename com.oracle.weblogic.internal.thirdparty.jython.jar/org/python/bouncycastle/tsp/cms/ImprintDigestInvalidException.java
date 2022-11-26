package org.python.bouncycastle.tsp.cms;

import org.python.bouncycastle.tsp.TimeStampToken;

public class ImprintDigestInvalidException extends Exception {
   private TimeStampToken token;

   public ImprintDigestInvalidException(String var1, TimeStampToken var2) {
      super(var1);
      this.token = var2;
   }

   public TimeStampToken getTimeStampToken() {
      return this.token;
   }
}
