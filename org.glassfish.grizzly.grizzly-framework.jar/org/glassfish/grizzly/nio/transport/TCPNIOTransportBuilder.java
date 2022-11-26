package org.glassfish.grizzly.nio.transport;

import org.glassfish.grizzly.NIOTransportBuilder;
import org.glassfish.grizzly.nio.NIOTransport;

public class TCPNIOTransportBuilder extends NIOTransportBuilder {
   protected boolean keepAlive = true;
   protected int linger = -1;
   protected int serverConnectionBackLog = 4096;
   protected int serverSocketSoTimeout = 0;
   protected boolean tcpNoDelay = true;

   protected TCPNIOTransportBuilder(Class transportClass) {
      super(transportClass);
   }

   public static TCPNIOTransportBuilder newInstance() {
      return new TCPNIOTransportBuilder(TCPNIOTransport.class);
   }

   public boolean isKeepAlive() {
      return this.keepAlive;
   }

   public TCPNIOTransportBuilder setKeepAlive(boolean keepAlive) {
      this.keepAlive = keepAlive;
      return this.getThis();
   }

   public int getLinger() {
      return this.linger;
   }

   public TCPNIOTransportBuilder setLinger(int linger) {
      this.linger = linger;
      return this.getThis();
   }

   public int getServerConnectionBackLog() {
      return this.serverConnectionBackLog;
   }

   public TCPNIOTransportBuilder setServerConnectionBackLog(int serverConnectionBackLog) {
      this.serverConnectionBackLog = serverConnectionBackLog;
      return this.getThis();
   }

   public int getServerSocketSoTimeout() {
      return this.serverSocketSoTimeout;
   }

   public TCPNIOTransportBuilder setServerSocketSoTimeout(int serverSocketSoTimeout) {
      this.serverSocketSoTimeout = serverSocketSoTimeout;
      return this.getThis();
   }

   public boolean isTcpNoDelay() {
      return this.tcpNoDelay;
   }

   public TCPNIOTransportBuilder setTcpNoDelay(boolean tcpNoDelay) {
      this.tcpNoDelay = tcpNoDelay;
      return this.getThis();
   }

   public TCPNIOTransport build() {
      TCPNIOTransport transport = (TCPNIOTransport)super.build();
      transport.setKeepAlive(this.keepAlive);
      transport.setLinger(this.linger);
      transport.setServerConnectionBackLog(this.serverConnectionBackLog);
      transport.setTcpNoDelay(this.tcpNoDelay);
      transport.setServerSocketSoTimeout(this.serverSocketSoTimeout);
      return transport;
   }

   protected TCPNIOTransportBuilder getThis() {
      return this;
   }

   protected NIOTransport create(String name) {
      return new TCPNIOTransport(name);
   }
}
