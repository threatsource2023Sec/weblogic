package javolution;

public final class JavolutionError extends Error {
   private static final long serialVersionUID = 1L;

   public JavolutionError(String var1) {
      super(var1);
   }

   public JavolutionError(String var1, Throwable var2) {
      super(var1);
      var2.printStackTrace();
   }

   public JavolutionError(Throwable var1) {
      var1.printStackTrace();
   }
}
