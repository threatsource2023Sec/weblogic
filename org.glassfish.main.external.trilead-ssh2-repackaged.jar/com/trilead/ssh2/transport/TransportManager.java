package com.trilead.ssh2.transport;

import com.trilead.ssh2.ConnectionInfo;
import com.trilead.ssh2.ConnectionMonitor;
import com.trilead.ssh2.DHGexParameters;
import com.trilead.ssh2.HTTPProxyData;
import com.trilead.ssh2.HTTPProxyException;
import com.trilead.ssh2.ProxyData;
import com.trilead.ssh2.ServerHostKeyVerifier;
import com.trilead.ssh2.crypto.Base64;
import com.trilead.ssh2.crypto.CryptoWishList;
import com.trilead.ssh2.crypto.cipher.BlockCipher;
import com.trilead.ssh2.crypto.digest.MAC;
import com.trilead.ssh2.log.Logger;
import com.trilead.ssh2.packets.PacketDisconnect;
import com.trilead.ssh2.packets.TypesReader;
import com.trilead.ssh2.util.Tokenizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Vector;

public class TransportManager {
   private static final Logger log;
   private final Vector asynchronousQueue = new Vector();
   private Thread asynchronousThread = null;
   String hostname;
   int port;
   final Socket sock = new Socket();
   Object connectionSemaphore = new Object();
   boolean flagKexOngoing = false;
   boolean connectionClosed = false;
   Throwable reasonClosedCause = null;
   TransportConnection tc;
   KexManager km;
   Vector messageHandlers = new Vector();
   Thread receiveThread;
   Vector connectionMonitors = new Vector();
   boolean monitorsWereInformed = false;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$transport$TransportManager;

   private InetAddress createInetAddress(String host) throws UnknownHostException {
      InetAddress addr = this.parseIPv4Address(host);
      return addr != null ? addr : InetAddress.getByName(host);
   }

   private InetAddress parseIPv4Address(String host) throws UnknownHostException {
      if (host == null) {
         return null;
      } else {
         String[] quad = Tokenizer.parseTokens(host, '.');
         if (quad != null && quad.length == 4) {
            byte[] addr = new byte[4];

            for(int i = 0; i < 4; ++i) {
               int part = 0;
               if (quad[i].length() == 0 || quad[i].length() > 3) {
                  return null;
               }

               for(int k = 0; k < quad[i].length(); ++k) {
                  char c = quad[i].charAt(k);
                  if (c < '0' || c > '9') {
                     return null;
                  }

                  part = part * 10 + (c - 48);
               }

               if (part > 255) {
                  return null;
               }

               addr[i] = (byte)part;
            }

            return InetAddress.getByAddress(host, addr);
         } else {
            return null;
         }
      }
   }

   public TransportManager(String host, int port) throws IOException {
      this.hostname = host;
      this.port = port;
   }

   public int getPacketOverheadEstimate() {
      return this.tc.getPacketOverheadEstimate();
   }

   public void setTcpNoDelay(boolean state) throws IOException {
      this.sock.setTcpNoDelay(state);
   }

   public void setSoTimeout(int timeout) throws IOException {
      this.sock.setSoTimeout(timeout);
   }

   public ConnectionInfo getConnectionInfo(int kexNumber) throws IOException {
      return this.km.getOrWaitForConnectionInfo(kexNumber);
   }

   public Throwable getReasonClosedCause() {
      synchronized(this.connectionSemaphore) {
         return this.reasonClosedCause;
      }
   }

   public byte[] getSessionIdentifier() {
      return this.km.sessionId;
   }

   public void close(Throwable cause, boolean useDisconnectPacket) {
      if (!useDisconnectPacket) {
         try {
            this.sock.close();
         } catch (IOException var10) {
         }
      }

      synchronized(this.connectionSemaphore) {
         if (!this.connectionClosed) {
            if (useDisconnectPacket) {
               try {
                  byte[] msg = (new PacketDisconnect(11, cause.getMessage(), "")).getPayload();
                  if (this.tc != null) {
                     this.tc.sendMessage(msg);
                  }
               } catch (IOException var9) {
               }

               try {
                  this.sock.close();
               } catch (IOException var8) {
               }
            }

            this.connectionClosed = true;
            this.reasonClosedCause = cause;
         }

         this.connectionSemaphore.notifyAll();
      }

      Vector monitors = null;
      synchronized(this) {
         if (!this.monitorsWereInformed) {
            this.monitorsWereInformed = true;
            monitors = (Vector)this.connectionMonitors.clone();
         }
      }

      if (monitors != null) {
         for(int i = 0; i < monitors.size(); ++i) {
            try {
               ConnectionMonitor cmon = (ConnectionMonitor)monitors.elementAt(i);
               cmon.connectionLost(this.reasonClosedCause);
            } catch (Exception var7) {
            }
         }
      }

   }

   private void establishConnection(ProxyData proxyData, int connectTimeout) throws IOException {
      if (proxyData == null) {
         InetAddress addr = this.createInetAddress(this.hostname);
         this.sock.connect(new InetSocketAddress(addr, this.port), connectTimeout);
         this.sock.setSoTimeout(0);
      } else if (proxyData instanceof HTTPProxyData) {
         HTTPProxyData pd = (HTTPProxyData)proxyData;
         InetAddress addr = this.createInetAddress(pd.proxyHost);
         this.sock.connect(new InetSocketAddress(addr, pd.proxyPort), connectTimeout);
         this.sock.setSoTimeout(0);
         StringBuffer sb = new StringBuffer();
         sb.append("CONNECT ");
         sb.append(this.hostname);
         sb.append(':');
         sb.append(this.port);
         sb.append(" HTTP/1.0\r\n");
         if (pd.proxyUser != null && pd.proxyPass != null) {
            String credentials = pd.proxyUser + ":" + pd.proxyPass;
            char[] encoded = Base64.encode(credentials.getBytes());
            sb.append("Proxy-Authorization: Basic ");
            sb.append(encoded);
            sb.append("\r\n");
         }

         if (pd.requestHeaderLines != null) {
            for(int i = 0; i < pd.requestHeaderLines.length; ++i) {
               if (pd.requestHeaderLines[i] != null) {
                  sb.append(pd.requestHeaderLines[i]);
                  sb.append("\r\n");
               }
            }
         }

         sb.append("\r\n");
         OutputStream out = this.sock.getOutputStream();
         out.write(sb.toString().getBytes());
         out.flush();
         byte[] buffer = new byte[1024];
         InputStream in = this.sock.getInputStream();
         int len = ClientServerHello.readLineRN(in, buffer);
         String httpReponse = new String(buffer, 0, len);
         if (!httpReponse.startsWith("HTTP/")) {
            throw new IOException("The proxy did not send back a valid HTTP response.");
         } else if (httpReponse.length() >= 14 && httpReponse.charAt(8) == ' ' && httpReponse.charAt(12) == ' ') {
            int errorCode = false;

            int errorCode;
            try {
               errorCode = Integer.parseInt(httpReponse.substring(9, 12));
            } catch (NumberFormatException var13) {
               throw new IOException("The proxy did not send back a valid HTTP response.");
            }

            if (errorCode >= 0 && errorCode <= 999) {
               if (errorCode != 200) {
                  throw new HTTPProxyException(httpReponse.substring(13), errorCode);
               } else {
                  do {
                     len = ClientServerHello.readLineRN(in, buffer);
                  } while(len != 0);

               }
            } else {
               throw new IOException("The proxy did not send back a valid HTTP response.");
            }
         } else {
            throw new IOException("The proxy did not send back a valid HTTP response.");
         }
      } else {
         throw new IOException("Unsupported ProxyData");
      }
   }

   public void initialize(CryptoWishList cwl, ServerHostKeyVerifier verifier, DHGexParameters dhgex, int connectTimeout, SecureRandom rnd, ProxyData proxyData) throws IOException {
      this.establishConnection(proxyData, connectTimeout);
      ClientServerHello csh = new ClientServerHello(this.sock.getInputStream(), this.sock.getOutputStream());
      this.tc = new TransportConnection(this.sock.getInputStream(), this.sock.getOutputStream(), rnd);
      this.km = new KexManager(this, csh, cwl, this.hostname, this.port, verifier, rnd);
      this.km.initiateKEX(cwl, dhgex);
      this.receiveThread = new Thread(new Runnable() {
         public void run() {
            try {
               TransportManager.this.receiveLoop();
            } catch (IOException var6) {
               TransportManager.this.close(var6, false);
               if (TransportManager.log.isEnabled()) {
                  TransportManager.log.log(10, "Receive thread: error in receiveLoop: " + var6.getMessage());
               }
            }

            if (TransportManager.log.isEnabled()) {
               TransportManager.log.log(50, "Receive thread: back from receiveLoop");
            }

            if (TransportManager.this.km != null) {
               try {
                  TransportManager.this.km.handleMessage((byte[])null, 0);
               } catch (IOException var5) {
               }
            }

            for(int i = 0; i < TransportManager.this.messageHandlers.size(); ++i) {
               HandlerEntry he = (HandlerEntry)TransportManager.this.messageHandlers.elementAt(i);

               try {
                  he.mh.handleMessage((byte[])null, 0);
               } catch (Exception var4) {
               }
            }

         }
      });
      this.receiveThread.setDaemon(true);
      this.receiveThread.start();
   }

   public void registerMessageHandler(MessageHandler mh, int low, int high) {
      HandlerEntry he = new HandlerEntry();
      he.mh = mh;
      he.low = low;
      he.high = high;
      synchronized(this.messageHandlers) {
         this.messageHandlers.addElement(he);
      }
   }

   public void removeMessageHandler(MessageHandler mh, int low, int high) {
      synchronized(this.messageHandlers) {
         for(int i = 0; i < this.messageHandlers.size(); ++i) {
            HandlerEntry he = (HandlerEntry)this.messageHandlers.elementAt(i);
            if (he.mh == mh && he.low == low && he.high == high) {
               this.messageHandlers.removeElementAt(i);
               break;
            }
         }

      }
   }

   public void sendKexMessage(byte[] msg) throws IOException {
      synchronized(this.connectionSemaphore) {
         if (this.connectionClosed) {
            throw (IOException)(new IOException("Sorry, this connection is closed.")).initCause(this.reasonClosedCause);
         } else {
            this.flagKexOngoing = true;

            try {
               this.tc.sendMessage(msg);
            } catch (IOException var5) {
               this.close(var5, false);
               throw var5;
            }

         }
      }
   }

   public void kexFinished() throws IOException {
      synchronized(this.connectionSemaphore) {
         this.flagKexOngoing = false;
         this.connectionSemaphore.notifyAll();
      }
   }

   public void forceKeyExchange(CryptoWishList cwl, DHGexParameters dhgex) throws IOException {
      this.km.initiateKEX(cwl, dhgex);
   }

   public void changeRecvCipher(BlockCipher bc, MAC mac) {
      this.tc.changeRecvCipher(bc, mac);
   }

   public void changeSendCipher(BlockCipher bc, MAC mac) {
      this.tc.changeSendCipher(bc, mac);
   }

   public void sendAsynchronousMessage(byte[] msg) throws IOException {
      synchronized(this.asynchronousQueue) {
         this.asynchronousQueue.addElement(msg);
         if (this.asynchronousQueue.size() > 100) {
            throw new IOException("Error: the peer is not consuming our asynchronous replies.");
         } else {
            if (this.asynchronousThread == null) {
               this.asynchronousThread = new AsynchronousWorker();
               this.asynchronousThread.setDaemon(true);
               this.asynchronousThread.start();
            }

         }
      }
   }

   public void setConnectionMonitors(Vector monitors) {
      synchronized(this) {
         this.connectionMonitors = (Vector)monitors.clone();
      }
   }

   public void sendMessage(byte[] msg) throws IOException {
      if (Thread.currentThread() == this.receiveThread) {
         throw new IOException("Assertion error: sendMessage may never be invoked by the receiver thread!");
      } else {
         synchronized(this.connectionSemaphore) {
            while(!this.connectionClosed) {
               if (!this.flagKexOngoing) {
                  try {
                     this.tc.sendMessage(msg);
                  } catch (IOException var5) {
                     this.close(var5, false);
                     throw var5;
                  }

                  return;
               }

               try {
                  this.connectionSemaphore.wait();
               } catch (InterruptedException var6) {
                  throw new InterruptedIOException();
               }
            }

            throw (IOException)(new IOException("Sorry, this connection is closed.")).initCause(this.reasonClosedCause);
         }
      }
   }

   public void receiveLoop() throws IOException {
      byte[] msg = new byte['袸'];

      while(true) {
         int msglen;
         TypesReader tr;
         label90:
         do {
            while(true) {
               while(true) {
                  msglen = this.tc.receiveMessage(msg, 0, msg.length);
                  int type = msg[0] & 255;
                  if (type != 2) {
                     if (type == 4) {
                        continue label90;
                     }

                     if (type == 3) {
                        throw new IOException("Peer sent UNIMPLEMENTED message, that should not happen.");
                     }

                     int i;
                     if (type == 1) {
                        tr = new TypesReader(msg, 0, msglen);
                        tr.readByte();
                        i = tr.readUINT32();
                        StringBuffer reasonBuffer = new StringBuffer();
                        reasonBuffer.append(tr.readString("UTF-8"));
                        if (reasonBuffer.length() > 255) {
                           reasonBuffer.setLength(255);
                           reasonBuffer.setCharAt(254, '.');
                           reasonBuffer.setCharAt(253, '.');
                           reasonBuffer.setCharAt(252, '.');
                        }

                        for(int i = 0; i < reasonBuffer.length(); ++i) {
                           char c = reasonBuffer.charAt(i);
                           if (c < ' ' || c > '~') {
                              reasonBuffer.setCharAt(i, '�');
                           }
                        }

                        throw new IOException("Peer sent DISCONNECT message (reason code " + i + "): " + reasonBuffer.toString());
                     }

                     if (type == 20 || type == 21 || type >= 30 && type <= 49) {
                        this.km.handleMessage(msg, msglen);
                     } else {
                        MessageHandler mh = null;

                        for(i = 0; i < this.messageHandlers.size(); ++i) {
                           HandlerEntry he = (HandlerEntry)this.messageHandlers.elementAt(i);
                           if (he.low <= type && type <= he.high) {
                              mh = he.mh;
                              break;
                           }
                        }

                        if (mh == null) {
                           throw new IOException("Unexpected SSH message (type " + type + ")");
                        }

                        mh.handleMessage(msg, msglen);
                     }
                  }
               }
            }
         } while(!log.isEnabled());

         tr = new TypesReader(msg, 0, msglen);
         tr.readByte();
         tr.readBoolean();
         StringBuffer debugMessageBuffer = new StringBuffer();
         debugMessageBuffer.append(tr.readString("UTF-8"));

         for(int i = 0; i < debugMessageBuffer.length(); ++i) {
            char c = debugMessageBuffer.charAt(i);
            if (c < ' ' || c > '~') {
               debugMessageBuffer.setCharAt(i, '�');
            }
         }

         log.log(50, "DEBUG Message from remote: '" + debugMessageBuffer.toString() + "'");
      }
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      log = Logger.getLogger(class$com$trilead$ssh2$transport$TransportManager == null ? (class$com$trilead$ssh2$transport$TransportManager = class$("com.trilead.ssh2.transport.TransportManager")) : class$com$trilead$ssh2$transport$TransportManager);
   }

   class AsynchronousWorker extends Thread {
      public void run() {
         while(true) {
            byte[] msgx = null;
            byte[] msg;
            synchronized(TransportManager.this.asynchronousQueue) {
               if (TransportManager.this.asynchronousQueue.size() == 0) {
                  try {
                     TransportManager.this.asynchronousQueue.wait(2000L);
                  } catch (InterruptedException var6) {
                  }

                  if (TransportManager.this.asynchronousQueue.size() == 0) {
                     TransportManager.this.asynchronousThread = null;
                     return;
                  }
               }

               msg = (byte[])((byte[])TransportManager.this.asynchronousQueue.remove(0));
            }

            try {
               TransportManager.this.sendMessage(msg);
            } catch (IOException var5) {
               return;
            }
         }
      }
   }

   class HandlerEntry {
      MessageHandler mh;
      int low;
      int high;
   }
}
