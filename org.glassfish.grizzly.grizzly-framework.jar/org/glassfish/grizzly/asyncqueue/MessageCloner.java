package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;

public interface MessageCloner {
   Object clone(Connection var1, Object var2);
}
