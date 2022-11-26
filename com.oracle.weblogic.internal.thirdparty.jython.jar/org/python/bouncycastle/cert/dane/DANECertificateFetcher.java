package org.python.bouncycastle.cert.dane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.python.bouncycastle.operator.DigestCalculator;

public class DANECertificateFetcher {
   private final DANEEntryFetcherFactory fetcherFactory;
   private final DANEEntrySelectorFactory selectorFactory;

   public DANECertificateFetcher(DANEEntryFetcherFactory var1, DigestCalculator var2) {
      this.fetcherFactory = var1;
      this.selectorFactory = new DANEEntrySelectorFactory(var2);
   }

   public List fetch(String var1) throws DANEException {
      DANEEntrySelector var2 = this.selectorFactory.createSelector(var1);
      List var3 = this.fetcherFactory.build(var2.getDomainName()).getEntries();
      ArrayList var4 = new ArrayList(var3.size());
      Iterator var5 = var3.iterator();

      while(var5.hasNext()) {
         DANEEntry var6 = (DANEEntry)var5.next();
         if (var2.match(var6)) {
            var4.add(var6.getCertificate());
         }
      }

      return Collections.unmodifiableList(var4);
   }
}
