package weblogic.workarea;

public class PropertyReadOnlyException extends Exception {
   public PropertyReadOnlyException() {
   }

   public PropertyReadOnlyException(String msg) {
      super(msg);
   }
}
