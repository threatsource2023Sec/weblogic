package com.bea.core.repackaged.springframework.jmx.export.assembler;

import com.bea.core.repackaged.springframework.beans.factory.BeanClassLoaderAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class InterfaceBasedMBeanInfoAssembler extends AbstractConfigurableMBeanInfoAssembler implements BeanClassLoaderAware, InitializingBean {
   @Nullable
   private Class[] managedInterfaces;
   @Nullable
   private Properties interfaceMappings;
   @Nullable
   private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();
   @Nullable
   private Map resolvedInterfaceMappings;

   public void setManagedInterfaces(@Nullable Class... managedInterfaces) {
      if (managedInterfaces != null) {
         Class[] var2 = managedInterfaces;
         int var3 = managedInterfaces.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class ifc = var2[var4];
            if (!ifc.isInterface()) {
               throw new IllegalArgumentException("Management interface [" + ifc.getName() + "] is not an interface");
            }
         }
      }

      this.managedInterfaces = managedInterfaces;
   }

   public void setInterfaceMappings(@Nullable Properties mappings) {
      this.interfaceMappings = mappings;
   }

   public void setBeanClassLoader(@Nullable ClassLoader beanClassLoader) {
      this.beanClassLoader = beanClassLoader;
   }

   public void afterPropertiesSet() {
      if (this.interfaceMappings != null) {
         this.resolvedInterfaceMappings = this.resolveInterfaceMappings(this.interfaceMappings);
      }

   }

   private Map resolveInterfaceMappings(Properties mappings) {
      Map resolvedMappings = new HashMap(mappings.size());
      Enumeration en = mappings.propertyNames();

      while(en.hasMoreElements()) {
         String beanKey = (String)en.nextElement();
         String[] classNames = StringUtils.commaDelimitedListToStringArray(mappings.getProperty(beanKey));
         Class[] classes = this.resolveClassNames(classNames, beanKey);
         resolvedMappings.put(beanKey, classes);
      }

      return resolvedMappings;
   }

   private Class[] resolveClassNames(String[] classNames, String beanKey) {
      Class[] classes = new Class[classNames.length];

      for(int x = 0; x < classes.length; ++x) {
         Class cls = ClassUtils.resolveClassName(classNames[x].trim(), this.beanClassLoader);
         if (!cls.isInterface()) {
            throw new IllegalArgumentException("Class [" + classNames[x] + "] mapped to bean key [" + beanKey + "] is no interface");
         }

         classes[x] = cls;
      }

      return classes;
   }

   protected boolean includeReadAttribute(Method method, String beanKey) {
      return this.isPublicInInterface(method, beanKey);
   }

   protected boolean includeWriteAttribute(Method method, String beanKey) {
      return this.isPublicInInterface(method, beanKey);
   }

   protected boolean includeOperation(Method method, String beanKey) {
      return this.isPublicInInterface(method, beanKey);
   }

   private boolean isPublicInInterface(Method method, String beanKey) {
      return Modifier.isPublic(method.getModifiers()) && this.isDeclaredInInterface(method, beanKey);
   }

   private boolean isDeclaredInInterface(Method method, String beanKey) {
      Class[] ifaces = null;
      if (this.resolvedInterfaceMappings != null) {
         ifaces = (Class[])this.resolvedInterfaceMappings.get(beanKey);
      }

      if (ifaces == null) {
         ifaces = this.managedInterfaces;
         if (ifaces == null) {
            ifaces = ClassUtils.getAllInterfacesForClass(method.getDeclaringClass());
         }
      }

      Class[] var4 = ifaces;
      int var5 = ifaces.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class ifc = var4[var6];
         Method[] var8 = ifc.getMethods();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Method ifcMethod = var8[var10];
            if (ifcMethod.getName().equals(method.getName()) && ifcMethod.getParameterCount() == method.getParameterCount() && Arrays.equals(ifcMethod.getParameterTypes(), method.getParameterTypes())) {
               return true;
            }
         }
      }

      return false;
   }
}
