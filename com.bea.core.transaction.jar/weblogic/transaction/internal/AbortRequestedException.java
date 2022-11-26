package weblogic.transaction.internal;

public final class AbortRequestedException extends Exception {
   private static final long serialVersionUID = -7708808279895787952L;
   private static final boolean PRINTSTACK = Boolean.getBoolean("weblogic.transaction.internal.AbortRequestedException.printStack");

   public AbortRequestedException() {
      if (PRINTSTACK) {
         (new Throwable("AbortRequestedException no arg constructor")).printStackTrace();
      }

   }

   public AbortRequestedException(String msg) {
      super(msg);
      if (PRINTSTACK) {
         (new Throwable("AbortRequestedException constructor msg:" + msg)).printStackTrace();
      }

   }
}
