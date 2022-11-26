package weblogic.coherence.descriptor.wl;

public class CoherenceClusterDefaults {
   public static String getDefaultClusteringMode(CoherenceClusterParamsBean cpBean) {
      String defaultMode = "unicast";
      if (cpBean.getCoherenceClusterWellKnownAddresses().getCoherenceClusterWellKnownAddresses().length == 0 && cpBean.getMulticastListenAddress() != null && !cpBean.getMulticastListenAddress().isEmpty()) {
         defaultMode = "multicast";
      }

      return defaultMode;
   }
}
