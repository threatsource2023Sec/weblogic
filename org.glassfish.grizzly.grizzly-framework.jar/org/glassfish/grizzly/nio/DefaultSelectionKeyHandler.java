package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;

public final class DefaultSelectionKeyHandler implements SelectionKeyHandler {
   private static final Logger LOGGER = Grizzly.logger(DefaultSelectionKeyHandler.class);
   private static final IOEvent[][] ioEventMap = new IOEvent[32][];

   public void onKeyRegistered(SelectionKey key) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "KEY IS REGISTERED: {0}", key);
      }

   }

   public void onKeyDeregistered(SelectionKey key) {
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "KEY IS DEREGISTERED: {0}", key);
      }

   }

   public void cancel(SelectionKey key) throws IOException {
      this.onKeyDeregistered(key);
      key.cancel();
   }

   public int ioEvent2SelectionKeyInterest(IOEvent ioEvent) {
      switch (ioEvent) {
         case READ:
            return 1;
         case WRITE:
            return 4;
         case SERVER_ACCEPT:
            return 16;
         case CLIENT_CONNECTED:
            return 8;
         default:
            return 0;
      }
   }

   public IOEvent[] getIOEvents(int interest) {
      return ioEventMap[interest];
   }

   public IOEvent selectionKeyInterest2IoEvent(int selectionKeyInterest) {
      if ((selectionKeyInterest & 1) != 0) {
         return IOEvent.READ;
      } else if ((selectionKeyInterest & 4) != 0) {
         return IOEvent.WRITE;
      } else if ((selectionKeyInterest & 16) != 0) {
         return IOEvent.SERVER_ACCEPT;
      } else {
         return (selectionKeyInterest & 8) != 0 ? IOEvent.CLIENT_CONNECTED : IOEvent.NONE;
      }
   }

   public boolean onProcessInterest(SelectionKey key, int interest) throws IOException {
      return true;
   }

   public NIOConnection getConnectionForKey(SelectionKey selectionKey) {
      return (NIOConnection)selectionKey.attachment();
   }

   public void setConnectionForKey(NIOConnection connection, SelectionKey selectionKey) {
      selectionKey.attach(connection);
   }

   static {
      for(int i = 0; i < ioEventMap.length; ++i) {
         int idx = 0;
         IOEvent[] tmpArray = new IOEvent[4];
         if ((i & 1) != 0) {
            tmpArray[idx++] = IOEvent.READ;
         }

         if ((i & 4) != 0) {
            tmpArray[idx++] = IOEvent.WRITE;
         }

         if ((i & 8) != 0) {
            tmpArray[idx++] = IOEvent.CLIENT_CONNECTED;
         }

         if ((i & 16) != 0) {
            tmpArray[idx++] = IOEvent.SERVER_ACCEPT;
         }

         ioEventMap[i] = (IOEvent[])Arrays.copyOf(tmpArray, idx);
      }

   }
}
