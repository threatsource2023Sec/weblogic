package org.python.bouncycastle.est;

import org.python.bouncycastle.util.Store;

public class EnrollmentResponse {
   private final Store store;
   private final long notBefore;
   private final ESTRequest requestToRetry;
   private final Source source;

   public EnrollmentResponse(Store var1, long var2, ESTRequest var4, Source var5) {
      this.store = var1;
      this.notBefore = var2;
      this.requestToRetry = var4;
      this.source = var5;
   }

   public boolean canRetry() {
      return this.notBefore < System.currentTimeMillis();
   }

   public Store getStore() {
      return this.store;
   }

   public long getNotBefore() {
      return this.notBefore;
   }

   public ESTRequest getRequestToRetry() {
      return this.requestToRetry;
   }

   public Object getSession() {
      return this.source.getSession();
   }

   public Source getSource() {
      return this.source;
   }

   public boolean isCompleted() {
      return this.requestToRetry == null;
   }
}
