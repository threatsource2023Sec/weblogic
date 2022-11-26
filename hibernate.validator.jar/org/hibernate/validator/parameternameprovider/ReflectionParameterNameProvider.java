package org.hibernate.validator.parameternameprovider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import javax.validation.ParameterNameProvider;
import org.hibernate.validator.internal.util.CollectionHelper;

/** @deprecated */
@Deprecated
public class ReflectionParameterNameProvider implements ParameterNameProvider {
   public List getParameterNames(Constructor constructor) {
      return this.getParameterNames(constructor.getParameters());
   }

   public List getParameterNames(Method method) {
      return this.getParameterNames(method.getParameters());
   }

   private List getParameterNames(Parameter[] parameters) {
      List parameterNames = CollectionHelper.newArrayList();
      Parameter[] var3 = parameters;
      int var4 = parameters.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Parameter parameter = var3[var5];
         parameterNames.add(parameter.getName());
      }

      return parameterNames;
   }
}
