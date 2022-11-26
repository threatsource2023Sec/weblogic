package org.python.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.python.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.util.StoreException;
import org.python.bouncycastle.x509.X509CertPairStoreSelector;
import org.python.bouncycastle.x509.X509StoreParameters;
import org.python.bouncycastle.x509.X509StoreSpi;
import org.python.bouncycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCertPairs extends X509StoreSpi {
   private LDAPStoreHelper helper;

   public void engineInit(X509StoreParameters var1) {
      if (!(var1 instanceof X509LDAPCertStoreParameters)) {
         throw new IllegalArgumentException("Initialization parameters must be an instance of " + X509LDAPCertStoreParameters.class.getName() + ".");
      } else {
         this.helper = new LDAPStoreHelper((X509LDAPCertStoreParameters)var1);
      }
   }

   public Collection engineGetMatches(Selector var1) throws StoreException {
      if (!(var1 instanceof X509CertPairStoreSelector)) {
         return Collections.EMPTY_SET;
      } else {
         X509CertPairStoreSelector var2 = (X509CertPairStoreSelector)var1;
         HashSet var3 = new HashSet();
         var3.addAll(this.helper.getCrossCertificatePairs(var2));
         return var3;
      }
   }
}
