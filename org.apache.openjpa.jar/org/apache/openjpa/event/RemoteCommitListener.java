package org.apache.openjpa.event;

import org.apache.openjpa.lib.util.Closeable;

public interface RemoteCommitListener extends Closeable {
   void afterCommit(RemoteCommitEvent var1);

   void close();
}
