package weblogic.diagnostics.instrumentation.action;

import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.diagnostics.debug.DebugLogger;

public class MemoryAllocationMethod {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticInstrumentationActions");
   private static MemoryAllocationMethod SINGLETON = null;
   private boolean isSupported = false;
   private boolean useThreadMX = false;
   private Method getAllocatedBytesMtd = null;
   private Object threadMXBeanInstance = null;

   protected MemoryAllocationMethod() {
      this.initializeGetAllocatedBytesMethod();
      if (this.getAllocatedBytesMtd != null) {
         this.isSupported = true;
      }

   }

   public static synchronized MemoryAllocationMethod getInstance() {
      if (SINGLETON != null) {
         return SINGLETON;
      } else {
         SINGLETON = new MemoryAllocationMethod();
         return SINGLETON;
      }
   }

   public boolean isSupported() {
      return this.isSupported;
   }

   public long getAllocatedBytes() {
      if (!this.isSupported) {
         return 0L;
      } else {
         try {
            if (this.useThreadMX) {
               Object[] args = new Object[]{Thread.currentThread().getId()};
               return (Long)this.getAllocatedBytesMtd.invoke(this.threadMXBeanInstance, args);
            }

            return (Long)this.getAllocatedBytesMtd.invoke((Object)null, (Object[])null);
         } catch (IllegalArgumentException var2) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("getAllocatedBytes failed", var2);
            }
         } catch (IllegalAccessException var3) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("getAllocatedBytes failed", var3);
            }
         } catch (InvocationTargetException var4) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("getAllocatedBytes failed", var4);
            }
         }

         return 0L;
      }
   }

   private void initializeGetAllocatedBytesMethod() {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("initializeGetAllocatedBytes checking for JRockit support first");
      }

      Class threadMXBeanClass;
      try {
         threadMXBeanClass = Class.forName("jrockit.ext.ThreadInfo");
         this.getAllocatedBytesMtd = threadMXBeanClass.getMethod("getAllocatedBytes", (Class[])null);
         this.useThreadMX = false;
         this.isSupported = true;
         return;
      } catch (ClassNotFoundException var13) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes JRockit check ClassNotFoundException: ", var13);
         }
      } catch (SecurityException var14) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes JRockit extension check SecurityException: ", var14);
         }
      } catch (NoSuchMethodException var15) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes JRockit extension check NoSuchMethodException: ", var15);
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("initializeGetAllocatedBytes checking for HotSpot extension support next");
      }

      try {
         threadMXBeanClass = Class.forName("com.sun.management.ThreadMXBean");
         this.threadMXBeanInstance = ManagementFactory.getThreadMXBean();
         if (this.threadMXBeanInstance != null && threadMXBeanClass.isInstance(this.threadMXBeanInstance)) {
            Class[] booleanArg = new Class[]{Boolean.TYPE};
            Class[] longArg = new Class[]{Long.TYPE};
            Method isSupportedMethod = threadMXBeanClass.getMethod("isThreadAllocatedMemorySupported", (Class[])null);
            Method setEnabledMethod = threadMXBeanClass.getMethod("setThreadAllocatedMemoryEnabled", booleanArg);
            this.getAllocatedBytesMtd = threadMXBeanClass.getMethod("getThreadAllocatedBytes", longArg);

            try {
               this.isSupported = (Boolean)isSupportedMethod.invoke(this.threadMXBeanInstance, (Object[])null);
               if (!this.isSupported) {
                  this.useThreadMX = true;
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("initializeGetAllocatedBytes HotSpot extension detected, but it indicates it is not supported");
                  }

                  return;
               }

               Object[] args = new Object[]{true};
               setEnabledMethod.invoke(this.threadMXBeanInstance, args);
            } catch (IllegalArgumentException var7) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("initializeGetAllocatedBytes HotSpot extension detected, but failed invoking API: ", var7);
               }
            } catch (IllegalAccessException var8) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("initializeGetAllocatedBytes HotSpot extension detected, but failed invoking API: ", var8);
               }
            } catch (InvocationTargetException var9) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("initializeGetAllocatedBytes HotSpot extension detected, but failed invoking API: ", var9);
               }
            }

            this.useThreadMX = true;
            this.isSupported = true;
            return;
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes threadMXBeanInstance was null or not extension: " + this.threadMXBeanInstance);
         }
      } catch (ClassNotFoundException var10) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes HotSpot extension check ClassNotFoundException: ", var10);
         }
      } catch (SecurityException var11) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes HotSpot extension check SecurityException: ", var11);
         }
      } catch (NoSuchMethodException var12) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("initializeGetAllocatedBytes HotSpot extension check NoSuchMethodException: ", var12);
         }
      }

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("getAllocatedBytes is not supported here");
      }

      this.threadMXBeanInstance = null;
      this.getAllocatedBytesMtd = null;
      this.isSupported = false;
   }
}
