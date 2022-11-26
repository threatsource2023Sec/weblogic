package com.sun.faces.spi;

public interface ThreadContext {
   Object getParentWebContext();

   void propagateWebContextToChild(Object var1);

   void clearChildContext();
}
