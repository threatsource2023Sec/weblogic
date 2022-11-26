package com.trilead.ssh2;

import com.trilead.ssh2.channel.Channel;
import com.trilead.ssh2.channel.ChannelManager;
import com.trilead.ssh2.channel.X11ServerData;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

public class Session {
   ChannelManager cm;
   Channel cn;
   boolean flag_pty_requested = false;
   boolean flag_x11_requested = false;
   boolean flag_execution_started = false;
   boolean flag_closed = false;
   String x11FakeCookie = null;
   final SecureRandom rnd;

   Session(ChannelManager cm, SecureRandom rnd) throws IOException {
      this.cm = cm;
      this.cn = cm.openSessionChannel();
      this.rnd = rnd;
   }

   public void requestDumbPTY() throws IOException {
      this.requestPTY("dumb", 0, 0, 0, 0, (byte[])null);
   }

   public void requestPTY(String term) throws IOException {
      this.requestPTY(term, 0, 0, 0, 0, (byte[])null);
   }

   public void requestPTY(String term, int term_width_characters, int term_height_characters, int term_width_pixels, int term_height_pixels, byte[] terminal_modes) throws IOException {
      if (term == null) {
         throw new IllegalArgumentException("TERM cannot be null.");
      } else {
         if (terminal_modes != null && terminal_modes.length > 0) {
            if (terminal_modes[terminal_modes.length - 1] != 0) {
               throw new IOException("Illegal terminal modes description, does not end in zero byte");
            }
         } else {
            terminal_modes = new byte[]{0};
         }

         synchronized(this) {
            if (this.flag_closed) {
               throw new IOException("This session is closed.");
            }

            if (this.flag_pty_requested) {
               throw new IOException("A PTY was already requested.");
            }

            if (this.flag_execution_started) {
               throw new IOException("Cannot request PTY at this stage anymore, a remote execution has already started.");
            }

            this.flag_pty_requested = true;
         }

         this.cm.requestPTY(this.cn, term, term_width_characters, term_height_characters, term_width_pixels, term_height_pixels, terminal_modes);
      }
   }

   public void requestX11Forwarding(String hostname, int port, byte[] cookie, boolean singleConnection) throws IOException {
      if (hostname == null) {
         throw new IllegalArgumentException("hostname argument may not be null");
      } else {
         synchronized(this) {
            if (this.flag_closed) {
               throw new IOException("This session is closed.");
            }

            if (this.flag_x11_requested) {
               throw new IOException("X11 forwarding was already requested.");
            }

            if (this.flag_execution_started) {
               throw new IOException("Cannot request X11 forwarding at this stage anymore, a remote execution has already started.");
            }

            this.flag_x11_requested = true;
         }

         X11ServerData x11data = new X11ServerData();
         x11data.hostname = hostname;
         x11data.port = port;
         x11data.x11_magic_cookie = cookie;
         byte[] fakeCookie = new byte[16];

         String hexEncodedFakeCookie;
         do {
            this.rnd.nextBytes(fakeCookie);
            StringBuffer tmp = new StringBuffer(32);

            for(int i = 0; i < fakeCookie.length; ++i) {
               String digit2 = Integer.toHexString(fakeCookie[i] & 255);
               tmp.append(digit2.length() == 2 ? digit2 : "0" + digit2);
            }

            hexEncodedFakeCookie = tmp.toString();
         } while(this.cm.checkX11Cookie(hexEncodedFakeCookie) != null);

         this.cm.requestX11(this.cn, singleConnection, "MIT-MAGIC-COOKIE-1", hexEncodedFakeCookie, 0);
         synchronized(this) {
            if (!this.flag_closed) {
               this.x11FakeCookie = hexEncodedFakeCookie;
               this.cm.registerX11Cookie(hexEncodedFakeCookie, x11data);
            }

         }
      }
   }

   public void execCommand(String cmd) throws IOException {
      if (cmd == null) {
         throw new IllegalArgumentException("cmd argument may not be null");
      } else {
         synchronized(this) {
            if (this.flag_closed) {
               throw new IOException("This session is closed.");
            }

            if (this.flag_execution_started) {
               throw new IOException("A remote execution has already started.");
            }

            this.flag_execution_started = true;
         }

         this.cm.requestExecCommand(this.cn, cmd);
      }
   }

   public void startShell() throws IOException {
      synchronized(this) {
         if (this.flag_closed) {
            throw new IOException("This session is closed.");
         }

         if (this.flag_execution_started) {
            throw new IOException("A remote execution has already started.");
         }

         this.flag_execution_started = true;
      }

      this.cm.requestShell(this.cn);
   }

   public void startSubSystem(String name) throws IOException {
      if (name == null) {
         throw new IllegalArgumentException("name argument may not be null");
      } else {
         synchronized(this) {
            if (this.flag_closed) {
               throw new IOException("This session is closed.");
            }

            if (this.flag_execution_started) {
               throw new IOException("A remote execution has already started.");
            }

            this.flag_execution_started = true;
         }

         this.cm.requestSubSystem(this.cn, name);
      }
   }

   public void ping() throws IOException {
      synchronized(this) {
         if (this.flag_closed) {
            throw new IOException("This session is closed.");
         }
      }

      this.cm.requestChannelTrileadPing(this.cn);
   }

   public InputStream getStdout() {
      return this.cn.getStdoutStream();
   }

   public InputStream getStderr() {
      return this.cn.getStderrStream();
   }

   public OutputStream getStdin() {
      return this.cn.getStdinStream();
   }

   /** @deprecated */
   public int waitUntilDataAvailable(long timeout) throws IOException, InterruptedException {
      if (timeout < 0L) {
         throw new IllegalArgumentException("timeout must not be negative!");
      } else {
         int conditions = this.cm.waitForCondition(this.cn, timeout, 28);
         if ((conditions & 1) != 0) {
            return -1;
         } else if ((conditions & 12) != 0) {
            return 1;
         } else if ((conditions & 16) != 0) {
            return 0;
         } else {
            throw new IllegalStateException("Unexpected condition result (" + conditions + ")");
         }
      }
   }

   public int waitForCondition(int condition_set, long timeout) throws InterruptedException {
      if (timeout < 0L) {
         throw new IllegalArgumentException("timeout must be non-negative!");
      } else {
         return this.cm.waitForCondition(this.cn, timeout, condition_set);
      }
   }

   public Integer getExitStatus() {
      return this.cn.getExitStatus();
   }

   public String getExitSignal() {
      return this.cn.getExitSignal();
   }

   public void close() {
      synchronized(this) {
         if (!this.flag_closed) {
            this.flag_closed = true;
            if (this.x11FakeCookie != null) {
               this.cm.unRegisterX11Cookie(this.x11FakeCookie, true);
            }

            try {
               this.cm.closeChannel(this.cn, "Closed due to user request", true);
            } catch (IOException var4) {
            }

         }
      }
   }
}
