package weblogic.utils.classfile;

public class OpNotInMethodException extends RuntimeException {
   public OpNotInMethodException() {
   }

   public OpNotInMethodException(String msg) {
      super(msg);
   }
}
