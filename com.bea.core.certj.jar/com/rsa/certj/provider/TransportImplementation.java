package com.rsa.certj.provider;

import com.rsa.certj.CertJ;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.ProviderImplementation;
import com.rsa.certj.core.util.b;
import com.rsa.certj.provider.pki.HTTPResult;
import com.rsa.certj.spi.pki.PKIException;
import com.rsa.certj.spi.pki.PKIResult;
import com.rsa.certj.spi.pki.PKIStatusInfo;
import com.rsa.certj.spi.pki.PKITransportException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

/** @deprecated */
public abstract class TransportImplementation extends ProviderImplementation {
   /** @deprecated */
   public static final String MIME_CONTENT_TYPE_PREFIX = "Content-type: ";
   /** @deprecated */
   public static final String MIME_CONTENT_LENGTH_PREFIX = "Content-length: ";
   /** @deprecated */
   protected static final String MIME_USER_AGENT_STRING = "User-Agent: Cert-J/" + b.b();
   /** @deprecated */
   protected static final String HTTP_HEADER_CACHE_CONTROL_NO_CACHE = "Cache-Control: no-cache";
   /** @deprecated */
   protected static final String HTTP_HEADER_PRAGMA_NO_CACHE = "Pragma: no-cache";
   private static final int DEFAULT_HTTP_PORT = 80;
   private static final int DEFAULT_HTTPS_PORT = 443;
   private static final int READ_BUFFER_SIZE = 30000;
   private static final char CR = '\r';
   private static final char LF = '\n';
   private static final String CRLF = "\r\n";
   /** @deprecated */
   protected String[] destList;
   /** @deprecated */
   protected String[] proxyList;
   /** @deprecated */
   protected String profile;
   /** @deprecated */
   protected int timeoutSecs;
   /** @deprecated */
   protected String host;
   /** @deprecated */
   protected int port;
   /** @deprecated */
   protected Socket socket;
   /** @deprecated */
   private String urlFile;
   /** @deprecated */
   protected boolean closeConnection;

   private Socket socketWithTimeout(String var1, int var2, int var3) throws IOException {
      a var4 = new a(var1, var2);
      synchronized(var4.a) {
         var4.start();

         try {
            var4.a.wait((long)var3);
         } catch (InterruptedException var8) {
         }
      }

      if (var4.b != null) {
         return var4.b;
      } else if (var4.c != null) {
         throw var4.c;
      } else {
         var4.interrupt();
         throw new InterruptedIOException("connect timed out");
      }
   }

   /** @deprecated */
   public TransportImplementation(CertJ var1, String var2) throws InvalidParameterException {
      super(var1, var2);
   }

   /** @deprecated */
   public synchronized void unregister() {
      try {
         if (this.socket != null) {
            this.socket.close();
         }
      } catch (Exception var2) {
      }

   }

   /** @deprecated */
   public PKIResult sendAndReceiveHttp(URL var1, String[] var2, String[] var3, byte[] var4, String[] var5) throws PKIException {
      HTTPResult var6 = this.http("POST", var1, var3, var2, var4);
      String[] var7 = var6.getHeaders();
      int var8 = var6.getStatus();
      int var9 = this.mapHTTPStatus(var8);
      int var10 = this.mapHTTPFailInfo(var8);
      int var12;
      if (var9 == 0) {
         String var11 = null;

         for(var12 = 0; var12 < var7.length; ++var12) {
            String var13 = var7[var12];
            int var14 = "Content-type: ".length();
            if (var13.length() > var14 && "Content-type: ".equalsIgnoreCase(var13.substring(0, var14))) {
               var11 = var13;
               break;
            }
         }

         if (var11 == null) {
            throw new PKIException("TransportImplementation.sendAndReceiveHttp: no Content-type found.");
         }

         boolean var16 = false;

         for(int var17 = 0; var17 < var5.length; ++var17) {
            String var18 = var5[var17];
            if (var11.length() >= var18.length() && var18.equalsIgnoreCase(var11.substring(0, var18.length()))) {
               var16 = true;
               break;
            }
         }

         if (!var16) {
            var9 = 2;
            var10 = 2097152;
         }
      }

      if (var9 != 0 && var6.getMessage().length > 0) {
         String[] var15 = var7;
         var12 = var7.length;
         var7 = new String[var12 + 2];
         System.arraycopy(var15, 0, var7, 0, var12);
         var7[var12] = null;
         var7[var12 + 1] = new String(var6.getMessage());
      }

      return new PKIResult(new PKIStatusInfo(var9, var10, var7, var8), var6.getMessage());
   }

   /** @deprecated */
   public synchronized HTTPResult http(String var1, URL var2, String[] var3, String[] var4, byte[] var5) throws PKIException {
      if (this.socket != null) {
         return this.httpSend(var1, var4, var5);
      } else {
         int var6 = 0;
         if (var3 != null) {
            var6 = var3.length;
         }

         if (var5 == null) {
            var5 = new byte[0];
         }

         if (var3 != null && var6 != 0) {
            String var7 = var2.getHost();
            int var8 = var2.getPort();
            int var9 = 0;

            while(var9 < var6) {
               String var11 = null;

               URL var10;
               try {
                  var11 = var3[var9];
                  var10 = new URL(var11);
               } catch (MalformedURLException var13) {
                  throw new PKIException("TransportImplementation.http: unable to parse proxy specification" + var11 + ".", var13);
               }

               try {
                  this.host = var10.getHost();
                  this.port = var10.getPort();
                  String var12 = ":" + (var8 <= 0 ? 80 : var8);
                  this.urlFile = "http://" + var7 + var12 + this.getFile(var2);
                  return this.httpSend(var1, var4, var5);
               } catch (Exception var14) {
                  ++var9;
               }
            }

            throw new PKIException("TransportImplementation.http: no proxy succeeds.");
         } else {
            this.host = var2.getHost();
            this.port = this.getPort(var2);
            this.urlFile = this.getFile(var2);
            return this.httpSend(var1, var4, var5);
         }
      }
   }

   /** @deprecated */
   public int mapHTTPStatus(int var1) {
      return var1 >= 200 && var1 < 300 ? 0 : 2;
   }

   /** @deprecated */
   public int mapHTTPFailInfo(int var1) {
      if (var1 < 200) {
         return 2097152;
      } else if (var1 >= 200 && var1 < 300) {
         return 0;
      } else if (var1 >= 300 && var1 < 400) {
         return 2097152;
      } else if (var1 >= 400 && var1 < 500) {
         return 536870912;
      } else {
         return var1 >= 500 && var1 < 600 ? 1048576 : 2097152;
      }
   }

   private HTTPResult httpSend(String var1, String[] var2, byte[] var3) throws PKIException {
      if (var1 != null && var1.equalsIgnoreCase("POST")) {
         StringBuffer var4 = new StringBuffer(var1 + " " + this.urlFile + " HTTP/1.0" + "\r\n");
         if (var2 == null) {
            throw new PKIException("TransportImplementation.httpSend: Content-Type not found.");
         } else {
            int var5 = var2.length;
            boolean var6 = false;

            for(int var7 = 0; var7 < var5; ++var7) {
               String var8 = var2[var7];
               if (var8.length() >= "Content-type: ".length() && "Content-type: ".equalsIgnoreCase(var8.substring(0, "Content-type: ".length()))) {
                  var6 = true;
               }

               if (var8.length() >= "Content-length: ".length() && "Content-length: ".equalsIgnoreCase(var8.substring(0, "Content-length: ".length()))) {
                  throw new PKIException("TransportImplementation.httpSend: found Content-Length.");
               }

               var4.append(var8).append("\r\n");
            }

            if (!var6) {
               throw new PKIException("TransportImplementation.httpSend: Content-Type not found.");
            } else {
               var4.append("Content-length: ");
               var4.append(var3.length);
               var4.append("\r\n\r\n");
               Object var23 = null;

               byte[] var24;
               try {
                  this.openSocketIfNecessary();
                  byte[] var25 = (new String(var4)).getBytes();
                  var24 = this.httpTransport(var25, var3, true);
               } finally {
                  if (this.socket != null && this.closeConnection) {
                     try {
                        this.socket.close();
                        this.socket = null;
                     } catch (IOException var21) {
                        throw new PKITransportException("TransportImplementation.httpSend: unable to close a socket.", var21);
                     }
                  }

               }

               int var26 = this.getHTTPHeaderLen(var24);
               String var9 = new String(var24, 0, var26);
               byte[] var10 = new byte[var24.length - var26];
               System.arraycopy(var24, var26, var10, 0, var24.length - var26);
               Vector var11 = new Vector();
               int var12 = 0;
               boolean var13 = false;

               int var14;
               int var15;
               for(var14 = 0; var14 < var9.length(); ++var14) {
                  var15 = var9.charAt(var14);
                  switch (var15) {
                     case 10:
                        if (var13) {
                           var11.addElement(var9.substring(var12, var14 - 1));
                           var12 = var14 + 1;
                        }

                        var13 = false;
                        break;
                     case 13:
                        var13 = true;
                        break;
                     default:
                        var13 = false;
                  }
               }

               var14 = var11.size();
               if (var14 == 0) {
                  throw new PKIException("TransportImplementation.httpSend: no headers received.");
               } else {
                  var15 = this.findStatusNumber((String)var11.elementAt(0));
                  String[] var16 = new String[var14 - 1];

                  for(int var17 = 1; var17 < var14; ++var17) {
                     var16[var17 - 1] = (String)var11.elementAt(var17);
                  }

                  return new HTTPResult(var15, var16, var10);
               }
            }
         }
      } else {
         throw new PKIException("TransportImplementation.httpSend: only POST is supported.");
      }
   }

   private byte[] httpTransport(byte[] var1, byte[] var2, boolean var3) throws PKITransportException {
      try {
         this.writeToSocket(var1);
         this.writeToSocket(var2, 0, var2.length);
         byte[] var4 = this.readFromSocket();
         if (var3 && var4.length == 0) {
            this.openSocket();
            return this.httpTransport(var1, var2, false);
         } else {
            return var4;
         }
      } catch (IOException var5) {
         if (var3) {
            this.openSocket();
            return this.httpTransport(var1, var2, false);
         } else {
            throw new PKITransportException("TransportImplementation.httpTransfer: socket I/O failed.", var5);
         }
      }
   }

   /** @deprecated */
   protected void openSocket() throws PKITransportException {
      try {
         if (this.timeoutSecs >= 0) {
            this.socket = this.socketWithTimeout(this.host, this.port, this.timeoutSecs * 1000);
         } else {
            this.socket = new Socket(this.host, this.port);
         }

         if (this.timeoutSecs >= 0) {
            this.socket.setSoTimeout(this.timeoutSecs * 1000);
         }

      } catch (UnknownHostException var2) {
         throw new PKITransportException("TransportImplementation.openSocket: unknown host(" + this.host + ")", var2);
      } catch (SocketException var3) {
         throw new PKITransportException("TransportImplementation.openSocket: socket operation failed.", var3);
      } catch (IOException var4) {
         throw new PKITransportException("TransportImplementation.openSocket: IO operation failed.", var4);
      }
   }

   /** @deprecated */
   protected void writeToSocket(byte[] var1) throws PKITransportException, IOException {
      this.writeToSocket(var1, 0, var1.length);
   }

   /** @deprecated */
   protected void writeToSocket(byte[] var1, int var2, int var3) throws PKITransportException, IOException {
      OutputStream var4 = this.socket.getOutputStream();
      var4.write(var1, var2, var3);
      var4.flush();
   }

   private byte[] readFromSocket() throws IOException {
      byte[] var1 = new byte[30000];
      byte[] var2 = new byte[0];
      byte[] var3 = var2;

      int var5;
      for(InputStream var4 = this.socket.getInputStream(); (var5 = var4.read(var1)) >= 0; var2 = var3) {
         var3 = new byte[var2.length + var5];
         System.arraycopy(var2, 0, var3, 0, var2.length);
         System.arraycopy(var1, 0, var3, var2.length, var5);
      }

      return var3;
   }

   private int getHTTPHeaderLen(byte[] var1) throws PKIException {
      int var2 = 0;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         switch (var1[var3]) {
            case 10:
               if (var2 == 1 || var2 == 3) {
                  ++var2;
               }
               break;
            case 13:
               if (var2 == 0 || var2 == 2) {
                  ++var2;
               }
               break;
            default:
               var2 = 0;
         }

         if (var2 == 4) {
            return var3 + 1;
         }
      }

      throw new PKIException("TransportImplementation.getHTTPHeaderLen: HTTP header not found.");
   }

   private int getPort(URL var1) throws PKIException {
      int var2 = var1.getPort();
      if (var2 != -1) {
         return var2;
      } else if (var1.getProtocol().equalsIgnoreCase("http")) {
         return 80;
      } else if (var1.getProtocol().equalsIgnoreCase("https")) {
         return 443;
      } else {
         throw new PKIException("TransportImplementation.getPort: make sure to specify the port number for URLs.");
      }
   }

   private String getFile(URL var1) {
      String var2 = var1.getFile();
      return var2 != null && !var2.equals("") ? var2 : "/";
   }

   private int findStatusNumber(String var1) throws PKIException {
      StringTokenizer var2 = new StringTokenizer(var1, " ");
      int var3 = var2.countTokens();
      if (var3 < 2) {
         throw new PKIException("PKICommonImplementation.findStatusNumber: invalid status line.");
      } else {
         var2.nextToken();
         String var4 = var2.nextToken();
         int var5 = 0;

         for(int var6 = 0; var6 < var4.length(); ++var6) {
            char var7 = var4.charAt(var6);
            if (var7 < '0' || var7 > '9') {
               throw new PKIException("PKICommonImplementation.findStatusNumber: non-decimal digit found in status code.");
            }

            var5 = var5 * 10 + var7 - 48;
         }

         return var5;
      }
   }

   /** @deprecated */
   protected void openSocketIfNecessary() throws PKITransportException {
      if (this.socket == null) {
         this.openSocket();
      }

   }

   /** @deprecated */
   public String getName() {
      return super.getName();
   }

   static class a extends Thread {
      Object a;
      Socket b;
      IOException c;
      private final String d;
      private final int e;

      a(String var1, int var2) {
         this.d = var1;
         this.e = var2;
         this.a = new Object();
      }

      public void run() {
         Socket var1;
         try {
            var1 = new Socket(this.d, this.e);
         } catch (IOException var8) {
            if (interrupted()) {
               return;
            }

            this.c = var8;
            synchronized(this.a) {
               this.a.notify();
               return;
            }
         }

         synchronized(this.a) {
            this.b = var1;
            this.a.notify();
         }
      }
   }
}
