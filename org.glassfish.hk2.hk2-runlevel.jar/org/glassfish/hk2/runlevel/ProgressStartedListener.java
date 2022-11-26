package org.glassfish.hk2.runlevel;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ProgressStartedListener {
   void onProgressStarting(ChangeableRunLevelFuture var1, int var2);
}
