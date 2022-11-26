package weblogic.diagnostics.context;

import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.KernelStatus;

@Service
public final class CorrelationFactory implements org.glassfish.hk2.api.Factory {
   static final String DMS_ENTRY_NAME_IN_MAP = "oracle.dms.context.internal.wls.WLSContextFamily";
   private static final Factory DEFAULT_FACTORY = new DefaultFactoryImpl();
   private static Factory SINGLETON;
   private static boolean contextEnabled;
   private static int remotePropagationMode;
   private static int propagationMode;
   private static int nonInheritablePropagationMode;

   static synchronized void setFactory(Factory factory) {
      if (KernelStatus.isServer() && SINGLETON == DEFAULT_FACTORY && factory != null) {
         SINGLETON = factory;
      }
   }

   public static boolean isEnabled() {
      return contextEnabled;
   }

   static void setEnabled(boolean val) {
      contextEnabled = val;
   }

   static int getRemotePropagationMode() {
      return remotePropagationMode;
   }

   static int getPropagationMode() {
      return propagationMode;
   }

   static void setPropagationMode(int val) {
      propagationMode = val;
   }

   static int getNonInheritablePropagationMode() {
      return nonInheritablePropagationMode;
   }

   public Correlation provide() {
      return findOrCreateCorrelation();
   }

   public void dispose(Correlation correlation) {
   }

   /** @deprecated */
   @Deprecated
   public static Correlation findOrCreateCorrelation() {
      return findOrCreateCorrelation(contextEnabled);
   }

   public static Correlation findOrCreateCorrelation(boolean enabled) {
      if (!KernelStatus.isServer()) {
         enabled = true;
      }

      return SINGLETON.findOrCreateCorrelation(enabled);
   }

   public static Correlation findCorrelation() {
      if (KernelStatus.isServer()) {
         return SINGLETON.findOrCreateCorrelation(false);
      } else {
         DiagnosticContext dc = DiagnosticContextFactory.getDiagnosticContext();
         return dc == null ? null : new JavaSECorrelationImpl((DiagnosticContextImpl)dc);
      }
   }

   public static void invalidateCache() {
      SINGLETON.invalidateCache();
   }

   public static void setJFRThrottled(Correlation ctx) {
      SINGLETON.setJFRThrottled(ctx);
   }

   static void handleLocalContextAsNonInheritable() {
      SINGLETON.handleLocalContextAsNonInheritable();
   }

   static void setCorrelation(Correlation ctx) {
      SINGLETON.setCorrelation(ctx);
   }

   static void updateCorrelation(Correlation ctx) {
      SINGLETON.updateCorrelation(ctx);
   }

   static {
      SINGLETON = DEFAULT_FACTORY;
      remotePropagationMode = 124;
      propagationMode = 383;
      nonInheritablePropagationMode = 1;
   }

   private static class DefaultFactoryImpl implements Factory {
      private DefaultFactoryImpl() {
      }

      public Correlation findOrCreateCorrelation(boolean enabled) {
         if (KernelStatus.isServer()) {
            return null;
         } else {
            DiagnosticContext dc = DiagnosticContextFactory.findOrCreateDiagnosticContext(enabled);
            return dc == null ? null : new JavaSECorrelationImpl((DiagnosticContextImpl)dc);
         }
      }

      public void invalidateCache() {
      }

      public void setJFRThrottled(Correlation ctx) {
      }

      public void correlationPropagatedIn(Correlation ctx) {
      }

      public void setCorrelation(Correlation ctx) {
         if (ctx == null) {
            DiagnosticContextFactory.setDiagnosticContext((DiagnosticContext)null);
         }

         if (!(ctx instanceof JavaSECorrelationImpl)) {
            throw new UnsupportedOperationException("Operation not supported on client");
         } else {
            DiagnosticContextFactory.setDiagnosticContext(((JavaSECorrelationImpl)ctx).getDiagnosticContextImpl());
         }
      }

      public void updateCorrelation(Correlation ctx) {
         throw new UnsupportedOperationException("Operation not supported on client");
      }

      public void handleLocalContextAsNonInheritable() {
      }

      // $FF: synthetic method
      DefaultFactoryImpl(Object x0) {
         this();
      }
   }

   public interface Factory {
      Correlation findOrCreateCorrelation(boolean var1);

      void invalidateCache();

      void setJFRThrottled(Correlation var1);

      void correlationPropagatedIn(Correlation var1);

      void setCorrelation(Correlation var1);

      void updateCorrelation(Correlation var1);

      void handleLocalContextAsNonInheritable();
   }
}
