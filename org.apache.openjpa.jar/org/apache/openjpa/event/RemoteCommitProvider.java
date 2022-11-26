package org.apache.openjpa.event;

import org.apache.openjpa.lib.util.Closeable;

public interface RemoteCommitProvider extends Closeable {
   void setRemoteCommitEventManager(RemoteCommitEventManager var1);

   void broadcast(RemoteCommitEvent var1);

   void close();
}
