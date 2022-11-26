package weblogic.servlet.proxy;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.common.resourcepool.ResourcePoolGroup;

public class SocketConnResource implements PooledResource {
   private final String host;
   private final int port;
   private Socket conn;
   private long creationTime;
   private boolean used;
   private final String url;
   private PooledResourceInfo prInfo;

   SocketConnResource(String host, int port) {
      this.host = host;
      this.port = port;
      this.url = "Host:" + host + ":" + port;
   }

   public PooledResourceInfo getPooledResourceInfo() {
      return this.prInfo;
   }

   public void setPooledResourceInfo(PooledResourceInfo info) {
      this.prInfo = info;
   }

   public void initialize() {
      this.createConnection();
   }

   public void setup() {
   }

   public void enable() {
      if (this.conn == null) {
         this.createConnection();
      }

   }

   public void disable() {
      this.destroy();
   }

   public void cleanup() throws ResourceException {
      this.destroy();
   }

   public void destroy() {
      if (this.conn != null) {
         try {
            this.conn.close();
            this.conn = null;
            this.creationTime = 0L;
         } catch (IOException var2) {
         }
      }

   }

   public void forceDestroy() {
      this.destroy();
   }

   public int test() throws ResourceException {
      return 0;
   }

   public long getCreationTime() throws ResourceException {
      return this.creationTime;
   }

   public void setResourceCleanupHandler(ResourceCleanupHandler hdlr) {
   }

   public ResourceCleanupHandler getResourceCleanupHandler() {
      return null;
   }

   public void setUsed(boolean newVal) {
      this.used = newVal;
   }

   public boolean getUsed() {
      return this.used;
   }

   public ResourcePoolGroup getPrimaryGroup() {
      return null;
   }

   public Collection getGroups() {
      return null;
   }

   public ResourcePoolGroup getGroup(String category) {
      return null;
   }

   public void setDestroyAfterRelease() {
   }

   public boolean needDestroyAfterRelease() {
      return false;
   }

   private void createConnection() {
      try {
         this.conn = new Socket(this.host, this.port);
         this.creationTime = System.currentTimeMillis();
         this.conn.setTcpNoDelay(true);
      } catch (UnknownHostException var2) {
      } catch (IOException var3) {
      }

   }

   final PrintStream getOutputStream() throws IOException {
      if (this.conn == null || this.conn.isClosed()) {
         this.createConnection();
      }

      return new PrintStream(this.conn.getOutputStream(), true);
   }

   final PushbackInputStream getInputStream() throws IOException {
      if (this.conn == null || this.conn.isClosed()) {
         this.createConnection();
      }

      return new PushbackInputStream(this.conn.getInputStream());
   }

   final String getHost() {
      return this.url;
   }
}
