package weblogic.security.net;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Locale;

class SlowFilterEntry extends FilterEntry {
   private String pattern;
   private BigInteger localAddress;
   private int localPort;

   SlowFilterEntry(boolean action, int protomask, String pattern, BigInteger addr, int port) {
      super(action, protomask);
      this.pattern = pattern.toLowerCase(Locale.ENGLISH).substring(1);
      this.localAddress = addr;
      this.localPort = port;
   }

   protected boolean match(InetAddress addr, InetAddress lAddr, int lPort) {
      return addr.getHostName().toLowerCase(Locale.ENGLISH).endsWith(this.pattern) && (ConnectionFilterImpl.addressToBigInteger(lAddr).equals(this.localAddress) || this.localAddress.equals(ConnectionFilterImpl.MINUS_ONE)) && (lPort == this.localPort || this.localPort == -1);
   }
}
