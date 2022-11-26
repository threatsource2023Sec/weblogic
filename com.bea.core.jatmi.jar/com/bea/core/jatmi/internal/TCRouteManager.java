package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCRouteService;
import java.util.ArrayList;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedBuffer;

public final class TCRouteManager {
   private static TCRouteService _svc = null;
   private static TCRouteManager _mgr = null;
   public static int TC_RT_SHUTDOWN_NORMAL = 0;
   public static int TC_RT_SHUTDOWN_FORCE = 1;

   public static void initialize(TCRouteService tcrt) throws TPException {
      if (tcrt == null) {
         throw new TPException(12, "null license service");
      } else if (_mgr != null) {
         throw new TPException(12, "Security Manager already configured!");
      } else {
         _svc = tcrt;
         _mgr = new TCRouteManager();
         ntrace.doTrace("INFO: TC Outbound routing service instantiated!");
      }
   }

   public static TCRouteManager getRouteService() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCRouteManager/getRouteService()");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCRouteManager/getRouteService/" + _mgr);
      }

      return _mgr;
   }

   public static ArrayList[] selectTargetRoutes(String svc, TypedBuffer data, Xid xid, int flag) throws TPException {
      return _svc != null ? _svc.selectTargetRoutes(svc, data, xid, flag) : null;
   }
}
