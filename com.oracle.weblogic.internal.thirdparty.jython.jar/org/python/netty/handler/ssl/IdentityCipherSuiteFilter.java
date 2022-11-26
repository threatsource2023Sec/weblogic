package org.python.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {
   public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter();

   private IdentityCipherSuiteFilter() {
   }

   public String[] filterCipherSuites(Iterable ciphers, List defaultCiphers, Set supportedCiphers) {
      if (ciphers == null) {
         return (String[])defaultCiphers.toArray(new String[defaultCiphers.size()]);
      } else {
         List newCiphers = new ArrayList(supportedCiphers.size());
         Iterator var5 = ciphers.iterator();

         while(var5.hasNext()) {
            String c = (String)var5.next();
            if (c == null) {
               break;
            }

            newCiphers.add(c);
         }

         return (String[])newCiphers.toArray(new String[newCiphers.size()]);
      }
   }
}
