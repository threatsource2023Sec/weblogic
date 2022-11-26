package weblogic.transaction.internal;

public abstract class NotificationBroadcasterManager {
   private static NotificationBroadcasterManager singleton = new NotificationBroadcasterImpl();

   public static NotificationBroadcasterManager getInstance() {
      return singleton;
   }

   public abstract void setSubCoordinator(SubCoordinator var1);

   public abstract void xaResourceRegistrationChange(String[] var1, String[] var2);

   public abstract void nonXAResourceRegistrationChange(String[] var1, String[] var2);

   public abstract void addNotificationListener(NotificationListener var1, Object var2) throws IllegalArgumentException;

   public abstract void removeNotificationListener(NotificationListener var1) throws ListenerNotFoundException;

   public abstract void startTimer();

   public abstract void stopTimer();
}
