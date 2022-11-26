package weblogic.apache.org.apache.log.output.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.format.Formatter;
import weblogic.apache.org.apache.log.output.AbstractOutputTarget;

public class DatagramOutputTarget extends AbstractOutputTarget {
   private DatagramSocket m_socket;

   public DatagramOutputTarget(InetAddress address, int port, Formatter formatter) throws IOException {
      super(formatter);
      this.m_socket = new DatagramSocket();
      this.m_socket.connect(address, port);
      this.open();
   }

   public DatagramOutputTarget(InetAddress address, int port) throws IOException {
      this(address, port, (Formatter)null);
   }

   protected void write(String stringData) {
      byte[] data = stringData.getBytes();

      try {
         DatagramPacket packet = new DatagramPacket(data, data.length);
         this.m_socket.send(packet);
      } catch (IOException var4) {
         this.getErrorHandler().error("Error sending datagram.", var4, (LogEvent)null);
      }

   }

   public synchronized void close() {
      super.close();
      this.m_socket = null;
   }
}
