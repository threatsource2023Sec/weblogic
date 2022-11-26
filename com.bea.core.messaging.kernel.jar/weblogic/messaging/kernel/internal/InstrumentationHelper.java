package weblogic.messaging.kernel.internal;

import weblogic.messaging.Message;
import weblogic.messaging.kernel.KernelException;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;
import weblogic.workarea.spi.WorkContextMapInterceptor;

public final class InstrumentationHelper {
   public static final String CRASH_BEFORE_ACKNOWLEDGE = "weblogic.messaging.kernel.CrashBeforeAcknowledge";
   public static final String CRASH_BEFORE_NEGATIVE_ACKNOWLEDGE = "weblogic.messaging.kernel.CrashBeforeNegativeAcknowledge";
   public static final String CRASH_BEFORE_SEND = "weblogic.messaging.kernel.CrashBeforeSend";
   public static final String CRASH_AFTER_SEND = "weblogic.messaging.kernel.CrashAfterSend";
   private static final boolean ENABLED;

   private static WorkContextMap pushContext(Message msg) {
      WorkContextHelper helper = WorkContextHelper.getWorkContextHelper();
      helper.setLocalInterceptor((WorkContextMapInterceptor)msg.getWorkContext());
      return helper.getWorkContextMap();
   }

   private static void popContext() {
      WorkContextHelper.getWorkContextHelper().setLocalInterceptor((WorkContextMapInterceptor)null);
   }

   private static void crashInterceptionPoint(Message msg, String key) {
      assert ENABLED;

      if (msg.getWorkContext() != null) {
         WorkContextMap map = pushContext(msg);
         if (map.get(key) != null) {
            System.err.println("**** Messaging kernel instrumentation ****");
            System.err.println("Work context key found: " + key);
            System.err.println("Server is halting.");
            Runtime.getRuntime().halt(201);
         }

         popContext();
      }
   }

   static void beforeAcknowledgeInterceptionPoint(MessageReference ref, KernelImpl kernel) throws KernelException {
      if (ENABLED) {
         crashInterceptionPoint(ref.getMessage(kernel), "weblogic.messaging.kernel.CrashBeforeAcknowledge");
      }
   }

   static void beforeNegativeAcknowledgeInterceptionPoint(MessageReference ref, KernelImpl kernel) throws KernelException {
      if (ENABLED) {
         crashInterceptionPoint(ref.getMessage(kernel), "weblogic.messaging.kernel.CrashBeforeNegativeAcknowledge");
      }
   }

   static void beforeSendInterceptionPoint(MessageHandle handle) {
      if (ENABLED) {
         crashInterceptionPoint(handle.getMessage(), "weblogic.messaging.kernel.CrashBeforeSend");
      }
   }

   static void afterSendInterceptionPoint(MessageHandle handle) {
      if (ENABLED) {
         crashInterceptionPoint(handle.getMessage(), "weblogic.messaging.kernel.CrashAfterSend");
      }
   }

   static {
      String enabledVal = System.getProperty("weblogic.messaging.kernel.EnableInstrumentation");
      ENABLED = enabledVal != null && enabledVal.equalsIgnoreCase("true");
   }
}
