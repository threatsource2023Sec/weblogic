package weblogic.management;

public final class MBeanCreationException extends ManagementException {
   private static final long serialVersionUID = 7229085686697923303L;

   public MBeanCreationException(String message) {
      super(message);
   }

   public MBeanCreationException(Throwable t) {
      super(t);
   }

   public MBeanCreationException(String message, Throwable t) {
      super(message, t);
   }
}
