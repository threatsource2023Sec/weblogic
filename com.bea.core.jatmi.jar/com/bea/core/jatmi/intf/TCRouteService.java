package com.bea.core.jatmi.intf;

import java.util.ArrayList;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedBuffer;

public interface TCRouteService {
   void shutdown(int var1);

   ArrayList[] selectTargetRoutes(String var1, TypedBuffer var2, Xid var3, int var4) throws TPException;
}
