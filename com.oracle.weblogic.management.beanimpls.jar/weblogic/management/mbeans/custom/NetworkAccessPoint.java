package weblogic.management.mbeans.custom;

import java.util.Locale;
import weblogic.descriptor.SettableBean;
import weblogic.management.configuration.KernelMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.protocol.ProtocolManager;

public final class NetworkAccessPoint {
   private static final long serialVersionUID = 7259163894528106650L;
   private static final String LISTEN_ADDRESS = "ListenAddress";
   private static final String PUBLIC_ADDRESS = "PublicAddress";
   private static final String EXTERNAL_ADDRESS = "ExternalDNSName";
   private static final String CLUSTER_ADDRESS = "ClusterAddress";

   public static String getPublicAddress(NetworkAccessPointMBean impl) {
      if (impl.isSet("ExternalDNSName")) {
         return impl.getExternalDNSName();
      } else if (impl.isSet("ListenAddress")) {
         return impl.getListenAddress();
      } else {
         return ((SettableBean)impl.getParent()).isSet("ExternalDNSName") ? ((ServerTemplateMBean)impl.getParent()).getExternalDNSName() : impl.getListenAddress();
      }
   }

   public static String getClusterAddress(NetworkAccessPointMBean impl) {
      if (impl.isSet("PublicAddress")) {
         return impl.getPublicAddress();
      } else if (impl.isSet("ExternalDNSName")) {
         return impl.getExternalDNSName();
      } else {
         return ((ServerTemplateMBean)impl.getParent()).getCluster() != null && ((ServerTemplateMBean)impl.getParent()).getCluster().isSet("ClusterAddress") ? ((ServerTemplateMBean)impl.getParent()).getCluster().getClusterAddress() : impl.getListenAddress();
      }
   }

   public static int getMaxMessageSize(NetworkAccessPointMBean impl) {
      String protocol = impl.getProtocol().toUpperCase(Locale.US);
      if (protocol.endsWith("S")) {
         protocol = protocol.substring(0, protocol.length() - 1);
      }

      int val = -1;
      if (protocol.equals("HTTP")) {
         val = ((KernelMBean)impl.getParent()).getMaxHTTPMessageSize();
      } else if (protocol.equals("T3")) {
         val = ((KernelMBean)impl.getParent()).getMaxT3MessageSize();
      } else if (protocol.equals("IIOP")) {
         val = ((KernelMBean)impl.getParent()).getMaxIIOPMessageSize();
      } else if (protocol.equals("COM")) {
         val = ((KernelMBean)impl.getParent()).getMaxCOMMessageSize();
      }

      if (val == -1) {
         val = ((KernelMBean)impl.getParent()).getMaxMessageSize();
      }

      return val;
   }

   public static boolean isSecure(NetworkAccessPointMBean nap) {
      byte qos = ProtocolManager.getProtocolByName(nap.getProtocol()).getQOS();
      return qos == 102 || qos == 103;
   }
}
