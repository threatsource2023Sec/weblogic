package jnr.ffi.provider;

import java.util.Collection;
import jnr.ffi.NativeType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;

public class ResultType extends FromNativeType {
   public ResultType(Class javaType, NativeType nativeType, Collection annotations, FromNativeConverter fromNativeConverter, FromNativeContext fromNativeContext) {
      super(javaType, nativeType, annotations, fromNativeConverter, fromNativeContext);
   }
}
