package jnr.ffi.util;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeConverter;

@ToNativeConverter.NoContext
@FromNativeConverter.NoContext
public final class EnumMapper {
   private final Class enumClass;
   private final Integer[] intValues;
   private final Long[] longValues;
   private final Map reverseLookupMap = new HashMap();

   private EnumMapper(Class enumClass) {
      this.enumClass = enumClass;
      EnumSet enums = EnumSet.allOf(enumClass);
      this.intValues = new Integer[enums.size()];
      this.longValues = new Long[enums.size()];
      Method intValueMethod = getNumberValueMethod(enumClass, Integer.TYPE);
      Method longValueMethod = getNumberValueMethod(enumClass, Long.TYPE);
      Iterator var5 = enums.iterator();

      while(var5.hasNext()) {
         Enum e = (Enum)var5.next();
         Object value;
         if (longValueMethod != null) {
            value = reflectedNumberValue(e, longValueMethod);
         } else if (intValueMethod != null) {
            value = reflectedNumberValue(e, intValueMethod);
         } else {
            value = e.ordinal();
         }

         this.intValues[e.ordinal()] = ((Number)value).intValue();
         this.longValues[e.ordinal()] = ((Number)value).longValue();
         this.reverseLookupMap.put(value, e);
      }

   }

   public static EnumMapper getInstance(Class enumClass) {
      EnumMapper mapper = (EnumMapper)EnumMapper.StaticDataHolder.MAPPERS.get(enumClass);
      return mapper != null ? mapper : addMapper(enumClass);
   }

   private static synchronized EnumMapper addMapper(Class enumClass) {
      EnumMapper mapper = new EnumMapper(enumClass);
      Map tmp = new IdentityHashMap(EnumMapper.StaticDataHolder.MAPPERS);
      tmp.put(enumClass, mapper);
      EnumMapper.StaticDataHolder.MAPPERS = tmp;
      return mapper;
   }

   private static Method getNumberValueMethod(Class c, Class numberClass) {
      try {
         Method m = c.getDeclaredMethod(numberClass.getSimpleName() + "Value");
         return m != null && numberClass == m.getReturnType() ? m : null;
      } catch (Throwable var3) {
         return null;
      }
   }

   private static Number reflectedNumberValue(Enum e, Method m) {
      try {
         return (Number)m.invoke(e);
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   public final Integer integerValue(Enum value) {
      if (value.getClass() != this.enumClass) {
         throw new IllegalArgumentException("enum class mismatch, " + value.getClass());
      } else {
         return this.intValues[value.ordinal()];
      }
   }

   public final int intValue(Enum value) {
      return this.integerValue(value);
   }

   public final Long longValue(Enum value) {
      if (value.getClass() != this.enumClass) {
         throw new IllegalArgumentException("enum class mismatch, " + value.getClass());
      } else {
         return this.longValues[value.ordinal()];
      }
   }

   public Enum valueOf(int value) {
      return this.reverseLookup(value);
   }

   public Enum valueOf(long value) {
      return this.reverseLookup(value);
   }

   public Enum valueOf(Number value) {
      return this.reverseLookup(value);
   }

   private Enum reverseLookup(Number value) {
      Enum e = (Enum)this.reverseLookupMap.get(value);
      return e != null ? e : this.badValue(value);
   }

   private Enum badValue(Number value) {
      try {
         return Enum.valueOf(this.enumClass, "__UNKNOWN_NATIVE_VALUE");
      } catch (IllegalArgumentException var3) {
         throw new IllegalArgumentException("No known Enum mapping for value " + value + " of type " + this.enumClass.getName());
      }
   }

   public interface IntegerEnum {
      int intValue();
   }

   private static final class StaticDataHolder {
      private static volatile Map MAPPERS = Collections.emptyMap();
   }
}
