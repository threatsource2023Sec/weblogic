package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPTransport implements DatagramTransport {
   protected static final int MIN_IP_OVERHEAD = 20;
   protected static final int MAX_IP_OVERHEAD = 84;
   protected static final int UDP_OVERHEAD = 8;
   protected final DatagramSocket socket;
   protected final int receiveLimit;
   protected final int sendLimit;

   public UDPTransport(DatagramSocket var1, int var2) throws IOException {
      if (var1.isBound() && var1.isConnected()) {
         this.socket = var1;
         this.receiveLimit = var2 - 20 - 8;
         this.sendLimit = var2 - 84 - 8;
      } else {
         throw new IllegalArgumentException("'socket' must be bound and connected");
      }
   }

   public int getReceiveLimit() {
      return this.receiveLimit;
   }

   public int getSendLimit() {
      return this.sendLimit;
   }

   public int receive(byte[] var1, int var2, int var3, int var4) throws IOException {
      this.socket.setSoTimeout(var4);
      DatagramPacket var5 = new DatagramPacket(var1, var2, var3);
      this.socket.receive(var5);
      return var5.getLength();
   }

   public void send(byte[] var1, int var2, int var3) throws IOException {
      if (var3 > this.getSendLimit()) {
         throw new TlsFatalAlert((short)80);
      } else {
         DatagramPacket var4 = new DatagramPacket(var1, var2, var3);
         this.socket.send(var4);
      }
   }

   public void close() throws IOException {
      this.socket.close();
   }
}
