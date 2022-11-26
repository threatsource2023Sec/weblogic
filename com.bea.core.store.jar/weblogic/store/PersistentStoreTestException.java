package weblogic.store;

import java.util.Date;
import weblogic.logging.Loggable;

public class PersistentStoreTestException extends PersistentStoreException {
   private static final long serialVersionUID = -3161742376448203531L;
   private static final int FAIL_AS_FATAL = 1;
   private static final int FAIL_ON_SAVE = 2;
   private static final int FAIL_ON_BOOT = 4;
   private int failureFlags = 2;
   private int bootFailureCount = 0;
   private Date bootFailureUntilDate = null;

   public PersistentStoreTestException(Loggable message) {
      super(message.getMessage());
   }

   public PersistentStoreTestException(Loggable message, Throwable cause) {
      super(message.getMessage(), cause);
   }

   public PersistentStoreTestException(Throwable cause) {
      super(cause);
   }

   public PersistentStoreTestException(String text) {
      super(text);
   }

   public PersistentStoreTestException(String text, Throwable cause) {
      super(text, cause);
   }

   public void setFatalFailure() {
      this.failureFlags |= 1;
   }

   public void clearFatalFailure() {
      this.failureFlags &= -2;
   }

   public boolean isFatalFailure() {
      return (this.failureFlags & 1) != 0;
   }

   public void setFailOnFlush() {
      this.failureFlags |= 2;
   }

   public void clearFailOnFlush() {
      this.failureFlags &= -3;
   }

   public boolean shouldFailOnFlush() {
      return (this.failureFlags & 2) != 0;
   }

   public void clearFailOnBoot() {
      this.failureFlags &= -5;
      this.bootFailureCount = 0;
      this.bootFailureUntilDate = null;
   }

   public boolean shouldFailOnBoot() {
      return (this.failureFlags & 4) != 0;
   }

   public void setBootFailureCount(int bootFailCount) {
      if (bootFailCount >= 1) {
         this.failureFlags |= 4;
      } else if (this.bootFailureUntilDate == null && (this.failureFlags & 4) != 0) {
         this.failureFlags &= -5;
      }

      this.bootFailureCount = bootFailCount;
   }

   public int getBootFailureCount() {
      return this.bootFailureCount;
   }

   public void setBootFailureUntil(Date failUntil) {
      if (failUntil != null) {
         this.failureFlags |= 4;
      } else if (this.bootFailureCount <= 0 && (this.failureFlags & 4) != 0) {
         this.failureFlags &= -5;
      }

      this.bootFailureUntilDate = failUntil;
   }

   public Date getBootFailureUntil() {
      return this.bootFailureUntilDate;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + this.bootFailureCount;
      result = 31 * result + (this.bootFailureUntilDate == null ? 0 : this.bootFailureUntilDate.hashCode());
      result = 31 * result + this.failureFlags;
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof PersistentStoreTestException)) {
         return false;
      } else {
         PersistentStoreTestException other = (PersistentStoreTestException)obj;
         if (this.bootFailureCount != other.bootFailureCount) {
            return false;
         } else {
            if (this.bootFailureUntilDate == null) {
               if (other.bootFailureUntilDate != null) {
                  return false;
               }
            } else if (!this.bootFailureUntilDate.equals(other.bootFailureUntilDate)) {
               return false;
            }

            return this.failureFlags == other.failureFlags;
         }
      }
   }

   public String toString() {
      String msg = this.getMessage();
      return "PersistentStoreTestException [msg=" + msg + ", failureFlags=" + this.failureFlags + ", bootFailureCount=" + this.bootFailureCount + ", bootFailureUntilDate=" + this.bootFailureUntilDate + "]";
   }
}
