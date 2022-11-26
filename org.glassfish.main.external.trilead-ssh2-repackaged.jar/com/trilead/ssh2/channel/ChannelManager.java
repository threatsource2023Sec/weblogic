package com.trilead.ssh2.channel;

import com.trilead.ssh2.log.Logger;
import com.trilead.ssh2.packets.PacketChannelOpenConfirmation;
import com.trilead.ssh2.packets.PacketChannelOpenFailure;
import com.trilead.ssh2.packets.PacketChannelTrileadPing;
import com.trilead.ssh2.packets.PacketGlobalCancelForwardRequest;
import com.trilead.ssh2.packets.PacketGlobalForwardRequest;
import com.trilead.ssh2.packets.PacketGlobalTrileadPing;
import com.trilead.ssh2.packets.PacketOpenDirectTCPIPChannel;
import com.trilead.ssh2.packets.PacketOpenSessionChannel;
import com.trilead.ssh2.packets.PacketSessionExecCommand;
import com.trilead.ssh2.packets.PacketSessionPtyRequest;
import com.trilead.ssh2.packets.PacketSessionStartShell;
import com.trilead.ssh2.packets.PacketSessionSubsystemRequest;
import com.trilead.ssh2.packets.PacketSessionX11Request;
import com.trilead.ssh2.packets.TypesReader;
import com.trilead.ssh2.transport.MessageHandler;
import com.trilead.ssh2.transport.TransportManager;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.HashMap;
import java.util.Vector;

public class ChannelManager implements MessageHandler {
   private static final Logger log;
   private HashMap x11_magic_cookies = new HashMap();
   private TransportManager tm;
   private Vector channels = new Vector();
   private int nextLocalChannel = 100;
   private boolean shutdown = false;
   private int globalSuccessCounter = 0;
   private int globalFailedCounter = 0;
   private HashMap remoteForwardings = new HashMap();
   private Vector listenerThreads = new Vector();
   private boolean listenerThreadsAllowed = true;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$channel$ChannelManager;

   public ChannelManager(TransportManager tm) {
      this.tm = tm;
      tm.registerMessageHandler(this, 80, 100);
   }

   private Channel getChannel(int id) {
      synchronized(this.channels) {
         for(int i = 0; i < this.channels.size(); ++i) {
            Channel c = (Channel)this.channels.elementAt(i);
            if (c.localID == id) {
               return c;
            }
         }

         return null;
      }
   }

   private void removeChannel(int id) {
      synchronized(this.channels) {
         for(int i = 0; i < this.channels.size(); ++i) {
            Channel c = (Channel)this.channels.elementAt(i);
            if (c.localID == id) {
               this.channels.removeElementAt(i);
               break;
            }
         }

      }
   }

   private int addChannel(Channel c) {
      synchronized(this.channels) {
         this.channels.addElement(c);
         return this.nextLocalChannel++;
      }
   }

   private void waitUntilChannelOpen(Channel c) throws IOException {
      synchronized(c) {
         while(c.state == 1) {
            try {
               c.wait();
            } catch (InterruptedException var5) {
               throw new InterruptedIOException();
            }
         }

         if (c.state != 2) {
            this.removeChannel(c.localID);
            String detail = c.getReasonClosed();
            if (detail == null) {
               detail = "state: " + c.state;
            }

            throw new IOException("Could not open channel (" + detail + ")");
         }
      }
   }

   private final boolean waitForGlobalRequestResult() throws IOException {
      synchronized(this.channels) {
         while(this.globalSuccessCounter == 0 && this.globalFailedCounter == 0) {
            if (this.shutdown) {
               throw new IOException("The connection is being shutdown");
            }

            try {
               this.channels.wait();
            } catch (InterruptedException var4) {
               throw new InterruptedIOException();
            }
         }

         if (this.globalFailedCounter == 0 && this.globalSuccessCounter == 1) {
            return true;
         } else if (this.globalFailedCounter == 1 && this.globalSuccessCounter == 0) {
            return false;
         } else {
            throw new IOException("Illegal state. The server sent " + this.globalSuccessCounter + " SSH_MSG_REQUEST_SUCCESS and " + this.globalFailedCounter + " SSH_MSG_REQUEST_FAILURE messages.");
         }
      }
   }

   private final boolean waitForChannelRequestResult(Channel c) throws IOException {
      synchronized(c) {
         while(c.successCounter == 0 && c.failedCounter == 0) {
            if (c.state != 2) {
               String detail = c.getReasonClosed();
               if (detail == null) {
                  detail = "state: " + c.state;
               }

               throw new IOException("This SSH2 channel is not open (" + detail + ")");
            }

            try {
               c.wait();
            } catch (InterruptedException var5) {
               throw new InterruptedIOException();
            }
         }

         if (c.failedCounter == 0 && c.successCounter == 1) {
            return true;
         } else if (c.failedCounter == 1 && c.successCounter == 0) {
            return false;
         } else {
            throw new IOException("Illegal state. The server sent " + c.successCounter + " SSH_MSG_CHANNEL_SUCCESS and " + c.failedCounter + " SSH_MSG_CHANNEL_FAILURE messages.");
         }
      }
   }

   public void registerX11Cookie(String hexFakeCookie, X11ServerData data) {
      synchronized(this.x11_magic_cookies) {
         this.x11_magic_cookies.put(hexFakeCookie, data);
      }
   }

   public void unRegisterX11Cookie(String hexFakeCookie, boolean killChannels) {
      if (hexFakeCookie == null) {
         throw new IllegalStateException("hexFakeCookie may not be null");
      } else {
         synchronized(this.x11_magic_cookies) {
            this.x11_magic_cookies.remove(hexFakeCookie);
         }

         if (killChannels) {
            if (log.isEnabled()) {
               log.log(50, "Closing all X11 channels for the given fake cookie");
            }

            Vector channel_copy;
            synchronized(this.channels) {
               channel_copy = (Vector)this.channels.clone();
            }

            for(int i = 0; i < channel_copy.size(); ++i) {
               Channel c = (Channel)channel_copy.elementAt(i);
               synchronized(c) {
                  if (!hexFakeCookie.equals(c.hexX11FakeCookie)) {
                     continue;
                  }
               }

               try {
                  this.closeChannel(c, "Closing X11 channel since the corresponding session is closing", true);
               } catch (IOException var8) {
               }
            }

         }
      }
   }

   public X11ServerData checkX11Cookie(String hexFakeCookie) {
      synchronized(this.x11_magic_cookies) {
         return hexFakeCookie != null ? (X11ServerData)this.x11_magic_cookies.get(hexFakeCookie) : null;
      }
   }

   public void closeAllChannels() {
      if (log.isEnabled()) {
         log.log(50, "Closing all channels");
      }

      Vector channel_copy;
      synchronized(this.channels) {
         channel_copy = (Vector)this.channels.clone();
      }

      for(int i = 0; i < channel_copy.size(); ++i) {
         Channel c = (Channel)channel_copy.elementAt(i);

         try {
            this.closeChannel(c, "Closing all channels", true);
         } catch (IOException var5) {
         }
      }

   }

   public void closeChannel(Channel c, String reason, boolean force) throws IOException {
      byte[] msg = new byte[5];
      synchronized(c) {
         if (force) {
            c.state = 4;
            c.EOF = true;
         }

         c.setReasonClosed(reason);
         msg[0] = 97;
         msg[1] = (byte)(c.remoteID >> 24);
         msg[2] = (byte)(c.remoteID >> 16);
         msg[3] = (byte)(c.remoteID >> 8);
         msg[4] = (byte)c.remoteID;
         c.notifyAll();
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            return;
         }

         this.tm.sendMessage(msg);
         c.closeMessageSent = true;
      }

      if (log.isEnabled()) {
         log.log(50, "Sent SSH_MSG_CHANNEL_CLOSE (channel " + c.localID + ")");
      }

   }

   public void sendEOF(Channel c) throws IOException {
      byte[] msg = new byte[5];
      synchronized(c) {
         if (c.state != 2) {
            return;
         }

         msg[0] = 96;
         msg[1] = (byte)(c.remoteID >> 24);
         msg[2] = (byte)(c.remoteID >> 16);
         msg[3] = (byte)(c.remoteID >> 8);
         msg[4] = (byte)c.remoteID;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            return;
         }

         this.tm.sendMessage(msg);
      }

      if (log.isEnabled()) {
         log.log(50, "Sent EOF (Channel " + c.localID + "/" + c.remoteID + ")");
      }

   }

   public void sendOpenConfirmation(Channel c) throws IOException {
      PacketChannelOpenConfirmation pcoc = null;
      synchronized(c) {
         if (c.state != 1) {
            return;
         }

         c.state = 2;
         pcoc = new PacketChannelOpenConfirmation(c.remoteID, c.localID, c.localWindow, c.localMaxPacketSize);
      }

      synchronized(c.channelSendLock) {
         if (!c.closeMessageSent) {
            this.tm.sendMessage(pcoc.getPayload());
         }
      }
   }

   public void sendData(Channel c, byte[] buffer, int pos, int len) throws IOException {
      while(len > 0) {
         int thislen = false;
         byte[] msg;
         int thislen;
         synchronized(c) {
            while(true) {
               if (c.state == 4) {
                  throw new IOException("SSH channel is closed. (" + c.getReasonClosed() + ")");
               }

               if (c.state != 2) {
                  throw new IOException("SSH channel in strange state. (" + c.state + ")");
               }

               if (c.remoteWindow != 0L) {
                  thislen = c.remoteWindow >= (long)len ? len : (int)c.remoteWindow;
                  int estimatedMaxDataLen = c.remoteMaxPacketSize - (this.tm.getPacketOverheadEstimate() + 9);
                  if (estimatedMaxDataLen <= 0) {
                     estimatedMaxDataLen = 1;
                  }

                  if (thislen > estimatedMaxDataLen) {
                     thislen = estimatedMaxDataLen;
                  }

                  c.remoteWindow -= (long)thislen;
                  msg = new byte[9 + thislen];
                  msg[0] = 94;
                  msg[1] = (byte)(c.remoteID >> 24);
                  msg[2] = (byte)(c.remoteID >> 16);
                  msg[3] = (byte)(c.remoteID >> 8);
                  msg[4] = (byte)c.remoteID;
                  msg[5] = (byte)(thislen >> 24);
                  msg[6] = (byte)(thislen >> 16);
                  msg[7] = (byte)(thislen >> 8);
                  msg[8] = (byte)thislen;
                  System.arraycopy(buffer, pos, msg, 9, thislen);
                  break;
               }

               try {
                  c.wait();
               } catch (InterruptedException var11) {
                  throw new InterruptedIOException();
               }
            }
         }

         synchronized(c.channelSendLock) {
            if (c.closeMessageSent) {
               throw new IOException("SSH channel is closed. (" + c.getReasonClosed() + ")");
            }

            this.tm.sendMessage(msg);
         }

         pos += thislen;
         len -= thislen;
      }

   }

   public int requestGlobalForward(String bindAddress, int bindPort, String targetAddress, int targetPort) throws IOException {
      RemoteForwardingData rfd = new RemoteForwardingData();
      rfd.bindAddress = bindAddress;
      rfd.bindPort = bindPort;
      rfd.targetAddress = targetAddress;
      rfd.targetPort = targetPort;
      synchronized(this.remoteForwardings) {
         Integer key = new Integer(bindPort);
         if (this.remoteForwardings.get(key) != null) {
            throw new IOException("There is already a forwarding for remote port " + bindPort);
         }

         this.remoteForwardings.put(key, rfd);
      }

      synchronized(this.channels) {
         this.globalSuccessCounter = this.globalFailedCounter = 0;
      }

      PacketGlobalForwardRequest pgf = new PacketGlobalForwardRequest(true, bindAddress, bindPort);
      this.tm.sendMessage(pgf.getPayload());
      if (log.isEnabled()) {
         log.log(50, "Requesting a remote forwarding ('" + bindAddress + "', " + bindPort + ")");
      }

      try {
         if (!this.waitForGlobalRequestResult()) {
            throw new IOException("The server denied the request (did you enable port forwarding?)");
         } else {
            return bindPort;
         }
      } catch (IOException var12) {
         synchronized(this.remoteForwardings) {
            this.remoteForwardings.remove(rfd);
         }

         throw var12;
      }
   }

   public void requestCancelGlobalForward(int bindPort) throws IOException {
      RemoteForwardingData rfd = null;
      synchronized(this.remoteForwardings) {
         rfd = (RemoteForwardingData)this.remoteForwardings.get(new Integer(bindPort));
         if (rfd == null) {
            throw new IOException("Sorry, there is no known remote forwarding for remote port " + bindPort);
         }
      }

      synchronized(this.channels) {
         this.globalSuccessCounter = this.globalFailedCounter = 0;
      }

      PacketGlobalCancelForwardRequest pgcf = new PacketGlobalCancelForwardRequest(true, rfd.bindAddress, rfd.bindPort);
      this.tm.sendMessage(pgcf.getPayload());
      if (log.isEnabled()) {
         log.log(50, "Requesting cancelation of remote forward ('" + rfd.bindAddress + "', " + rfd.bindPort + ")");
      }

      boolean var15 = false;

      try {
         var15 = true;
         if (!this.waitForGlobalRequestResult()) {
            throw new IOException("The server denied the request.");
         }

         var15 = false;
      } finally {
         if (var15) {
            synchronized(this.remoteForwardings) {
               this.remoteForwardings.remove(rfd);
            }
         }
      }

      synchronized(this.remoteForwardings) {
         this.remoteForwardings.remove(rfd);
      }
   }

   public void registerThread(IChannelWorkerThread thr) throws IOException {
      synchronized(this.listenerThreads) {
         if (!this.listenerThreadsAllowed) {
            throw new IOException("Too late, this connection is closed.");
         } else {
            this.listenerThreads.addElement(thr);
         }
      }
   }

   public Channel openDirectTCPIPChannel(String host_to_connect, int port_to_connect, String originator_IP_address, int originator_port) throws IOException {
      Channel c = new Channel(this);
      synchronized(c) {
         c.localID = this.addChannel(c);
      }

      PacketOpenDirectTCPIPChannel dtc = new PacketOpenDirectTCPIPChannel(c.localID, c.localWindow, c.localMaxPacketSize, host_to_connect, port_to_connect, originator_IP_address, originator_port);
      this.tm.sendMessage(dtc.getPayload());
      this.waitUntilChannelOpen(c);
      return c;
   }

   public Channel openSessionChannel() throws IOException {
      Channel c = new Channel(this);
      synchronized(c) {
         c.localID = this.addChannel(c);
      }

      if (log.isEnabled()) {
         log.log(50, "Sending SSH_MSG_CHANNEL_OPEN (Channel " + c.localID + ")");
      }

      PacketOpenSessionChannel smo = new PacketOpenSessionChannel(c.localID, c.localWindow, c.localMaxPacketSize);
      this.tm.sendMessage(smo.getPayload());
      this.waitUntilChannelOpen(c);
      return c;
   }

   public void requestGlobalTrileadPing() throws IOException {
      synchronized(this.channels) {
         this.globalSuccessCounter = this.globalFailedCounter = 0;
      }

      PacketGlobalTrileadPing pgtp = new PacketGlobalTrileadPing();
      this.tm.sendMessage(pgtp.getPayload());
      if (log.isEnabled()) {
         log.log(50, "Sending SSH_MSG_GLOBAL_REQUEST 'trilead-ping'.");
      }

      try {
         if (this.waitForGlobalRequestResult()) {
            throw new IOException("Your server is alive - but buggy. It replied with SSH_MSG_REQUEST_SUCCESS when it actually should not.");
         }
      } catch (IOException var3) {
         throw (IOException)(new IOException("The ping request failed.")).initCause(var3);
      }
   }

   public void requestChannelTrileadPing(Channel c) throws IOException {
      PacketChannelTrileadPing pctp;
      synchronized(c) {
         if (c.state != 2) {
            throw new IOException("Cannot ping this channel (" + c.getReasonClosed() + ")");
         }

         pctp = new PacketChannelTrileadPing(c.remoteID);
         c.successCounter = c.failedCounter = 0;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            throw new IOException("Cannot ping this channel (" + c.getReasonClosed() + ")");
         }

         this.tm.sendMessage(pctp.getPayload());
      }

      try {
         if (this.waitForChannelRequestResult(c)) {
            throw new IOException("Your server is alive - but buggy. It replied with SSH_MSG_SESSION_SUCCESS when it actually should not.");
         }
      } catch (IOException var6) {
         throw (IOException)(new IOException("The ping request failed.")).initCause(var6);
      }
   }

   public void requestPTY(Channel c, String term, int term_width_characters, int term_height_characters, int term_width_pixels, int term_height_pixels, byte[] terminal_modes) throws IOException {
      PacketSessionPtyRequest spr;
      synchronized(c) {
         if (c.state != 2) {
            throw new IOException("Cannot request PTY on this channel (" + c.getReasonClosed() + ")");
         }

         spr = new PacketSessionPtyRequest(c.remoteID, true, term, term_width_characters, term_height_characters, term_width_pixels, term_height_pixels, terminal_modes);
         c.successCounter = c.failedCounter = 0;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            throw new IOException("Cannot request PTY on this channel (" + c.getReasonClosed() + ")");
         }

         this.tm.sendMessage(spr.getPayload());
      }

      try {
         if (!this.waitForChannelRequestResult(c)) {
            throw new IOException("The server denied the request.");
         }
      } catch (IOException var12) {
         throw (IOException)(new IOException("PTY request failed")).initCause(var12);
      }
   }

   public void requestX11(Channel c, boolean singleConnection, String x11AuthenticationProtocol, String x11AuthenticationCookie, int x11ScreenNumber) throws IOException {
      PacketSessionX11Request psr;
      synchronized(c) {
         if (c.state != 2) {
            throw new IOException("Cannot request X11 on this channel (" + c.getReasonClosed() + ")");
         }

         psr = new PacketSessionX11Request(c.remoteID, true, singleConnection, x11AuthenticationProtocol, x11AuthenticationCookie, x11ScreenNumber);
         c.successCounter = c.failedCounter = 0;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            throw new IOException("Cannot request X11 on this channel (" + c.getReasonClosed() + ")");
         }

         this.tm.sendMessage(psr.getPayload());
      }

      if (log.isEnabled()) {
         log.log(50, "Requesting X11 forwarding (Channel " + c.localID + "/" + c.remoteID + ")");
      }

      try {
         if (!this.waitForChannelRequestResult(c)) {
            throw new IOException("The server denied the request.");
         }
      } catch (IOException var10) {
         throw (IOException)(new IOException("The X11 request failed.")).initCause(var10);
      }
   }

   public void requestSubSystem(Channel c, String subSystemName) throws IOException {
      PacketSessionSubsystemRequest ssr;
      synchronized(c) {
         if (c.state != 2) {
            throw new IOException("Cannot request subsystem on this channel (" + c.getReasonClosed() + ")");
         }

         ssr = new PacketSessionSubsystemRequest(c.remoteID, true, subSystemName);
         c.successCounter = c.failedCounter = 0;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            throw new IOException("Cannot request subsystem on this channel (" + c.getReasonClosed() + ")");
         }

         this.tm.sendMessage(ssr.getPayload());
      }

      try {
         if (!this.waitForChannelRequestResult(c)) {
            throw new IOException("The server denied the request.");
         }
      } catch (IOException var7) {
         throw (IOException)(new IOException("The subsystem request failed.")).initCause(var7);
      }
   }

   public void requestExecCommand(Channel c, String cmd) throws IOException {
      PacketSessionExecCommand sm;
      synchronized(c) {
         if (c.state != 2) {
            throw new IOException("Cannot execute command on this channel (" + c.getReasonClosed() + ")");
         }

         sm = new PacketSessionExecCommand(c.remoteID, true, cmd);
         c.successCounter = c.failedCounter = 0;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            throw new IOException("Cannot execute command on this channel (" + c.getReasonClosed() + ")");
         }

         this.tm.sendMessage(sm.getPayload());
      }

      if (log.isEnabled()) {
         log.log(50, "Executing command (channel " + c.localID + ", '" + cmd + "')");
      }

      try {
         if (!this.waitForChannelRequestResult(c)) {
            throw new IOException("The server denied the request.");
         }
      } catch (IOException var7) {
         throw (IOException)(new IOException("The execute request failed.")).initCause(var7);
      }
   }

   public void requestShell(Channel c) throws IOException {
      PacketSessionStartShell sm;
      synchronized(c) {
         if (c.state != 2) {
            throw new IOException("Cannot start shell on this channel (" + c.getReasonClosed() + ")");
         }

         sm = new PacketSessionStartShell(c.remoteID, true);
         c.successCounter = c.failedCounter = 0;
      }

      synchronized(c.channelSendLock) {
         if (c.closeMessageSent) {
            throw new IOException("Cannot start shell on this channel (" + c.getReasonClosed() + ")");
         }

         this.tm.sendMessage(sm.getPayload());
      }

      try {
         if (!this.waitForChannelRequestResult(c)) {
            throw new IOException("The server denied the request.");
         }
      } catch (IOException var6) {
         throw (IOException)(new IOException("The shell request failed.")).initCause(var6);
      }
   }

   public void msgChannelExtendedData(byte[] msg, int msglen) throws IOException {
      if (msglen <= 13) {
         throw new IOException("SSH_MSG_CHANNEL_EXTENDED_DATA message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         int dataType = (msg[5] & 255) << 24 | (msg[6] & 255) << 16 | (msg[7] & 255) << 8 | msg[8] & 255;
         int len = (msg[9] & 255) << 24 | (msg[10] & 255) << 16 | (msg[11] & 255) << 8 | msg[12] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_EXTENDED_DATA message for non-existent channel " + id);
         } else if (dataType != 1) {
            throw new IOException("SSH_MSG_CHANNEL_EXTENDED_DATA message has unknown type (" + dataType + ")");
         } else if (len != msglen - 13) {
            throw new IOException("SSH_MSG_CHANNEL_EXTENDED_DATA message has wrong len (calculated " + (msglen - 13) + ", got " + len + ")");
         } else {
            if (log.isEnabled()) {
               log.log(80, "Got SSH_MSG_CHANNEL_EXTENDED_DATA (channel " + id + ", " + len + ")");
            }

            synchronized(c) {
               if (c.state != 4) {
                  if (c.state != 2) {
                     throw new IOException("Got SSH_MSG_CHANNEL_EXTENDED_DATA, but channel is not in correct state (" + c.state + ")");
                  } else if (c.localWindow < len) {
                     throw new IOException("Remote sent too much data, does not fit into window.");
                  } else {
                     c.localWindow -= len;
                     System.arraycopy(msg, 13, c.stderrBuffer, c.stderrWritepos, len);
                     c.stderrWritepos += len;
                     c.notifyAll();
                  }
               }
            }
         }
      }
   }

   public int waitForCondition(Channel c, long timeout, int condition_mask) throws InterruptedException {
      long end_time = 0L;
      boolean end_time_set = false;
      synchronized(c) {
         while(true) {
            int current_cond = 0;
            int stdoutAvail = c.stdoutWritepos - c.stdoutReadpos;
            int stderrAvail = c.stderrWritepos - c.stderrReadpos;
            if (stdoutAvail > 0) {
               current_cond |= 4;
            }

            if (stderrAvail > 0) {
               current_cond |= 8;
            }

            if (c.EOF) {
               current_cond |= 16;
            }

            if (c.getExitStatus() != null) {
               current_cond |= 32;
            }

            if (c.getExitSignal() != null) {
               current_cond |= 64;
            }

            if (c.state == 4) {
               return current_cond | 2 | 16;
            }

            if ((current_cond & condition_mask) != 0) {
               return current_cond;
            }

            if (timeout > 0L) {
               if (!end_time_set) {
                  end_time = System.currentTimeMillis() + timeout;
                  end_time_set = true;
               } else {
                  timeout = end_time - System.currentTimeMillis();
                  if (timeout <= 0L) {
                     return current_cond | 1;
                  }
               }
            }

            if (timeout > 0L) {
               c.wait(timeout);
            } else {
               c.wait();
            }
         }
      }
   }

   public int getAvailable(Channel c, boolean extended) throws IOException {
      synchronized(c) {
         int avail;
         if (extended) {
            avail = c.stderrWritepos - c.stderrReadpos;
         } else {
            avail = c.stdoutWritepos - c.stdoutReadpos;
         }

         return avail > 0 ? avail : (c.EOF ? -1 : 0);
      }
   }

   public int getChannelData(Channel c, boolean extended, byte[] target, int off, int len) throws IOException {
      int copylen = false;
      int increment = 0;
      int remoteID = false;
      int localID = false;
      int copylen;
      int remoteID;
      int localID;
      synchronized(c) {
         int stdoutAvail = false;
         int stderrAvail = false;

         while(true) {
            int stdoutAvail = c.stdoutWritepos - c.stdoutReadpos;
            int stderrAvail = c.stderrWritepos - c.stderrReadpos;
            if (!extended && stdoutAvail != 0 || extended && stderrAvail != 0) {
               if (!extended) {
                  copylen = stdoutAvail > len ? len : stdoutAvail;
                  System.arraycopy(c.stdoutBuffer, c.stdoutReadpos, target, off, copylen);
                  c.stdoutReadpos += copylen;
                  if (c.stdoutReadpos != c.stdoutWritepos) {
                     System.arraycopy(c.stdoutBuffer, c.stdoutReadpos, c.stdoutBuffer, 0, c.stdoutWritepos - c.stdoutReadpos);
                  }

                  c.stdoutWritepos -= c.stdoutReadpos;
                  c.stdoutReadpos = 0;
               } else {
                  copylen = stderrAvail > len ? len : stderrAvail;
                  System.arraycopy(c.stderrBuffer, c.stderrReadpos, target, off, copylen);
                  c.stderrReadpos += copylen;
                  if (c.stderrReadpos != c.stderrWritepos) {
                     System.arraycopy(c.stderrBuffer, c.stderrReadpos, c.stderrBuffer, 0, c.stderrWritepos - c.stderrReadpos);
                  }

                  c.stderrWritepos -= c.stderrReadpos;
                  c.stderrReadpos = 0;
               }

               if (c.state != 2) {
                  return copylen;
               }

               if (c.localWindow < 15000) {
                  int minFreeSpace = Math.min(30000 - c.stdoutWritepos, 30000 - c.stderrWritepos);
                  increment = minFreeSpace - c.localWindow;
                  c.localWindow = minFreeSpace;
               }

               remoteID = c.remoteID;
               localID = c.localID;
               break;
            }

            if (c.EOF || c.state != 2) {
               return -1;
            }

            try {
               c.wait();
            } catch (InterruptedException var17) {
               throw new InterruptedIOException();
            }
         }
      }

      if (increment > 0) {
         if (log.isEnabled()) {
            log.log(80, "Sending SSH_MSG_CHANNEL_WINDOW_ADJUST (channel " + localID + ", " + increment + ")");
         }

         synchronized(c.channelSendLock) {
            byte[] msg = c.msgWindowAdjust;
            msg[0] = 93;
            msg[1] = (byte)(remoteID >> 24);
            msg[2] = (byte)(remoteID >> 16);
            msg[3] = (byte)(remoteID >> 8);
            msg[4] = (byte)remoteID;
            msg[5] = (byte)(increment >> 24);
            msg[6] = (byte)(increment >> 16);
            msg[7] = (byte)(increment >> 8);
            msg[8] = (byte)increment;
            if (!c.closeMessageSent) {
               this.tm.sendMessage(msg);
            }
         }
      }

      return copylen;
   }

   public void msgChannelData(byte[] msg, int msglen) throws IOException {
      if (msglen <= 9) {
         throw new IOException("SSH_MSG_CHANNEL_DATA message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         int len = (msg[5] & 255) << 24 | (msg[6] & 255) << 16 | (msg[7] & 255) << 8 | msg[8] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_DATA message for non-existent channel " + id);
         } else if (len != msglen - 9) {
            throw new IOException("SSH_MSG_CHANNEL_DATA message has wrong len (calculated " + (msglen - 9) + ", got " + len + ")");
         } else {
            if (log.isEnabled()) {
               log.log(80, "Got SSH_MSG_CHANNEL_DATA (channel " + id + ", " + len + ")");
            }

            synchronized(c) {
               if (c.state != 4) {
                  if (c.state != 2) {
                     throw new IOException("Got SSH_MSG_CHANNEL_DATA, but channel is not in correct state (" + c.state + ")");
                  } else if (c.localWindow < len) {
                     throw new IOException("Remote sent too much data, does not fit into window.");
                  } else {
                     c.localWindow -= len;
                     System.arraycopy(msg, 9, c.stdoutBuffer, c.stdoutWritepos, len);
                     c.stdoutWritepos += len;
                     c.notifyAll();
                  }
               }
            }
         }
      }
   }

   public void msgChannelWindowAdjust(byte[] msg, int msglen) throws IOException {
      if (msglen != 9) {
         throw new IOException("SSH_MSG_CHANNEL_WINDOW_ADJUST message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         int windowChange = (msg[5] & 255) << 24 | (msg[6] & 255) << 16 | (msg[7] & 255) << 8 | msg[8] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_WINDOW_ADJUST message for non-existent channel " + id);
         } else {
            synchronized(c) {
               long huge = 4294967295L;
               c.remoteWindow += (long)windowChange & 4294967295L;
               if (c.remoteWindow > 4294967295L) {
                  c.remoteWindow = 4294967295L;
               }

               c.notifyAll();
            }

            if (log.isEnabled()) {
               log.log(80, "Got SSH_MSG_CHANNEL_WINDOW_ADJUST (channel " + id + ", " + windowChange + ")");
            }

         }
      }
   }

   public void msgChannelOpen(byte[] msg, int msglen) throws IOException {
      TypesReader tr = new TypesReader(msg, 0, msglen);
      tr.readByte();
      String channelType = tr.readString();
      int remoteID = tr.readUINT32();
      int remoteWindow = tr.readUINT32();
      int remoteMaxPacketSize = tr.readUINT32();
      int remoteConnectedPort;
      String remoteConnectedAddress;
      if ("x11".equals(channelType)) {
         synchronized(this.x11_magic_cookies) {
            if (this.x11_magic_cookies.size() == 0) {
               PacketChannelOpenFailure pcof = new PacketChannelOpenFailure(remoteID, 1, "X11 forwarding not activated", "");
               this.tm.sendAsynchronousMessage(pcof.getPayload());
               if (log.isEnabled()) {
                  log.log(20, "Unexpected X11 request, denying it!");
               }

               return;
            }
         }

         remoteConnectedAddress = tr.readString();
         remoteConnectedPort = tr.readUINT32();
         Channel c = new Channel(this);
         synchronized(c) {
            c.remoteID = remoteID;
            c.remoteWindow = (long)remoteWindow & 4294967295L;
            c.remoteMaxPacketSize = remoteMaxPacketSize;
            c.localID = this.addChannel(c);
         }

         RemoteX11AcceptThread rxat = new RemoteX11AcceptThread(c, remoteConnectedAddress, remoteConnectedPort);
         rxat.setDaemon(true);
         rxat.start();
      } else if ("forwarded-tcpip".equals(channelType)) {
         remoteConnectedAddress = tr.readString();
         remoteConnectedPort = tr.readUINT32();
         String remoteOriginatorAddress = tr.readString();
         int remoteOriginatorPort = tr.readUINT32();
         RemoteForwardingData rfd = null;
         synchronized(this.remoteForwardings) {
            rfd = (RemoteForwardingData)this.remoteForwardings.get(new Integer(remoteConnectedPort));
         }

         if (rfd == null) {
            PacketChannelOpenFailure pcof = new PacketChannelOpenFailure(remoteID, 1, "No thanks, unknown port in forwarded-tcpip request", "");
            this.tm.sendAsynchronousMessage(pcof.getPayload());
            if (log.isEnabled()) {
               log.log(20, "Unexpected forwarded-tcpip request, denying it!");
            }

         } else {
            Channel c = new Channel(this);
            synchronized(c) {
               c.remoteID = remoteID;
               c.remoteWindow = (long)remoteWindow & 4294967295L;
               c.remoteMaxPacketSize = remoteMaxPacketSize;
               c.localID = this.addChannel(c);
            }

            RemoteAcceptThread rat = new RemoteAcceptThread(c, remoteConnectedAddress, remoteConnectedPort, remoteOriginatorAddress, remoteOriginatorPort, rfd.targetAddress, rfd.targetPort);
            rat.setDaemon(true);
            rat.start();
         }
      } else {
         PacketChannelOpenFailure pcof = new PacketChannelOpenFailure(remoteID, 3, "Unknown channel type", "");
         this.tm.sendAsynchronousMessage(pcof.getPayload());
         if (log.isEnabled()) {
            log.log(20, "The peer tried to open an unsupported channel type (" + channelType + ")");
         }

      }
   }

   public void msgChannelRequest(byte[] msg, int msglen) throws IOException {
      TypesReader tr = new TypesReader(msg, 0, msglen);
      tr.readByte();
      int id = tr.readUINT32();
      Channel c = this.getChannel(id);
      if (c == null) {
         throw new IOException("Unexpected SSH_MSG_CHANNEL_REQUEST message for non-existent channel " + id);
      } else {
         String type = tr.readString("US-ASCII");
         boolean wantReply = tr.readBoolean();
         if (log.isEnabled()) {
            log.log(80, "Got SSH_MSG_CHANNEL_REQUEST (channel " + id + ", '" + type + "')");
         }

         if (type.equals("exit-status")) {
            if (wantReply) {
               throw new IOException("Badly formatted SSH_MSG_CHANNEL_REQUEST message, 'want reply' is true");
            } else {
               int exit_status = tr.readUINT32();
               if (tr.remain() != 0) {
                  throw new IOException("Badly formatted SSH_MSG_CHANNEL_REQUEST message");
               } else {
                  synchronized(c) {
                     c.exit_status = new Integer(exit_status);
                     c.notifyAll();
                  }

                  if (log.isEnabled()) {
                     log.log(50, "Got EXIT STATUS (channel " + id + ", status " + exit_status + ")");
                  }

               }
            }
         } else if (type.equals("exit-signal")) {
            if (wantReply) {
               throw new IOException("Badly formatted SSH_MSG_CHANNEL_REQUEST message, 'want reply' is true");
            } else {
               String signame = tr.readString("US-ASCII");
               tr.readBoolean();
               tr.readString();
               tr.readString();
               if (tr.remain() != 0) {
                  throw new IOException("Badly formatted SSH_MSG_CHANNEL_REQUEST message");
               } else {
                  synchronized(c) {
                     c.exit_signal = signame;
                     c.notifyAll();
                  }

                  if (log.isEnabled()) {
                     log.log(50, "Got EXIT SIGNAL (channel " + id + ", signal " + signame + ")");
                  }

               }
            }
         } else {
            if (wantReply) {
               byte[] reply = new byte[]{100, (byte)(c.remoteID >> 24), (byte)(c.remoteID >> 16), (byte)(c.remoteID >> 8), (byte)c.remoteID};
               this.tm.sendAsynchronousMessage(reply);
            }

            if (log.isEnabled()) {
               log.log(50, "Channel request '" + type + "' is not known, ignoring it");
            }

         }
      }
   }

   public void msgChannelEOF(byte[] msg, int msglen) throws IOException {
      if (msglen != 5) {
         throw new IOException("SSH_MSG_CHANNEL_EOF message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_EOF message for non-existent channel " + id);
         } else {
            synchronized(c) {
               c.EOF = true;
               c.notifyAll();
            }

            if (log.isEnabled()) {
               log.log(50, "Got SSH_MSG_CHANNEL_EOF (channel " + id + ")");
            }

         }
      }
   }

   public void msgChannelClose(byte[] msg, int msglen) throws IOException {
      if (msglen != 5) {
         throw new IOException("SSH_MSG_CHANNEL_CLOSE message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_CLOSE message for non-existent channel " + id);
         } else {
            synchronized(c) {
               c.EOF = true;
               c.state = 4;
               c.setReasonClosed("Close requested by remote");
               c.closeMessageRecv = true;
               this.removeChannel(c.localID);
               c.notifyAll();
            }

            if (log.isEnabled()) {
               log.log(50, "Got SSH_MSG_CHANNEL_CLOSE (channel " + id + ")");
            }

         }
      }
   }

   public void msgChannelSuccess(byte[] msg, int msglen) throws IOException {
      if (msglen != 5) {
         throw new IOException("SSH_MSG_CHANNEL_SUCCESS message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_SUCCESS message for non-existent channel " + id);
         } else {
            synchronized(c) {
               ++c.successCounter;
               c.notifyAll();
            }

            if (log.isEnabled()) {
               log.log(80, "Got SSH_MSG_CHANNEL_SUCCESS (channel " + id + ")");
            }

         }
      }
   }

   public void msgChannelFailure(byte[] msg, int msglen) throws IOException {
      if (msglen != 5) {
         throw new IOException("SSH_MSG_CHANNEL_FAILURE message has wrong size (" + msglen + ")");
      } else {
         int id = (msg[1] & 255) << 24 | (msg[2] & 255) << 16 | (msg[3] & 255) << 8 | msg[4] & 255;
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_FAILURE message for non-existent channel " + id);
         } else {
            synchronized(c) {
               ++c.failedCounter;
               c.notifyAll();
            }

            if (log.isEnabled()) {
               log.log(50, "Got SSH_MSG_CHANNEL_FAILURE (channel " + id + ")");
            }

         }
      }
   }

   public void msgChannelOpenConfirmation(byte[] msg, int msglen) throws IOException {
      PacketChannelOpenConfirmation sm = new PacketChannelOpenConfirmation(msg, 0, msglen);
      Channel c = this.getChannel(sm.recipientChannelID);
      if (c == null) {
         throw new IOException("Unexpected SSH_MSG_CHANNEL_OPEN_CONFIRMATION message for non-existent channel " + sm.recipientChannelID);
      } else {
         synchronized(c) {
            if (c.state != 1) {
               throw new IOException("Unexpected SSH_MSG_CHANNEL_OPEN_CONFIRMATION message for channel " + sm.recipientChannelID);
            }

            c.remoteID = sm.senderChannelID;
            c.remoteWindow = (long)sm.initialWindowSize & 4294967295L;
            c.remoteMaxPacketSize = sm.maxPacketSize;
            c.state = 2;
            c.notifyAll();
         }

         if (log.isEnabled()) {
            log.log(50, "Got SSH_MSG_CHANNEL_OPEN_CONFIRMATION (channel " + sm.recipientChannelID + " / remote: " + sm.senderChannelID + ")");
         }

      }
   }

   public void msgChannelOpenFailure(byte[] msg, int msglen) throws IOException {
      if (msglen < 5) {
         throw new IOException("SSH_MSG_CHANNEL_OPEN_FAILURE message has wrong size (" + msglen + ")");
      } else {
         TypesReader tr = new TypesReader(msg, 0, msglen);
         tr.readByte();
         int id = tr.readUINT32();
         Channel c = this.getChannel(id);
         if (c == null) {
            throw new IOException("Unexpected SSH_MSG_CHANNEL_OPEN_FAILURE message for non-existent channel " + id);
         } else {
            int reasonCode = tr.readUINT32();
            String description = tr.readString("UTF-8");
            String reasonCodeSymbolicName = null;
            switch (reasonCode) {
               case 1:
                  reasonCodeSymbolicName = "SSH_OPEN_ADMINISTRATIVELY_PROHIBITED";
                  break;
               case 2:
                  reasonCodeSymbolicName = "SSH_OPEN_CONNECT_FAILED";
                  break;
               case 3:
                  reasonCodeSymbolicName = "SSH_OPEN_UNKNOWN_CHANNEL_TYPE";
                  break;
               case 4:
                  reasonCodeSymbolicName = "SSH_OPEN_RESOURCE_SHORTAGE";
                  break;
               default:
                  reasonCodeSymbolicName = "UNKNOWN REASON CODE (" + reasonCode + ")";
            }

            StringBuffer descriptionBuffer = new StringBuffer();
            descriptionBuffer.append(description);

            for(int i = 0; i < descriptionBuffer.length(); ++i) {
               char cc = descriptionBuffer.charAt(i);
               if (cc < ' ' || cc > '~') {
                  descriptionBuffer.setCharAt(i, '�');
               }
            }

            synchronized(c) {
               c.EOF = true;
               c.state = 4;
               c.setReasonClosed("The server refused to open the channel (" + reasonCodeSymbolicName + ", '" + descriptionBuffer.toString() + "')");
               c.notifyAll();
            }

            if (log.isEnabled()) {
               log.log(50, "Got SSH_MSG_CHANNEL_OPEN_FAILURE (channel " + id + ")");
            }

         }
      }
   }

   public void msgGlobalRequest(byte[] msg, int msglen) throws IOException {
      TypesReader tr = new TypesReader(msg, 0, msglen);
      tr.readByte();
      String requestName = tr.readString();
      boolean wantReply = tr.readBoolean();
      if (wantReply) {
         byte[] reply_failure = new byte[]{82};
         this.tm.sendAsynchronousMessage(reply_failure);
      }

      if (log.isEnabled()) {
         log.log(80, "Got SSH_MSG_GLOBAL_REQUEST (" + requestName + ")");
      }

   }

   public void msgGlobalSuccess() throws IOException {
      synchronized(this.channels) {
         ++this.globalSuccessCounter;
         this.channels.notifyAll();
      }

      if (log.isEnabled()) {
         log.log(80, "Got SSH_MSG_REQUEST_SUCCESS");
      }

   }

   public void msgGlobalFailure() throws IOException {
      synchronized(this.channels) {
         ++this.globalFailedCounter;
         this.channels.notifyAll();
      }

      if (log.isEnabled()) {
         log.log(80, "Got SSH_MSG_REQUEST_FAILURE");
      }

   }

   public void handleMessage(byte[] msg, int msglen) throws IOException {
      if (msg != null) {
         switch (msg[0]) {
            case 80:
               this.msgGlobalRequest(msg, msglen);
               break;
            case 81:
               this.msgGlobalSuccess();
               break;
            case 82:
               this.msgGlobalFailure();
               break;
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            default:
               throw new IOException("Cannot handle unknown channel message " + (msg[0] & 255));
            case 90:
               this.msgChannelOpen(msg, msglen);
               break;
            case 91:
               this.msgChannelOpenConfirmation(msg, msglen);
               break;
            case 92:
               this.msgChannelOpenFailure(msg, msglen);
               break;
            case 93:
               this.msgChannelWindowAdjust(msg, msglen);
               break;
            case 94:
               this.msgChannelData(msg, msglen);
               break;
            case 95:
               this.msgChannelExtendedData(msg, msglen);
               break;
            case 96:
               this.msgChannelEOF(msg, msglen);
               break;
            case 97:
               this.msgChannelClose(msg, msglen);
               break;
            case 98:
               this.msgChannelRequest(msg, msglen);
               break;
            case 99:
               this.msgChannelSuccess(msg, msglen);
               break;
            case 100:
               this.msgChannelFailure(msg, msglen);
         }

      } else {
         if (log.isEnabled()) {
            log.log(50, "HandleMessage: got shutdown");
         }

         int i;
         synchronized(this.listenerThreads) {
            i = 0;

            while(true) {
               if (i >= this.listenerThreads.size()) {
                  this.listenerThreadsAllowed = false;
                  break;
               }

               IChannelWorkerThread lat = (IChannelWorkerThread)this.listenerThreads.elementAt(i);
               lat.stopWorking();
               ++i;
            }
         }

         synchronized(this.channels) {
            this.shutdown = true;

            for(i = 0; i < this.channels.size(); ++i) {
               Channel c = (Channel)this.channels.elementAt(i);
               synchronized(c) {
                  c.EOF = true;
                  c.state = 4;
                  c.setReasonClosed("The connection is being shutdown");
                  c.closeMessageRecv = true;
                  c.notifyAll();
               }
            }

            this.channels.setSize(0);
            this.channels.trimToSize();
            this.channels.notifyAll();
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
      log = Logger.getLogger(class$com$trilead$ssh2$channel$ChannelManager == null ? (class$com$trilead$ssh2$channel$ChannelManager = class$("com.trilead.ssh2.channel.ChannelManager")) : class$com$trilead$ssh2$channel$ChannelManager);
   }
}
