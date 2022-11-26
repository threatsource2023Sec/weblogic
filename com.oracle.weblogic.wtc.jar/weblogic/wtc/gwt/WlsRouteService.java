package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCRouteEntry;
import com.bea.core.jatmi.intf.TCRouteService;
import java.util.ArrayList;
import javax.transaction.xa.Xid;
import weblogic.management.ManagementException;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.gwatmi;

public final class WlsRouteService implements TCRouteService {
   private WTCService _svc;
   private OatmialServices _tos = null;

   public WlsRouteService() {
      try {
         this._svc = WTCService.getService();
      } catch (ManagementException var2) {
      }

   }

   public void shutdown(int type) {
      this._svc = null;
      this._tos = null;
   }

   public ArrayList[] selectTargetRoutes(String svc, TypedBuffer data, Xid xid, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      TDMImport imp = null;
      TDMRemote rap = null;
      gwatmi myAtmi = null;
      boolean do_connect = false;
      boolean no_connect = false;
      boolean timedOut = false;
      TDMRemote selected_rap = null;
      if (traceEnabled) {
         ntrace.doTrace("[/WTCRouteManager/selectTargetRotes/" + svc);
      }

      imp = this._svc.getImport(svc, xid);
      TDMRemote[] rap_list;
      if ((rap_list = imp.getRemoteAccessPointObjectList()) != null && rap_list.length != 0) {
         ArrayList selected = new ArrayList(rap_list.length);

         for(int lcv = 0; lcv < rap_list.length; ++lcv) {
            String cp = rap_list[lcv].getConnectionPolicy();
            if (no_connect || cp != null && !cp.equals("ON_DEMAND")) {
               do_connect = false;
            } else {
               do_connect = true;
            }

            if ((myAtmi = rap_list[lcv].getTsession(do_connect)) != null) {
               rap = rap_list[lcv];
               if (rap != null && xid != null) {
                  if (this._tos == null) {
                     this._tos = WTCService.getOatmialServices();
                  }

                  this._tos.addOutboundRdomToXid(xid, rap);
               }

               if (do_connect) {
                  no_connect = true;
               }

               TCRouteEntry e = new TCRouteEntry(myAtmi, imp.getRemoteName());
               e.setTDMImport(imp.getLocalAccessPoint(), imp.getRemoteAccessPointListString());
               selected.add(e);
               break;
            }

            if (rap_list[lcv].getTimedOut()) {
               timedOut = true;
            }
         }

         if (selected.size() == 0) {
            if (timedOut) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCRouteManager/selectTargetRotes/20.1/");
               }

               throw new TPException(13, "Connection establishment timed out");
            } else {
               if (traceEnabled) {
                  ntrace.doTrace("*]/WTCRouteManager/selectTargetRotes/20.2/");
               }

               throw new TPException(6, "Could not get a Tuxedo session");
            }
         } else {
            ArrayList[] returned = new ArrayList[]{selected};
            if (traceEnabled) {
               ntrace.doTrace("]/WTCRouteManager/selectTargetRotes/30/" + myAtmi);
            }

            return returned;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("*]/WTCRouteManager/selectTargetRotes/10/");
         }

         throw new TPException(6, "Could not find remote accesss point");
      }
   }
}
