package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.annotated.enhanced.jlr.MethodSignatureImpl;
import org.jboss.weld.resources.WeldClassLoaderResourceLoader;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class GroovyMethodFilter implements ProxiedMethodFilter {
   private static final String GROOVY_OBJECT = "groovy.lang.GroovyObject";
   private static final Set METHODS = ImmutableSet.of(new MethodSignatureImpl("invokeMethod", new String[]{String.class.getName(), Object.class.getName()}), new MethodSignatureImpl("getProperty", new String[]{String.class.getName()}), new MethodSignatureImpl("setProperty", new String[]{String.class.getName(), Object.class.getName()}), new MethodSignatureImpl("getMetaClass", new String[0]), new MethodSignatureImpl("setMetaClass", new String[]{"groovy.lang.MetaClass"}), new MethodSignatureImpl("$getStaticMetaClass", new String[0]));

   public boolean isEnabled() {
      return Reflections.isClassLoadable("groovy.lang.GroovyObject", WeldClassLoaderResourceLoader.INSTANCE);
   }

   public boolean accept(Method method, Class proxySuperclass) {
      if ("groovy.lang.GroovyObject".equals(method.getDeclaringClass().getName())) {
         return false;
      } else {
         if (this.isGroovyObject(proxySuperclass)) {
            Iterator var3 = METHODS.iterator();

            while(var3.hasNext()) {
               MethodSignature groovyMethod = (MethodSignature)var3.next();
               if (groovyMethod.matches(method)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private boolean isGroovyObject(Class clazz) {
      while(clazz != null) {
         Class[] var2 = clazz.getInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class intf = var2[var4];
            if ("groovy.lang.GroovyObject".equals(intf.getName())) {
               return true;
            }
         }

         clazz = clazz.getSuperclass();
      }

      return false;
   }
}
