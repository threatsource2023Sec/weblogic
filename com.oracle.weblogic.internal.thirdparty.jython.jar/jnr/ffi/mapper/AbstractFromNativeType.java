package jnr.ffi.mapper;

public abstract class AbstractFromNativeType implements FromNativeType {
   private final FromNativeConverter converter;

   AbstractFromNativeType(FromNativeConverter converter) {
      this.converter = converter;
   }

   public FromNativeConverter getFromNativeConverter() {
      return this.converter;
   }
}
