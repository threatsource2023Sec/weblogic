package weblogic.spring.monitoring.utils;

import java.lang.reflect.Method;

public class AbstractBeanDefinitionDelegator extends Delegator {
   private Class abstractBeanDefinitionClass;
   private Method isSingletonMethod;
   private Method isPrototypeMethod;
   private Method getScopeMethod;

   private void initialize(Object theDelegate) {
      if (theDelegate != null) {
         theDelegate.getClass().getClassLoader();

         try {
            this.abstractBeanDefinitionClass = Class.forName("org.springframework.beans.factory.support.AbstractBeanDefinition", true, theDelegate.getClass().getClassLoader());
            this.isSingletonMethod = this.abstractBeanDefinitionClass.getDeclaredMethod("isSingleton", (Class[])null);
            this.isPrototypeMethod = this.abstractBeanDefinitionClass.getDeclaredMethod("isPrototype", (Class[])null);
            this.getScopeMethod = this.abstractBeanDefinitionClass.getDeclaredMethod("getScope", (Class[])null);
         } catch (ClassNotFoundException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("AbstractBeanDefinitionDelegator.initialize failed", var3);
            }
         } catch (SecurityException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("AbstractBeanDefinitionDelegator.initialize failed", var4);
            }
         } catch (NoSuchMethodException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("AbstractBeanDefinitionDelegator.initialize failed", var5);
            }
         }

         if (this.abstractBeanDefinitionClass != null && this.abstractBeanDefinitionClass.isInstance(theDelegate)) {
            this.delegate = theDelegate;
         }

      }
   }

   protected AbstractBeanDefinitionDelegator() {
   }

   public AbstractBeanDefinitionDelegator(Object theDelegate) {
      this.initialize(theDelegate);
   }

   public boolean isSingleton() {
      return this.invokeGetBoolean(this.isSingletonMethod);
   }

   public boolean isPrototype() {
      return this.invokeGetBoolean(this.isPrototypeMethod);
   }

   public String getScope() {
      return this.invokeGetString(this.getScopeMethod);
   }
}
