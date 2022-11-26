package org.python.netty.resolver;

import java.io.Closeable;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;

public interface NameResolver extends Closeable {
   Future resolve(String var1);

   Future resolve(String var1, Promise var2);

   Future resolveAll(String var1);

   Future resolveAll(String var1, Promise var2);

   void close();
}
