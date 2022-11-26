package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

public final class RegisterChannelResult {
   private final SelectorRunner selectorRunner;
   private final SelectionKey selectionKey;
   private final SelectableChannel channel;

   public RegisterChannelResult(SelectorRunner selectorRunner, SelectionKey selectionKey, SelectableChannel channel) {
      this.selectorRunner = selectorRunner;
      this.selectionKey = selectionKey;
      this.channel = channel;
   }

   public SelectorRunner getSelectorRunner() {
      return this.selectorRunner;
   }

   public SelectionKey getSelectionKey() {
      return this.selectionKey;
   }

   public SelectableChannel getChannel() {
      return this.channel;
   }
}
