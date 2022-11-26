package org.glassfish.hk2.runlevel;

import java.util.concurrent.Future;

public interface RunLevelFuture extends Future {
   int getProposedLevel();

   boolean isUp();

   boolean isDown();

   boolean cancel(boolean var1);
}
