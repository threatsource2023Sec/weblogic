package weblogic.spring.monitoring.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.diagnostics.debug.DebugLogger;

public abstract class Delegator {
   protected static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");
   protected Object delegate;

   protected Delegator() {
   }

   protected boolean invokeGetBoolean(Method method) {
      if (this.delegate != null && method != null) {
         try {
            Boolean result = (Boolean)method.invoke(this.delegate, (Object[])null);
            return result;
         } catch (IllegalArgumentException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetBoolean failed", var3);
            }
         } catch (IllegalAccessException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetBoolean failed", var4);
            }
         } catch (InvocationTargetException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetBoolean failed", var5);
            }
         }

         return false;
      } else {
         return false;
      }
   }

   protected int invokeGetInt(Method method) {
      if (this.delegate != null && method != null) {
         try {
            Integer result = (Integer)method.invoke(this.delegate, (Object[])null);
            return result;
         } catch (IllegalArgumentException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetInt failed", var3);
            }
         } catch (IllegalAccessException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetInt failed", var4);
            }
         } catch (InvocationTargetException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetInt failed", var5);
            }
         }

         return 0;
      } else {
         return 0;
      }
   }

   protected long invokeGetLong(Method method) {
      if (this.delegate != null && method != null) {
         try {
            Long result = (Long)method.invoke(this.delegate, (Object[])null);
            return result;
         } catch (IllegalArgumentException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetLong failed", var3);
            }
         } catch (IllegalAccessException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetLong failed", var4);
            }
         } catch (InvocationTargetException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetLong failed", var5);
            }
         }

         return 0L;
      } else {
         return 0L;
      }
   }

   protected String invokeGetString(Method method) {
      if (this.delegate != null && method != null) {
         try {
            return (String)method.invoke(this.delegate, (Object[])null);
         } catch (IllegalArgumentException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetString failed", var3);
            }
         } catch (IllegalAccessException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetString failed", var4);
            }
         } catch (InvocationTargetException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetString failed", var5);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   protected Object invokeGetObject(Method method) {
      if (this.delegate != null && method != null) {
         try {
            return method.invoke(this.delegate, (Object[])null);
         } catch (IllegalArgumentException var3) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetObject failed", var3);
            }
         } catch (IllegalAccessException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetObject failed", var4);
            }
         } catch (InvocationTargetException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Delegator.invokeGetObject failed", var5);
            }
         }

         return null;
      } else {
         return null;
      }
   }
}
