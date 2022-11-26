package javax.enterprise.event;

import java.util.concurrent.Executor;

public interface NotificationOptions {
   Executor getExecutor();

   Object get(String var1);

   static NotificationOptions ofExecutor(Executor executor) {
      return builder().setExecutor(executor).build();
   }

   static NotificationOptions of(String optionName, Object optionValue) {
      return builder().set(optionName, optionValue).build();
   }

   static Builder builder() {
      return new ImmutableNotificationOptions.Builder();
   }

   public interface Builder {
      Builder setExecutor(Executor var1);

      Builder set(String var1, Object var2);

      NotificationOptions build();
   }
}
