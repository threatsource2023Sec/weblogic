package com.oracle.tyrus.fallback.bridge;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import org.glassfish.tyrus.spi.Connection;

public class TyrusCloseListener implements Connection.CloseListener {
   private static final Logger LOGGER = Logger.getLogger(TyrusCloseListener.class.getName());
   private final com.oracle.tyrus.fallback.Connection connection;

   public TyrusCloseListener(com.oracle.tyrus.fallback.Connection connection) {
      this.connection = connection;
   }

   public void close(CloseReason reason) {
      try {
         this.connection.close();
      } catch (IOException var3) {
         LOGGER.log(Level.WARNING, "Exception closing fallback connection", var3);
      }

   }
}
