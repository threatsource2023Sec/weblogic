package weblogic.cluster;

import java.util.ArrayList;
import java.util.EventListener;

public interface RemoteClusterMembersChangeListener extends EventListener {
   void remoteClusterMembersChanged(ArrayList var1);
}
