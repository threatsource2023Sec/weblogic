package weblogic.timers;

public abstract class RuntimeDomainSelector {
   public static final RuntimeDomainSelector NULL = new NullRuntimeDomainSelector();
   public static final String DEFAULT_DOMAIN = "weblogic.timers.defaultDomain";
   private static RuntimeDomainSelector selector;

   public static String getDomain() {
      String domainId = selector.getRuntimeDomain();
      return domainId == null ? "weblogic.timers.defaultDomain" : domainId;
   }

   public static boolean isDefaultDomain(String domainId) {
      return "weblogic.timers.defaultDomain".equals(domainId);
   }

   public static void setSelector(RuntimeDomainSelector selector) {
      RuntimeDomainSelector.selector = selector;
   }

   public abstract String getRuntimeDomain();

   static {
      selector = NULL;
   }

   private static class NullRuntimeDomainSelector extends RuntimeDomainSelector {
      private NullRuntimeDomainSelector() {
      }

      public String getRuntimeDomain() {
         return "global";
      }

      // $FF: synthetic method
      NullRuntimeDomainSelector(Object x0) {
         this();
      }
   }
}
