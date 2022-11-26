package org.python.netty.util.internal;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.python.netty.util.NetUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class MacAddressUtil {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(MacAddressUtil.class);
   private static final int EUI64_MAC_ADDRESS_LENGTH = 8;
   private static final int EUI48_MAC_ADDRESS_LENGTH = 6;

   public static byte[] bestAvailableMac() {
      byte[] bestMacAddr = EmptyArrays.EMPTY_BYTES;
      InetAddress bestInetAddr = NetUtil.LOCALHOST4;
      Map ifaces = new LinkedHashMap();

      InetAddress inetAddr;
      try {
         Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
         if (interfaces != null) {
            while(interfaces.hasMoreElements()) {
               NetworkInterface iface = (NetworkInterface)interfaces.nextElement();
               Enumeration addrs = SocketUtils.addressesFromNetworkInterface(iface);
               if (addrs.hasMoreElements()) {
                  inetAddr = (InetAddress)addrs.nextElement();
                  if (!inetAddr.isLoopbackAddress()) {
                     ifaces.put(iface, inetAddr);
                  }
               }
            }
         }
      } catch (SocketException var11) {
         logger.warn("Failed to retrieve the list of available network interfaces", (Throwable)var11);
      }

      Iterator var12 = ifaces.entrySet().iterator();

      while(true) {
         NetworkInterface iface;
         do {
            if (!var12.hasNext()) {
               if (bestMacAddr == EmptyArrays.EMPTY_BYTES) {
                  return null;
               }

               switch (bestMacAddr.length) {
                  case 6:
                     byte[] newAddr = new byte[8];
                     System.arraycopy(bestMacAddr, 0, newAddr, 0, 3);
                     newAddr[3] = -1;
                     newAddr[4] = -2;
                     System.arraycopy(bestMacAddr, 3, newAddr, 5, 3);
                     bestMacAddr = newAddr;
                     break;
                  default:
                     bestMacAddr = Arrays.copyOf(bestMacAddr, 8);
               }

               return bestMacAddr;
            }

            Map.Entry entry = (Map.Entry)var12.next();
            iface = (NetworkInterface)entry.getKey();
            inetAddr = (InetAddress)entry.getValue();
         } while(iface.isVirtual());

         byte[] macAddr;
         try {
            macAddr = SocketUtils.hardwareAddressFromNetworkInterface(iface);
         } catch (SocketException var10) {
            logger.debug("Failed to get the hardware address of a network interface: {}", iface, var10);
            continue;
         }

         boolean replace = false;
         int res = compareAddresses(bestMacAddr, macAddr);
         if (res < 0) {
            replace = true;
         } else if (res == 0) {
            res = compareAddresses((InetAddress)bestInetAddr, (InetAddress)inetAddr);
            if (res < 0) {
               replace = true;
            } else if (res == 0 && bestMacAddr.length < macAddr.length) {
               replace = true;
            }
         }

         if (replace) {
            bestMacAddr = macAddr;
            bestInetAddr = inetAddr;
         }
      }
   }

   public static byte[] defaultMachineId() {
      byte[] bestMacAddr = bestAvailableMac();
      if (bestMacAddr == null) {
         bestMacAddr = new byte[8];
         PlatformDependent.threadLocalRandom().nextBytes(bestMacAddr);
         logger.warn("Failed to find a usable hardware address from the network interfaces; using random bytes: {}", (Object)formatAddress(bestMacAddr));
      }

      return bestMacAddr;
   }

   public static byte[] parseMAC(String value) {
      char separator;
      byte[] machineId;
      switch (value.length()) {
         case 17:
            separator = value.charAt(2);
            validateMacSeparator(separator);
            machineId = new byte[6];
            break;
         case 23:
            separator = value.charAt(2);
            validateMacSeparator(separator);
            machineId = new byte[8];
            break;
         default:
            throw new IllegalArgumentException("value is not supported [MAC-48, EUI-48, EUI-64]");
      }

      int end = machineId.length - 1;
      int j = 0;

      for(int i = 0; i < end; j += 3) {
         int sIndex = j + 2;
         machineId[i] = (byte)Integer.parseInt(value.substring(j, sIndex), 16);
         if (value.charAt(sIndex) != separator) {
            throw new IllegalArgumentException("expected separator '" + separator + " but got '" + value.charAt(sIndex) + "' at index: " + sIndex);
         }

         ++i;
      }

      machineId[end] = (byte)Integer.parseInt(value.substring(j, value.length()), 16);
      return machineId;
   }

   private static void validateMacSeparator(char separator) {
      if (separator != ':' && separator != '-') {
         throw new IllegalArgumentException("unsupported separator: " + separator + " (expected: [:-])");
      }
   }

   public static String formatAddress(byte[] addr) {
      StringBuilder buf = new StringBuilder(24);
      byte[] var2 = addr;
      int var3 = addr.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         byte b = var2[var4];
         buf.append(String.format("%02x:", b & 255));
      }

      return buf.substring(0, buf.length() - 1);
   }

   static int compareAddresses(byte[] current, byte[] candidate) {
      if (candidate != null && candidate.length >= 6) {
         boolean onlyZeroAndOne = true;
         byte[] var3 = candidate;
         int var4 = candidate.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            if (b != 0 && b != 1) {
               onlyZeroAndOne = false;
               break;
            }
         }

         if (onlyZeroAndOne) {
            return 1;
         } else if ((candidate[0] & 1) != 0) {
            return 1;
         } else if ((candidate[0] & 2) == 0) {
            return current.length != 0 && (current[0] & 2) == 0 ? 0 : -1;
         } else {
            return current.length != 0 && (current[0] & 2) == 0 ? 1 : 0;
         }
      } else {
         return 1;
      }
   }

   private static int compareAddresses(InetAddress current, InetAddress candidate) {
      return scoreAddress(current) - scoreAddress(candidate);
   }

   private static int scoreAddress(InetAddress addr) {
      if (!addr.isAnyLocalAddress() && !addr.isLoopbackAddress()) {
         if (addr.isMulticastAddress()) {
            return 1;
         } else if (addr.isLinkLocalAddress()) {
            return 2;
         } else {
            return addr.isSiteLocalAddress() ? 3 : 4;
         }
      } else {
         return 0;
      }
   }

   private MacAddressUtil() {
   }
}
