package weblogic.cluster.messaging.internal;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.StringTokenizer;
import weblogic.cluster.messaging.UnicastLogger;
import weblogic.utils.io.ChunkedDataOutputStream;
import weblogic.utils.io.FilteringObjectInputStream;

public class ConnectionImpl implements Connection {
   protected static final boolean DEBUG;
   public static final String ACCEPT = "accept";
   public static final String REJECT = "reject";
   private static final String DELIMITER = ":";
   private Socket socket;
   private boolean isDead;
   private ServerConfigurationInformation info;
   private String serverId;
   private static final String delimiter = "-";
   protected static final String STR_READ_TIMEOUT = "weblogic.unicast.readTimeout";
   private volatile boolean isBootStrapped = false;
   private int timeout;

   public ConnectionImpl(ServerConfigurationInformation info, int timeout) {
      this.info = info;
      if (info != null && info.getCreationTime() > 1L) {
         this.serverId = info.getServerName() + "-" + info.getCreationTime();
      }

      this.timeout = timeout;
   }

   public ConnectionImpl(Socket socket, int timeout) throws IOException {
      this.socket = socket;
      this.socket.setTcpNoDelay(true);
      this.timeout = timeout;
      this.setSoTimeout(this.socket, this.timeout);
   }

   protected OutputStream getOutputStream() throws IOException {
      try {
         return this.socket.getOutputStream();
      } catch (IOException var2) {
         this.close();
         throw var2;
      }
   }

   public final boolean isDead() {
      return this.isDead;
   }

   public final ServerConfigurationInformation getConfiguration() {
      return this.info;
   }

   public void send(Message message) throws IOException {
      ChunkedDataOutputStream sos = new ChunkedDataOutputStream();
      ObjectOutputStream outputStream = null;

      try {
         this.skipHeader(sos);
         outputStream = new ObjectOutputStream(sos);
         outputStream.writeObject(message);
         int size = sos.getSize();
         if (DEBUG) {
            this.debug("writing length " + size);
         }

         sos.setPosition(0);
         this.writeHeader(sos);
         sos.writeInt(size);
         if (DEBUG) {
            this.debug("writing data " + Arrays.toString(sos.getBuffer()));
         }

         sos.writeTo(this.getOutputStream());
      } catch (IOException var8) {
         this.close();
         throw var8;
      } finally {
         if (outputStream != null) {
            this.close(outputStream);
         }

      }

   }

   public void bootStrapConnection() throws IOException {
      this.sendBootStrapMessage();
      if (DEBUG) {
         this.debug("Sent bootstrap message to " + this.info.getServerName());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(this.getInputStream()));
      String response = br.readLine();
      if (DEBUG) {
         this.debug("response: " + response);
      }

      boolean result = this.checkResponse(response);
      if (response != null && result) {
         this.isBootStrapped = true;
      } else if (response != null) {
         throw new RejectConnectionException("Connection request rejected due to cross cluster connection");
      } else {
         throw new IOException("Bootstrap Response is null");
      }
   }

   private InputStream getInputStream() throws IOException {
      return this.socket.getInputStream();
   }

   private boolean checkResponse(String response) {
      if (response == null) {
         return false;
      } else {
         StringTokenizer tokenizer = new StringTokenizer(response, ":");
         if (tokenizer.countTokens() != 3) {
            return false;
         } else {
            String resp = tokenizer.nextToken();
            String server = tokenizer.nextToken();
            String cluster = tokenizer.nextToken();
            if (resp.equals("accept")) {
               return true;
            } else {
               UnicastLogger.logUnicastBootStrapRejected(server, cluster);
               return false;
            }
         }
      }
   }

   private void sendBootStrapMessage() throws IOException {
      ChunkedDataOutputStream sos = new ChunkedDataOutputStream();
      ObjectOutputStream outputStream = null;

      try {
         this.skipHeader(sos);
         outputStream = new ObjectOutputStream(sos);
         ServerConfigurationInformation info = Environment.getConfiguredServersMonitor().getLocalServerConfiguration();
         outputStream.writeObject(info);
         int size = sos.getSize();
         if (DEBUG) {
            this.debug("writing length " + size);
         }

         sos.setPosition(0);
         this.writeHeader(sos);
         sos.writeInt(size);
         if (DEBUG) {
            this.debug("writing data " + Arrays.toString(sos.getBuffer()));
         }

         sos.writeTo(this.getOutputStream());
         this.getOutputStream().flush();
      } catch (IOException var12) {
         this.close();
         throw var12;
      } finally {
         if (outputStream != null) {
            try {
               outputStream.close();
            } catch (IOException var11) {
            }
         }

      }

   }

   private void close(Closeable stream) {
      try {
         stream.close();
      } catch (IOException var3) {
      }

   }

   protected void skipHeader(ChunkedDataOutputStream sos) {
      sos.skip(Message.HEADER_LENGTH);
   }

   protected void writeHeader(ChunkedDataOutputStream sos) {
      sos.writeBytes("CLUSTER-BROADCAST");
   }

   protected void debug(String s) {
      Environment.getLogService().debug("[Connection][" + this.info + "]" + s);
   }

   public void handleIncomingMessage(InputStream inputStream) throws IOException {
      if (DEBUG) {
         this.debug("reading message from input stream ...");
      }

      ObjectInputStream ois = null;

      final Message message;
      try {
         ois = new FilteringObjectInputStream(inputStream);
         message = (Message)ois.readObject();
      } catch (ClassNotFoundException var8) {
         if (DEBUG) {
            this.debug("handleIncomingMessage e: " + var8);
         }

         throw new AssertionError(var8);
      } finally {
         if (ois != null) {
            this.close(ois);
         }

      }

      if (DEBUG) {
         this.debug("received message: " + message);
      }

      GroupMember localMember = Environment.getGroupManager().getLocalMember();
      if (localMember.getConfiguration().equals(message.getForwardingServer())) {
         if (DEBUG) {
            this.debug("squelching message forwarded from local server:" + message);
         }

      } else {
         if (this.info == null) {
            this.info = message.getForwardingServer();
         }

         this.serverId = message.getForwardingServer().getServerName() + "-" + message.getForwardingServer().getCreationTime();
         if (DEBUG) {
            this.debug("dispatching to group manager with serverId: " + this.serverId);
         }

         Environment.executeDispatchMessage(new Runnable() {
            public void run() {
               Environment.getGroupManager().handleMessage(message, ConnectionImpl.this);
            }
         });
      }
   }

   public String getServerId() {
      return this.serverId;
   }

   public void close() {
      if (!this.isDead) {
         this.isDead = true;
         this.isBootStrapped = false;

         try {
            if (this.socket != null) {
               this.socket.close();
            }
         } catch (IOException var2) {
         }

      }
   }

   public final void setSocket(Socket socket) throws IOException {
      this.socket = socket;
      this.socket.setTcpNoDelay(true);
      this.setSoTimeout(this.socket, this.timeout);
   }

   public boolean isBootStrapped() {
      return this.isBootStrapped;
   }

   public void handleBootStrapMessage(InputStream inputStream) throws IOException {
      ObjectInputStream ois = null;
      ServerConfigurationInformation local = Environment.getConfiguredServersMonitor().getLocalServerConfiguration();

      ServerConfigurationInformation remoteInfo;
      label85: {
         try {
            ois = new FilteringObjectInputStream(inputStream);
            remoteInfo = (ServerConfigurationInformation)ois.readObject();
            break label85;
         } catch (Exception var9) {
            UnicastLogger.logUnicastBootStrapException(var9.toString());
            if (DEBUG) {
               this.debug("Exception during unicast connection bootstrapping: " + var9);
            }

            this.sendRejectBootStrapResponse(local);
         } finally {
            if (ois != null) {
               this.close(ois);
            }

         }

         return;
      }

      if (DEBUG) {
         this.debug("handleBootStrapMessage remoteInfo: " + remoteInfo);
      }

      if (local.getClusterName().equals(remoteInfo.getClusterName())) {
         this.sendAcceptBootStrapResponse(local);
         this.isBootStrapped = true;
      } else {
         this.sendRejectBootStrapResponse(local);
      }

   }

   private void sendRejectBootStrapResponse(ServerConfigurationInformation local) throws IOException {
      DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(this.getOutputStream()));
      dOut.writeBytes("reject:" + local.getServerName() + ":" + local.getClusterName() + '\n');
      dOut.flush();
      this.close();
      if (DEBUG) {
         this.debug("sent RejectBootStrapResponse");
      }

   }

   private void sendAcceptBootStrapResponse(ServerConfigurationInformation local) throws IOException {
      DataOutputStream dOut = new DataOutputStream(new BufferedOutputStream(this.getOutputStream()));
      dOut.writeBytes("accept:" + local.getServerName() + ":" + local.getClusterName() + '\n');
      dOut.flush();
      if (DEBUG) {
         this.debug("sent AcceptBootStrapResponse");
      }

   }

   private void setSoTimeout(Socket sock, int timeout) throws SocketException {
      String readTimeout = System.getProperty("weblogic.unicast.readTimeout");
      if (readTimeout != null) {
         try {
            timeout = Integer.parseInt(readTimeout);
         } catch (NumberFormatException var5) {
         }
      }

      if (timeout > 0) {
         sock.setSoTimeout(timeout);
         if (DEBUG) {
            this.debug("setSoTimeout: " + timeout);
         }
      }

   }

   static {
      DEBUG = Environment.DEBUG;
   }
}
