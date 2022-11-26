package org.python.netty.channel.socket;

import java.net.InetSocketAddress;
import org.python.netty.channel.ServerChannel;

public interface ServerSocketChannel extends ServerChannel {
   ServerSocketChannelConfig config();

   InetSocketAddress localAddress();

   InetSocketAddress remoteAddress();
}
