package weblogic.i18n.tools;

public final class GenException extends Exception {
   private static final long serialVersionUID = -3640338842941108144L;

   public GenException() {
      super("");
   }

   public GenException(String s) {
      super("Code gen exception: " + s);
   }
}
