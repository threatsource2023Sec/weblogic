package weblogic.ejb.container.dd;

public final class DDDefaults {
   public static short getTransactionAttribute(boolean isEJB30) {
      return (short)(isEJB30 ? 1 : 2);
   }

   public static short getBeanMethodTransactionAttribute(boolean isEJB30) {
      return (short)(isEJB30 ? 1 : 0);
   }

   public static boolean getBeanIsClusterable() {
      return true;
   }

   public static int getMaxBeansInCache() {
      return 1000;
   }

   public static int getMaxBeansInFreePool() {
      return 1000;
   }

   public static int getIdleTimeoutSeconds() {
      return 600;
   }

   public static boolean getDelayUpdatesUntilEndOfTx() {
      return true;
   }
}
