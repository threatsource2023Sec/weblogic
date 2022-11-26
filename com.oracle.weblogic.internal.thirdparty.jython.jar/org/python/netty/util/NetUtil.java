package org.python.netty.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SocketUtils;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class NetUtil {
   public static final Inet4Address LOCALHOST4;
   public static final Inet6Address LOCALHOST6;
   public static final InetAddress LOCALHOST;
   public static final NetworkInterface LOOPBACK_IF;
   public static final int SOMAXCONN;
   private static final int IPV6_WORD_COUNT = 8;
   private static final int IPV6_MAX_CHAR_COUNT = 39;
   private static final int IPV6_BYTE_COUNT = 16;
   private static final int IPV6_MAX_CHAR_BETWEEN_SEPARATOR = 4;
   private static final int IPV6_MIN_SEPARATORS = 2;
   private static final int IPV6_MAX_SEPARATORS = 8;
   private static final int IPV4_BYTE_COUNT = 4;
   private static final int IPV4_MAX_CHAR_BETWEEN_SEPARATOR = 3;
   private static final int IPV4_SEPARATORS = 3;
   private static final boolean IPV4_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv4Stack", false);
   private static final boolean IPV6_ADDRESSES_PREFERRED = SystemPropertyUtil.getBoolean("java.net.preferIPv6Addresses", false);
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NetUtil.class);

   private static Integer sysctlGetInt(String sysctlKey) throws IOException {
      Process process = (new ProcessBuilder(new String[]{"sysctl", sysctlKey})).start();

      Object i;
      try {
         InputStream is = process.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader br = new BufferedReader(isr);

         try {
            String line = br.readLine();
            if (line.startsWith(sysctlKey)) {
               for(i = line.length() - 1; i > sysctlKey.length(); --i) {
                  if (!Character.isDigit(line.charAt((int)i))) {
                     Integer var7 = Integer.valueOf(line.substring(i + 1, line.length()));
                     return var7;
                  }
               }
            }

            i = null;
         } finally {
            br.close();
         }
      } finally {
         if (process != null) {
            process.destroy();
         }

      }

      return (Integer)i;
   }

   public static boolean isIpV4StackPreferred() {
      return IPV4_PREFERRED;
   }

   public static boolean isIpV6AddressesPreferred() {
      return IPV6_ADDRESSES_PREFERRED;
   }

   public static byte[] createByteArrayFromIpAddressString(String ipAddressString) {
      if (!isValidIpV4Address(ipAddressString)) {
         if (isValidIpV6Address(ipAddressString)) {
            if (ipAddressString.charAt(0) == '[') {
               ipAddressString = ipAddressString.substring(1, ipAddressString.length() - 1);
            }

            int percentPos = ipAddressString.indexOf(37);
            if (percentPos >= 0) {
               ipAddressString = ipAddressString.substring(0, percentPos);
            }

            return getIPv6ByName(ipAddressString, true);
         } else {
            return null;
         }
      } else {
         StringTokenizer tokenizer = new StringTokenizer(ipAddressString, ".");
         byte[] byteAddress = new byte[4];

         for(int i = 0; i < 4; ++i) {
            String token = tokenizer.nextToken();
            int tempInt = Integer.parseInt(token);
            byteAddress[i] = (byte)tempInt;
         }

         return byteAddress;
      }
   }

   private static int getIntValue(char c) {
      switch (c) {
         case '0':
            return 0;
         case '1':
            return 1;
         case '2':
            return 2;
         case '3':
            return 3;
         case '4':
            return 4;
         case '5':
            return 5;
         case '6':
            return 6;
         case '7':
            return 7;
         case '8':
            return 8;
         case '9':
            return 9;
         default:
            c = Character.toLowerCase(c);
            switch (c) {
               case 'a':
                  return 10;
               case 'b':
                  return 11;
               case 'c':
                  return 12;
               case 'd':
                  return 13;
               case 'e':
                  return 14;
               case 'f':
                  return 15;
               default:
                  return 0;
            }
      }
   }

   public static String intToIpAddress(int i) {
      StringBuilder buf = new StringBuilder(15);
      buf.append(i >> 24 & 255);
      buf.append('.');
      buf.append(i >> 16 & 255);
      buf.append('.');
      buf.append(i >> 8 & 255);
      buf.append('.');
      buf.append(i & 255);
      return buf.toString();
   }

   public static String bytesToIpAddress(byte[] bytes) {
      return bytesToIpAddress(bytes, 0, bytes.length);
   }

   public static String bytesToIpAddress(byte[] bytes, int offset, int length) {
      switch (length) {
         case 4:
            return (new StringBuilder(15)).append(bytes[offset] & 255).append('.').append(bytes[offset + 1] & 255).append('.').append(bytes[offset + 2] & 255).append('.').append(bytes[offset + 3] & 255).toString();
         case 16:
            return toAddressString(bytes, offset, false);
         default:
            throw new IllegalArgumentException("length: " + length + " (expected: 4 or 16)");
      }
   }

   public static boolean isValidIpV6Address(String ipAddress) {
      boolean doubleColon = false;
      int numberOfColons = 0;
      int numberOfPeriods = 0;
      StringBuilder word = new StringBuilder();
      char c = 0;
      int startOffset = 0;
      int endOffset = ipAddress.length();
      if (endOffset < 2) {
         return false;
      } else {
         if (ipAddress.charAt(0) == '[') {
            if (ipAddress.charAt(endOffset - 1) != ']') {
               return false;
            }

            startOffset = 1;
            --endOffset;
         }

         int percentIdx = ipAddress.indexOf(37, startOffset);
         if (percentIdx >= 0) {
            endOffset = percentIdx;
         }

         for(int i = startOffset; i < endOffset; ++i) {
            char prevChar = c;
            c = ipAddress.charAt(i);
            switch (c) {
               case '.':
                  ++numberOfPeriods;
                  if (numberOfPeriods > 3) {
                     return false;
                  }

                  if (numberOfPeriods == 1) {
                     int j = i - word.length() - 2;
                     int beginColonIndex = ipAddress.lastIndexOf(58, j);
                     if (beginColonIndex == -1) {
                        return false;
                     }

                     char tmpChar = ipAddress.charAt(j);
                     if (isValidIPv4MappedChar(tmpChar)) {
                        if (j - beginColonIndex != 4 || !isValidIPv4MappedChar(ipAddress.charAt(j - 1)) || !isValidIPv4MappedChar(ipAddress.charAt(j - 2)) || !isValidIPv4MappedChar(ipAddress.charAt(j - 3))) {
                           return false;
                        }

                        j -= 5;
                     } else {
                        if (tmpChar != '0' && tmpChar != ':') {
                           return false;
                        }

                        --j;
                     }

                     if (numberOfColons != 6 && !doubleColon || numberOfColons > 7 || numberOfColons == 7 && (ipAddress.charAt(startOffset) != ':' || ipAddress.charAt(1 + startOffset) != ':')) {
                        return false;
                     }

                     while(j >= startOffset) {
                        tmpChar = ipAddress.charAt(j);
                        if (tmpChar != '0' && tmpChar != ':') {
                           return false;
                        }

                        --j;
                     }
                  }

                  if (!isValidIp4Word(word.toString())) {
                     return false;
                  }

                  word.delete(0, word.length());
                  break;
               case ':':
                  if (i == startOffset && (endOffset <= i || ipAddress.charAt(i + 1) != ':')) {
                     return false;
                  }

                  ++numberOfColons;
                  if (numberOfColons > 8) {
                     return false;
                  }

                  if (numberOfPeriods > 0) {
                     return false;
                  }

                  if (prevChar == ':') {
                     if (doubleColon) {
                        return false;
                     }

                     doubleColon = true;
                  }

                  word.delete(0, word.length());
                  break;
               default:
                  if (word != null && word.length() > 3) {
                     return false;
                  }

                  if (!isValidHexChar(c)) {
                     return false;
                  }

                  word.append(c);
            }
         }

         if (numberOfPeriods > 0) {
            if (numberOfPeriods != 3 || !isValidIp4Word(word.toString()) || numberOfColons >= 7 && !doubleColon) {
               return false;
            }
         } else {
            if (numberOfColons != 7 && !doubleColon) {
               return false;
            }

            if (word.length() == 0) {
               if (ipAddress.charAt(endOffset - 1) == ':' && ipAddress.charAt(endOffset - 2) != ':') {
                  return false;
               }
            } else if (numberOfColons == 8 && ipAddress.charAt(startOffset) != ':') {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean isValidIp4Word(String word) {
      if (word.length() >= 1 && word.length() <= 3) {
         for(int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (c < '0' || c > '9') {
               return false;
            }
         }

         return Integer.parseInt(word) <= 255;
      } else {
         return false;
      }
   }

   private static boolean isValidHexChar(char c) {
      return c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f';
   }

   private static boolean isValidNumericChar(char c) {
      return c >= '0' && c <= '9';
   }

   private static boolean isValidIPv4MappedChar(char c) {
      return c == 'f' || c == 'F';
   }

   private static boolean isValidIPv4MappedSeparators(byte b0, byte b1, boolean mustBeZero) {
      return b0 == b1 && (b0 == 0 || !mustBeZero && b1 == -1);
   }

   private static boolean isValidIPv4Mapped(byte[] bytes, int currentIndex, int compressBegin, int compressLength) {
      boolean mustBeZero = compressBegin + compressLength >= 14;
      return currentIndex <= 12 && currentIndex >= 2 && (!mustBeZero || compressBegin < 12) && isValidIPv4MappedSeparators(bytes[currentIndex - 1], bytes[currentIndex - 2], mustBeZero) && PlatformDependent.isZero(bytes, 0, currentIndex - 3);
   }

   public static boolean isValidIpV4Address(String value) {
      int periods = 0;
      int length = value.length();
      if (length > 15) {
         return false;
      } else {
         StringBuilder word = new StringBuilder();

         for(int i = 0; i < length; ++i) {
            char c = value.charAt(i);
            if (c == '.') {
               ++periods;
               if (periods > 3) {
                  return false;
               }

               if (word.length() == 0) {
                  return false;
               }

               if (Integer.parseInt(word.toString()) > 255) {
                  return false;
               }

               word.delete(0, word.length());
            } else {
               if (!Character.isDigit(c)) {
                  return false;
               }

               if (word.length() > 2) {
                  return false;
               }

               word.append(c);
            }
         }

         if (word.length() != 0 && Integer.parseInt(word.toString()) <= 255) {
            return periods == 3;
         } else {
            return false;
         }
      }
   }

   public static Inet6Address getByName(CharSequence ip) {
      return getByName(ip, true);
   }

   public static Inet6Address getByName(CharSequence ip, boolean ipv4Mapped) {
      byte[] bytes = getIPv6ByName(ip, ipv4Mapped);
      if (bytes == null) {
         return null;
      } else {
         try {
            return Inet6Address.getByAddress((String)null, bytes, -1);
         } catch (UnknownHostException var4) {
            throw new RuntimeException(var4);
         }
      }
   }

   private static byte[] getIPv6ByName(CharSequence ip, boolean ipv4Mapped) {
      byte[] bytes = new byte[16];
      int ipLength = ip.length();
      int compressBegin = 0;
      int compressLength = 0;
      int currentIndex = 0;
      int value = 0;
      int begin = -1;
      int i = 0;
      int ipv6Separators = 0;
      int ipv4Separators = 0;

      boolean needsShift;
      int tmp;
      for(needsShift = false; i < ipLength; ++i) {
         char c = ip.charAt(i);
         switch (c) {
            case '.':
               ++ipv4Separators;
               tmp = i - begin;
               if (tmp <= 3 && begin >= 0 && ipv4Separators <= 3 && (ipv6Separators <= 0 || currentIndex + compressLength >= 12) && i + 1 < ipLength && currentIndex < bytes.length && (ipv4Separators != 1 || ipv4Mapped && (currentIndex == 0 || isValidIPv4Mapped(bytes, currentIndex, compressBegin, compressLength)) && (tmp != 3 || isValidNumericChar(ip.charAt(i - 1)) && isValidNumericChar(ip.charAt(i - 2)) && isValidNumericChar(ip.charAt(i - 3))) && (tmp != 2 || isValidNumericChar(ip.charAt(i - 1)) && isValidNumericChar(ip.charAt(i - 2))) && (tmp != 1 || isValidNumericChar(ip.charAt(i - 1))))) {
                  value <<= 3 - tmp << 2;
                  begin = (value & 15) * 100 + (value >> 4 & 15) * 10 + (value >> 8 & 15);
                  if (begin >= 0 && begin <= 255) {
                     bytes[currentIndex++] = (byte)begin;
                     value = 0;
                     begin = -1;
                     break;
                  }

                  return null;
               }

               return null;
            case ':':
               ++ipv6Separators;
               if (i - begin > 4 || ipv4Separators > 0 || ipv6Separators > 8 || currentIndex + 1 >= bytes.length) {
                  return null;
               }

               value <<= 4 - (i - begin) << 2;
               if (compressLength > 0) {
                  compressLength -= 2;
               }

               bytes[currentIndex++] = (byte)((value & 15) << 4 | value >> 4 & 15);
               bytes[currentIndex++] = (byte)((value >> 8 & 15) << 4 | value >> 12 & 15);
               tmp = i + 1;
               if (tmp < ipLength && ip.charAt(tmp) == ':') {
                  ++tmp;
                  if (compressBegin != 0 || tmp < ipLength && ip.charAt(tmp) == ':') {
                     return null;
                  }

                  ++ipv6Separators;
                  needsShift = ipv6Separators == 2 && value == 0;
                  compressBegin = currentIndex;
                  compressLength = bytes.length - currentIndex - 2;
                  ++i;
               }

               value = 0;
               begin = -1;
               break;
            default:
               if (!isValidHexChar(c) || ipv4Separators > 0 && !isValidNumericChar(c)) {
                  return null;
               }

               if (begin < 0) {
                  begin = i;
               } else if (i - begin > 4) {
                  return null;
               }

               value += getIntValue(c) << (i - begin << 2);
         }
      }

      boolean isCompressed = compressBegin > 0;
      if (ipv4Separators > 0) {
         if (begin > 0 && i - begin > 3 || ipv4Separators != 3 || currentIndex >= bytes.length) {
            return null;
         }

         if (ipv6Separators == 0) {
            compressLength = 12;
         } else {
            if (ipv6Separators < 2 || (isCompressed || ipv6Separators != 6 || ip.charAt(0) == ':') && (!isCompressed || ipv6Separators >= 8 || ip.charAt(0) == ':' && compressBegin > 2)) {
               return null;
            }

            compressLength -= 2;
         }

         value <<= 3 - (i - begin) << 2;
         begin = (value & 15) * 100 + (value >> 4 & 15) * 10 + (value >> 8 & 15);
         if (begin < 0 || begin > 255) {
            return null;
         }

         bytes[currentIndex++] = (byte)begin;
      } else {
         tmp = ipLength - 1;
         if (begin > 0 && i - begin > 4 || ipv6Separators < 2 || !isCompressed && (ipv6Separators + 1 != 8 || ip.charAt(0) == ':' || ip.charAt(tmp) == ':') || isCompressed && (ipv6Separators > 8 || ipv6Separators == 8 && (compressBegin <= 2 && ip.charAt(0) != ':' || compressBegin >= 14 && ip.charAt(tmp) != ':')) || currentIndex + 1 >= bytes.length || begin < 0 && ip.charAt(tmp - 1) != ':' || compressBegin > 2 && ip.charAt(0) == ':') {
            return null;
         }

         if (begin >= 0 && i - begin <= 4) {
            value <<= 4 - (i - begin) << 2;
         }

         bytes[currentIndex++] = (byte)((value & 15) << 4 | value >> 4 & 15);
         bytes[currentIndex++] = (byte)((value >> 8 & 15) << 4 | value >> 12 & 15);
      }

      i = currentIndex + compressLength;
      if (!needsShift && i < bytes.length) {
         for(i = 0; i < compressLength; ++i) {
            begin = i + compressBegin;
            currentIndex = begin + compressLength;
            if (currentIndex >= bytes.length) {
               break;
            }

            bytes[currentIndex] = bytes[begin];
            bytes[begin] = 0;
         }
      } else {
         if (i >= bytes.length) {
            ++compressBegin;
         }

         for(i = currentIndex; i < bytes.length; ++i) {
            for(begin = bytes.length - 1; begin >= compressBegin; --begin) {
               bytes[begin] = bytes[begin - 1];
            }

            bytes[begin] = 0;
            ++compressBegin;
         }
      }

      if (ipv4Separators > 0) {
         bytes[10] = bytes[11] = -1;
      }

      return bytes;
   }

   public static String toSocketAddressString(InetSocketAddress addr) {
      String port = String.valueOf(addr.getPort());
      StringBuilder sb;
      if (addr.isUnresolved()) {
         String hostString = PlatformDependent.javaVersion() >= 7 ? addr.getHostString() : addr.getHostName();
         sb = newSocketAddressStringBuilder(hostString, port, !isValidIpV6Address(hostString));
      } else {
         InetAddress address = addr.getAddress();
         String hostString = toAddressString(address);
         sb = newSocketAddressStringBuilder(hostString, port, address instanceof Inet4Address);
      }

      return sb.append(':').append(port).toString();
   }

   public static String toSocketAddressString(String host, int port) {
      String portStr = String.valueOf(port);
      return newSocketAddressStringBuilder(host, portStr, !isValidIpV6Address(host)).append(':').append(portStr).toString();
   }

   private static StringBuilder newSocketAddressStringBuilder(String host, String port, boolean ipv4) {
      int hostLen = host.length();
      if (ipv4) {
         return (new StringBuilder(hostLen + 1 + port.length())).append(host);
      } else {
         StringBuilder stringBuilder = new StringBuilder(hostLen + 3 + port.length());
         return hostLen > 1 && host.charAt(0) == '[' && host.charAt(hostLen - 1) == ']' ? stringBuilder.append(host) : stringBuilder.append('[').append(host).append(']');
      }
   }

   public static String toAddressString(InetAddress ip) {
      return toAddressString(ip, false);
   }

   public static String toAddressString(InetAddress ip, boolean ipv4Mapped) {
      if (ip instanceof Inet4Address) {
         return ip.getHostAddress();
      } else if (!(ip instanceof Inet6Address)) {
         throw new IllegalArgumentException("Unhandled type: " + ip);
      } else {
         return toAddressString(ip.getAddress(), 0, ipv4Mapped);
      }
   }

   private static String toAddressString(byte[] bytes, int offset, boolean ipv4Mapped) {
      int[] words = new int[8];
      int end = offset + words.length;

      int i;
      for(i = offset; i < end; ++i) {
         words[i] = (bytes[i << 1] & 255) << 8 | bytes[(i << 1) + 1] & 255;
      }

      int currentStart = -1;
      int currentLength = false;
      int shortestStart = -1;
      int shortestLength = 0;

      int currentLength;
      for(i = 0; i < words.length; ++i) {
         if (words[i] == 0) {
            if (currentStart < 0) {
               currentStart = i;
            }
         } else if (currentStart >= 0) {
            currentLength = i - currentStart;
            if (currentLength > shortestLength) {
               shortestStart = currentStart;
               shortestLength = currentLength;
            }

            currentStart = -1;
         }
      }

      if (currentStart >= 0) {
         currentLength = i - currentStart;
         if (currentLength > shortestLength) {
            shortestStart = currentStart;
            shortestLength = currentLength;
         }
      }

      if (shortestLength == 1) {
         shortestLength = 0;
         shortestStart = -1;
      }

      int shortestEnd = shortestStart + shortestLength;
      StringBuilder b = new StringBuilder(39);
      if (shortestEnd < 0) {
         b.append(Integer.toHexString(words[0]));

         for(i = 1; i < words.length; ++i) {
            b.append(':');
            b.append(Integer.toHexString(words[i]));
         }
      } else {
         boolean isIpv4Mapped;
         if (!inRangeEndExclusive(0, shortestStart, shortestEnd)) {
            b.append(Integer.toHexString(words[0]));
            isIpv4Mapped = false;
         } else {
            b.append("::");
            isIpv4Mapped = ipv4Mapped && shortestEnd == 5 && words[5] == 65535;
         }

         for(i = 1; i < words.length; ++i) {
            if (inRangeEndExclusive(i, shortestStart, shortestEnd)) {
               if (!inRangeEndExclusive(i - 1, shortestStart, shortestEnd)) {
                  b.append("::");
               }
            } else {
               if (!inRangeEndExclusive(i - 1, shortestStart, shortestEnd)) {
                  if (isIpv4Mapped && i != 6) {
                     b.append('.');
                  } else {
                     b.append(':');
                  }
               }

               if (isIpv4Mapped && i > 5) {
                  b.append(words[i] >> 8);
                  b.append('.');
                  b.append(words[i] & 255);
               } else {
                  b.append(Integer.toHexString(words[i]));
               }
            }
         }
      }

      return b.toString();
   }

   private static boolean inRangeEndExclusive(int value, int start, int end) {
      return value >= start && value < end;
   }

   private NetUtil() {
   }

   static {
      logger.debug("-Djava.net.preferIPv4Stack: {}", (Object)IPV4_PREFERRED);
      logger.debug("-Djava.net.preferIPv6Addresses: {}", (Object)IPV6_ADDRESSES_PREFERRED);
      byte[] LOCALHOST4_BYTES = new byte[]{127, 0, 0, 1};
      byte[] LOCALHOST6_BYTES = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
      Inet4Address localhost4 = null;

      try {
         localhost4 = (Inet4Address)InetAddress.getByAddress("localhost", LOCALHOST4_BYTES);
      } catch (Exception var20) {
         PlatformDependent.throwException(var20);
      }

      LOCALHOST4 = localhost4;
      Inet6Address localhost6 = null;

      try {
         localhost6 = (Inet6Address)InetAddress.getByAddress("localhost", LOCALHOST6_BYTES);
      } catch (Exception var19) {
         PlatformDependent.throwException(var19);
      }

      LOCALHOST6 = localhost6;
      List ifaces = new ArrayList();

      try {
         Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
         if (interfaces != null) {
            while(interfaces.hasMoreElements()) {
               NetworkInterface iface = (NetworkInterface)interfaces.nextElement();
               if (SocketUtils.addressesFromNetworkInterface(iface).hasMoreElements()) {
                  ifaces.add(iface);
               }
            }
         }
      } catch (SocketException var23) {
         logger.warn("Failed to retrieve the list of available network interfaces", (Throwable)var23);
      }

      NetworkInterface loopbackIface = null;
      InetAddress loopbackAddr = null;
      Iterator var7 = ifaces.iterator();

      NetworkInterface iface;
      Enumeration i;
      label200:
      while(var7.hasNext()) {
         iface = (NetworkInterface)var7.next();
         i = SocketUtils.addressesFromNetworkInterface(iface);

         while(i.hasMoreElements()) {
            InetAddress addr = (InetAddress)i.nextElement();
            if (addr.isLoopbackAddress()) {
               loopbackIface = iface;
               loopbackAddr = addr;
               break label200;
            }
         }
      }

      if (loopbackIface == null) {
         try {
            var7 = ifaces.iterator();

            while(var7.hasNext()) {
               iface = (NetworkInterface)var7.next();
               if (iface.isLoopback()) {
                  i = SocketUtils.addressesFromNetworkInterface(iface);
                  if (i.hasMoreElements()) {
                     loopbackIface = iface;
                     loopbackAddr = (InetAddress)i.nextElement();
                     break;
                  }
               }
            }

            if (loopbackIface == null) {
               logger.warn("Failed to find the loopback interface");
            }
         } catch (SocketException var22) {
            logger.warn("Failed to find the loopback interface", (Throwable)var22);
         }
      }

      if (loopbackIface != null) {
         logger.debug("Loopback interface: {} ({}, {})", loopbackIface.getName(), loopbackIface.getDisplayName(), ((InetAddress)loopbackAddr).getHostAddress());
      } else if (loopbackAddr == null) {
         try {
            if (NetworkInterface.getByInetAddress(LOCALHOST6) != null) {
               logger.debug("Using hard-coded IPv6 localhost address: {}", (Object)localhost6);
               loopbackAddr = localhost6;
            }
         } catch (Exception var18) {
         } finally {
            if (loopbackAddr == null) {
               logger.debug("Using hard-coded IPv4 localhost address: {}", (Object)localhost4);
               loopbackAddr = localhost4;
            }

         }
      }

      LOOPBACK_IF = loopbackIface;
      LOCALHOST = (InetAddress)loopbackAddr;
      SOMAXCONN = (Integer)AccessController.doPrivileged(new PrivilegedAction() {
         public Integer run() {
            int somaxconn = PlatformDependent.isWindows() ? 200 : 128;
            File file = new File("/proc/sys/net/core/somaxconn");
            BufferedReader in = null;

            try {
               if (file.exists()) {
                  in = new BufferedReader(new FileReader(file));
                  somaxconn = Integer.parseInt(in.readLine());
                  if (NetUtil.logger.isDebugEnabled()) {
                     NetUtil.logger.debug("{}: {}", file, somaxconn);
                  }
               } else {
                  Integer tmp = null;
                  if (SystemPropertyUtil.getBoolean("org.python.netty.net.somaxconn.trySysctl", false)) {
                     tmp = NetUtil.sysctlGetInt("kern.ipc.somaxconn");
                     if (tmp == null) {
                        tmp = NetUtil.sysctlGetInt("kern.ipc.soacceptqueue");
                        if (tmp != null) {
                           somaxconn = tmp;
                        }
                     } else {
                        somaxconn = tmp;
                     }
                  }

                  if (tmp == null) {
                     NetUtil.logger.debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", file, somaxconn);
                  }
               }
            } catch (Exception var13) {
               NetUtil.logger.debug("Failed to get SOMAXCONN from sysctl and file {}. Default: {}", file, somaxconn, var13);
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (Exception var12) {
                  }
               }

            }

            return somaxconn;
         }
      });
   }
}
