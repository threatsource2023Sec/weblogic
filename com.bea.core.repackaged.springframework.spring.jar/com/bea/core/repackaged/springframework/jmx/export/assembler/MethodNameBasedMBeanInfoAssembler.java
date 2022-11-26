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

public class MethodNameBasedMBeanInfoAssembler extends AbstractConfigurableMBeanInfoAssembler {
   @Nullable
   private Set managedMethods;
   @Nullable
   private Map methodMappings;

   public void setManagedMethods(String... methodNames) {
      this.managedMethods = new HashSet(Arrays.asList(methodNames));
   }

   public void setMethodMappings(Properties mappings) {
      this.methodMappings = new HashMap();
      Enumeration en = mappings.keys();

      while(en.hasMoreElements()) {
         String beanKey = (String)en.nextElement();
         String[] methodNames = StringUtils.commaDelimitedListToStringArray(mappings.getProperty(beanKey));
         this.methodMappings.put(beanKey, new HashSet(Arrays.asList(methodNames)));
      }

   }

   protected boolean includeReadAttribute(Method method, String beanKey) {
      return this.isMatch(method, beanKey);
   }

   protected boolean includeWriteAttribute(Method method, String beanKey) {
      return this.isMatch(method, beanKey);
   }

   protected boolean includeOperation(Method method, String beanKey) {
      return this.isMatch(method, beanKey);
   }

   protected boolean isMatch(Method method, String beanKey) {
      if (this.methodMappings != null) {
         Set methodNames = (Set)this.methodMappings.get(beanKey);
         if (methodNames != null) {
            return methodNames.contains(method.getName());
         }
      }

      return this.managedMethods != null && this.managedMethods.contains(method.getName());
   }
}
