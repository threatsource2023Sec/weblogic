package com.trilead.ssh2.channel;

import com.trilead.ssh2.log.Logger;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;

public class RemoteAcceptThread extends Thread {
   private static final Logger log;
   Channel c;
   String remoteConnectedAddress;
   int remoteConnectedPort;
   String remoteOriginatorAddress;
   int remoteOriginatorPort;
   String targetAddress;
   int targetPort;
   Socket s;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$channel$RemoteAcceptThread;

   public RemoteAcceptThread(Channel c, String remoteConnectedAddress, int remoteConnectedPort, String remoteOriginatorAddress, int remoteOriginatorPort, String targetAddress, int targetPort) {
      this.c = c;
      this.remoteConnectedAddress = remoteConnectedAddress;
      this.remoteConnectedPort = remoteConnectedPort;
      this.remoteOriginatorAddress = remoteOriginatorAddress;
      this.remoteOriginatorPort = remoteOriginatorPort;
      this.targetAddress = targetAddress;
      this.targetPort = targetPort;
      if (log.isEnabled()) {
         log.log(20, "RemoteAcceptThread: " + remoteConnectedAddress + "/" + remoteConnectedPort + ", R: " + remoteOriginatorAddress + "/" + remoteOriginatorPort);
      }

   }

   public void run() {
      try {
         this.c.cm.sendOpenConfirmation(this.c);
         this.s = new Socket(this.targetAddress, this.targetPort);
         StreamForwarder r2l = new StreamForwarder(this.c, (StreamForwarder)null, (Socket)null, this.c.getStdoutStream(), this.s.getOutputStream(), "RemoteToLocal");
         StreamForwarder l2r = new StreamForwarder(this.c, (StreamForwarder)null, (Socket)null, this.s.getInputStream(), this.c.getStdinStream(), "LocalToRemote");
         r2l.setDaemon(true);
         r2l.start();
         l2r.run();

         while(r2l.isAlive()) {
            try {
               r2l.join();
            } catch (InterruptedException var6) {
               throw new InterruptedIOException();
            }
         }

         this.c.cm.closeChannel(this.c, "EOF on both streams reached.", true);
         this.s.close();
      } catch (IOException var7) {
         IOException e = var7;
         log.log(50, "IOException in proxy code: " + var7.getMessage());

         try {
            this.c.cm.closeChannel(this.c, "IOException in proxy code (" + e.getMessage() + ")", true);
         } catch (IOException var5) {
         }

         try {
            if (this.s != null) {
               this.s.close();
            }
         } catch (IOException var4) {
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
      log = Logger.getLogger(class$com$trilead$ssh2$channel$RemoteAcceptThread == null ? (class$com$trilead$ssh2$channel$RemoteAcceptThread = class$("com.trilead.ssh2.channel.RemoteAcceptThread")) : class$com$trilead$ssh2$channel$RemoteAcceptThread);
   }
}
