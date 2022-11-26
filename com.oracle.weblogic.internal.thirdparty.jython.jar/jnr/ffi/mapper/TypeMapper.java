package jnr.ffi.mapper;

import java.util.HashMap;
import java.util.Map;

public interface TypeMapper {
   FromNativeConverter getFromNativeConverter(Class var1);

   ToNativeConverter getToNativeConverter(Class var1);

   public static final class Builder {
      private final Map toNativeConverterMap = new HashMap();
      private final Map fromNativeConverterMap = new HashMap();

      public Builder map(Class javaType, ToNativeConverter toNativeConverter) {
         this.toNativeConverterMap.put(javaType, toNativeConverter);
         return this;
      }

      public Builder map(Class javaType, FromNativeConverter fromNativeConverter) {
         this.fromNativeConverterMap.put(javaType, fromNativeConverter);
         return this;
      }

      public Builder map(Class javaType, DataConverter dataConverter) {
         this.toNativeConverterMap.put(javaType, dataConverter);
         this.fromNativeConverterMap.put(javaType, dataConverter);
         return this;
      }

      public TypeMapper build() {
         return new SimpleTypeMapper(this.toNativeConverterMap, this.fromNativeConverterMap);
      }
   }
}
