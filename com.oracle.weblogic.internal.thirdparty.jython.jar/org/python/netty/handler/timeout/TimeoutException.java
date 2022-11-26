package org.python.netty.handler.timeout;

import org.python.netty.channel.ChannelException;

public class TimeoutException extends ChannelException {
   private static final long serialVersionUID = 4673641882869672533L;

   TimeoutException() {
   }

   public Throwable fillInStackTrace() {
      return this;
   }
}
