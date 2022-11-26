package org.python.netty.channel;

import java.net.SocketAddress;
import org.python.netty.util.ReferenceCounted;

public interface AddressedEnvelope extends ReferenceCounted {
   Object content();

   SocketAddress sender();

   SocketAddress recipient();

   AddressedEnvelope retain();

   AddressedEnvelope retain(int var1);

   AddressedEnvelope touch();

   AddressedEnvelope touch(Object var1);
}
