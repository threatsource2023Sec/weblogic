package org.python.bouncycastle.x509;

import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.PKIXParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.bouncycastle.jce.provider.AnnotatedException;
import org.python.bouncycastle.util.StoreException;

class PKIXCRLUtil {
   public Set findCRLs(X509CRLStoreSelector var1, ExtendedPKIXParameters var2, Date var3) throws AnnotatedException {
      HashSet var4 = new HashSet();

      try {
         var4.addAll(this.findCRLs(var1, var2.getAdditionalStores()));
         var4.addAll(this.findCRLs(var1, var2.getStores()));
         var4.addAll(this.findCRLs(var1, var2.getCertStores()));
      } catch (AnnotatedException var10) {
         throw new AnnotatedException("Exception obtaining complete CRLs.", var10);
      }

      HashSet var5 = new HashSet();
      Date var6 = var3;
      if (var2.getDate() != null) {
         var6 = var2.getDate();
      }

      Iterator var7 = var4.iterator();

      while(var7.hasNext()) {
         X509CRL var8 = (X509CRL)var7.next();
         if (var8.getNextUpdate().after(var6)) {
            X509Certificate var9 = var1.getCertificateChecking();
            if (var9 != null) {
               if (var8.getThisUpdate().before(var9.getNotAfter())) {
                  var5.add(var8);
               }
            } else {
               var5.add(var8);
            }
         }
      }

      return var5;
   }

   public Set findCRLs(X509CRLStoreSelector var1, PKIXParameters var2) throws AnnotatedException {
      HashSet var3 = new HashSet();

      try {
         var3.addAll(this.findCRLs(var1, var2.getCertStores()));
         return var3;
      } catch (AnnotatedException var5) {
         throw new AnnotatedException("Exception obtaining complete CRLs.", var5);
      }
   }

   private final Collection findCRLs(X509CRLStoreSelector var1, List var2) throws AnnotatedException {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();
      AnnotatedException var5 = null;
      boolean var6 = false;

      while(var4.hasNext()) {
         Object var7 = var4.next();
         if (var7 instanceof X509Store) {
            X509Store var8 = (X509Store)var7;

            try {
               var3.addAll(var8.getMatches(var1));
               var6 = true;
            } catch (StoreException var10) {
               var5 = new AnnotatedException("Exception searching in X.509 CRL store.", var10);
            }
         } else {
            CertStore var12 = (CertStore)var7;

            try {
               var3.addAll(var12.getCRLs(var1));
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
