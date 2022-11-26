package org.jboss.weld.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jboss.weld.annotated.runtime.InvokableAnnotatedMethod;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.resolution.CovariantTypes;
import org.jboss.weld.util.Decorators;
import org.jboss.weld.util.Types;
import org.jboss.weld.util.reflection.Reflections;

class DecoratedMethods {
   private static final Object NULL_MARKER = new Object();
   private final Set decoratedTypeMethods;
   private final ConcurrentMap cache;

   DecoratedMethods(BeanManagerImpl manager, WeldDecorator decorator) {
      this.decoratedTypeMethods = Decorators.getDecoratorMethods(manager, decorator);
      this.cache = new ConcurrentHashMap();
   }

   public InvokableAnnotatedMethod getDecoratedMethod(Method method) {
      if (!this.cache.containsKey(method)) {
         this.cache.putIfAbsent(method, this.findMatchingDecoratedMethod(method));
      }

      Object value = this.cache.get(method);
      return value == NULL_MARKER ? null : (InvokableAnnotatedMethod)Reflections.cast(value);
   }

   private Object findMatchingDecoratedMethod(Method method) {
      Iterator var2 = this.decoratedTypeMethods.iterator();

      InvokableAnnotatedMethod mostSpecific;
      do {
         if (!var2.hasNext()) {
            List matching = new ArrayList();
            Iterator var7 = this.decoratedTypeMethods.iterator();

            while(var7.hasNext()) {
               InvokableAnnotatedMethod decoratedMethod = (InvokableAnnotatedMethod)var7.next();
               if (this.matches(decoratedMethod, method)) {
                  matching.add(decoratedMethod);
               }
            }

            if (matching.isEmpty()) {
               return NULL_MARKER;
            }

            if (matching.size() == 1) {
               return matching.get(0);
            }

            mostSpecific = (InvokableAnnotatedMethod)matching.get(0);

            for(int i = 1; i < matching.size(); ++i) {
               InvokableAnnotatedMethod candidate = (InvokableAnnotatedMethod)matching.get(i);
               if (this.isMoreSpecific(candidate, mostSpecific)) {
                  mostSpecific = candidate;
               }
            }

            return mostSpecific;
         }

         mostSpecific = (InvokableAnnotatedMethod)var2.next();
      } while(!mostSpecific.getJavaMember().equals(method));

      return mostSpecific;
   }

   private boolean matches(InvokableAnnotatedMethod decoratedMethod, Method candidate) {
      if (candidate.getParameterTypes().length != decoratedMethod.getParameters().size()) {
         return false;
      } else if (!candidate.getName().equals(decoratedMethod.getJavaMember().getName())) {
         return false;
      } else {
         for(int i = 0; i < candidate.getParameterTypes().length; ++i) {
            Type decoratedMethodParamType = decoratedMethod.getJavaMember().getGenericParameterTypes()[i];
            Type candidateParamType = candidate.getGenericParameterTypes()[i];
            if (!Types.containsTypeVariable(decoratedMethodParamType) && !Types.containsTypeVariable(candidateParamType)) {
               if (!CovariantTypes.isAssignableFrom(decoratedMethodParamType, candidateParamType)) {
                  return false;
               }
            } else if (!decoratedMethod.getJavaMember().getParameterTypes()[i].isAssignableFrom(candidate.getParameterTypes()[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isMoreSpecific(InvokableAnnotatedMethod candidate, InvokableAnnotatedMethod mostSpecific) {
      for(int i = 0; i < candidate.getJavaMember().getGenericParameterTypes().length; ++i) {
         if (Types.isMoreSpecific(candidate.getJavaMember().getGenericParameterTypes()[i], mostSpecific.getJavaMember().getGenericParameterTypes()[i])) {
            return true;
         }
      }

      return false;
   }
}
