package weblogic.diagnostics.context;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;

public final class DiagnosticContextFactory {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
   private static final Factory DEFAULT_FACTORY = new DefaultFactoryImpl();
   private static Factory SINGLETON;
   private static boolean contextEnabled;
   private static int propagationMode;

   static synchronized void setFactory(Factory factory) {
   }

   static boolean isEnabled() {
      return contextEnabled;
   }

   static void setEnabled(boolean val) {
      contextEnabled = val;
   }

   static int getPropagationMode() {
      return propagationMode;
   }

   static void setPropagationMode(int val) {
      propagationMode = val;
   }

   public static DiagnosticContext getDiagnosticContext() {
      WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
      return (DiagnosticContext)map.get("weblogic.diagnostics.DiagnosticContext");
   }

   public static DiagnosticContext findOrCreateDiagnosticContext() {
      return findOrCreateDiagnosticContext(contextEnabled);
   }

   public static DiagnosticContext findOrCreateDiagnosticContext(boolean enabled) {
      return SINGLETON.findOrCreateDiagnosticContext(true);
   }

   public static DiagnosticContext findDiagnosticContext() {
      return SINGLETON.findOrCreateDiagnosticContext(false);
   }

   public static void invalidateCache() {
      SINGLETON.invalidateCache();
   }

   public static void setJFRThrottled(DiagnosticContext ctx) {
      SINGLETON.setJFRThrottled(ctx);
   }

   static void setDiagnosticContext(DiagnosticContext ctx) {
      SINGLETON.setDiagnosticContext(ctx);
   }

   static DiagnosticContext handleContextIdUpdate(boolean enabled, String id, String rid) {
      return SINGLETON.handleContextIdUpdate(enabled, id, rid);
   }

   static void handleReadContextUpdate(DiagnosticContext ctx) {
      SINGLETON.handleReadContextUpdate(ctx);
   }

   static {
      SINGLETON = DEFAULT_FACTORY;
      propagationMode = 383;
   }

   private static class DefaultFactoryImpl implements Factory {
      private DefaultFactoryImpl() {
      }

      public DiagnosticContext findOrCreateDiagnosticContext(boolean enabled) {
         DiagnosticContext ctx = null;
         WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
         ctx = (DiagnosticContext)map.get("weblogic.diagnostics.DiagnosticContext");
         if (ctx == null && enabled) {
            ctx = new DiagnosticContextImpl();

            try {
               map.put("weblogic.diagnostics.DiagnosticContext", (WorkContext)ctx, DiagnosticContextFactory.propagationMode);
            } catch (Throwable var5) {
               if (DiagnosticContextFactory.DEBUG_LOGGER.isDebugEnabled()) {
                  var5.printStackTrace();
               }
            }
         }

         return (DiagnosticContext)ctx;
      }

      public void invalidateCache() {
      }

      public void setJFRThrottled(DiagnosticContext ctx) {
      }

      public void handleReadContextUpdate(DiagnosticContext ctx) {
      }

      public DiagnosticContext handleContextIdUpdate(boolean enabled, String id, String rid) {
         DiagnosticContextImpl ctxImpl = (DiagnosticContextImpl)this.findOrCreateDiagnosticContext(true);
         ctxImpl.setContextId(id);
         ctxImpl.setRID(rid);
         return ctxImpl;
      }

      public void setDiagnosticContext(DiagnosticContext ctx) {
         WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();

         try {
            if (ctx != null) {
               map.put("weblogic.diagnostics.DiagnosticContext", ctx, DiagnosticContextFactory.propagationMode);
            } else if (map.get("weblogic.diagnostics.DiagnosticContext") != null) {
               map.remove("weblogic.diagnostics.DiagnosticContext");
               this.invalidateCache();
            }
         } catch (Throwable var4) {
            if (DiagnosticContextFactory.DEBUG_LOGGER.isDebugEnabled()) {
               var4.printStackTrace();
            }
         }

      }

      // $FF: synthetic method
      DefaultFactoryImpl(Object x0) {
         this();
      }
   }

   public interface Factory {
      DiagnosticContext findOrCreateDiagnosticContext(boolean var1);

      void invalidateCache();

      void setJFRThrottled(DiagnosticContext var1);

      void handleReadContextUpdate(DiagnosticContext var1);

      DiagnosticContext handleContextIdUpdate(boolean var1, String var2, String var3);

      void setDiagnosticContext(DiagnosticContext var1);
   }
}
