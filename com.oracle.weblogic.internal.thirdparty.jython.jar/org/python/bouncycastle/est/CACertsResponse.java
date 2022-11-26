package org.python.bouncycastle.est;

import org.python.bouncycastle.util.Store;

public class CACertsResponse {
   private final Store store;
   private Store crlHolderStore;
   private final ESTRequest requestToRetry;
   private final Source session;
   private final boolean trusted;

   public CACertsResponse(Store var1, Store var2, ESTRequest var3, Source var4, boolean var5) {
      this.store = var1;
      this.requestToRetry = var3;
      this.session = var4;
      this.trusted = var5;
      this.crlHolderStore = var2;
   }

   public boolean hasCertificates() {
      return this.store != null;
   }

   public Store getCertificateStore() {
      if (this.store == null) {
         throw new IllegalStateException("Response has no certificates.");
      } else {
         return this.store;
      }
   }

   public boolean hasCRLs() {
      return this.crlHolderStore != null;
   }

   public Store getCrlStore() {
      if (this.crlHolderStore == null) {
         throw new IllegalStateException("Response has no CRLs.");
      } else {
         return this.crlHolderStore;
      }
   }

   public ESTRequest getRequestToRetry() {
      return this.requestToRetry;
   }

   public Object getSession() {
      return this.session.getSession();
   }

   public boolean isTrusted() {
      return this.trusted;
   }
}
