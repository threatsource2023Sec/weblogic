package com.bea.core.repackaged.springframework.jmx.export.assembler;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MethodExclusionMBeanInfoAssembler extends AbstractConfigurableMBeanInfoAssembler {
   @Nullable
   private Set ignoredMethods;
   @Nullable
   private Map ignoredMethodMappings;

   public void setIgnoredMethods(String... ignoredMethodNames) {
      this.ignoredMethods = new HashSet(Arrays.asList(ignoredMethodNames));
   }

   public void setIgnoredMethodMappings(Properties mappings) {
      this.ignoredMethodMappings = new HashMap();
      Enumeration en = mappings.keys();

      while(en.hasMoreElements()) {
         String beanKey = (String)en.nextElement();
         String[] methodNames = StringUtils.commaDelimitedListToStringArray(mappings.getProperty(beanKey));
         this.ignoredMethodMappings.put(beanKey, new HashSet(Arrays.asList(methodNames)));
      }

   }

   protected boolean includeReadAttribute(Method method, String beanKey) {
      return this.isNotIgnored(method, beanKey);
   }

   protected boolean includeWriteAttribute(Method method, String beanKey) {
      return this.isNotIgnored(method, beanKey);
   }

   protected boolean includeOperation(Method method, String beanKey) {
      return this.isNotIgnored(method, beanKey);
   }

   protected boolean isNotIgnored(Method method, String beanKey) {
      if (this.ignoredMethodMappings != null) {
         Set methodNames = (Set)this.ignoredMethodMappings.get(beanKey);
         if (methodNames != null) {
            return !methodNames.contains(method.getName());
         }
      }

      if (this.ignoredMethods != null) {
         return !this.ignoredMethods.contains(method.getName());
      } else {
         return true;
      }
   }
}
