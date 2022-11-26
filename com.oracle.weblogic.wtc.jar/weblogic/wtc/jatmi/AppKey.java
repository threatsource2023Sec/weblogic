package weblogic.wtc.jatmi;

import weblogic.security.acl.internal.AuthenticatedSubject;

public interface AppKey {
   int UIDMASK = 131071;
   int GIDMASK = 16383;
   int GIDSHIFT = 17;
   int TPSYSADM_KEY = Integer.MIN_VALUE;
   int TPSYSOP_KEY = -1073741824;

   void init(String var1, boolean var2, int var3) throws TPException;

   void uninit() throws TPException;

   UserRec getTuxedoUserRecord(AuthenticatedSubject var1);
}
