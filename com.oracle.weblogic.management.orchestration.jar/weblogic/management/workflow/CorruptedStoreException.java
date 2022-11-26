package weblogic.management.workflow;

public class CorruptedStoreException extends WorkflowException {
   private final String path;

   public CorruptedStoreException(String path) {
      this.path = path;
   }

   public CorruptedStoreException(String msg, String path) {
      super(msg);
      this.path = path;
   }

   public CorruptedStoreException(String msg, String path, Throwable cause) {
      super(msg, cause);
      this.path = path;
   }

   public CorruptedStoreException(Throwable cause, String path) {
      super(cause);
      this.path = path;
   }

   public String toString() {
      StringBuilder result = new StringBuilder(super.toString());
      if (this.path != null) {
         result.append(" (").append(this.path).append(')');
      }

      return result.toString();
   }

   public String getPath() {
      return this.path;
   }
}
