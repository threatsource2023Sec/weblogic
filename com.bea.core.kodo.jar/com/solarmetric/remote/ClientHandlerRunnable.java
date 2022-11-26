package com.solarmetric.remote;

import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class ClientHandlerRunnable implements Runnable {
   private static final Localizer _loc = Localizer.forPackage(ClientHandlerRunnable.class);
   private final CommandIO _io;
   private final Transport.Channel _channel;

   public ClientHandlerRunnable(CommandIO io, Transport.Channel channel) {
      this._io = io;
      this._channel = channel;
   }

   public CommandIO getCommandIO() {
      return this._io;
   }

   public Transport.Channel getChannel() {
      return this._channel;
   }

   public void run() {
      Log log = this._io.getLog();
      if (log != null && log.isTraceEnabled()) {
         log.trace(_loc.get("start-client", this._io));
      }

      Exception lastErr = null;

      while(true) {
         try {
            boolean keepAlive = this._io.execute(this._channel);
            lastErr = null;
            if (!keepAlive) {
               break;
            }
         } catch (Exception var6) {
            if (lastErr != null) {
               break;
            }

            if (log != null && log.isErrorEnabled()) {
               log.error(var6);
            }

            lastErr = var6;
         }
      }

      try {
         this._channel.close();
      } catch (Exception var5) {
      }

      if (log != null && log.isTraceEnabled()) {
         log.info(_loc.get("stop-client", this._io));
      }

      if (lastErr != null) {
         if (lastErr instanceof TransportException) {
            throw (TransportException)lastErr;
         } else {
            throw new TransportException(lastErr);
         }
      }
   }
}
