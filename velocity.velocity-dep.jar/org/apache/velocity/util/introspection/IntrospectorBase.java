package org.apache.velocity.util.introspection;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntrospectorBase {
   protected Map classMethodMaps = new HashMap();
   protected Set cachedClassNames = new HashSet();

   public Method getMethod(Class c, String name, Object[] params) throws Exception {
      if (c == null) {
         throw new Exception("Introspector.getMethod(): Class method key was null: " + name);
      } else {
         ClassMap classMap = null;
         Map var5 = this.classMethodMaps;
         synchronized(var5) {
            classMap = (ClassMap)this.classMethodMaps.get(c);
            if (classMap == null) {
               if (this.cachedClassNames.contains(c.getName())) {
                  this.clearCache();
               }

               classMap = this.createClassMap(c);
            }
         }

         return classMap.findMethod(name, params);
      }
   }

   protected ClassMap createClassMap(Class c) {
      ClassMap classMap = new ClassMap(c);
      this.classMethodMaps.put(c, classMap);
      this.cachedClassNames.add(c.getName());
      return classMap;
   }

   protected void clearCache() {
      this.classMethodMaps.clear();
      this.cachedClassNames = new HashSet();
   }
}
