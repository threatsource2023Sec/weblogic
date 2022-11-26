package org.jboss.weld.util;

import java.util.HashMap;
import java.util.Map;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.reflection.Reflections;

public final class Primitives {
   private static final String ARG_TYPE = "type";
   private static final Map PRIMITIVE_TO_WRAPPER;
   private static final Map WRAPPER_TO_PRIMITIVE;

   private Primitives() {
   }

   private static void put(Map primitiveToWrapper, Map wrapperToPrimitive, Class primitive, Class wrapper) {
      primitiveToWrapper.put(primitive, wrapper);
      wrapperToPrimitive.put(wrapper, primitive);
   }

   public static Class wrap(Class type) {
      Preconditions.checkArgumentNotNull(type, "type");
      Class wrapped = (Class)Reflections.cast(PRIMITIVE_TO_WRAPPER.get(type));
      return wrapped != null ? wrapped : type;
   }

   public static Class unwrap(Class type) {
      Preconditions.checkArgumentNotNull(type, "type");
      Class primitive = (Class)Reflections.cast(WRAPPER_TO_PRIMITIVE.get(type));
      return primitive != null ? primitive : type;
   }

   static {
      Map primitiveToWrapper = new HashMap();
      Map wrapperToPrimitive = new HashMap();
      put(primitiveToWrapper, wrapperToPrimitive, Boolean.TYPE, Boolean.class);
      put(primitiveToWrapper, wrapperToPrimitive, Character.TYPE, Character.class);
      put(primitiveToWrapper, wrapperToPrimitive, Short.TYPE, Short.class);
      put(primitiveToWrapper, wrapperToPrimitive, Integer.TYPE, Integer.class);
      put(primitiveToWrapper, wrapperToPrimitive, Long.TYPE, Long.class);
      put(primitiveToWrapper, wrapperToPrimitive, Double.TYPE, Double.class);
      put(primitiveToWrapper, wrapperToPrimitive, Float.TYPE, Float.class);
      put(primitiveToWrapper, wrapperToPrimitive, Byte.TYPE, Byte.class);
      PRIMITIVE_TO_WRAPPER = ImmutableMap.copyOf(primitiveToWrapper);
      WRAPPER_TO_PRIMITIVE = ImmutableMap.copyOf(wrapperToPrimitive);
   }
}
