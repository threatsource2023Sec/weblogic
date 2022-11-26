package weblogic.ejb.container.compliance;

public final class Log {
   private static final Log INSTANCE = new Log();

   public static Log getInstance() {
      return INSTANCE;
   }

   private Log() {
   }

   public void logWarning(String text) {
      System.err.println(EJBComplianceTextFormatter.getInstance().warning() + " " + text);
   }

   public void logInfo(String text) {
      System.err.println(text);
   }
}
