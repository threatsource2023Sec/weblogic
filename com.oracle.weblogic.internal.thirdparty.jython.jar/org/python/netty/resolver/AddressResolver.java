package org.python.netty.resolver;

import java.io.Closeable;
import java.net.SocketAddress;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;

public interface AddressResolver extends Closeable {
   boolean isSupported(SocketAddress var1);

   boolean isResolved(SocketAddress var1);

   Future resolve(SocketAddress var1);

   Future resolve(SocketAddress var1, Promise var2);

   Future resolveAll(SocketAddress var1);

   Future resolveAll(SocketAddress var1, Promise var2);

   void close();
}
