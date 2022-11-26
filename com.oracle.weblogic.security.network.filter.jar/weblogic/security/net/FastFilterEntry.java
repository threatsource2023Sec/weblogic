package weblogic.security.net;

import java.math.BigInteger;
import java.net.InetAddress;

class FastFilterEntry extends FilterEntry {
   private BigInteger addrMask;
   private BigInteger netMask;
   private BigInteger localAddress;
   private int localPort;

   FastFilterEntry(boolean action, int protomask, BigInteger address, BigInteger netmask, BigInteger lAddr, int lPort) {
      super(action, protomask);
      this.addrMask = address.and(netmask);
      this.netMask = netmask;
      this.localAddress = lAddr;
      this.localPort = lPort;
   }

   protected boolean match(InetAddress addr, InetAddress lAddr, int lPort) {
      if (this.addrMask.equals(BigInteger.ZERO)) {
         return true;
      } else {
         return ConnectionFilterImpl.addressToBigInteger(addr).and(this.netMask).equals(this.addrMask) && (ConnectionFilterImpl.addressToBigInteger(lAddr).equals(this.localAddress) || this.localAddress.equals(ConnectionFilterImpl.MINUS_ONE)) && (lPort == this.localPort || this.localPort == -1);
      }
   }
}
