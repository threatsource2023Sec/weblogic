package org.glassfish.grizzly.portunif;

public class PUContext {
   private int protocolMissCount;
   short skippedProtocolFinders;
   boolean isSticky = true;
   PUProtocol protocol;

   public PUContext(PUFilter filter) {
      this.protocolMissCount = filter.getProtocols().size();
   }

   public boolean isSticky() {
      return this.isSticky;
   }

   public void setSticky(boolean isSticky) {
      this.isSticky = isSticky;
   }

   public boolean noProtocolsFound() {
      return Integer.bitCount(this.skippedProtocolFinders) == this.protocolMissCount;
   }

   void reset() {
      this.isSticky = true;
      this.protocol = null;
      this.skippedProtocolFinders = 0;
      this.protocolMissCount = 0;
   }
}
