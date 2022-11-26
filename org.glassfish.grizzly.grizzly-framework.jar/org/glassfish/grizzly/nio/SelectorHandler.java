package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Set;
import org.glassfish.grizzly.CompletionHandler;

public interface SelectorHandler {
   SelectorHandler DEFAULT_SELECTOR_HANDLER = new DefaultSelectorHandler();

   long getSelectTimeout();

   boolean preSelect(SelectorRunner var1) throws IOException;

   Set select(SelectorRunner var1) throws IOException;

   void postSelect(SelectorRunner var1) throws IOException;

   void registerKeyInterest(SelectorRunner var1, SelectionKey var2, int var3) throws IOException;

   void deregisterKeyInterest(SelectorRunner var1, SelectionKey var2, int var3) throws IOException;

   void registerChannel(SelectorRunner var1, SelectableChannel var2, int var3, Object var4) throws IOException;

   void registerChannelAsync(SelectorRunner var1, SelectableChannel var2, int var3, Object var4, CompletionHandler var5);

   void deregisterChannel(SelectorRunner var1, SelectableChannel var2) throws IOException;

   void deregisterChannelAsync(SelectorRunner var1, SelectableChannel var2, CompletionHandler var3);

   void execute(SelectorRunner var1, Task var2, CompletionHandler var3);

   void enque(SelectorRunner var1, Task var2, CompletionHandler var3);

   boolean onSelectorClosed(SelectorRunner var1);

   public interface Task {
      boolean run() throws Exception;
   }
}
