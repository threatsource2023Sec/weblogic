package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class NameMatchMethodPointcut extends StaticMethodMatcherPointcut implements Serializable {
   private List mappedNames = new ArrayList();

   public void setMappedName(String mappedName) {
      this.setMappedNames(mappedName);
   }

   public void setMappedNames(String... mappedNames) {
      this.mappedNames = new ArrayList(Arrays.asList(mappedNames));
   }

   public NameMatchMethodPointcut addMethodName(String name) {
      this.mappedNames.add(name);
      return this;
   }

   public boolean matches(Method method, Class targetClass) {
      Iterator var3 = this.mappedNames.iterator();

      String mappedName;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         mappedName = (String)var3.next();
      } while(!mappedName.equals(method.getName()) && !this.isMatch(method.getName(), mappedName));

      return true;
   }

   protected boolean isMatch(String methodName, String mappedName) {
      return PatternMatchUtils.simpleMatch(mappedName, methodName);
   }

   public boolean equals(Object other) {
      return this == other || other instanceof NameMatchMethodPointcut && this.mappedNames.equals(((NameMatchMethodPointcut)other).mappedNames);
   }

   public int hashCode() {
      return this.mappedNames.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.mappedNames;
   }
}
