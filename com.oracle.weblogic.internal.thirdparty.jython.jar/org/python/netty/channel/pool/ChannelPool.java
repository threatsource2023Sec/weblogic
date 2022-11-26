package org.python.netty.channel.pool;

import java.io.Closeable;
import org.python.netty.channel.Channel;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;

public interface ChannelPool extends Closeable {
   Future acquire();

   Future acquire(Promise var1);

   Future release(Channel var1);

   Future release(Channel var1, Promise var2);

   void close();
}
