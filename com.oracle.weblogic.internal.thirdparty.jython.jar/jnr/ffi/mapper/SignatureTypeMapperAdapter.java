package jnr.ffi.mapper;

public class SignatureTypeMapperAdapter implements SignatureTypeMapper {
   private final TypeMapper typeMapper;

   public SignatureTypeMapperAdapter(TypeMapper typeMapper) {
      this.typeMapper = typeMapper;
   }

   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      return FromNativeTypes.create(this.typeMapper.getFromNativeConverter(type.getDeclaredType()));
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      return ToNativeTypes.create(this.typeMapper.getToNativeConverter(type.getDeclaredType()));
   }
}
