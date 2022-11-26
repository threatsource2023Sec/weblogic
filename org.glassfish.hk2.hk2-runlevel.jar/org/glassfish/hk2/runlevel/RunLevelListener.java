package org.glassfish.hk2.runlevel;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RunLevelListener {
   void onProgress(ChangeableRunLevelFuture var1, int var2);

   void onCancelled(RunLevelFuture var1, int var2);

   void onError(RunLevelFuture var1, ErrorInformation var2);
}
