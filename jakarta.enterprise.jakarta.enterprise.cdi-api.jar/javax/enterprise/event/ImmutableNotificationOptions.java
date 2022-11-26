package javax.enterprise.event;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

class ImmutableNotificationOptions implements NotificationOptions {
   private final Executor executor;
   private final Map options;

   private ImmutableNotificationOptions(Executor executor, Map options) {
      this.executor = executor;
      this.options = new HashMap(options);
   }

   public Executor getExecutor() {
      return this.executor;
   }

   public Object get(String name) {
      return this.options.get(name);
   }

   // $FF: synthetic method
   ImmutableNotificationOptions(Executor x0, Map x1, Object x2) {
      this(x0, x1);
   }

   static class Builder implements NotificationOptions.Builder {
      private Executor executor;
      private Map options = new HashMap();

      public Builder setExecutor(Executor executor) {
         this.executor = executor;
         return this;
      }

      public Builder set(String name, Object value) {
         this.options.put(name, value);
         return this;
      }

      public ImmutableNotificationOptions build() {
         return new ImmutableNotificationOptions(this.executor, this.options);
      }
   }
}
