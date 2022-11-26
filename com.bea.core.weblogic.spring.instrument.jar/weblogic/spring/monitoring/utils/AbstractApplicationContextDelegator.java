package weblogic.spring.monitoring.utils;

import java.lang.reflect.Method;

public class AbstractApplicationContextDelegator extends Delegator {
   private Class abstractApplicationContextClass;
   private Method getParentMethod;
   private Method getStartupDateMethod;
   private Method getBeanFactoryMethod;
   private Object parentContext;
   private ApplicationContextDelegator parentDelegate;

   private void initialize(Object theDelegate) {
      if (theDelegate != null) {
         theDelegate.getClass().getClassLoader();

         try {
            this.abstractApplicationContextClass = Class.forName("org.springframework.context.support.AbstractApplicationContext", true, theDelegate.getClass().getClassLoader());
            this.getParentMethod = this.abstractApplicationContextClass.getDeclaredMethod("getParent", (Class[])null);
            this.getStartupDateMethod = this.abstractApplicationContextClass.getDeclaredMethod("getStartupDate", (Class[])null);
            this.getBeanFactoryMethod = this.abstractApplicationContextClass.getDeclaredMethod("getBeanFactory", (Class[])null);
            if (this.abstractApplicationContextClass != null && this.abstractApplicationContextClass.isInstance(theDelegate)) {
               this.delegate = theDelegate;
            }

            this.parentContext = this.getParent();
            if (this.parentContext != null) {
               this.parentDelegate = new ApplicationContextDelegator(this.parentContext);
            }
         } catch (ClassNotFoundException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("AbstractApplicationContextDelegator.initialize failed", var3);
            }
         } catch (SecurityException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("AbstractApplicationContextDelegator.initialize failed", var4);
            }
         } catch (NoSuchMethodException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("AbstractApplicationContextDelegator.initialize failed", var5);
            }
         }

      }
   }

   protected AbstractApplicationContextDelegator() {
   }

   public AbstractApplicationContextDelegator(Object theDelegate) {
      this.initialize(theDelegate);
   }

   public String getParentContext() {
      return this.parentDelegate == null ? null : this.parentDelegate.getDisplayName();
   }

   public long getStartupDate() {
      return this.invokeGetLong(this.getStartupDateMethod);
   }

   public Object getBeanFactory() {
      return this.invokeGetObject(this.getBeanFactoryMethod);
   }

   private Object getParent() {
      return this.invokeGetObject(this.getParentMethod);
   }
}
