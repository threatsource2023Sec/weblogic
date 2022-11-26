package weblogic.apache.org.apache.log.output.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.output.AbstractOutputTarget;

public class SocketOutputTarget extends AbstractOutputTarget {
   private Socket m_socket;
   private ObjectOutputStream m_outputStream;

   public SocketOutputTarget(InetAddress address, int port) throws IOException {
      this.m_socket = new Socket(address, port);
      this.m_outputStream = new ObjectOutputStream(this.m_socket.getOutputStream());
      super.open();
   }

   public SocketOutputTarget(String host, int port) throws IOException {
      this.m_socket = new Socket(host, port);
      this.m_outputStream = new ObjectOutputStream(this.m_socket.getOutputStream());
      super.open();
   }

   protected void write(LogEvent event) {
      try {
         this.m_outputStream.writeObject(event);
      } catch (IOException var3) {
         this.getErrorHandler().error("Error writting to socket", var3, (LogEvent)null);
      }

   }

   protected void doProcessEvent(LogEvent event) {
      this.write(event);
   }

   public synchronized void close() {
      super.close();
      this.m_socket = null;
   }
}
