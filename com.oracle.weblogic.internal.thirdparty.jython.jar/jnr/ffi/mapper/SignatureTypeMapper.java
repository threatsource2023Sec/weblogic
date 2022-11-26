package jnr.ffi.mapper;

public interface SignatureTypeMapper {
   FromNativeType getFromNativeType(SignatureType var1, FromNativeContext var2);

   ToNativeType getToNativeType(SignatureType var1, ToNativeContext var2);
}
