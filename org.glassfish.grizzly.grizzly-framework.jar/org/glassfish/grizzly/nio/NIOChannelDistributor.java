package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;

public interface NIOChannelDistributor {
   void registerChannel(SelectableChannel var1) throws IOException;

   void registerChannel(SelectableChannel var1, int var2) throws IOException;

   void registerChannel(SelectableChannel var1, int var2, Object var3) throws IOException;

   GrizzlyFuture registerChannelAsync(SelectableChannel var1);

   GrizzlyFuture registerChannelAsync(SelectableChannel var1, int var2);

   GrizzlyFuture registerChannelAsync(SelectableChannel var1, int var2, Object var3);

   void registerChannelAsync(SelectableChannel var1, int var2, Object var3, CompletionHandler var4);

   void registerServiceChannelAsync(SelectableChannel var1, int var2, Object var3, CompletionHandler var4);
}
