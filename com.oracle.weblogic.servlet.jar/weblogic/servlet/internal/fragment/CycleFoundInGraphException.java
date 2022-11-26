package weblogic.servlet.internal.fragment;

public class CycleFoundInGraphException extends Exception {
   private static final long serialVersionUID = 1L;

   public CycleFoundInGraphException() {
      super("Graph has at least one cycle.");
   }
}
