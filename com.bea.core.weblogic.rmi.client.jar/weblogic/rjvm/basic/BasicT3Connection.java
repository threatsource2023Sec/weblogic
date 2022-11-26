package weblogic.rjvm.basic;

import commonj.work.Work;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.protocol.OutgoingMessage;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.TransportUtils;
import weblogic.socket.Login;
import weblogic.utils.io.Chunk;

public class BasicT3Connection extends MsgAbbrevJVMConnection implements Work {
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("DebugConnection");
   private static final DebugLogger debugMessaging = DebugLogger.getDebugLogger("DebugMessaging");
   private static final String ENCODING = "UTF-8";
   private final ServerChannel channel;
   private String partitionUrl;
   private Socket socket;
   private InputStream input;
   private OutputStream output;
   private volatile boolean closed;
   private int messageLen = -1;
   private Chunk headChunk = null;
   private Chunk tailChunk = null;

   BasicT3Connection(ServerChannel channel, String partitionUrl) {
      this.channel = channel;
      this.partitionUrl = partitionUrl;
   }

   public void connect(String host, InetAddress address, int port, int timeout) throws IOException {
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("Connecting to " + address + " port " + port);
      }

      this.socket = new Socket(address, port);
      if (timeout > 0) {
         this.socket.setSoTimeout(timeout);
      }

      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("Connected to " + this.socket);
      }

      StringBuilder bootstrapMsg = new StringBuilder("t3 ");
      bootstrapMsg.append(VersionInfo.theOne().getReleaseVersion()).append('\n').append("AS").append(':').append(MsgAbbrevJVMConnection.ABBREV_TABLE_SIZE).append('\n').append("HL").append(':').append(19).append('\n');
      bootstrapMsg.append("MS").append(':').append(this.channel.getMaxMessageSize()).append('\n').append("PU").append(':').append(this.partitionUrl).append('\n');
      String localPName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      bootstrapMsg.append("LP").append(':').append(localPName).append("\n\n");
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("Sending bootstrap message: " + bootstrapMsg);
      }

      this.output = this.socket.getOutputStream();
      this.output.write(bootstrapMsg.toString().getBytes("UTF-8"));
      this.input = this.socket.getInputStream();
      BufferedReader br = new BufferedReader(new InputStreamReader(this.input));
      String line = br.readLine();
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("Got back bootstrap header " + line);
      }

      String result = Login.checkLoginSuccess(line);
      if (result != null) {
         this.close();
         throw new IOException(result);
      } else {
         String version = Login.getVersionString(line);
         if (version == null) {
            throw new IOException("No peer version found");
         } else {
            TransportUtils.BootstrapResult bootstrapResult = TransportUtils.readBootstrapParams(br);
            if (!bootstrapResult.isSuccess()) {
               this.close();
               throw new IOException(Login.RET_CODES[1]);
            } else {
               String remotePName = bootstrapResult.getPartitionName();
               if (remotePName == null) {
                  remotePName = "DOMAIN";
               }

               this.init(bootstrapResult.getAbbrevSize(), bootstrapResult.getHeaderLength(), bootstrapResult.getPeerChannelMaxMessageSize(), localPName, this.partitionUrl, remotePName);
            }
         }
      }
   }

   public void sendMsg(OutgoingMessage msg) throws IOException {
      Chunk chunk = msg.getChunks();
      if (debugMessaging.isDebugEnabled()) {
         debugMessaging.debug("About to send message of " + Chunk.size(chunk) + " bytes");
      }

      while(chunk != null) {
         this.output.write(chunk.buf, 0, chunk.end);
         if (debugMessaging.isDebugEnabled()) {
            debugMessaging.debug("Wrote " + chunk.end + " bytes");
         }

         Chunk dead = chunk;
         chunk = chunk.next;
         Chunk.releaseChunk(dead);
      }

   }

   public void close() {
      if (debugConnection.isDebugEnabled()) {
         Exception e = new Exception();
         debugConnection.debug("Closing socket " + this.socket, e);
      }

      this.closed = true;

      try {
         this.socket.close();
      } catch (IOException var2) {
      }

   }

   public int getLocalPort() {
      return this.socket == null ? 0 : this.socket.getLocalPort();
   }

   public InetAddress getLocalAddress() {
      return this.socket == null ? null : this.socket.getLocalAddress();
   }

   public ServerChannel getChannel() {
      return this.channel;
   }

   public final InetAddress getInetAddress() {
      return this.socket == null ? null : this.socket.getInetAddress();
   }

   public boolean isDaemon() {
      return true;
   }

   public void release() {
   }

   public void run() {
      label45:
      while(true) {
         if (!this.closed) {
            if (this.headChunk == null) {
               this.headChunk = this.tailChunk = Chunk.getChunk();
            } else {
               this.tailChunk = Chunk.ensureCapacity(this.tailChunk);
            }

            try {
               int bytesRead = this.input.read(this.tailChunk.buf, this.tailChunk.end, this.tailChunk.buf.length - this.tailChunk.end);
               if (bytesRead > 0) {
                  Chunk var10000 = this.tailChunk;
                  var10000.end += bytesRead;
               } else if (bytesRead < 0) {
                  throw new EOFException();
               }

               if (debugMessaging.isDebugEnabled()) {
                  debugMessaging.debug("Read " + bytesRead + " into chunk of new length " + this.tailChunk.end);
               }
            } catch (IOException var4) {
               if (debugConnection.isDebugEnabled()) {
                  debugConnection.debug("Got exception: " + var4, var4);
               }

               super.gotExceptionReceiving(var4);

               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var3) {
               }
            }

            while(true) {
               if (!this.processChunks()) {
                  continue label45;
               }
            }
         }

         return;
      }
   }

   private boolean processChunks() {
      if (this.headChunk == null) {
         return false;
      } else {
         int chunkLen = Chunk.size(this.headChunk);
         if (this.messageLen < 0) {
            if (chunkLen < 4) {
               return false;
            }

            int r0 = this.getHeaderByte(0) & 255;
            int r1 = this.getHeaderByte(1) & 255;
            int r2 = this.getHeaderByte(2) & 255;
            int r3 = this.getHeaderByte(3) & 255;
            this.messageLen = r0 << 24 | r1 << 16 | r2 << 8 | r3;
         }

         if (debugMessaging.isDebugEnabled()) {
            debugMessaging.debug("Currently have " + chunkLen + " bytes and next message is " + this.messageLen + " bytes long");
         }

         if (chunkLen < this.messageLen) {
            return false;
         } else {
            Chunk msgChunks = this.headChunk;
            this.headChunk = Chunk.split(this.headChunk, this.messageLen);
            if (this.headChunk == null) {
               this.tailChunk = null;
            }

            this.messageLen = -1;
            this.dispatch(msgChunks);
            return true;
         }
      }
   }

   private byte getHeaderByte(int index) {
      return this.headChunk.end > index ? this.headChunk.buf[index] : this.headChunk.next.buf[index - this.headChunk.end];
   }
}
