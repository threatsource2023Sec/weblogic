package weblogic.security.net;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import weblogic.security.SecurityLogger;
import weblogic.utils.LocatorUtilities;

public class ConnectionFilterImpl implements ConnectionFilter, ConnectionFilterRulesListener {
   protected static final BigInteger DEFAULT_NET_MASK;
   protected static final BigInteger MINUS_ONE;
   static ConnectionFilter impl;
   private FilterEntry[] rules;

   public void checkRules(String[] filterList) throws ParseException {
      if (filterList != null) {
         for(int i = 0; i < filterList.length; ++i) {
            String line = filterList[i];
            int hash = line.indexOf(35);
            if (hash != -1) {
               line = line.substring(0, hash).trim();
            }

            if (line.length() != 0) {
               try {
                  this.parseLine(line, (Vector)null);
               } catch (StreamCorruptedException var6) {
                  throw new ParseException(var6.getMessage(), i + 1);
               } catch (IllegalArgumentException var7) {
                  throw new ParseException(var7.getMessage(), i + 1);
               } catch (IOException var8) {
                  throw new ParseException(var8.getMessage(), i + 1);
               }
            }
         }
      }

   }

   protected void checkRules(String[] filterList, Vector entries) throws ParseException {
      if (filterList != null) {
         for(int i = 0; i < filterList.length; ++i) {
            String line = filterList[i];
            int hash = line.indexOf(35);
            if (hash != -1) {
               line = line.substring(0, hash).trim();
            }

            if (line.length() != 0) {
               try {
                  this.parseLine(line, entries);
               } catch (StreamCorruptedException var7) {
                  throw new ParseException(var7.getMessage(), i + 1);
               } catch (IllegalArgumentException var8) {
                  throw new ParseException(var8.getMessage(), i + 1);
               } catch (IOException var9) {
                  throw new ParseException(var9.getMessage(), i + 1);
               }
            }
         }
      }

   }

   public void setRules(String[] filterList) throws ParseException {
      Vector entries = new Vector();
      this.checkRules(filterList, entries);
      FilterEntry[] localRules = new FilterEntry[entries.size()];
      entries.copyInto(localRules);
      this.rules = localRules;
   }

   public void accept(ConnectionEvent evt) throws FilterException {
      if (this.rules != null) {
         InetAddress remoteAddress = evt.getRemoteAddress();
         String protocol = evt.getProtocol().toLowerCase(Locale.ENGLISH);
         int bit = protocolToMaskBit(protocol);
         InetAddress localAddress = evt.getLocalAddress();
         int localPort = evt.getLocalPort();
         int remotePort = evt.getRemotePort();
         if (bit == -559038737) {
            bit = 0;
         }

         int i = 0;

         while(i < this.rules.length) {
            switch (this.rules[i].check(remoteAddress, bit, localAddress, localPort)) {
               case 0:
                  return;
               case 1:
                  throw new FilterException(SecurityLogger.getRuleDenied("" + (i + 1)));
               case 2:
                  ++i;
                  break;
               default:
                  throw new RuntimeException(SecurityLogger.getConnFilterInternalErr());
            }
         }
      }

   }

   protected void parseLine(String line, Vector entries) throws IOException, IllegalArgumentException {
      StringTokenizer toks = new StringTokenizer(line);
      String addr = toks.nextToken();
      String lAddr = toks.nextToken();
      BigInteger localAddr = parseSingleAddress(lAddr);
      String lPort = toks.nextToken();
      int localPort;
      if (lPort.equals("*")) {
         localPort = -1;
      } else {
         localPort = Integer.parseInt(lPort);
      }

      boolean allow = parseAction(toks.nextToken());
      if (addr.startsWith("*")) {
         SlowFilterEntry sent = new SlowFilterEntry(allow, parseProtocols(toks), addr, localAddr, localPort);
         if (entries != null) {
            entries.addElement(sent);
         }
      } else {
         int slash = addr.indexOf(47);
         BigInteger[] addrs = null;
         BigInteger netmask = DEFAULT_NET_MASK;
         if (slash != -1) {
            addrs = parseAddresses(addr.substring(0, slash));
            netmask = parseNetmask(addr.substring(slash + 1));
         } else {
            addrs = parseAddresses(addr);
         }

         int protomask = parseProtocols(toks);

         for(int i = 0; i < addrs.length; ++i) {
            FastFilterEntry fent = new FastFilterEntry(allow, protomask, addrs[i], netmask, localAddr, localPort);
            if (entries != null) {
               entries.addElement(fent);
            }
         }
      }

   }

   protected static final int parseProtocols(StringTokenizer toks) throws FilterException {
      int protomask;
      int bit;
      for(protomask = 0; toks.hasMoreTokens(); protomask |= bit) {
         String tok = toks.nextToken();
         bit = protocolToMaskBit(tok);
         if (bit == -559038737) {
            throw new IllegalArgumentException(SecurityLogger.getUnknownProtocol(tok));
         }
      }

      return protomask;
   }

   private static final int protocolToMaskBit(String proto) throws FilterException {
      String[] PROTOCOLS = new String[]{"http", "t3", "https", "t3s", "iiop", "iiops", "giop", "giops", "com", "dcom", "ldap", "ldaps"};
      if (proto == null) {
         return 0;
      } else {
         proto = proto.toLowerCase(Locale.ENGLISH);

         for(int i = 0; i < PROTOCOLS.length; ++i) {
            if (proto.equals(PROTOCOLS[i])) {
               return 1 << i + 1;
            }
         }

         return -559038737;
      }
   }

   protected static final BigInteger[] parseAddresses(String str) throws IOException {
      InetAddress[] addrs = InetAddress.getAllByName(str);
      BigInteger[] raw = new BigInteger[addrs.length];

      for(int i = 0; i < addrs.length; ++i) {
         raw[i] = addressToBigInteger(addrs[i]);
      }

      return raw;
   }

   protected static final BigInteger parseSingleAddress(String str) throws IOException {
      if (str.equals("*")) {
         return MINUS_ONE;
      } else {
         InetAddress addr = InetAddress.getByName(str);
         return addressToBigInteger(addr);
      }
   }

   static final BigInteger addressToBigInteger(InetAddress addr) {
      byte[] roh = addr.getAddress();
      BigInteger raw = BigInteger.ZERO;

      for(int j = 0; j < roh.length; ++j) {
         BigInteger b = BigInteger.valueOf((long)(255 & roh[j]));
         b = b.shiftLeft(8 * (roh.length - j - 1));
         raw = raw.or(b);
      }

      return raw;
   }

   protected static final BigInteger parseNetmask(String maskStr) throws IOException {
      StringTokenizer toks = new StringTokenizer(maskStr, ".");
      int ntoks = toks.countTokens();

      try {
         int mask;
         if (ntoks == 1) {
            mask = Integer.parseInt(toks.nextToken());
            if (mask <= 128 && mask >= 0) {
               return DEFAULT_NET_MASK.subtract(BigInteger.ONE.shiftLeft(128 - mask).subtract(BigInteger.ONE));
            } else {
               throw new StreamCorruptedException(SecurityLogger.getBadNetMaskBits(maskStr));
            }
         } else {
            mask = 0;
            if (ntoks != 4) {
               throw new StreamCorruptedException(SecurityLogger.getBadNetMaskTokens(maskStr));
            } else {
               for(int i = 24; toks.hasMoreTokens(); i -= 8) {
                  int num = Integer.parseInt(toks.nextToken());
                  if (num < 0 || num > 255) {
                     throw new StreamCorruptedException(SecurityLogger.getBadNetMaskNum(maskStr));
                  }

                  mask |= num << i;
               }

               return BigInteger.valueOf((long)mask);
            }
         }
      } catch (NumberFormatException var6) {
         throw new StreamCorruptedException(SecurityLogger.getBadNetMaskFormat(maskStr));
      }
   }

   protected static final boolean parseAction(String whatever) throws IOException {
      String action = whatever.toLowerCase(Locale.ENGLISH);
      if (action.equals("allow")) {
         return true;
      } else if (action.equals("deny")) {
         return false;
      } else {
         throw new StreamCorruptedException(SecurityLogger.getBadAction(action));
      }
   }

   public static final boolean filterEnabled() {
      return ConnectionFilterImpl.ConnectionFilterServiceInstance.CONNECTION_FILTER_SERVICE.getConnectionFilterEnabled();
   }

   public static final ConnectionFilter getFilter() {
      return ConnectionFilterImpl.ConnectionFilterServiceInstance.CONNECTION_FILTER_SERVICE.getConnectionFilter();
   }

   /** @deprecated */
   @Deprecated
   public static final void setFilter(ConnectionFilter filter) {
      if (filter == null) {
         throw new NullPointerException(SecurityLogger.getNullFilter());
      } else if (ConnectionFilterImpl.ConnectionFilterServiceInstance.CONNECTION_FILTER_SERVICE.getConnectionFilter() != null) {
         throw new SecurityException(SecurityLogger.getSetFilterMoreThanOnce());
      } else {
         ConnectionFilterImpl.ConnectionFilterServiceInstance.CONNECTION_FILTER_SERVICE.setConnectionFilter(filter);
      }
   }

   static {
      DEFAULT_NET_MASK = BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE);
      MINUS_ONE = BigInteger.valueOf(-1L);
      impl = new ConnectionFilterImpl();
   }

   private static final class ConnectionFilterServiceInstance {
      private static final ConnectionFilterService CONNECTION_FILTER_SERVICE = (ConnectionFilterService)LocatorUtilities.getService(ConnectionFilterService.class);
   }
}
