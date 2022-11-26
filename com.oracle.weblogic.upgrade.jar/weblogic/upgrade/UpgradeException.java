package weblogic.upgrade;

public class UpgradeException extends Exception {
   public UpgradeException(String message) {
      super(message);
   }

   public UpgradeException(Throwable t) {
      super(t);
   }

   public UpgradeException(String message, Throwable t) {
      super(message, t);
   }
}
