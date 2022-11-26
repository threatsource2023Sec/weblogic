package com.bea.core.repackaged.springframework.aop.support;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.IntroductionInfo;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class IntroductionInfoSupport implements IntroductionInfo, Serializable {
   protected final Set publishedInterfaces = new LinkedHashSet();
   private transient Map rememberedMethods = new ConcurrentHashMap(32);

   public void suppressInterface(Class ifc) {
      this.publishedInterfaces.remove(ifc);
   }

   public Class[] getInterfaces() {
      return ClassUtils.toClassArray(this.publishedInterfaces);
   }

   public boolean implementsInterface(Class ifc) {
      Iterator var2 = this.publishedInterfaces.iterator();

      Class pubIfc;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         pubIfc = (Class)var2.next();
      } while(!ifc.isInterface() || !ifc.isAssignableFrom(pubIfc));

      return true;
   }

   protected void implementInterfacesOnObject(Object delegate) {
      this.publishedInterfaces.addAll(ClassUtils.getAllInterfacesAsSet(delegate));
   }

   protected final boolean isMethodOnIntroducedInterface(MethodInvocation mi) {
      Boolean rememberedResult = (Boolean)this.rememberedMethods.get(mi.getMethod());
      if (rememberedResult != null) {
         return rememberedResult;
      } else {
         boolean result = this.implementsInterface(mi.getMethod().getDeclaringClass());
         this.rememberedMethods.put(mi.getMethod(), result);
         return result;
      }
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ois.defaultReadObject();
      this.rememberedMethods = new ConcurrentHashMap(32);
   }
}
