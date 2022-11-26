package weblogic.platform;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface GCListener {
   void onGarbageCollection(GarbageCollectionEvent var1);
}
