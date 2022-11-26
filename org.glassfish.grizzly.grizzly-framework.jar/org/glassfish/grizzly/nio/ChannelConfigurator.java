package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.SelectableChannel;

public interface ChannelConfigurator {
   void preConfigure(NIOTransport var1, SelectableChannel var2) throws IOException;

   void postConfigure(NIOTransport var1, SelectableChannel var2) throws IOException;
}
