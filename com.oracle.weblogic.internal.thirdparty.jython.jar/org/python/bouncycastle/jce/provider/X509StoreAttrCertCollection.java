package org.python.bouncycastle.jce.provider;

import java.util.Collection;
import org.python.bouncycastle.util.CollectionStore;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.x509.X509CollectionStoreParameters;
import org.python.bouncycastle.x509.X509StoreParameters;
import org.python.bouncycastle.x509.X509StoreSpi;

public class X509StoreAttrCertCollection extends X509StoreSpi {
   private CollectionStore _store;

   public void engineInit(X509StoreParameters var1) {
      if (!(var1 instanceof X509CollectionStoreParameters)) {
         throw new IllegalArgumentException(var1.toString());
      } else {
         this._store = new CollectionStore(((X509CollectionStoreParameters)var1).getCollection());
      }
   }

   public Collection engineGetMatches(Selector var1) {
      return this._store.getMatches(var1);
   }
}
