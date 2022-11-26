package weblogic.utils.reflect;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class DynamicProxyUtils {
   public static Class[] getAllInterfaces(Class source, Class first) {
      List interfaces = new ArrayList();
      Enumeration ai = ReflectUtils.allInterfaces(source);

      while(ai.hasMoreElements()) {
         interfaces.add(ai.nextElement());
      }

      interfaces.remove(first);
      interfaces.add(0, first);
      return (Class[])((Class[])interfaces.toArray(new Class[interfaces.size()]));
   }
}
