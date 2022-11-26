package weblogic.rmi.cluster;

public class RetryHandler {
   private int retryCount;
   private boolean isListRefreshed;
   private int maxRetryCount;
   private boolean staleListRevivingAttempted = false;

   public int getRetryCount() {
      return this.retryCount;
   }

   public void setRetryCount(int retryCount) {
      this.retryCount = retryCount;
   }

   public boolean isListRefreshed() {
      return this.isListRefreshed;
   }

   public void setIsListRefreshed(boolean isListRefreshed) {
      this.isListRefreshed = isListRefreshed;
   }

   public boolean updateIsListRefreshed(boolean isListRefreshed) {
      this.isListRefreshed = isListRefreshed;
      return isListRefreshed;
   }

   public int getMaxRetryCount() {
      return this.maxRetryCount;
   }

   public void setMaxRetryCount(int maxRetryCount) {
      this.maxRetryCount = maxRetryCount;
   }

   public boolean isListExhausted() {
      return this.isListRefreshed && this.retryCount > this.maxRetryCount;
   }

   public boolean isStaleListRevivingAttempted() {
      return this.staleListRevivingAttempted;
   }

   public void setStaleListRevivingAttempted() {
      this.staleListRevivingAttempted = true;
   }
}
