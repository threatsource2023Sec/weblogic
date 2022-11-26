package org.python.bouncycastle.jce.provider;

import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.bouncycastle.jcajce.PKIXCRLStoreSelector;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.StoreException;

class PKIXCRLUtil {
   public Set findCRLs(PKIXCRLStoreSelector var1, Date var2, List var3, List var4) throws AnnotatedException {
      HashSet var5 = new HashSet();

      try {
         var5.addAll(this.findCRLs(var1, var4));
         var5.addAll(this.findCRLs(var1, var3));
      } catch (AnnotatedException var10) {
         throw new AnnotatedException("Exception obtaining complete CRLs.", var10);
      }

      HashSet var6 = new HashSet();
      Iterator var7 = var5.iterator();

      while(var7.hasNext()) {
         X509CRL var8 = (X509CRL)var7.next();
         if (var8.getNextUpdate().after(var2)) {
            X509Certificate var9 = var1.getCertificateChecking();
            if (var9 != null) {
               if (var8.getThisUpdate().before(var9.getNotAfter())) {
                  var6.add(var8);
               }
            } else {
               var6.add(var8);
            }
         }
      }

      return var6;
   }

   private final Collection findCRLs(PKIXCRLStoreSelector var1, List var2) throws AnnotatedException {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();
      AnnotatedException var5 = null;
      boolean var6 = false;

      while(var4.hasNext()) {
         Object var7 = var4.next();
         if (var7 instanceof Store) {
            Store var8 = (Store)var7;

            try {
               var3.addAll(var8.getMatches(var1));
               var6 = true;
            } catch (StoreException var10) {
               var5 = new AnnotatedException("Exception searching in X.509 CRL store.", var10);
            }
         } else {
            CertStore var12 = (CertStore)var7;

            try {
               var3.addAll(PKIXCRLStoreSelector.getCRLs(var1, var12));
               var6 = true;
            } catch (CertStoreException var11) {
               var5 = new AnnotatedException("Exception searching in X.509 CRL store.", var11);
            }
         }
      }

      if (!var6 && var5 != null) {
         throw var5;
      } else {
         return var3;
      }
   }
}
