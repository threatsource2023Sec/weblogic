package org.python.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class SupportedCipherSuiteFilter implements CipherSuiteFilter {
   public static final SupportedCipherSuiteFilter INSTANCE = new SupportedCipherSuiteFilter();

   private SupportedCipherSuiteFilter() {
   }

   public String[] filterCipherSuites(Iterable ciphers, List defaultCiphers, Set supportedCiphers) {
      if (defaultCiphers == null) {
         throw new NullPointerException("defaultCiphers");
      } else if (supportedCiphers == null) {
         throw new NullPointerException("supportedCiphers");
      } else {
         ArrayList newCiphers;
         if (ciphers == null) {
            newCiphers = new ArrayList(defaultCiphers.size());
            ciphers = defaultCiphers;
         } else {
            newCiphers = new ArrayList(supportedCiphers.size());
         }

         Iterator var5 = ((Iterable)ciphers).iterator();

         while(var5.hasNext()) {
            String c = (String)var5.next();
            if (c == null) {
               break;
            }

            if (supportedCiphers.contains(c)) {
               newCiphers.add(c);
            }
         }

         return (String[])newCiphers.toArray(new String[newCiphers.size()]);
      }
   }
}
