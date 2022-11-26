package weblogic.management.configuration;

import java.util.StringTokenizer;
import javax.management.InvalidAttributeValueException;
import weblogic.logging.Loggable;
import weblogic.utils.net.InetAddressHelper;
import weblogic.wtc.WTCLogger;

public final class WTCLegalHelper {
   public static boolean isMinLteMax(WTCLocalTuxDomMBean ltdMBean, String minv) throws InvalidAttributeValueException {
      String maxv = ltdMBean.getMaxEncryptBits();
      if (Integer.parseInt(minv, 10) > Integer.parseInt(maxv, 10)) {
         String apid = ltdMBean.getAccessPointId();
         Loggable le = WTCLogger.logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable("Local", apid);
         throw new InvalidAttributeValueException(le.getMessage());
      } else {
         return true;
      }
   }

   public static boolean isMinLteMax(WTCRemoteTuxDomMBean rtdMBean, String minv) throws InvalidAttributeValueException {
      String maxv = rtdMBean.getMaxEncryptBits();
      if (Integer.parseInt(minv, 10) > Integer.parseInt(maxv, 10)) {
         String apid = rtdMBean.getAccessPointId();
         Loggable le = WTCLogger.logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable("Remote", apid);
         throw new InvalidAttributeValueException(le.getMessage());
      } else {
         return true;
      }
   }

   public static boolean isMaxGteMin(WTCLocalTuxDomMBean ltdMBean, String maxv) throws InvalidAttributeValueException {
      String minv = ltdMBean.getMinEncryptBits();
      if (Integer.parseInt(maxv, 10) < Integer.parseInt(minv, 10)) {
         String apid = ltdMBean.getAccessPointId();
         Loggable le = WTCLogger.logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable("Local", apid);
         throw new InvalidAttributeValueException(le.getMessage());
      } else {
         return true;
      }
   }

   public static boolean isMaxGteMin(WTCRemoteTuxDomMBean rtdMBean, String maxv) throws InvalidAttributeValueException {
      String minv = rtdMBean.getMinEncryptBits();
      if (Integer.parseInt(maxv, 10) < Integer.parseInt(minv, 10)) {
         String apid = rtdMBean.getAccessPointId();
         Loggable le = WTCLogger.logMinEncryptBitsGreaterThanMaxEncryptBitsLoggable("Remote", apid);
         throw new InvalidAttributeValueException(le.getMessage());
      } else {
         return true;
      }
   }

   public static boolean checkNWAddrFormat(String mbname, String nwaddr) throws InvalidAttributeValueException {
      if (nwaddr != null && nwaddr.toLowerCase().startsWith("sdp://")) {
         nwaddr = nwaddr.substring(4);
      }

      if (InetAddressHelper.isIPV6Address(nwaddr)) {
         return true;
      } else {
         boolean good_addr = true;
         int colon_index = 0;
         if (nwaddr != null) {
            colon_index = nwaddr.indexOf(58);
         }

         if (nwaddr == null || !nwaddr.startsWith("//") || colon_index < 3 || colon_index + 1 >= nwaddr.length()) {
            good_addr = false;
         }

         if (good_addr) {
            String ipaddress = nwaddr.substring(2, colon_index);
            if (Character.isDigit(ipaddress.charAt(0))) {
               int count = 0;
               int intval = false;
               String strval = null;

               for(StringTokenizer st = new StringTokenizer(ipaddress, "."); st.hasMoreTokens(); ++count) {
                  strval = st.nextToken();

                  try {
                     int intval = Integer.parseInt(strval, 10);
                     if (intval < 0 || intval > 255) {
                        good_addr = false;
                        break;
                     }
                  } catch (NumberFormatException var11) {
                     good_addr = false;
                     break;
                  }
               }

               if (count != 4) {
                  good_addr = false;
               }
            }
         }

         if (good_addr) {
            try {
               if (Integer.parseInt(nwaddr.substring(colon_index + 1), 10) < 0) {
                  good_addr = false;
               }
            } catch (NumberFormatException var10) {
               good_addr = false;
            }
         }

         if (!good_addr) {
            Loggable le = WTCLogger.logInvalidMBeanAttrLoggable("NWAddr", mbname);
            throw new InvalidAttributeValueException(le.getMessage());
         } else {
            return true;
         }
      }
   }

   public static boolean isNWAddrFormat(String mbname, String nwaddr) throws InvalidAttributeValueException {
      if (nwaddr == null) {
         return checkNWAddrFormat(mbname, nwaddr);
      } else {
         StringTokenizer tok_nwaddr = new StringTokenizer(nwaddr, ",");
         int count = tok_nwaddr.countTokens();
         String[] tok = new String[count];

         for(int i = 0; i < count; ++i) {
            tok[i] = tok_nwaddr.nextToken();
            if (!checkNWAddrFormat(mbname, tok[i])) {
               return false;
            }
         }

         return true;
      }
   }

   public static void validateWTCLocalTuxDom(WTCLocalTuxDomMBean dom) throws IllegalArgumentException {
      String name = dom.getName();
      String nwAddr = dom.getNWAddr();
      String minEncryptBits = dom.getMinEncryptBits();
      String maxEncryptBits = dom.getMaxEncryptBits();

      try {
         if (!isNWAddrFormat(name, nwAddr)) {
            throw new IllegalArgumentException("Invalid value for NWAddr: " + nwAddr);
         } else {
            String msg;
            if (!isMinLteMax(dom, minEncryptBits)) {
               msg = "Invalid value for MinEncryptBits: " + minEncryptBits;
               throw new IllegalArgumentException(msg);
            } else if (!isMaxGteMin(dom, maxEncryptBits)) {
               msg = "Invalid value for MaxEncryptBits: " + maxEncryptBits;
               throw new IllegalArgumentException(msg);
            }
         }
      } catch (InvalidAttributeValueException var6) {
         throw new IllegalArgumentException(var6.getMessage());
      }
   }

   public static void validateWTCRemoteTuxDom(WTCRemoteTuxDomMBean dom) throws IllegalArgumentException {
      String name = dom.getName();
      String nwAddr = dom.getNWAddr();
      String minEncryptBits = dom.getMinEncryptBits();
      String maxEncryptBits = dom.getMaxEncryptBits();

      try {
         if (!isNWAddrFormat(name, nwAddr)) {
            throw new IllegalArgumentException("Invalid value for NWAddr: " + nwAddr);
         } else {
            String msg;
            if (!isMinLteMax(dom, minEncryptBits)) {
               msg = "Invalid value for MinEncryptBits: " + minEncryptBits;
               throw new IllegalArgumentException(msg);
            } else if (!isMaxGteMin(dom, maxEncryptBits)) {
               msg = "Invalid value for MaxEncryptBits: " + maxEncryptBits;
               throw new IllegalArgumentException(msg);
            }
         }
      } catch (InvalidAttributeValueException var6) {
         throw new IllegalArgumentException(var6.getMessage());
      }
   }
}
