package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCTaskHelper;
import weblogic.kernel.Kernel;
import weblogic.wtc.jatmi.InvokeInfo;
import weblogic.wtc.jatmi.InvokeSvc;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TuxedoService;
import weblogic.wtc.jatmi.gwatmi;

public final class WLSInvoke implements InvokeSvc {
   private TDMLocal myLocalDomain;
   private TDMRemote myRemoteDomain;

   public WLSInvoke(TDMLocal localDomain) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WLSInvoke/" + localDomain);
      }

      this.myLocalDomain = localDomain;
      if (traceEnabled) {
         ntrace.doTrace("]/WLSInvoke/10");
      }

   }

   public WLSInvoke(TDMLocal localDomain, TDMRemote remoteDomain) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WLSInvoke/" + localDomain + "/" + remoteDomain);
      }

      this.myLocalDomain = localDomain;
      this.myRemoteDomain = remoteDomain;
      if (traceEnabled) {
         ntrace.doTrace("]/WLSInvoke/10");
      }

   }

   public void setRemoteDomain(TDMRemote remoteDomain) {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WLSInvoke/setRemoteDomain/" + remoteDomain);
      }

      this.myRemoteDomain = remoteDomain;
      if (traceEnabled) {
         ntrace.doTrace("]/WLSInvoke/setRemoteDomain/10");
      }

   }

   public TDMRemote getRemoteDomain() {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WLSInvoke/getRemoteDomain/");
      }

      TDMRemote ret = this.myRemoteDomain;
      if (traceEnabled) {
         ntrace.doTrace("]/WLSInvoke/getRemoteDomain/10/" + ret);
      }

      return ret;
   }

   public void invoke(InvokeInfo invokeinfo, gwatmi rplyObj) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/WLSInvoke/invoke/");
      }

      if (invokeinfo == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/WLSInvoke/invoke/20/");
         }

         throw new TPException(4);
      } else {
         if (WTCService.applicationQueueId == -1) {
            InboundEJBRequest ier = new InboundEJBRequest(new ServiceParameters(invokeinfo, rplyObj), this.myLocalDomain, this.myRemoteDomain);
            TCTaskHelper.schedule(ier);
         } else {
            InboundEJBRequestKEQ ier = new InboundEJBRequestKEQ(new ServiceParameters(invokeinfo, rplyObj), this.myLocalDomain, this.myRemoteDomain);
            Kernel.execute(ier, WTCService.applicationQueueId);
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WLSInvoke/invoke/30/");
         }

      }
   }

   public void advertise(String svc, TuxedoService func) throws TPException {
      throw new TPException(7);
   }

   public void unadvertise(String svc) throws TPException {
      throw new TPException(7);
   }

   public void shutdown() {
   }
}
