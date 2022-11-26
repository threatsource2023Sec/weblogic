package com.oracle.buzzmessagebus.impl;

import com.oracle.common.io.BufferSequence;

class InitCookie {
   final ConnectionInfo connectionInfo;
   private BufferSequence bufferSequence;

   InitCookie(ConnectionInfo c, BufferSequence b) {
      this.connectionInfo = c;
      this.bufferSequence = b;
   }

   public BufferSequence getBufferSequence() {
      return this.bufferSequence;
   }

   public void setBufferSequence(BufferSequence x) {
      this.bufferSequence = x;
   }
}
