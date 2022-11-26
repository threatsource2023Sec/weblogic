package jnr.ffi.provider;

import jnr.ffi.mapper.AbstractSignatureTypeMapper;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.TypeMapper;

public class NullTypeMapper extends AbstractSignatureTypeMapper implements TypeMapper, SignatureTypeMapper {
   public FromNativeConverter getFromNativeConverter(Class type) {
      return null;
   }

   public ToNativeConverter getToNativeConverter(Class type) {
      return null;
   }

   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      return null;
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      return null;
   }
}
