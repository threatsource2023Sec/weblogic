package org.python.netty.channel.local;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentMap;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelException;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;

final class LocalChannelRegistry {
   private static final ConcurrentMap boundChannels = PlatformDependent.newConcurrentHashMap();

   static LocalAddress register(Channel channel, LocalAddress oldLocalAddress, SocketAddress localAddress) {
      if (oldLocalAddress != null) {
         throw new ChannelException("already bound");
      } else if (!(localAddress instanceof LocalAddress)) {
         throw new ChannelException("unsupported address type: " + StringUtil.simpleClassName((Object)localAddress));
      } else {
         LocalAddress addr = (LocalAddress)localAddress;
         if (LocalAddress.ANY.equals(addr)) {
            addr = new LocalAddress(channel);
         }

         Channel boundChannel = (Channel)boundChannels.putIfAbsent(addr, channel);
         if (boundChannel != null) {
            throw new ChannelException("address already in use by: " + boundChannel);
         } else {
            return addr;
         }
      }
   }

   static Channel get(SocketAddress localAddress) {
      return (Channel)boundChannels.get(localAddress);
   }

   static void unregister(LocalAddress localAddress) {
      boundChannels.remove(localAddress);
   }

   private LocalChannelRegistry() {
   }
}
