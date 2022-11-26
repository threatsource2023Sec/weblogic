package org.python.bouncycastle.cert.jcajce;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.python.bouncycastle.cert.X509CRLHolder;
import org.python.bouncycastle.util.CollectionStore;

public class JcaCRLStore extends CollectionStore {
   public JcaCRLStore(Collection var1) throws CRLException {
      super(convertCRLs(var1));
   }

   private static Collection convertCRLs(Collection var0) throws CRLException {
      ArrayList var1 = new ArrayList(var0.size());
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         if (var3 instanceof X509CRL) {
            try {
               var1.add(new X509CRLHolder(((X509CRL)var3).getEncoded()));
            } catch (IOException var5) {
               throw new CRLException("cannot read encoding: " + var5.getMessage());
            }
         } else {
            var1.add((X509CRLHolder)var3);
         }
      }

      return var1;
   }
}
