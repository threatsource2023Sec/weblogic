package com.bea.core.jatmi.intf;

import weblogic.wtc.jatmi.Txid;

public interface TuxedoLoggable {
   int IS_NONE = 0;
   int IS_READY = 1;
   int IS_COMMIT = 2;
   int IS_PREPARED = 3;
   int IS_COMMITTING = 4;
   int IS_MAXTYPE = 4;

   int getType();

   void waitForDisk();

   Txid getTxid();

   boolean equals(Object var1);

   int hashCode();

   void write();

   void forget();
}
