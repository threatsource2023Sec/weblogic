package jnr.ffi.mapper;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

final class SimpleTypeMapper implements TypeMapper {
   private final Map toNativeConverters;
   private final Map fromNativeConverters;

   public SimpleTypeMapper(Map toNativeConverters, Map fromNativeConverters) {
      this.toNativeConverters = Collections.unmodifiableMap(new IdentityHashMap(toNativeConverters));
      this.fromNativeConverters = Collections.unmodifiableMap(new IdentityHashMap(fromNativeConverters));
   }

   public FromNativeConverter getFromNativeConverter(Class type) {
      return (FromNativeConverter)this.fromNativeConverters.get(type);
   }

   public ToNativeConverter getToNativeConverter(Class type) {
      return (ToNativeConverter)this.toNativeConverters.get(type);
   }
}
