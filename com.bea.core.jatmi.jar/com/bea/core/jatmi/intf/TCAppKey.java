package com.bea.core.jatmi.intf;

import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.UserRec;

public interface TCAppKey {
   int UIDMASK = 131071;
   int GIDMASK = 16383;
   int GIDSHIFT = 17;
   int TPSYSADM_KEY = Integer.MIN_VALUE;
   int TPSYSOP_KEY = -1073741824;

   void init(String var1, boolean var2, int var3) throws TPException;

   void uninit() throws TPException;

   UserRec getTuxedoUserRecord(TCAuthenticatedUser var1);

   void doCache(boolean var1);

   boolean isCached();
}
