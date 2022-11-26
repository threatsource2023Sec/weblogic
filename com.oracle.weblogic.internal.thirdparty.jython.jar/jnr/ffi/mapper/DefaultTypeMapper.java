package jnr.ffi.mapper;

import java.util.LinkedHashMap;
import java.util.Map;

public final class DefaultTypeMapper implements TypeMapper {
   private final Map toNativeConverters = new LinkedHashMap();
   private final Map fromNativeConverters = new LinkedHashMap();

   public final void put(Class javaClass, DataConverter converter) {
      this.toNativeConverters.put(javaClass, converter);
      this.fromNativeConverters.put(javaClass, converter);
   }

   public final void put(Class javaClass, ToNativeConverter converter) {
      this.toNativeConverters.put(javaClass, converter);
   }

   public final void put(Class javaClass, FromNativeConverter converter) {
      this.fromNativeConverters.put(javaClass, converter);
   }

   public FromNativeConverter getFromNativeConverter(Class type) {
      return (FromNativeConverter)this.fromNativeConverters.get(type);
   }

   public ToNativeConverter getToNativeConverter(Class type) {
      return (ToNativeConverter)this.toNativeConverters.get(type);
   }
}
