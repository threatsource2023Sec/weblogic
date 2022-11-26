package org.apache.xmlbeans.impl.store;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class QueryDelegate {
   private static HashMap _constructors = new HashMap();

   private QueryDelegate() {
   }

   private static synchronized void init(String implClassName) {
      if (implClassName == null) {
         implClassName = "org.apache.xmlbeans.impl.xquery.saxon.XBeansXQuery";
      }

      Class queryInterfaceImpl = null;
      boolean engineAvailable = true;

      try {
         queryInterfaceImpl = Class.forName(implClassName);
      } catch (ClassNotFoundException var5) {
         engineAvailable = false;
      } catch (NoClassDefFoundError var6) {
         engineAvailable = false;
      }

      if (engineAvailable) {
         try {
            Constructor constructor = queryInterfaceImpl.getConstructor(String.class, String.class, Integer.class);
            _constructors.put(implClassName, constructor);
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

   }

   public static synchronized QueryInterface createInstance(String implClassName, String query, String contextVar, int boundary) {
      if (_constructors.get(implClassName) == null) {
         init(implClassName);
      }

      if (_constructors.get(implClassName) == null) {
         return null;
      } else {
         Constructor constructor = (Constructor)_constructors.get(implClassName);

         try {
            return (QueryInterface)constructor.newInstance(query, contextVar, new Integer(boundary));
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }
      }
   }

   public interface QueryInterface {
      List execQuery(Object var1, Map var2);
   }
}
