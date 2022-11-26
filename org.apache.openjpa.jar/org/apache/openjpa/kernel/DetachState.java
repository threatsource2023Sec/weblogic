package org.apache.openjpa.kernel;

public interface DetachState {
   int DETACH_FETCH_GROUPS = 0;
   /** @deprecated */
   int DETACH_FGS = 0;
   int DETACH_LOADED = 1;
   int DETACH_ALL = 2;
}
