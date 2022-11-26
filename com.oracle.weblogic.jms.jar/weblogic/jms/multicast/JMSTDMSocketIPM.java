package weblogic.jms.multicast;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public final class JMSTDMSocketIPM implements JMSTDMSocket {
   private MulticastSocket sock;
   private ArrayList groups;
   private int timeout = 0;

   public JMSTDMSocketIPM(int port) throws IOException, UnknownHostException {
      this.sock = new MulticastSocket(port);
      this.groups = new ArrayList();
   }

   public JMSTDMSocketIPM() throws IOException, UnknownHostException {
      this.sock = new MulticastSocket();
      this.groups = new ArrayList();
   }

   public void send(byte[] buffer, int length, InetAddress group, int port, byte ttl) throws IOException {
      DatagramPacket packet = new DatagramPacket(buffer, length, group, port);
      this.sock.send(packet, ttl);
   }

   public void setSoTimeout(int timeout) throws IOException {
      try {
         this.sock.setSoTimeout(timeout);
      } catch (SocketException var3) {
         throw new IOException(var3.toString());
      }
   }

   public void setTTL(byte ttl) throws IOException {
      this.sock.setTTL(ttl);
   }

   public int receive(byte[] buffer) throws IOException {
      DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

      try {
         this.sock.receive(packet);
      } catch (InterruptedIOException var4) {
         return 0;
      }

      return packet.getLength();
   }

   public void close() {
      if (this.sock != null) {
         for(int i = 0; i < this.groups.size(); ++i) {
            try {
               this.sock.leaveGroup((InetAddress)this.groups.get(i));
            } catch (IOException var3) {
            }
         }

         this.sock.close();
         this.sock = null;
         this.groups = null;
      }
   }

   public void joinGroup(InetAddress group) throws IOException {
      if (this.sock == null) {
         throw new IOException("Socket is closed");
      } else if (this.groups.indexOf(group) >= 0) {
         throw new IOException("Group exists");
      } else {
         this.sock.joinGroup(group);
         this.groups.add(group);
      }
   }

   public void leaveGroup(InetAddress group) throws IOException {
      if (this.sock == null) {
         throw new IOException("Socket is closed");
      } else if (this.groups.indexOf(group) == -1) {
         throw new IOException("Group not found");
      } else {
         this.sock.leaveGroup(group);
         this.groups.remove(group);
      }
   }

   void setTimeout(int timeout) {
      this.timeout = timeout;
   }
}
