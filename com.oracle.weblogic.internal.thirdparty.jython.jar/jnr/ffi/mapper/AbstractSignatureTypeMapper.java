package jnr.ffi.mapper;

public abstract class AbstractSignatureTypeMapper implements SignatureTypeMapper {
   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      return null;
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      return null;
   }
}
