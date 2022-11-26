package com.trilead.ssh2.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalAcceptThread extends Thread implements IChannelWorkerThread {
   ChannelManager cm;
   String host_to_connect;
   int port_to_connect;
   final ServerSocket ss;

   public LocalAcceptThread(ChannelManager cm, int local_port, String host_to_connect, int port_to_connect) throws IOException {
      this.cm = cm;
      this.host_to_connect = host_to_connect;
      this.port_to_connect = port_to_connect;
      this.ss = new ServerSocket(local_port);
   }

   public LocalAcceptThread(ChannelManager cm, InetSocketAddress localAddress, String host_to_connect, int port_to_connect) throws IOException {
      this.cm = cm;
      this.host_to_connect = host_to_connect;
      this.port_to_connect = port_to_connect;
      this.ss = new ServerSocket();
      this.ss.bind(localAddress);
   }

   public void run() {
      try {
         this.cm.registerThread(this);
      } catch (IOException var10) {
         this.stopWorking();
         return;
      }

      while(true) {
         Socket s = null;

         try {
            s = this.ss.accept();
         } catch (IOException var7) {
            this.stopWorking();
            return;
         }

         Channel cn = null;
         StreamForwarder r2l = null;
         StreamForwarder l2r = null;

         try {
            cn = this.cm.openDirectTCPIPChannel(this.host_to_connect, this.port_to_connect, s.getInetAddress().getHostAddress(), s.getPort());
         } catch (IOException var12) {
            try {
               s.close();
            } catch (IOException var9) {
            }
            continue;
         }

         try {
            r2l = new StreamForwarder(cn, (StreamForwarder)null, (Socket)null, cn.stdoutStream, s.getOutputStream(), "RemoteToLocal");
            l2r = new StreamForwarder(cn, r2l, s, s.getInputStream(), cn.stdinStream, "LocalToRemote");
         } catch (IOException var11) {
            IOException e = var11;

            try {
               cn.cm.closeChannel(cn, "Weird error during creation of StreamForwarder (" + e.getMessage() + ")", true);
            } catch (IOException var8) {
            }
            continue;
         }

         r2l.setDaemon(true);
         l2r.setDaemon(true);
         r2l.start();
         l2r.start();
      }
   }

   public void stopWorking() {
      try {
         this.ss.close();
      } catch (IOException var2) {
      }

   }
}
