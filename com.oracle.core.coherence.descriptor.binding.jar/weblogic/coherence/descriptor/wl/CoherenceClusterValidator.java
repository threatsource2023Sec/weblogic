package weblogic.coherence.descriptor.wl;

public class CoherenceClusterValidator {
   public static void validateCoherenceCluster(WeblogicCoherenceBean bean) throws IllegalArgumentException {
      if (bean.getName() == null) {
         throw new IllegalArgumentException("WeblogicCoherenceBean: Name cannot be null");
      }
   }

   public static void validateCoherenceClusterParams(CoherenceClusterParamsBean bean) throws IllegalArgumentException {
      String transportType = bean.getTransport();
      if (bean.getClusteringMode().equals("multicast") && transportType.equals("ssl")) {
         throw new IllegalArgumentException("CoherenceClusterParamsBean: Invalid transport. SSL cannot be used with multicast clustering");
      }
   }

   public static void validateWKAMember(CoherenceClusterWellKnownAddressBean wkaMember) {
      String listenAddress = wkaMember.getListenAddress();
      String missingFields = listenAddress != null && !listenAddress.isEmpty() ? "" : "listenAddress";
      if (!missingFields.isEmpty()) {
         throw new IllegalArgumentException("CoherenceClusterWellKnownAddressBean: Required field(s) [" + missingFields.trim() + "] were not provided");
      }
   }

   public static void validateSocketAddress(CoherenceSocketAddressBean sMember) {
      String address = sMember.getAddress();
      int port = sMember.getPort();
      String missingFields = address != null && !address.isEmpty() ? "" : "address";
      missingFields = missingFields + (port < 1 ? " port" : "");
      if (!missingFields.isEmpty()) {
         throw new IllegalArgumentException("CoherenceSocketAddressBean: Required field(s) [" + missingFields.trim() + "] were not provided");
      }
   }
}
