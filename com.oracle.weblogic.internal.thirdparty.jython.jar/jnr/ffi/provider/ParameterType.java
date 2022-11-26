package jnr.ffi.provider;

import java.util.Collection;
import jnr.ffi.NativeType;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;

public class ParameterType extends ToNativeType {
   public ParameterType(Class javaType, NativeType nativeType, Collection annotations, ToNativeConverter toNativeConverter, ToNativeContext toNativeContext) {
      super(javaType, nativeType, annotations, toNativeConverter, toNativeContext);
   }
}
