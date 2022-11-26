package weblogic.common;

public class ParamSetException extends Exception {
   public ParamSetException(String s) {
      super("[" + s + "]");
   }

   public ParamSetException(String s, boolean noop) {
      super(s);
   }
}
