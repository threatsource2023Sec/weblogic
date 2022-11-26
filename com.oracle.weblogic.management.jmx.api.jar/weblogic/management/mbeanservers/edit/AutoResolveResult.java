package weblogic.management.mbeanservers.edit;

import java.io.Serializable;

public class AutoResolveResult implements Serializable {
   private static final long serialVersionUID = 0L;
   private final long timestamp;
   private final Throwable throwable;

   public AutoResolveResult(long timestamp, Throwable throwable) {
      this.timestamp = timestamp;
      this.throwable = throwable;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public Throwable getThrowable() {
      return this.throwable;
   }
}
