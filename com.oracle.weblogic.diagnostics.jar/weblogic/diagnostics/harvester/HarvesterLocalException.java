package weblogic.diagnostics.harvester;

import weblogic.diagnostics.type.DiagnosticException;

public abstract class HarvesterLocalException extends DiagnosticException {
   private HarvesterLocalException() {
   }

   private HarvesterLocalException(String msg) {
      super(msg);
   }

   // $FF: synthetic method
   HarvesterLocalException(String x0, Object x1) {
      this(x0);
   }

   public static final class DuplicateProviderName extends HarvesterLocalException {
      private String providerName = null;

      public String getProviderNames() {
         return this.providerName;
      }

      public DuplicateProviderName(String provider) {
         super(getExplanation(provider), null);
         this.providerName = provider;
      }

      private static String getExplanation(String provider) {
         return I18NSupport.formatter().getDuplicateProviderMessage(provider);
      }
   }
}
