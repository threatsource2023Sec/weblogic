package weblogic.security.net;

import java.net.InetAddress;

abstract class FilterEntry {
   static final int ALLOW = 0;
   static final int DENY = 1;
   static final int IGNORE = 2;
   private int protomask;
   private boolean action;

   protected FilterEntry(boolean action, int protomask) {
      this.protomask = protomask;
      this.action = action;
   }

   int check(InetAddress addr, int protocol, InetAddress lAddr, int lPort) {
      if (this.match(addr, lAddr, lPort) && this.match(protocol)) {
         return this.action ? 0 : 1;
      } else {
         return 2;
      }
   }

   protected abstract boolean match(InetAddress var1, InetAddress var2, int var3);

   protected boolean match(int protocol) {
      return this.protomask == 0 || (protocol & this.protomask) != 0;
   }
}
