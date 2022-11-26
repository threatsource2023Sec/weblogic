package weblogic.spring.monitoring.utils;

import java.lang.reflect.Method;

public class ApplicationContextDelegator extends Delegator {
   private Class applicationContextClass;
   private Method getDisplayNameMethod;
   private Method getIdMethod;

   private void initialize(Object theDelegate) {
      if (theDelegate != null) {
         theDelegate.getClass().getClassLoader();

         try {
            this.applicationContextClass = Class.forName("org.springframework.context.ApplicationContext", true, theDelegate.getClass().getClassLoader());
            this.getDisplayNameMethod = this.applicationContextClass.getDeclaredMethod("getDisplayName", (Class[])null);
            this.getIdMethod = this.applicationContextClass.getDeclaredMethod("getId", (Class[])null);
         } catch (ClassNotFoundException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextDelegator.initialize failed", var3);
            }
         } catch (SecurityException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextDelegator.initialize failed", var4);
            }
         } catch (NoSuchMethodException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextDelegator.initialize failed", var5);
            }
         }

         if (this.applicationContextClass != null && this.applicationContextClass.isInstance(theDelegate)) {
            this.delegate = theDelegate;
         }

      }
   }

   protected ApplicationContextDelegator() {
   }

   public ApplicationContextDelegator(Object theDelegate) {
      this.initialize(theDelegate);
   }

   public String getDisplayName() {
      return this.invokeGetString(this.getDisplayNameMethod);
   }

   public String getId() {
      return this.invokeGetString(this.getIdMethod);
   }
}
