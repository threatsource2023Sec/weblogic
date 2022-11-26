package com.bea.wls.redef.debug;

import java.util.Collection;

interface BackingStore {
   void write(Collection var1);

   void write(StoreEntry var1);
}
