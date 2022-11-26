package org.glassfish.grizzly.nio;

import java.io.IOException;

public interface SelectorHandlerTask {
   boolean run(SelectorRunner var1) throws IOException;

   void cancel();
}
