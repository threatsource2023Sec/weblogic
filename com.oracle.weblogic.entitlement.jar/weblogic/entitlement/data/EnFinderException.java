package weblogic.entitlement.data;

public class EnFinderException extends Exception {
   public EnFinderException() {
   }

   public EnFinderException(String message) {
      super(message);
   }

   public int getTargetIndex() {
      return -1;
   }
}
