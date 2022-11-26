package net.shibboleth.utilities.java.support.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.BitSet;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class IPRange {
   private final int addressLength;
   private final BitSet network;
   private final BitSet host;
   private BitSet mask;

   public IPRange(InetAddress address, int maskSize) {
      this(address.getAddress(), maskSize);
   }

   public IPRange(byte[] address, int maskSize) {
      this.addressLength = address.length * 8;
      if (this.addressLength != 32 && this.addressLength != 128) {
         throw new IllegalArgumentException("address was neither an IPv4 or IPv6 address");
      } else if (maskSize >= 0 && maskSize <= this.addressLength) {
         this.mask = new BitSet(this.addressLength);
         this.mask.set(this.addressLength - maskSize, this.addressLength, true);
         BitSet hostAddress = this.toBitSet(address);
         this.network = (BitSet)hostAddress.clone();
         this.network.and(this.mask);
         if (hostAddress.equals(this.network)) {
            this.host = null;
         } else {
            this.host = hostAddress;
         }

      } else {
         throw new IllegalArgumentException("prefix length must be in range 0 to " + this.addressLength);
      }
   }

   public InetAddress getNetworkAddress() {
      return this.toInetAddress(this.network);
   }

   public InetAddress getHostAddress() {
      return this.toInetAddress(this.host);
   }

   private static void validateV4Address(String address) {
      String[] components = address.split("\\.");
      if (components.length != 4) {
         throw new IllegalArgumentException("IPv4 address should have four components");
      } else {
         String[] arr$ = components;
         int len$ = components.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String component = arr$[i$];
            int value = Integer.parseInt(component, 10);
            if (value < 0 || value > 255) {
               throw new IllegalArgumentException("IPv4 component range error: " + component);
            }
         }

      }
   }

   private static void validateV6Address(String address) {
      String[] components = address.split(":");
      String[] arr$ = components;
      int len$ = components.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         String component = arr$[i$];
         if (component.length() != 0) {
            int value = Integer.parseInt(component, 16);
            if (value < 0 || value > 65535) {
               throw new IllegalArgumentException("IPv6 component range error: " + component);
            }
         }
      }

   }

   private static void validateIPAddress(String address) {
      if (address.indexOf(58) >= 0) {
         validateV6Address(address);
      } else {
         validateV4Address(address);
      }

   }

   public static IPRange parseCIDRBlock(String cidrBlock) {
      String block = StringSupport.trimOrNull(cidrBlock);
      if (block == null) {
         throw new IllegalArgumentException("CIDR block definition can not be null or empty");
      } else {
         String[] blockParts = block.split("/");
         if (blockParts.length != 2) {
            throw new IllegalArgumentException("CIDR block definition is invalid, check for missing or extra slash");
         } else {
            try {
               validateIPAddress(blockParts[0]);
               InetAddress address = InetAddress.getByName(blockParts[0]);
               int maskSize = Integer.parseInt(blockParts[1]);
               return new IPRange(address, maskSize);
            } catch (UnknownHostException var5) {
               throw new IllegalArgumentException("Invalid IP address");
            } catch (NumberFormatException var6) {
               throw new IllegalArgumentException("Invalid netmask size");
            }
         }
      }
   }

   public boolean contains(InetAddress address) {
      return this.contains(address.getAddress());
   }

   public boolean contains(byte[] address) {
      if (address.length * 8 != this.addressLength) {
         return false;
      } else {
         BitSet addrNetwork = this.toBitSet(address);
         addrNetwork.and(this.mask);
         return addrNetwork.equals(this.network);
      }
   }

   protected BitSet toBitSet(byte[] bytes) {
      BitSet bits = new BitSet(bytes.length * 8);

      for(int i = 0; i < bytes.length * 8; ++i) {
         if ((bytes[bytes.length - i / 8 - 1] & 1 << i % 8) > 0) {
            bits.set(i);
         }
      }

      return bits;
   }

   private byte[] toByteArray(BitSet bits) {
      byte[] bytes = new byte[this.addressLength / 8];

      for(int i = 0; i < this.addressLength; ++i) {
         if (bits.get(i)) {
            bytes[bytes.length - i / 8 - 1] = (byte)(bytes[bytes.length - i / 8 - 1] | 1 << i % 8);
         }
      }

      return bytes;
   }

   private InetAddress toInetAddress(BitSet bits) {
      if (bits == null) {
         return null;
      } else {
         try {
            return InetAddress.getByAddress(this.toByteArray(bits));
         } catch (UnknownHostException var3) {
            return null;
         }
      }
   }
}
