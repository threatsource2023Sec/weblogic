package jnr.ffi.provider.converters;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.util.EnumMapper;

@FromNativeConverter.Cacheable
@ToNativeConverter.Cacheable
public final class EnumSetConverter implements DataConverter {
   private final Class enumClass;
   private final EnumMapper enumMapper;
   private final EnumSet allValues;

   private EnumSetConverter(Class enumClass) {
      this.enumClass = enumClass;
      this.enumMapper = EnumMapper.getInstance(enumClass);
      this.allValues = EnumSet.allOf(enumClass);
   }

   public static ToNativeConverter getToNativeConverter(SignatureType type, ToNativeContext toNativeContext) {
      return getInstance(type.getGenericType());
   }

   public static FromNativeConverter getFromNativeConverter(SignatureType type, FromNativeContext fromNativeContext) {
      return getInstance(type.getGenericType());
   }

   private static EnumSetConverter getInstance(Type parameterizedType) {
      if (!(parameterizedType instanceof ParameterizedType)) {
         return null;
      } else if (((ParameterizedType)parameterizedType).getActualTypeArguments().length < 1) {
         return null;
      } else {
         Type enumType = ((ParameterizedType)parameterizedType).getActualTypeArguments()[0];
         return enumType instanceof Class && Enum.class.isAssignableFrom((Class)enumType) ? new EnumSetConverter(((Class)enumType).asSubclass(Enum.class)) : null;
      }
   }

   public Set fromNative(Integer nativeValue, FromNativeContext context) {
      EnumSet enums = EnumSet.noneOf(this.enumClass);
      Iterator var4 = this.allValues.iterator();

      while(var4.hasNext()) {
         Enum e = (Enum)var4.next();
         int enumValue = this.enumMapper.intValue(e);
         if ((nativeValue & enumValue) == enumValue) {
            enums.add(e);
         }
      }

      return enums;
   }

   public Integer toNative(Set value, ToNativeContext context) {
      int intValue = 0;

      Enum e;
      for(Iterator var4 = value.iterator(); var4.hasNext(); intValue |= this.enumMapper.intValue(e)) {
         e = (Enum)var4.next();
      }

      return intValue;
   }

   public Class nativeType() {
      return Integer.class;
   }
}
