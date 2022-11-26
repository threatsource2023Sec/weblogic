package jnr.ffi.provider.converters;

import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.util.EnumMapper;

@ToNativeConverter.NoContext
@FromNativeConverter.NoContext
@ToNativeConverter.Cacheable
@FromNativeConverter.Cacheable
public final class EnumConverter implements DataConverter {
   private final EnumMapper mapper;

   public static EnumConverter getInstance(Class enumClass) {
      return new EnumConverter(enumClass);
   }

   private EnumConverter(Class enumClass) {
      this.mapper = EnumMapper.getInstance(enumClass);
   }

   public Enum fromNative(Integer nativeValue, FromNativeContext context) {
      return this.mapper.valueOf(nativeValue);
   }

   public Integer toNative(Enum value, ToNativeContext context) {
      return this.mapper.integerValue(value);
   }

   public Class nativeType() {
      return Integer.class;
   }
}
