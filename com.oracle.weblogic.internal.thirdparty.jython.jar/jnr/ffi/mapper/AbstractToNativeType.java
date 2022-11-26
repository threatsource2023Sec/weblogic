package jnr.ffi.mapper;

public abstract class AbstractToNativeType implements ToNativeType {
   private final ToNativeConverter converter;

   AbstractToNativeType(ToNativeConverter converter) {
      this.converter = converter;
   }

   public ToNativeConverter getToNativeConverter() {
      return this.converter;
   }
}
