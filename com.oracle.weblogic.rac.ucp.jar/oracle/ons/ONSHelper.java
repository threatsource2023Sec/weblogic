package oracle.ons;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import oracle.security.pki.OracleWallet;

public class ONSHelper {
   byte[] clusteridheader = (new String("clusterId: ")).getBytes();
   byte[] clusternameheader = (new String("clusterName: ")).getBytes();
   byte[] instanceidheader = (new String("instanceId: ")).getBytes();
   byte[] instancenameheader = (new String("instanceName: ")).getBytes();
   private static byte[] connectmessage = (new String("POST /connect HTTP/1.1\r\nVersion: 3\r\nFormFactor: ")).getBytes();
   private static byte[] endconnect = (new String("\r\n\r\n")).getBytes();
   private static byte[] selfid = (new String("\r\nSelfId: java; Home=")).getBytes();

   public void onsPing(String hostname, int port, String walletfile, char[] password) throws Exception {
      String s = System.getProperty("oracle.ons.ignorescanvip");
      if (s != null && Boolean.valueOf(s)) {
         InetAddress[] ainetaddress = InetAddress.getAllByName(hostname);
         Exception save = null;
         int i = 0;

         while(i < ainetaddress.length) {
            try {
               this.ping(ainetaddress[i].getHostAddress(), port, walletfile, password);
               return;
            } catch (Exception var10) {
               save = var10;
               ++i;
            }
         }

         throw save;
      } else {
         this.ping(hostname, port, walletfile, password);
      }
   }

   private void ping(String hostname, int port, String walletfile, char[] password) throws Exception {
      Socket s = null;
      int socketTimeout = 5000;

      String socket;
      try {
         socket = System.getProperty("oracle.ons.sockettimeout");
         if (socket != null) {
            socketTimeout = Integer.parseInt(socket);
         }
      } catch (NumberFormatException var17) {
      }

      s = null;
      socket = null;

      Socket socket;
      try {
         InetSocketAddress inetsocketaddress = new InetSocketAddress(hostname, port);
         if (walletfile != null && !walletfile.equals("")) {
            SSLContext sslcontext = SSLContext.getInstance("SSL");
            KeyManagerFactory keymanagerfactory = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory trustmanagerfactory = TrustManagerFactory.getInstance("SunX509");
            OracleWallet oraclewallet = new OracleWallet();
            oraclewallet.open(walletfile, password);
            KeyStore keystore = oraclewallet.getKeyStore();
            keymanagerfactory.init(keystore, password);
            trustmanagerfactory.init(keystore);
            sslcontext.init(keymanagerfactory.getKeyManagers(), trustmanagerfactory.getTrustManagers(), (SecureRandom)null);
            SSLSocketFactory sslsocketfactory = sslcontext.getSocketFactory();
            socket = sslsocketfactory.createSocket();
         } else {
            socket = new Socket();
         }

         socket.connect(inetsocketaddress, socketTimeout);
         OutputStream outputstream = socket.getOutputStream();
         OutputBuffer outputbuffer = new OutputBuffer(outputstream);
         outputbuffer.putBytes(connectmessage, connectmessage.length);
         outputbuffer.putBytes(selfid, selfid.length);
         outputbuffer.putString("direct-connect");
         outputbuffer.putBytes(endconnect, endconnect.length);
         outputbuffer.flush();
         InputBuffer inputbuffer = new InputBuffer(new BufferedInputStream(socket.getInputStream()));
         inputbuffer.getNextString();
         inputbuffer.getNextString();
         inputbuffer.getNextString();
         inputbuffer.getNextString();
         inputbuffer.getNextString();
         inputbuffer.getNextString();
         inputbuffer.skipBytes(this.clusteridheader.length);
         inputbuffer.getNextString();
         inputbuffer.skipBytes(this.clusternameheader.length);
         inputbuffer.getNextString();
         inputbuffer.skipBytes(this.instanceidheader.length);
         inputbuffer.getNextString();
         inputbuffer.skipBytes(this.instancenameheader.length);
         inputbuffer.getNextString();
         inputbuffer.getNextString();
      } catch (Exception var18) {
         if (s != null) {
            try {
               socket.close();
            } catch (IOException var15) {
            }
         }

         socket = null;
         s = null;
         throw var18;
      }

      if (socket != null) {
         try {
            socket.close();
         } catch (Exception var16) {
         }
      }

   }

   public class OutputBuffer {
      private static final int BUFFERSIZE = 1024;
      private OutputStream ostr;
      private byte[] buffer;
      private int spaceleft;
      private int position;

      protected OutputBuffer(OutputStream o) {
         this.ostr = o;
         this.buffer = new byte[1024];
         this.spaceleft = 1024;
         this.position = 0;
      }

      protected void putBytes(byte[] b, int len) throws IOException {
         if (this.spaceleft >= len) {
            System.arraycopy(b, 0, this.buffer, this.position, len);
            this.spaceleft -= len;
            this.position += len;
         } else if (len > 1024) {
            if (this.position > 0) {
               this.ostr.write(this.buffer, 0, this.position);
            }

            this.position = 0;
            this.spaceleft = 1024;
            this.ostr.write(b, 0, len);
         } else {
            System.arraycopy(b, 0, this.buffer, this.position, this.spaceleft);
            this.ostr.write(this.buffer, 0, 1024);
            System.arraycopy(b, this.spaceleft, this.buffer, 0, len - this.spaceleft);
            this.position = len - this.spaceleft;
            this.spaceleft = 1024 - this.position;
         }

      }

      protected void putString(String s) throws IOException {
         this.putBytes(s.getBytes(), s.length());
      }

      protected void flush() throws IOException {
         if (this.position > 0) {
            this.ostr.write(this.buffer, 0, this.position);
         }

         this.ostr.flush();
         this.position = 0;
         this.spaceleft = 1024;
      }
   }

   class InputBuffer {
      static final String END_OF_STREAM_MESSAGE = "End of data encountered.";
      private InputStream inputStream;

      protected InputBuffer(InputStream inputStream) {
         this.inputStream = inputStream;
      }

      protected int skipBytes(int len) throws IOException {
         int retVal = 0;

         for(int i = 0; i < len; ++i) {
            int b = this.inputStream.read();
            if (b < 0) {
               throw new IOException("End of data encountered.");
            }

            ++retVal;
         }

         return retVal;
      }

      protected String getNextString() throws IOException {
         StringBuilder sb = new StringBuilder();
         String retVal = null;

         while(true) {
            int b = this.inputStream.read();
            if (b < 0) {
               throw new IOException("End of data encountered.");
            }

            if (b == 13) {
               this.inputStream.read();
               if (sb.length() > 0) {
                  retVal = sb.toString();
               }

               return retVal;
            }

            sb.append((char)b);
         }
      }
   }
}
