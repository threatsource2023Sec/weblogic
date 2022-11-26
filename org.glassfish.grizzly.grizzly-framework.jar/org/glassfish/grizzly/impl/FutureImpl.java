package org.glassfish.grizzly.impl;

import org.glassfish.grizzly.GrizzlyFuture;

public interface FutureImpl extends GrizzlyFuture {
   Object getResult();

   void result(Object var1);

   void failure(Throwable var1);
}
