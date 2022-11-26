package weblogic.utils.classloaders.debug;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;

class Trigger {
   private static final DebugLogger ctDebugLogger = DebugLogger.getDebugLogger("DebugClassLoadingContextualTrace");
   private final RecordTrace recordTrace;
   private String triggeringThrowablesStringValue = null;
   private Set triggeringThrowables;
   private final Set triggeringThrowablesDefaultSet = new HashSet();

   Trigger(RecordTrace recordTrace) {
      this.recordTrace = recordTrace;
      this.triggeringThrowablesDefaultSet.add(ClassNotFoundException.class);
      this.triggeringThrowablesDefaultSet.add(ClassFormatError.class);
      this.triggeringThrowablesDefaultSet.add(NoClassDefFoundError.class);
      this.triggeringThrowablesDefaultSet.add(UnsatisfiedLinkError.class);
      this.triggeringThrowables = this.triggeringThrowablesDefaultSet;
      this.checkAndSetTriggeringThrowables();
      if (ctDebugLogger.isDebugEnabled()) {
         this.dumpTriggeringThrowables();
      }

   }

   private void checkAndSetTriggeringThrowables() {
      String paramValue = (String)ctDebugLogger.getDebugParameters().get("ErrorTrigger");
      if (paramValue == null && this.triggeringThrowablesStringValue != null) {
         this.triggeringThrowables = this.triggeringThrowablesDefaultSet;
         this.triggeringThrowablesStringValue = null;
         this.dumpTriggeringThrowables();
      } else if (paramValue != null && (this.triggeringThrowablesStringValue == null || !paramValue.equals(this.triggeringThrowablesStringValue))) {
         this.triggeringThrowables = new HashSet();
         String[] var2 = paramValue.split(",");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String et = var2[var4];

            try {
               this.triggeringThrowables.add(Class.forName(et));
            } catch (Throwable var7) {
               ctDebugLogger.debug("Unable to load error class for filtering" + et);
            }
         }

         this.triggeringThrowablesStringValue = paramValue;
         this.dumpTriggeringThrowables();
      }

   }

   private void dumpTriggeringThrowables() {
      Iterator var1 = this.triggeringThrowables.iterator();

      while(var1.hasNext()) {
         Class c = (Class)var1.next();
         ctDebugLogger.debug("Contextual Trace Triggering Throwable = " + c.getName());
      }

   }

   boolean checkAndDump(Throwable e, StringBuilder builder, StackTraceElement[] triggerStackTrace, int triggerStackTraceOffset) {
      String throwableMessageFilter = (String)ctDebugLogger.getDebugParameters().get("ThrowableMessageFilter");
      String resourceFilter = (String)ctDebugLogger.getDebugParameters().get("ResourceTrigger");
      this.checkAndSetTriggeringThrowables();
      if (this.triggeringThrowables.contains(e.getClass()) && (throwableMessageFilter == null || throwableMessageFilter != null && e.getMessage().contains(throwableMessageFilter)) && this.recordTrace.size() > 0) {
         this.recordTrace.dump(Thread.currentThread(), builder, e.getClass().getSimpleName(), triggerStackTrace, triggerStackTraceOffset);
         return true;
      } else {
         return false;
      }
   }

   boolean checkAndDump(String resourceName, StringBuilder builder, StackTraceElement[] triggerStackTrace, int triggerStackTraceOffset) {
      String throwableMessageFilter = (String)ctDebugLogger.getDebugParameters().get("ThrowableMessageFilter");
      String resourceFilter = (String)ctDebugLogger.getDebugParameters().get("ResourceTrigger");
      if (resourceFilter != null && resourceName.contains(resourceFilter) && this.recordTrace.size() > 0) {
         this.recordTrace.dump(Thread.currentThread(), builder, resourceName, triggerStackTrace, triggerStackTraceOffset);
         return true;
      } else {
         return false;
      }
   }
}
