package org.hibernate.validator.internal.engine;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.ParameterNameProvider;

public class DefaultParameterNameProvider implements ParameterNameProvider {
   public List getParameterNames(Constructor constructor) {
      return this.doGetParameterNames(constructor);
   }

   public List getParameterNames(Method method) {
      return this.doGetParameterNames(method);
   }

   private List doGetParameterNames(Executable executable) {
      Parameter[] parameters = executable.getParameters();
      List parameterNames = new ArrayList(parameters.length);
      Parameter[] var4 = parameters;
      int var5 = parameters.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Parameter parameter = var4[var6];
         parameterNames.add(parameter.getName());
      }

      return Collections.unmodifiableList(parameterNames);
   }
}
