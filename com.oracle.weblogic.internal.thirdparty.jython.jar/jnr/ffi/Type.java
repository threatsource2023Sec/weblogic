package jnr.ffi;

public abstract class Type {
   public abstract int size();

   public abstract int alignment();

   public abstract NativeType getNativeType();
}
