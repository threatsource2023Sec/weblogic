package org.apache.openjpa.event;

import java.util.Iterator;
import java.util.Set;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashSet;

public class SingleJVMRemoteCommitProvider extends AbstractRemoteCommitProvider {
   private static Set s_providers = new ConcurrentReferenceHashSet(0);

   public SingleJVMRemoteCommitProvider() {
      s_providers.add(this);
   }

   public void broadcast(RemoteCommitEvent event) {
      Iterator iter = s_providers.iterator();

      while(iter.hasNext()) {
         SingleJVMRemoteCommitProvider provider = (SingleJVMRemoteCommitProvider)iter.next();
         if (provider != this) {
            provider.fireEvent(event);
         }
      }

   }

   public void close() {
      s_providers.remove(this);
   }
}
