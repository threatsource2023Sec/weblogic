package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;

public class Marker {
   public static final Marker INSTANCE = new Marker();

   private Marker() {
   }

   public static boolean isMarker(int position, Method method, Object[] args) {
      return method.getParameterTypes().length > position && method.getParameterTypes()[position].equals(Marker.class) && INSTANCE.equals(args[position]);
   }
}
