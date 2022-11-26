package jnr.ffi.provider.jffi;

public class SymbolNotFoundError extends UnsatisfiedLinkError {
   public SymbolNotFoundError(String msg) {
      super(msg);
   }
}
