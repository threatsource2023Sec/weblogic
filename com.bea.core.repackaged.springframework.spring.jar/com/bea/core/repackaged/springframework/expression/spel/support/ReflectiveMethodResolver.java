package com.bea.core.repackaged.springframework.expression.spel.support;

import com.bea.core.repackaged.springframework.core.BridgeMethodResolver;
import com.bea.core.repackaged.springframework.core.MethodParameter;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.MethodExecutor;
import com.bea.core.repackaged.springframework.expression.MethodFilter;
import com.bea.core.repackaged.springframework.expression.MethodResolver;
import com.bea.core.repackaged.springframework.expression.TypeConverter;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReflectiveMethodResolver implements MethodResolver {
   private final boolean useDistance;
   @Nullable
   private Map filters;

   public ReflectiveMethodResolver() {
      this.useDistance = true;
   }

   public ReflectiveMethodResolver(boolean useDistance) {
      this.useDistance = useDistance;
   }

   public void registerMethodFilter(Class type, @Nullable MethodFilter filter) {
      if (this.filters == null) {
         this.filters = new HashMap();
      }

      if (filter != null) {
         this.filters.put(type, filter);
      } else {
         this.filters.remove(type);
      }

   }

   @Nullable
   public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List argumentTypes) throws AccessException {
      try {
         TypeConverter typeConverter = context.getTypeConverter();
         Class type = targetObject instanceof Class ? (Class)targetObject : targetObject.getClass();
         ArrayList methods = new ArrayList(this.getMethods(type, targetObject));
         MethodFilter filter = this.filters != null ? (MethodFilter)this.filters.get(type) : null;
         if (filter != null) {
            List filtered = filter.filter(methods);
            methods = filtered instanceof ArrayList ? (ArrayList)filtered : new ArrayList(filtered);
         }

         if (methods.size() > 1) {
            methods.sort((m1, m2) -> {
               int m1pl = m1.getParameterCount();
               int m2pl = m2.getParameterCount();
               if (m1pl == m2pl) {
                  if (!m1.isVarArgs() && m2.isVarArgs()) {
                     return -1;
                  } else {
                     return m1.isVarArgs() && !m2.isVarArgs() ? 1 : 0;
                  }
               } else {
                  return Integer.compare(m1pl, m2pl);
               }
            });
         }

         for(int i = 0; i < methods.size(); ++i) {
            methods.set(i, BridgeMethodResolver.findBridgedMethod((Method)methods.get(i)));
         }

         Set methodsToIterate = new LinkedHashSet(methods);
         Method closeMatch = null;
         int closeMatchDistance = Integer.MAX_VALUE;
         Method matchRequiringConversion = null;
         boolean multipleOptions = false;
         Iterator var14 = methodsToIterate.iterator();

         while(true) {
            Method method;
            int matchDistance;
            label122:
            do {
               while(true) {
                  while(true) {
                     ArrayList paramDescriptors;
                     ReflectionHelper.ArgumentsMatchInfo matchInfo;
                     do {
                        do {
                           if (!var14.hasNext()) {
                              if (closeMatch != null) {
                                 return new ReflectiveMethodExecutor(closeMatch);
                              }

                              if (matchRequiringConversion != null) {
                                 if (multipleOptions) {
                                    throw new SpelEvaluationException(SpelMessage.MULTIPLE_POSSIBLE_METHODS, new Object[]{name});
                                 }

                                 return new ReflectiveMethodExecutor(matchRequiringConversion);
                              }

                              return null;
                           }

                           method = (Method)var14.next();
                        } while(!method.getName().equals(name));

                        Class[] paramTypes = method.getParameterTypes();
                        paramDescriptors = new ArrayList(paramTypes.length);

                        for(int i = 0; i < paramTypes.length; ++i) {
                           paramDescriptors.add(new TypeDescriptor(new MethodParameter(method, i)));
                        }

                        matchInfo = null;
                        if (method.isVarArgs() && argumentTypes.size() >= paramTypes.length - 1) {
                           matchInfo = ReflectionHelper.compareArgumentsVarargs(paramDescriptors, argumentTypes, typeConverter);
                        } else if (paramTypes.length == argumentTypes.size()) {
                           matchInfo = ReflectionHelper.compareArguments(paramDescriptors, argumentTypes, typeConverter);
                        }
                     } while(matchInfo == null);

                     if (matchInfo.isExactMatch()) {
                        return new ReflectiveMethodExecutor(method);
                     }

                     if (matchInfo.isCloseMatch()) {
                        if (this.useDistance) {
                           matchDistance = ReflectionHelper.getTypeDifferenceWeight(paramDescriptors, argumentTypes);
                           continue label122;
                        }

                        if (closeMatch == null) {
                           closeMatch = method;
                        }
                     } else if (matchInfo.isMatchRequiringConversion()) {
                        if (matchRequiringConversion != null) {
                           multipleOptions = true;
                        }

                        matchRequiringConversion = method;
                     }
                  }
               }
            } while(closeMatch != null && matchDistance >= closeMatchDistance);

            closeMatch = method;
            closeMatchDistance = matchDistance;
         }
      } catch (EvaluationException var20) {
         throw new AccessException("Failed to resolve method", var20);
      }
   }

   private Set getMethods(Class type, Object targetObject) {
      LinkedHashSet result;
      int var6;
      Method[] methods;
      Method[] var14;
      int var15;
      Method method;
      if (targetObject instanceof Class) {
         result = new LinkedHashSet();
         methods = this.getMethods(type);
         var14 = methods;
         var6 = methods.length;

         for(var15 = 0; var15 < var6; ++var15) {
            method = var14[var15];
            if (Modifier.isStatic(method.getModifiers())) {
               result.add(method);
            }
         }

         Collections.addAll(result, this.getMethods(Class.class));
         return result;
      } else if (!Proxy.isProxyClass(type)) {
         result = new LinkedHashSet();
         methods = this.getMethods(type);
         var14 = methods;
         var6 = methods.length;

         for(var15 = 0; var15 < var6; ++var15) {
            method = var14[var15];
            if (this.isCandidateForInvocation(method, type)) {
               result.add(method);
            }
         }

         return result;
      } else {
         result = new LinkedHashSet();
         Class[] var4 = type.getInterfaces();
         int var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            Class ifc = var4[var6];
            Method[] methods = this.getMethods(ifc);
            Method[] var9 = methods;
            int var10 = methods.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               Method method = var9[var11];
               if (this.isCandidateForInvocation(method, type)) {
                  result.add(method);
               }
            }
         }

         return result;
      }
   }

   protected Method[] getMethods(Class type) {
      return type.getMethods();
   }

   protected boolean isCandidateForInvocation(Method method, Class targetClass) {
      return true;
   }
}
