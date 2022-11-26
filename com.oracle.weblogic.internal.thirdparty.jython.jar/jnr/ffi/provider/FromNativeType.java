package jnr.ffi.provider;

import java.util.Collection;
import jnr.ffi.NativeType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;

public class FromNativeType extends SigType implements jnr.ffi.mapper.FromNativeType {
   private final FromNativeConverter fromNativeConverter;
   private final FromNativeContext fromNativeContext;

   public FromNativeType(Class javaType, NativeType nativeType, Collection annotations, FromNativeConverter fromNativeConverter, FromNativeContext fromNativeContext) {
      super(javaType, nativeType, annotations, fromNativeConverter != null ? fromNativeConverter.nativeType() : javaType);
      this.fromNativeConverter = fromNativeConverter;
      this.fromNativeContext = fromNativeContext;
   }

   public FromNativeConverter getFromNativeConverter() {
      return this.fromNativeConverter;
   }

   public FromNativeContext getFromNativeContext() {
      return this.fromNativeContext;
   }
}
