package weblogic.jms.dotnet.transport.t3client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.StringTokenizer;
import weblogic.jms.dotnet.transport.Transport;

public class T3Connection {
   private final T3ConnectionLock lock = new T3ConnectionLock();
   private static final boolean DEBUG = false;
   private static final SecureRandom RANDOM = new SecureRandom();
   static final byte T3_CONNECTION_DISPATCHER_ID_DEFAULT = 0;
   static final byte T3_CONNECTION_DISPATCHER_ID_TRANSPORT = 1;
   private static final byte REMOTE_INVOKE_HELLO_REQUEST = 1;
   private static final byte REMOTE_INVOKE_HELLO_RESPONSE = 2;
   private static final byte REMOTE_INVOKE_DISPATCH_REQUEST = 3;
   private static final byte REMOTE_INVOKE_FAILURE = 4;
   private static final int INIT = 0;
   private static final int LOGIN = 1;
   private static final int HELLO = 2;
   private static final int READY = 3;
   private static final int CLOSED = 4;
   private static final int CLIENT_RAWADDRESS = 0;
   private final Socket socket;
   private final OutputStream output;
   private final InputStream input;
   private final long transportId;
   private final T3PeerInfo lPeerInfo;
   private final T3PeerInfo rPeerInfo;
   private final T3JVMID lJvmId;
   private final T3JVMID rJvmId;
   private long scratchID;
   private final byte serviceId;
   private static final boolean SEQUENCE_ENABLED = false;
   private int nextSendSequenceId = 0;
   private int nextExpectedSequenceId = 0;
   private int state;
   private boolean inProgress = false;
   private final T3Header T3HEADER_IDENTIFY;
   private final T3Header T3HEADER_ONEWAY;
   private final T3Header T3HEADER_TWOWAY;
   private int backLog;
   private boolean hasWaiter;
   private MarshalWriterImpl first;
   private MarshalWriterImpl last;

   T3Connection(String hostAddress, int port, T3PeerInfo peerInfo, byte serviceId) throws Exception {
      this.T3HEADER_IDENTIFY = new T3Header(T3.PROTOCOL_IDENTIFY_REQUEST_NO_PEERINFO, T3.PROTOCOL_QOS_ANY, T3.PROTOCOL_FLAG_JVMID, T3.PROTOCOL_RESPONSEID, T3.PROTOCOL_INVOKEID_NONE);
      this.T3HEADER_ONEWAY = new T3Header(T3.PROTOCOL_ONE_WAY, T3.PROTOCOL_QOS_ANY, T3.PROTOCOL_FLAG_NONE, T3.PROTOCOL_RESPONSEID_NONE, T3.PROTOCOL_INVOKEID_JMSCSHARP_SERVICE);
      this.T3HEADER_TWOWAY = new T3Header(T3.PROTOCOL_REQUEST, T3.PROTOCOL_QOS_ANY, T3.PROTOCOL_FLAG_NONE, T3.PROTOCOL_RESPONSEID, T3.PROTOCOL_INVOKEID_JMSCSHARP_SERVICE);
      this.scratchID = RANDOM.nextLong();
      this.lJvmId = new T3JVMID((byte)1, this.scratchID, hostAddress, 0, (int[])null);
      this.rJvmId = new T3JVMID((byte)1, 0L, hostAddress, 0, new int[]{port, -1, -1, -1, -1, -1, -1});
      this.serviceId = serviceId;
      if (peerInfo == null) {
         this.lPeerInfo = T3PeerInfo.defaultPeerInfo;
      } else {
         this.lPeerInfo = peerInfo;
      }

      this.state = 0;
      this.inProgress = false;
      this.socket = new Socket(hostAddress, port);
      this.output = this.socket.getOutputStream();
      this.input = this.socket.getInputStream();
      this.state = 1;
      this.inProgress = true;
      MarshalWriterImpl lr = createLoginRequest(this.lPeerInfo);
      this.sendSocket(lr);
      this.rPeerInfo = processLoginResponse(this.input);
      MarshalWriterImpl ir = createIdentifyRequest(this.lPeerInfo, this.lJvmId, this.rJvmId, this.T3HEADER_IDENTIFY);
      this.sendSocket(ir);
      processIdentifyResponse(this.input);
      this.state = 2;
      MarshalWriterImpl hr = createHelloRequest(this.lPeerInfo, this.lJvmId, serviceId, this.T3HEADER_TWOWAY);
      this.sendSocket(hr);
      this.transportId = processHelloResponse(this.input);
      this.state = 3;
      this.inProgress = false;
   }

   void sendSocket(MarshalWriterImpl mwi) throws IOException {
      mwi.copyTo(this.output);
   }

   T3PeerInfo getLocalPeerInfo() {
      return this.lPeerInfo;
   }

   T3PeerInfo getRemotePeerInfo() {
      synchronized(this.lock) {
         return this.rPeerInfo;
      }
   }

   T3JVMID getLocalJVMID() {
      return this.lJvmId;
   }

   T3JVMID getRemoteJVMID() {
      return this.rJvmId;
   }

   void close() {
      synchronized(this.lock) {
         if (this.state == 4) {
            return;
         }

         this.state = 4;
      }

      System.out.println("Debug: Closing T3 Conn " + this);

      try {
         this.socket.close();
      } catch (Exception var3) {
      }

   }

   MarshalReaderImpl receiveOneWay(Transport t) throws Exception {
      synchronized(this.lock) {
         if (this.state != 3 || this.inProgress) {
            throw new Exception("Connection failure, state = " + this.getStateAsString());
         }
      }

      T3Message msg = processResponse(this.input, false, t);
      MarshalReaderImpl payload = msg.getPayload();

      try {
         if (payload != null && msg.getBodyLength() >= 5) {
            int code = payload.readByte();
            if (code != 3) {
               throw new Exception("Unknown connection opcode " + code);
            } else {
               return payload;
            }
         } else {
            throw new Exception("Unknown connection message syntax");
         }
      } catch (Exception var5) {
         msg.cleanup();
         throw var5;
      }
   }

   private static T3Message processResponse(InputStream input, boolean showBody) throws Exception {
      return processResponse(input, showBody, (Transport)null);
   }

   private static T3Message processResponse(InputStream input, boolean showBody, Transport transport) throws Exception {
      T3Message msg = T3Message.readT3Message(input, transport);
      return msg;
   }

   static MarshalWriterImpl createOneWay(MarshalWriterImpl mwi) {
      if (mwi == null) {
         mwi = new MarshalWriterImpl();
      }

      mwi.skip(19);
      mwi.skip(9);
      return mwi;
   }

   void sendOneWay(MarshalWriterImpl mwi) throws Exception {
      MarshalWriterImpl current = null;
      synchronized(this.lock) {
         if (this.state != 3 || this.inProgress) {
            mwi.closeInternal();
            throw new Exception("Connection is not ready");
         }

         ++this.backLog;
         mwi.setNext((MarshalWriterImpl)null);
         if (this.last != null) {
            this.last.setNext(mwi);
            this.last = mwi;
            if (this.backLog > 32) {
               this.hasWaiter = true;

               try {
                  this.lock.wait();
               } catch (InterruptedException var17) {
               }
            }

            return;
         }

         this.first = mwi;
         this.last = mwi;
         current = mwi;
      }

      while(true) {
         int savePos = current.getPosition();
         current.setPosition(19);
         current.write(3);
         current.writeLong(this.transportId);
         current.setPosition(savePos);
         T3Message msg = new T3Message(this.T3HEADER_ONEWAY, T3Abbrev.NULL_ABBREVS);
         msg.write(current);
         boolean var15 = false;

         try {
            var15 = true;
            this.sendSocket(current);
            var15 = false;
         } finally {
            if (var15) {
               current.closeInternal();
               synchronized(this.lock) {
                  --this.backLog;
                  this.first = this.first.getNext();
                  if (this.first == null) {
                     this.last = null;
                     if (this.hasWaiter) {
                        this.lock.notifyAll();
                     }

                     return;
                  }

                  current = this.first;
               }
            }
         }

         current.closeInternal();
         synchronized(this.lock) {
            --this.backLog;
            this.first = this.first.getNext();
            if (this.first == null) {
               this.last = null;
               if (this.hasWaiter) {
                  this.lock.notifyAll();
               }

               return;
            }

            current = this.first;
         }
      }
   }

   private static MarshalWriterImpl createLoginRequest(T3PeerInfo localPeerInfo) {
      MarshalWriterImpl mwi = new MarshalWriterImpl();
      String loginString = T3.PROTOCOL_NAME + " " + localPeerInfo.getVersion() + T3.PROTOCOL_DELIMITER + T3.PROTOCOL_ABBV + T3.PROTOCOL_ABBV_DELIMITER + T3.PROTOCOL_ABBV_SIZE + T3.PROTOCOL_DELIMITER + T3.PROTOCOL_HDR + T3.PROTOCOL_HDR_DELIMITER + T3.PROTOCOL_HDR_SIZE + T3.PROTOCOL_DELIMITER + T3.PROTOCOL_DELIMITER;
      int len = loginString.length();
      byte[] buf = new byte[len];
      loginString.getBytes(0, len, buf, 0);
      mwi.write(buf, 0, buf.length);
      return mwi;
   }

   private static T3PeerInfo processLoginResponse(InputStream in) throws Exception {
      String input = readLine(in);
      StringTokenizer token = new StringTokenizer(input, ":");
      if (token.countTokens() < 2) {
         throw new Exception("Unknown Login response  " + input);
      } else {
         String str = token.nextToken();
         if (!str.equalsIgnoreCase(T3.PROTOCOL_REPLY_OK)) {
            throw new Exception("Unknown Login response  " + input);
         } else {
            str = token.nextToken();
            T3PeerInfo peerInfo = new T3PeerInfo(str);

            String line;
            while((line = readLine(in)) != null && line.length() != 0) {
               checkConnectionParams(line);
            }

            return peerInfo;
         }
      }
   }

   private static MarshalWriterImpl createIdentifyRequest(T3PeerInfo lPeerInfo, T3JVMID lJvmId, T3JVMID rJvmId, T3Header t3Header) throws Exception {
      MarshalWriterImpl mwi = new MarshalWriterImpl();
      mwi.skip(19);
      mwi.writeInt(T3.PROTOCOL_HEARTBEAT_DISABLE);
      mwi.writeInt(0);
      lPeerInfo.write(mwi);
      T3Abbrev[] abbrevs = new T3Abbrev[]{new T3Abbrev(255, (T3JVMID)null), new T3Abbrev(256, rJvmId), new T3Abbrev(256, lJvmId)};
      T3Message msg = new T3Message(t3Header, abbrevs);
      msg.write(mwi);
      return mwi;
   }

   private static void processIdentifyResponse(InputStream input) throws Exception {
      T3Message msg = processResponse(input, false);

      try {
         T3Header hdr = msg.getMessageHeader();
         if (hdr.getCommand() != T3.PROTOCOL_IDENTIFY_RESPONSE_NO_PEERINFO) {
            throw new Exception("Unknown identify response " + hdr.getCommand());
         }
      } finally {
         msg.cleanup();
      }

   }

   private static MarshalWriterImpl createHelloRequest(T3PeerInfo lPeerInfo, T3JVMID lJvmId, byte serviceId, T3Header t3Header) throws Exception {
      MarshalWriterImpl mwi = new MarshalWriterImpl();
      mwi.skip(19);
      mwi.write(1);
      lPeerInfo.write(mwi);
      mwi.write(serviceId);
      mwi.write(lJvmId.getFlags());
      mwi.writeLong(lJvmId.getDifferentiator());
      mwi.writeUTF(lJvmId.getHostAddress());
      mwi.writeInt(lJvmId.getRawAddress());
      mwi.writeInt(0);
      T3Message msg = new T3Message(t3Header, T3Abbrev.NULL_ABBREVS);
      msg.write(mwi);
      return mwi;
   }

   private static long processHelloResponse(InputStream input) throws Exception {
      T3Message msg = processResponse(input, false);
      MarshalReaderImpl mri = msg.getPayload();

      long var6;
      try {
         int code = mri.readByte();
         if (code != 2) {
            throw new Exception("Expect hello response but got " + msg.getMessageHeader().getCommand() + "," + code);
         }

         long transportId = mri.readLong();
         var6 = transportId;
      } finally {
         msg.cleanup();
      }

      return var6;
   }

   private static void checkConnectionParams(String input) throws Exception {
      int var1;
      if (input.charAt(0) == T3.PROTOCOL_ABBV.charAt(0) && input.charAt(1) == T3.PROTOCOL_ABBV.charAt(1) && input.charAt(2) == ':') {
         var1 = Integer.parseInt(input.substring(3, input.length()));
      } else {
         if (input.charAt(0) != T3.PROTOCOL_HDR.charAt(0) || input.charAt(1) != T3.PROTOCOL_HDR.charAt(1) || input.charAt(2) != ':') {
            throw new Exception("Unknown connection parameters " + input);
         }

         var1 = Integer.parseInt(input.substring(3, input.length()));
      }

   }

   private static String readLine(InputStream in) throws IOException {
      char[] buf = new char[128];
      int offset = 0;
      int c = in.read();

      while(true) {
         if (c == -1) {
            if (offset == 0) {
               return null;
            }
            break;
         }

         if (c == 10) {
            if (offset > 0 && buf[offset - 1] == '\r') {
               --offset;
            }
            break;
         }

         if (offset == buf.length) {
            char[] old = buf;
            buf = new char[offset + 128];
            System.arraycopy(old, 0, buf, 0, offset);
         }

         buf[offset++] = (char)c;
         c = in.read();
      }

      return String.copyValueOf(buf, 0, offset);
   }

   public String getStateAsString() {
      synchronized(this.lock) {
         switch (this.state) {
            case 0:
               return "INIT";
            case 1:
               return "LOGIN";
            case 2:
               return "HELLO";
            case 3:
               return "READY";
            case 4:
               return "CLOSED";
            default:
               return "UNKNOWN";
         }
      }
   }

   public long getScratchID() {
      return this.scratchID;
   }

   public String toString() {
      return this.getStateAsString() + " " + (this.inProgress ? "In Progress" : "done");
   }
}
