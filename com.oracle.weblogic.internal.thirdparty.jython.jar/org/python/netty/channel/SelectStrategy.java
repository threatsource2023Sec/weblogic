package org.python.netty.channel;

import org.python.netty.util.IntSupplier;

public interface SelectStrategy {
   int SELECT = -1;
   int CONTINUE = -2;

   int calculateStrategy(IntSupplier var1, boolean var2) throws Exception;
}
