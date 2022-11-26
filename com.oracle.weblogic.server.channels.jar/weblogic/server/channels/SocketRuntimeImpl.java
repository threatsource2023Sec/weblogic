package weblogic.server.channels;

import java.io.Serializable;
import weblogic.management.runtime.SocketRuntime;

public class SocketRuntimeImpl implements SocketRuntime, Serializable {
   private final int fd;
   private final String localAddress;
   private final int localPort;
   private final String remoteAddress;
   private final int remotePort;

   public SocketRuntimeImpl(SocketRuntime s) {
      this.fd = s.getFileDescriptor();
      this.remoteAddress = s.getRemoteAddress();
      this.localAddress = s.getLocalAddress();
      this.remotePort = s.getRemotePort();
      this.localPort = s.getLocalPort();
   }

   public int getFileDescriptor() {
      return this.fd;
   }

   public String getLocalAddress() {
      return this.localAddress;
   }

   public int getLocalPort() {
      return this.localPort;
   }

   public String getRemoteAddress() {
      return this.remoteAddress;
   }

   public int getRemotePort() {
      return this.remotePort;
   }
}
