package com.rsa.certj;

import com.rsa.certj.x.c;

public class CertJInternalHelper {
   /** @deprecated */
   public static c context(CertJ var0) {
      return var0.context;
   }

   public static int hashCodeValue(Object var0) {
      return var0 == null ? 0 : var0.hashCode();
   }
}
