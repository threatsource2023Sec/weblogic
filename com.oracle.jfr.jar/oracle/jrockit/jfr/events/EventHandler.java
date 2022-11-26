package oracle.jrockit.jfr.events;

import java.net.URI;

public abstract class EventHandler implements EventControl {
   protected final JavaEventDescriptor descriptor;

   public EventHandler(JavaEventDescriptor descriptor) {
      this.descriptor = descriptor;
   }

   public abstract void write(Object var1, long var2, long var4);

   public final int getId() {
      return this.descriptor.getId();
   }

   public final String getName() {
      return this.descriptor.getName();
   }

   public final String getDescription() {
      return this.descriptor.getDescription();
   }

   public final JavaEventDescriptor getDescriptor() {
      return this.descriptor;
   }

   public final boolean isRequestable() {
      return this.descriptor.isRequestable();
   }

   public final boolean isTimed() {
      return this.descriptor.isTimed();
   }

   public boolean hasStartTime() {
      return this.descriptor.hasStartTime();
   }

   public String getPath() {
      return this.descriptor.getPath();
   }

   public URI getURI() {
      return this.descriptor.getURI();
   }

   public boolean hasStackTrace() {
      return this.descriptor.hasStackTrace();
   }

   public boolean hasThread() {
      return this.descriptor.hasThread();
   }

   public abstract long counterTime();

   public String toString() {
      StringBuilder buf = new StringBuilder();
      this.descriptor.describe(buf);
      if (this.isEnabled()) {
         buf.append(", enabled");
      }

      if (this.hasStackTrace() && this.isStackTraceEnabled()) {
         buf.append(", stacktrace");
      }

      if (this.isTimed()) {
         buf.append(", threshold=").append(this.getThresholdTicks()).append("ns");
      }

      if (this.isRequestable()) {
         buf.append(", period=").append(this.getPeriod()).append("ms");
      }

      buf.append(" }");
      return buf.toString();
   }
}
