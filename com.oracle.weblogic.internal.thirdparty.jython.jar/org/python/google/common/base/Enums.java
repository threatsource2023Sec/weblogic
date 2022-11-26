package org.python.google.common.base;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;

@GwtCompatible(
   emulated = true
)
public final class Enums {
   @GwtIncompatible
   private static final Map enumConstantCache = new WeakHashMap();

   private Enums() {
   }

   @GwtIncompatible
   public static Field getField(Enum enumValue) {
      Class clazz = enumValue.getDeclaringClass();

      try {
         return clazz.getDeclaredField(enumValue.name());
      } catch (NoSuchFieldException var3) {
         throw new AssertionError(var3);
      }
   }

   public static Optional getIfPresent(Class enumClass, String value) {
      Preconditions.checkNotNull(enumClass);
      Preconditions.checkNotNull(value);
      return Platform.getEnumIfPresent(enumClass, value);
   }

   @GwtIncompatible
   private static Map populateCache(Class enumClass) {
      Map result = new HashMap();
      Iterator var2 = EnumSet.allOf(enumClass).iterator();

      while(var2.hasNext()) {
         Enum enumInstance = (Enum)var2.next();
         result.put(enumInstance.name(), new WeakReference(enumInstance));
      }

      enumConstantCache.put(enumClass, result);
      return result;
   }

   @GwtIncompatible
   static Map getEnumConstants(Class enumClass) {
      synchronized(enumConstantCache) {
         Map constants = (Map)enumConstantCache.get(enumClass);
         if (constants == null) {
            constants = populateCache(enumClass);
         }

         return constants;
      }
   }

   public static Converter stringConverter(Class enumClass) {
      return new StringConverter(enumClass);
   }

   private static final class StringConverter extends Converter implements Serializable {
      private final Class enumClass;
      private static final long serialVersionUID = 0L;

      StringConverter(Class enumClass) {
         this.enumClass = (Class)Preconditions.checkNotNull(enumClass);
      }

      protected Enum doForward(String value) {
         return Enum.valueOf(this.enumClass, value);
      }

      protected String doBackward(Enum enumValue) {
         return enumValue.name();
      }

      public boolean equals(@Nullable Object object) {
         if (object instanceof StringConverter) {
            StringConverter that = (StringConverter)object;
            return this.enumClass.equals(that.enumClass);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.enumClass.hashCode();
      }

      public String toString() {
         return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
      }
   }
}
