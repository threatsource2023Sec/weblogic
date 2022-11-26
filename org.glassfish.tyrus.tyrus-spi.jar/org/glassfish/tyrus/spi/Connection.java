package org.glassfish.tyrus.spi;

import javax.websocket.CloseReason;

public interface Connection {
   ReadHandler getReadHandler();

   Writer getWriter();

   CloseListener getCloseListener();

   void close(CloseReason var1);

   public interface CloseListener {
      void close(CloseReason var1);
   }
}
