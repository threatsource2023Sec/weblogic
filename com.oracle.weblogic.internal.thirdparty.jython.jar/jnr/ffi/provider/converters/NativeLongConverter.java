package jnr.ffi.provider.converters;

import jnr.ffi.NativeLong;
import jnr.ffi.mapper.AbstractDataConverter;
import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;

@ToNativeConverter.NoContext
@FromNativeConverter.NoContext
@ToNativeConverter.Cacheable
@FromNativeConverter.Cacheable
public final class NativeLongConverter extends AbstractDataConverter {
   private static final DataConverter INSTANCE = new NativeLongConverter();

   public static DataConverter getInstance() {
      return INSTANCE;
   }

   public Class nativeType() {
      return Long.class;
   }

   public Long toNative(NativeLong value, ToNativeContext toNativeContext) {
      return value.longValue();
   }

   public NativeLong fromNative(Long value, FromNativeContext fromNativeContext) {
      return NativeLong.valueOf(value);
   }
}
