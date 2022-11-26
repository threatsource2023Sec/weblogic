package com.bea.core.repackaged.springframework.core;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PrioritizedParameterNameDiscoverer implements ParameterNameDiscoverer {
   private final List parameterNameDiscoverers = new LinkedList();

   public void addDiscoverer(ParameterNameDiscoverer pnd) {
      this.parameterNameDiscoverers.add(pnd);
   }

   @Nullable
   public String[] getParameterNames(Method method) {
      Iterator var2 = this.parameterNameDiscoverers.iterator();

      String[] result;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ParameterNameDiscoverer pnd = (ParameterNameDiscoverer)var2.next();
         result = pnd.getParameterNames(method);
      } while(result == null);

      return result;
   }

   @Nullable
   public String[] getParameterNames(Constructor ctor) {
      Iterator var2 = this.parameterNameDiscoverers.iterator();

      String[] result;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         ParameterNameDiscoverer pnd = (ParameterNameDiscoverer)var2.next();
         result = pnd.getParameterNames(ctor);
      } while(result == null);

      return result;
   }
}
