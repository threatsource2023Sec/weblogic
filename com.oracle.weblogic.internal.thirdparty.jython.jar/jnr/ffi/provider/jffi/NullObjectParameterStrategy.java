package jnr.ffi.provider.jffi;

public final class NullObjectParameterStrategy extends ParameterStrategy {
   public static final ParameterStrategy NULL = new NullObjectParameterStrategy();

   public NullObjectParameterStrategy() {
      super(DIRECT);
   }

   public long address(Object parameter) {
      return 0L;
   }

   public Object object(Object parameter) {
      throw new NullPointerException("null reference");
   }

   public int offset(Object parameter) {
      throw new NullPointerException("null reference");
   }

   public int length(Object parameter) {
      throw new NullPointerException("null reference");
   }
}
