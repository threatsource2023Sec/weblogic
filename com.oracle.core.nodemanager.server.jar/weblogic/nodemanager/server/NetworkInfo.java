package weblogic.nodemanager.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.InvalidPropertiesFormatException;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.utils.net.InetAddressHelper;

public class NetworkInfo {
   private InetAddress beginIPRange;
   private InetAddress endIPRange;
   private String interfaceName;
   private String netMaskOrPrefixLength;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();

   public NetworkInfo(String interfaceName, String netMaskOrPrefixLength, InetAddress beginIPRange, InetAddress endIPRange) {
      this.init(interfaceName, netMaskOrPrefixLength, beginIPRange, endIPRange);
   }

   public NetworkInfo(String interfaceName, String netMaskOrPrefixLength) {
      this.init(interfaceName, netMaskOrPrefixLength, (InetAddress)null, (InetAddress)null);
   }

   private void init(String interfaceName, String netMask, InetAddress beginIPRange, InetAddress endIPRange) {
      this.interfaceName = interfaceName;
      this.netMaskOrPrefixLength = netMask;
      this.beginIPRange = beginIPRange;
      this.endIPRange = endIPRange;
   }

   public boolean isNetworkInfoFor(InetAddress ip) {
      return this.beginIPRange == null && this.endIPRange == null || isGreaterThanEqual(ip, this.beginIPRange) && isGreaterThanEqual(this.endIPRange, ip);
   }

   private static boolean isGreaterThanEqual(InetAddress ip, InetAddress beginIP) {
      if (InetAddressHelper.isIPV6Address(ip.getHostAddress())) {
         return isGreaterThanEqualIPv6(ip, beginIP);
      } else {
         byte[] ipbytes = ip.getAddress();
         byte[] lowbytes = beginIP.getAddress();
         if (ipbytes.length == lowbytes.length) {
            for(int i = 0; i < ipbytes.length; ++i) {
               int ipVal = ipbytes[i] & 255;
               int lowVal = lowbytes[i] & 255;
               if (ipVal > lowVal || i == ipbytes.length - 1) {
                  return true;
               }

               if (ipVal < lowVal) {
                  break;
               }
            }
         }

         return false;
      }
   }

   private static boolean isGreaterThanEqualIPv6(InetAddress ip, InetAddress beginIP) {
      byte[] ipbytes = ip.getAddress();
      byte[] lowbytes = beginIP.getAddress();
      if (ipbytes.length == lowbytes.length) {
         for(int i = 0; i < ipbytes.length - 1; i += 2) {
            int ipVal = ((ipbytes[i] & 255) << 8) + (ipbytes[i + 1] & 255);
            int lowVal = ((lowbytes[i] & 255) << 8) + (lowbytes[i + 1] & 255);
            if (ipVal > lowVal || i == ipbytes.length - 2) {
               return true;
            }

            if (ipVal < lowVal) {
               break;
            }
         }
      }

      return false;
   }

   public String getInterfaceName() {
      return this.interfaceName;
   }

   public String getNetMaskOrPrefixLength() {
      return this.netMaskOrPrefixLength;
   }

   public static NetworkInfo convertConfEntry(String displayName, String confEntry) throws IOException {
      InetAddress minIP = null;
      InetAddress maxIP = null;
      String subNetMaskOrPrefixLength = null;
      String[] entryParts = confEntry.split(",");
      if (entryParts.length <= 2 && entryParts.length >= 1) {
         String ipRange = entryParts[0].trim();
         if (!ipRange.equals("*")) {
            String[] range = ipRange.split("-");
            if (range.length != 2) {
               throw new InvalidPropertiesFormatException(nmText.unknownIPRange(confEntry));
            }

            minIP = InetAddress.getByName(range[0]);
            maxIP = InetAddress.getByName(range[1]);
            if (!isGreaterThanEqual(maxIP, minIP)) {
               minIP = InetAddress.getByName(range[1]);
               maxIP = InetAddress.getByName(range[0]);
            }
         }

         if (entryParts.length == 2) {
            String masking = entryParts[1].trim();
            int equalsIndex = masking.indexOf("=");
            if (equalsIndex > 0 && equalsIndex < masking.length() - 1) {
               subNetMaskOrPrefixLength = masking.substring(masking.indexOf("=") + 1);
               if ("netmask".equals(masking.substring(0, masking.indexOf("=") - 1))) {
                  try {
                     subNetMaskOrPrefixLength = InetAddress.getByName(subNetMaskOrPrefixLength).getHostAddress();
                  } catch (UnknownHostException var10) {
                     throw new InvalidPropertiesFormatException(nmText.invalidNetMask(subNetMaskOrPrefixLength, confEntry) + var10.toString());
                  }
               }
            }
         }

         return new NetworkInfo(displayName, subNetMaskOrPrefixLength, minIP, maxIP);
      } else {
         throw new InvalidPropertiesFormatException(nmText.unknownIPRange(confEntry));
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.interfaceName);
      sb.append("=");
      sb.append(this.getPropertyValueString());
      return sb.toString();
   }

   public String getPropertyValueString() {
      StringBuffer sb = new StringBuffer();
      if (this.beginIPRange == null && this.endIPRange == null) {
         sb.append("*");
      } else if (this.beginIPRange != null && this.endIPRange != null) {
         sb.append(this.beginIPRange.getHostAddress());
         sb.append("-");
         sb.append(this.endIPRange.getHostAddress());
      }

      if (this.netMaskOrPrefixLength != null) {
         sb.append(",NetMask=");
         sb.append(this.netMaskOrPrefixLength);
      }

      return sb.toString();
   }
}
