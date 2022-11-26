package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import org.glassfish.grizzly.IOEvent;

public interface SelectionKeyHandler {
   SelectionKeyHandler DEFAULT_SELECTION_KEY_HANDLER = SelectionKeyHandlerInitializer.initHandler();

   void onKeyRegistered(SelectionKey var1);

   void onKeyDeregistered(SelectionKey var1);

   boolean onProcessInterest(SelectionKey var1, int var2) throws IOException;

   void cancel(SelectionKey var1) throws IOException;

   NIOConnection getConnectionForKey(SelectionKey var1);

   void setConnectionForKey(NIOConnection var1, SelectionKey var2);

   int ioEvent2SelectionKeyInterest(IOEvent var1);

   IOEvent selectionKeyInterest2IoEvent(int var1);

   IOEvent[] getIOEvents(int var1);
}
