package com.trilead.ssh2.channel;

import com.trilead.ssh2.log.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.Socket;

public class RemoteX11AcceptThread extends Thread {
   private static final Logger log;
   Channel c;
   String remoteOriginatorAddress;
   int remoteOriginatorPort;
   Socket s;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$channel$RemoteX11AcceptThread;

   public RemoteX11AcceptThread(Channel c, String remoteOriginatorAddress, int remoteOriginatorPort) {
      this.c = c;
      this.remoteOriginatorAddress = remoteOriginatorAddress;
      this.remoteOriginatorPort = remoteOriginatorPort;
   }

   public void run() {
      try {
         this.c.cm.sendOpenConfirmation(this.c);
         OutputStream remote_os = this.c.getStdinStream();
         InputStream remote_is = this.c.getStdoutStream();
         byte[] header = new byte[6];
         if (remote_is.read(header) != 6) {
            throw new IOException("Unexpected EOF on X11 startup!");
         }

         if (header[0] != 66 && header[0] != 108) {
            throw new IOException("Unknown endian format in X11 message!");
         }

         int idxMSB = header[0] == 66 ? 0 : 1;
         byte[] auth_buff = new byte[6];
         if (remote_is.read(auth_buff) != 6) {
            throw new IOException("Unexpected EOF on X11 startup!");
         }

         int authProtocolNameLength = (auth_buff[idxMSB] & 255) << 8 | auth_buff[1 - idxMSB] & 255;
         int authProtocolDataLength = (auth_buff[2 + idxMSB] & 255) << 8 | auth_buff[3 - idxMSB] & 255;
         if (authProtocolNameLength > 256 || authProtocolDataLength > 256) {
            throw new IOException("Buggy X11 authorization data");
         }

         int authProtocolNamePadding = (4 - authProtocolNameLength % 4) % 4;
         int authProtocolDataPadding = (4 - authProtocolDataLength % 4) % 4;
         byte[] authProtocolName = new byte[authProtocolNameLength];
         byte[] authProtocolData = new byte[authProtocolDataLength];
         byte[] paddingBuffer = new byte[4];
         if (remote_is.read(authProtocolName) != authProtocolNameLength) {
            throw new IOException("Unexpected EOF on X11 startup! (authProtocolName)");
         }

         if (remote_is.read(paddingBuffer, 0, authProtocolNamePadding) != authProtocolNamePadding) {
            throw new IOException("Unexpected EOF on X11 startup! (authProtocolNamePadding)");
         }

         if (remote_is.read(authProtocolData) != authProtocolDataLength) {
            throw new IOException("Unexpected EOF on X11 startup! (authProtocolData)");
         }

         if (remote_is.read(paddingBuffer, 0, authProtocolDataPadding) != authProtocolDataPadding) {
            throw new IOException("Unexpected EOF on X11 startup! (authProtocolDataPadding)");
         }

         if (!"MIT-MAGIC-COOKIE-1".equals(new String(authProtocolName))) {
            throw new IOException("Unknown X11 authorization protocol!");
         }

         if (authProtocolDataLength != 16) {
            throw new IOException("Wrong data length for X11 authorization data!");
         }

         StringBuffer tmp = new StringBuffer(32);

         for(int i = 0; i < authProtocolData.length; ++i) {
            String digit2 = Integer.toHexString(authProtocolData[i] & 255);
            tmp.append(digit2.length() == 2 ? digit2 : "0" + digit2);
         }

         String hexEncodedFakeCookie = tmp.toString();
         synchronized(this.c) {
            this.c.hexX11FakeCookie = hexEncodedFakeCookie;
         }

         X11ServerData sd = this.c.cm.checkX11Cookie(hexEncodedFakeCookie);
         if (sd == null) {
            throw new IOException("Invalid X11 cookie received.");
         }

         this.s = new Socket(sd.hostname, sd.port);
         OutputStream x11_os = this.s.getOutputStream();
         InputStream x11_is = this.s.getInputStream();
         x11_os.write(header);
         if (sd.x11_magic_cookie == null) {
            byte[] emptyAuthData = new byte[6];
            x11_os.write(emptyAuthData);
         } else {
            if (sd.x11_magic_cookie.length != 16) {
               throw new IOException("The real X11 cookie has an invalid length!");
            }

            x11_os.write(auth_buff);
            x11_os.write(authProtocolName);
            x11_os.write(paddingBuffer, 0, authProtocolNamePadding);
            x11_os.write(sd.x11_magic_cookie);
            x11_os.write(paddingBuffer, 0, authProtocolDataPadding);
         }

         x11_os.flush();
         StreamForwarder r2l = new StreamForwarder(this.c, (StreamForwarder)null, (Socket)null, remote_is, x11_os, "RemoteToX11");
         StreamForwarder l2r = new StreamForwarder(this.c, (StreamForwarder)null, (Socket)null, x11_is, remote_os, "X11ToRemote");
         r2l.setDaemon(true);
         r2l.start();
         l2r.run();

         while(r2l.isAlive()) {
            try {
               r2l.join();
            } catch (InterruptedException var23) {
               throw new InterruptedIOException();
            }
         }

         this.c.cm.closeChannel(this.c, "EOF on both X11 streams reached.", true);
         this.s.close();
      } catch (IOException var25) {
         IOException e = var25;
         log.log(50, "IOException in X11 proxy code: " + var25.getMessage());

         try {
            this.c.cm.closeChannel(this.c, "IOException in X11 proxy code (" + e.getMessage() + ")", true);
         } catch (IOException var22) {
         }

         try {
            if (this.s != null) {
               this.s.close();
            }
         } catch (IOException var21) {
         }
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
      log = Logger.getLogger(class$com$trilead$ssh2$channel$RemoteX11AcceptThread == null ? (class$com$trilead$ssh2$channel$RemoteX11AcceptThread = class$("com.trilead.ssh2.channel.RemoteX11AcceptThread")) : class$com$trilead$ssh2$channel$RemoteX11AcceptThread);
   }
}
