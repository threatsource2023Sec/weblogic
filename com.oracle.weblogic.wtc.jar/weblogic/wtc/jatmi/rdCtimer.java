package weblogic.wtc.jatmi;

import com.bea.core.jatmi.internal.TCTaskHelper;
import java.io.IOException;
import java.util.Map;
import java.util.TimerTask;
import org.omg.CORBA.NO_RESPONSE;
import weblogic.iiop.messages.SequencedRequestMessage;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.tgiop.TGIOPConnection;
import weblogic.tgiop.TGIOPEndPointImpl;
import weblogic.wtc.gwt.MethodParameters;
import weblogic.wtc.gwt.ServiceParameters;

class rdCtimer extends TimerTask {
   private dsession myDSession;
   private int reqId;
   private int giopReqId;

   public rdCtimer(dsession aDsession, int reqId, int giopReqId) {
      this.myDSession = aDsession;
      this.reqId = reqId;
      this.giopReqId = giopReqId;
   }

   public void run() {
      TdomTcb fail_tmmsg_tdom = new TdomTcb(3, this.reqId, 4194304, (String)null);
      fail_tmmsg_tdom.set_diagnostic(13);
      tfmh fail_tmmsg = new tfmh(1);
      fail_tmmsg.tdom = new tcm((short)7, fail_tmmsg_tdom);
      Object[] reqInfo = null;
      Map rmiCallList = this.myDSession.getRMICallList();
      if (rmiCallList != null) {
         synchronized(rmiCallList) {
            if ((reqInfo = (Object[])((Object[])rmiCallList.remove(new Integer(this.reqId)))) != null) {
               RMIReplyRequest rmiRplyReq = new RMIReplyRequest(fail_tmmsg, reqInfo, this.myDSession);
               TCTaskHelper.schedule(rmiRplyReq);
            } else {
               MethodParameters methodParm = new MethodParameters((ServiceParameters)null, (Objrecv)null, (Object[])null, this.myDSession);
               TGIOPConnection connection = null;

               try {
                  connection = new TGIOPConnection(methodParm);
               } catch (IOException var12) {
                  return;
               }

               TGIOPEndPointImpl endpoint = new TGIOPEndPointImpl(connection, (AuthenticatedSubject)null);
               SequencedRequestMessage message = endpoint.removePendingResponse(this.giopReqId);
               if (message != null) {
                  Exception exception = new NO_RESPONSE();
                  message.notify(exception);
               }
            }
         }
      }

   }
}
