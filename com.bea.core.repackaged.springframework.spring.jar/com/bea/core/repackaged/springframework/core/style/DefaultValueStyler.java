package com.bea.core.repackaged.springframework.core.style;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultValueStyler implements ValueStyler {
   private static final String EMPTY = "[empty]";
   private static final String NULL = "[null]";
   private static final String COLLECTION = "collection";
   private static final String SET = "set";
   private static final String LIST = "list";
   private static final String MAP = "map";
   private static final String ARRAY = "array";

   public String style(@Nullable Object value) {
      if (value == null) {
         return "[null]";
      } else if (value instanceof String) {
         return "'" + value + "'";
      } else if (value instanceof Class) {
         return ClassUtils.getShortName((Class)value);
      } else if (value instanceof Method) {
         Method method = (Method)value;
         return method.getName() + "@" + ClassUtils.getShortName(method.getDeclaringClass());
      } else if (value instanceof Map) {
         return this.style((Map)value);
      } else if (value instanceof Map.Entry) {
         return this.style((Map.Entry)value);
      } else if (value instanceof Collection) {
         return this.style((Collection)value);
      } else {
         return value.getClass().isArray() ? this.styleArray(ObjectUtils.toObjectArray(value)) : String.valueOf(value);
      }
   }

   private String style(Map value) {
      StringBuilder result = new StringBuilder(value.size() * 8 + 16);
      result.append("map[");
      Iterator it = value.entrySet().iterator();

      while(it.hasNext()) {
         Map.Entry entry = (Map.Entry)it.next();
         result.append(this.style(entry));
         if (it.hasNext()) {
            result.append(',').append(' ');
         }
      }

      if (value.isEmpty()) {
         result.append("[empty]");
      }

      result.append("]");
      return result.toString();
   }

   private String style(Map.Entry value) {
      return this.style(value.getKey()) + " -> " + this.style(value.getValue());
   }

   private String style(Collection value) {
      StringBuilder result = new StringBuilder(value.size() * 8 + 16);
      result.append(this.getCollectionTypeString(value)).append('[');
      Iterator i = value.iterator();

      while(i.hasNext()) {
         result.append(this.style(i.next()));
         if (i.hasNext()) {
            result.append(',').append(' ');
         }
      }

      if (value.isEmpty()) {
         result.append("[empty]");
      }

      result.append("]");
      return result.toString();
   }

   private String getCollectionTypeString(Collection value) {
      if (value instanceof List) {
         return "list";
      } else {
         return value instanceof Set ? "set" : "collection";
      }
   }

   private String styleArray(Object[] array) {
      StringBuilder result = new StringBuilder(array.length * 8 + 16);
      result.append("array<").append(ClassUtils.getShortName(array.getClass().getComponentType())).append(">[");

      for(int i = 0; i < array.length - 1; ++i) {
         result.append(this.style(array[i]));
         result.append(',').append(' ');
      }

      if (array.length > 0) {
         result.append(this.style(array[array.length - 1]));
      } else {
         result.append("[empty]");
      }

      result.append("]");
      return result.toString();
   }
}
