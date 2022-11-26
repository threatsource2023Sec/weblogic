package weblogic.jms.dotnet.t3.server.internal;

import weblogic.protocol.ServerChannel;
import weblogic.rjvm.RJVM;

public class T3RJVM {
   private RJVM rjvm;
   private ServerChannel ch;

   public T3RJVM(RJVM rjvm, ServerChannel ch) {
      this.rjvm = rjvm;
      this.ch = ch;
   }

   public RJVM getRJVM() {
      return this.rjvm;
   }

   public ServerChannel getServerChannel() {
      return this.ch;
   }
}
