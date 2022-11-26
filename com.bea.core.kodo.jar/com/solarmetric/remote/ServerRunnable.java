package com.solarmetric.remote;

import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;

public class ServerRunnable implements Runnable {
   private static final Localizer _loc = Localizer.forPackage(ServerRunnable.class);
   private final Transport.Server _server;
   private final CommandIO _io;

   public ServerRunnable(Transport.Server server, CommandIO io) {
      this._server = server;
      this._io = io;
   }

   public Transport.Server getServer() {
      return this._server;
   }

   public CommandIO getCommandIO() {
      return this._io;
   }

   public void run() {
      Log log = this._io.getLog();
      if (log != null && log.isInfoEnabled()) {
         log.info(_loc.get("start-server", this._io));
      }

      Exception lastErr = null;
      boolean closed = false;

      while(true) {
         while(true) {
            try {
               Transport.Channel channel = this._server.accept();
               lastErr = null;
               Runnable client = new ClientHandlerRunnable(this._io, channel);
               Thread handler = new Thread(client, "ClientHandler");
               handler.setDaemon(true);
               handler.start();
            } catch (Exception var9) {
               label59: {
                  try {
                     if (this._server.isClosed()) {
                        closed = true;
                        break label59;
                     }
                  } catch (Exception var8) {
                  }

                  if (lastErr == null) {
                     if (log != null && log.isErrorEnabled()) {
                        log.error(var9);
                     }

                     lastErr = var9;
                     continue;
                  }
               }

               if (!closed) {
                  try {
                     this._server.close();
                  } catch (Exception var7) {
                  }
               }

               if (log != null && log.isInfoEnabled()) {
                  log.info(_loc.get("stop-server", this._io));
               }

               if (lastErr != null) {
                  if (lastErr instanceof RuntimeException) {
                     throw (RuntimeException)lastErr;
                  }

                  throw new TransportException(lastErr);
               }

               return;
            }
         }
      }
   }
}
