package org.python.bouncycastle.cert.dane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.bouncycastle.util.CollectionStore;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.StoreException;

public class DANEEntryStore implements Store {
   private final Map entries;

   DANEEntryStore(List var1) {
      HashMap var2 = new HashMap();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         DANEEntry var4 = (DANEEntry)var3.next();
         var2.put(var4.getDomainName(), var4);
      }

      this.entries = Collections.unmodifiableMap(var2);
   }

   public Collection getMatches(Selector var1) throws StoreException {
      if (var1 == null) {
         return this.entries.values();
      } else {
         ArrayList var2 = new ArrayList();
         Iterator var3 = this.entries.values().iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            if (var1.match(var4)) {
               var2.add(var4);
            }
         }

         return Collections.unmodifiableList(var2);
      }
   }

   public Store toCertificateStore() {
      Collection var1 = this.getMatches((Selector)null);
      ArrayList var2 = new ArrayList(var1.size());
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         DANEEntry var4 = (DANEEntry)var3.next();
         var2.add(var4.getCertificate());
      }

      return new CollectionStore(var2);
   }
}
