package weblogic.management.mbeanservers.edit;

public class ActivateConflictException extends EditException {
   private static final long serialVersionUID = 7026407156180614281L;
   private final String[] conflicts;

   public ActivateConflictException(String message, String[] conflicts) {
      super(message);
      this.conflicts = conflicts;
   }

   public ActivateConflictException(Throwable t, String[] conflicts) {
      super(t);
      this.conflicts = conflicts;
   }

   public ActivateConflictException(String message, Throwable t, String[] conflicts) {
      super(message, t);
      this.conflicts = conflicts;
   }

   String[] getConflicts() {
      return this.conflicts;
   }
}
