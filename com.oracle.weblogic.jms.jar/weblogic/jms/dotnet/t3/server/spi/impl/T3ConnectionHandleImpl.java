package weblogic.jms.dotnet.t3.server.spi.impl;

import weblogic.jms.dotnet.t3.server.spi.T3Connection;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionGoneEvent;
import weblogic.jms.dotnet.t3.server.spi.T3ConnectionHandle;
import weblogic.utils.io.ChunkedDataInputStream;
import weblogic.utils.io.ChunkedDataOutputStream;

public class T3ConnectionHandleImpl implements T3ConnectionHandle {
   private T3Connection client;

   T3ConnectionHandleImpl(T3Connection client) {
      this.client = client;
   }

   public void onPeerGone(T3ConnectionGoneEvent event) {
   }

   public void onMessage(ChunkedDataInputStream input) {
      try {
         int cmd = input.readInt();
         ChunkedDataOutputStream output = this.client.getRequestStream();
         output.writeInt(cmd + 1);
         this.client.send(output);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void close() {
   }
}
