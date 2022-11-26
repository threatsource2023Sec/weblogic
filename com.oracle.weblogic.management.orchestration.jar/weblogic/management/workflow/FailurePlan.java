package weblogic.management.workflow;

import java.io.Serializable;

public class FailurePlan implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final boolean DEFAULT_SHOULD_RETRY = true;
   public static final boolean DEFAULT_SHOULD_IGNORE = false;
   public static final boolean DEFAULT_SHOULD_REVERT = true;
   public static final int DEFAULT_NUMBER_OF_RETRIES = 2;
   public static final long DEFAULT_RETRY_DELAY_IN_MILLIS = 2000L;
   private boolean shouldRetry;
   private boolean shouldIgnore;
   private boolean shouldRevert;
   private int numberOfRetriesAllowed;
   private long retryDelayInMillis;

   public FailurePlan(boolean shouldRetry, boolean shouldIgnore, boolean shouldRevert, int numberOfRetriesAllowed, long retryDelayInMillis) {
      this.shouldRetry = shouldRetry;
      this.shouldIgnore = shouldIgnore;
      this.shouldRevert = shouldRevert;
      this.numberOfRetriesAllowed = numberOfRetriesAllowed;
      this.retryDelayInMillis = retryDelayInMillis;
   }

   public FailurePlan() {
      this(true, false, true, 2, 2000L);
   }

   public boolean shouldRetry() {
      return this.shouldRetry;
   }

   public void setShouldRetry(boolean shouldRetry) {
      this.shouldRetry = shouldRetry;
   }

   public boolean shouldIgnore() {
      return this.shouldIgnore;
   }

   public void setShouldIgnore(boolean shouldIgnore) {
      this.shouldIgnore = shouldIgnore;
   }

   public boolean shouldRevert() {
      return this.shouldRevert;
   }

   public void setShouldRevert(boolean shouldRevert) {
      this.shouldRevert = shouldRevert;
   }

   public int getNumberOfRetriesAllowed() {
      return this.numberOfRetriesAllowed;
   }

   public void setNumberOfRetriesAllowed(int numberOfRetriesAllowed) {
      this.numberOfRetriesAllowed = numberOfRetriesAllowed;
   }

   public long getRetryDelayInMillis() {
      return this.retryDelayInMillis;
   }

   public void setRetryDelayInMillis(long retryDelayInMillis) {
      this.retryDelayInMillis = retryDelayInMillis;
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("FailurePlan: ");
      if (this.shouldRetry) {
         result.append("retry[").append(this.numberOfRetriesAllowed).append('x');
         result.append(this.retryDelayInMillis).append("ms], ");
      } else {
         result.append("not-retry, ");
      }

      if (this.shouldIgnore) {
         result.append("ignore, ");
      } else {
         result.append("not-ignore, ");
      }

      if (this.shouldRevert) {
         result.append("revert");
      } else {
         result.append("not-revert");
      }

      return result.toString();
   }
}
