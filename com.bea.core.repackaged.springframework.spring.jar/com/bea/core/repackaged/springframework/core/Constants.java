package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class Constants {
   private final String className;
   private final Map fieldCache = new HashMap();

   public Constants(Class clazz) {
      Assert.notNull(clazz, (String)"Class must not be null");
      this.className = clazz.getName();
      Field[] fields = clazz.getFields();
      Field[] var3 = fields;
      int var4 = fields.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Field field = var3[var5];
         if (ReflectionUtils.isPublicStaticFinal(field)) {
            String name = field.getName();

            try {
               Object value = field.get((Object)null);
               this.fieldCache.put(name, value);
            } catch (IllegalAccessException var9) {
            }
         }
      }

   }

   public final String getClassName() {
      return this.className;
   }

   public final int getSize() {
      return this.fieldCache.size();
   }

   protected final Map getFieldCache() {
      return this.fieldCache;
   }

   public Number asNumber(String code) throws ConstantException {
      Object obj = this.asObject(code);
      if (!(obj instanceof Number)) {
         throw new ConstantException(this.className, code, "not a Number");
      } else {
         return (Number)obj;
      }
   }

   public String asString(String code) throws ConstantException {
      return this.asObject(code).toString();
   }

   public Object asObject(String code) throws ConstantException {
      Assert.notNull(code, (String)"Code must not be null");
      String codeToUse = code.toUpperCase(Locale.ENGLISH);
      Object val = this.fieldCache.get(codeToUse);
      if (val == null) {
         throw new ConstantException(this.className, codeToUse, "not found");
      } else {
         return val;
      }
   }

   public Set getNames(@Nullable String namePrefix) {
      String prefixToUse = namePrefix != null ? namePrefix.trim().toUpperCase(Locale.ENGLISH) : "";
      Set names = new HashSet();
      Iterator var4 = this.fieldCache.keySet().iterator();

      while(var4.hasNext()) {
         String code = (String)var4.next();
         if (code.startsWith(prefixToUse)) {
            names.add(code);
         }
      }

      return names;
   }

   public Set getNamesForProperty(String propertyName) {
      return this.getNames(this.propertyToConstantNamePrefix(propertyName));
   }

   public Set getNamesForSuffix(@Nullable String nameSuffix) {
      String suffixToUse = nameSuffix != null ? nameSuffix.trim().toUpperCase(Locale.ENGLISH) : "";
      Set names = new HashSet();
      Iterator var4 = this.fieldCache.keySet().iterator();

      while(var4.hasNext()) {
         String code = (String)var4.next();
         if (code.endsWith(suffixToUse)) {
            names.add(code);
         }
      }

      return names;
   }

   public Set getValues(@Nullable String namePrefix) {
      String prefixToUse = namePrefix != null ? namePrefix.trim().toUpperCase(Locale.ENGLISH) : "";
      Set values = new HashSet();
      this.fieldCache.forEach((code, value) -> {
         if (code.startsWith(prefixToUse)) {
            values.add(value);
         }

      });
      return values;
   }

   public Set getValuesForProperty(String propertyName) {
      return this.getValues(this.propertyToConstantNamePrefix(propertyName));
   }

   public Set getValuesForSuffix(@Nullable String nameSuffix) {
      String suffixToUse = nameSuffix != null ? nameSuffix.trim().toUpperCase(Locale.ENGLISH) : "";
      Set values = new HashSet();
      this.fieldCache.forEach((code, value) -> {
         if (code.endsWith(suffixToUse)) {
            values.add(value);
         }

      });
      return values;
   }

   public String toCode(Object value, @Nullable String namePrefix) throws ConstantException {
      String prefixToUse = namePrefix != null ? namePrefix.trim().toUpperCase(Locale.ENGLISH) : "";
      Iterator var4 = this.fieldCache.entrySet().iterator();

      Map.Entry entry;
      do {
         if (!var4.hasNext()) {
            throw new ConstantException(this.className, prefixToUse, value);
         }

         entry = (Map.Entry)var4.next();
      } while(!((String)entry.getKey()).startsWith(prefixToUse) || !entry.getValue().equals(value));

      return (String)entry.getKey();
   }

   public String toCodeForProperty(Object value, String propertyName) throws ConstantException {
      return this.toCode(value, this.propertyToConstantNamePrefix(propertyName));
   }

   public String toCodeForSuffix(Object value, @Nullable String nameSuffix) throws ConstantException {
      String suffixToUse = nameSuffix != null ? nameSuffix.trim().toUpperCase(Locale.ENGLISH) : "";
      Iterator var4 = this.fieldCache.entrySet().iterator();

      Map.Entry entry;
      do {
         if (!var4.hasNext()) {
            throw new ConstantException(this.className, suffixToUse, value);
         }

         entry = (Map.Entry)var4.next();
      } while(!((String)entry.getKey()).endsWith(suffixToUse) || !entry.getValue().equals(value));

      return (String)entry.getKey();
   }

   public String propertyToConstantNamePrefix(String propertyName) {
      StringBuilder parsedPrefix = new StringBuilder();

      for(int i = 0; i < propertyName.length(); ++i) {
         char c = propertyName.charAt(i);
         if (Character.isUpperCase(c)) {
            parsedPrefix.append("_");
            parsedPrefix.append(c);
         } else {
            parsedPrefix.append(Character.toUpperCase(c));
         }
      }

      return parsedPrefix.toString();
   }

   public static class ConstantException extends IllegalArgumentException {
      public ConstantException(String className, String field, String message) {
         super("Field '" + field + "' " + message + " in class [" + className + "]");
      }

      public ConstantException(String className, String namePrefix, Object value) {
         super("No '" + namePrefix + "' field with value '" + value + "' found in class [" + className + "]");
      }
   }
}
