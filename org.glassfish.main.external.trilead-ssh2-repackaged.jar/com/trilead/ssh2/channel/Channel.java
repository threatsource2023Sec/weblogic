package com.trilead.ssh2.channel;

public class Channel {
   static final int STATE_OPENING = 1;
   static final int STATE_OPEN = 2;
   static final int STATE_CLOSED = 4;
   static final int CHANNEL_BUFFER_SIZE = 30000;
   final ChannelManager cm;
   final ChannelOutputStream stdinStream;
   final ChannelInputStream stdoutStream;
   final ChannelInputStream stderrStream;
   int localID = -1;
   int remoteID = -1;
   final Object channelSendLock = new Object();
   boolean closeMessageSent = false;
   final byte[] msgWindowAdjust = new byte[9];
   int state = 1;
   boolean closeMessageRecv = false;
   int successCounter = 0;
   int failedCounter = 0;
   int localWindow = 0;
   long remoteWindow = 0L;
   int localMaxPacketSize = -1;
   int remoteMaxPacketSize = -1;
   final byte[] stdoutBuffer = new byte[30000];
   final byte[] stderrBuffer = new byte[30000];
   int stdoutReadpos = 0;
   int stdoutWritepos = 0;
   int stderrReadpos = 0;
   int stderrWritepos = 0;
   boolean EOF = false;
   Integer exit_status;
   String exit_signal;
   String hexX11FakeCookie;
   private final Object reasonClosedLock = new Object();
   private String reasonClosed = null;

   public Channel(ChannelManager cm) {
      this.cm = cm;
      this.localWindow = 30000;
      this.localMaxPacketSize = 33976;
      this.stdinStream = new ChannelOutputStream(this);
      this.stdoutStream = new ChannelInputStream(this, false);
      this.stderrStream = new ChannelInputStream(this, true);
   }

   public ChannelInputStream getStderrStream() {
      return this.stderrStream;
   }

   public ChannelOutputStream getStdinStream() {
      return this.stdinStream;
   }

   public ChannelInputStream getStdoutStream() {
      return this.stdoutStream;
   }

   public String getExitSignal() {
      synchronized(this) {
         return this.exit_signal;
      }
   }

   public Integer getExitStatus() {
      synchronized(this) {
         return this.exit_status;
      }
   }

   public String getReasonClosed() {
      synchronized(this.reasonClosedLock) {
         return this.reasonClosed;
      }
   }

   public void setReasonClosed(String reasonClosed) {
      synchronized(this.reasonClosedLock) {
         if (this.reasonClosed == null) {
            this.reasonClosed = reasonClosed;
         }

      }
   }
}
