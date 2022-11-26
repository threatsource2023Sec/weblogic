package weblogic.tgiop;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import weblogic.iiop.Connection;
import weblogic.iiop.interceptors.ServerContextInterceptor;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.messages.SequencedRequestMessage;
import weblogic.iiop.server.ServerEndPointImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.TxHelper;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.SyncKeyTable;
import weblogic.wtc.WTCLogger;

public final class TGIOPEndPointImpl extends ServerEndPointImpl {
   private AuthenticatedSubject user;
   private static final SyncKeyTable pendingResponses = new SyncKeyTable();
   private static int nextRequestID = 1;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory debugSecurity = Debug.getCategory("weblogic.tgiop.security");

   protected boolean isSecure(Connection c) {
      return false;
   }

   public boolean supportsForwarding() {
      return false;
   }

   public IOR getCurrentIor(IOR ior, long timeout) throws RemoteException {
      return ior;
   }

   public int getNextRequestID() {
      return getNextInternalRequestID();
   }

   private static synchronized int getNextInternalRequestID() {
      return ++nextRequestID;
   }

   public SequencedRequestMessage getPendingResponse(int requestid) {
      return (SequencedRequestMessage)pendingResponses.get(requestid);
   }

   public SequencedRequestMessage removePendingResponse(int requestid) {
      boolean traceEnabled = ntrace.isTraceEnabled(65000);
      if (traceEnabled) {
         ntrace.doTrace("[/TGIOPEndpoint/removePendingResponse requestid = " + requestid);
      }

      super.removePendingResponse(requestid);
      SequencedRequestMessage msg = (SequencedRequestMessage)pendingResponses.remove(requestid);
      if (traceEnabled) {
         ntrace.doTrace("]/TGIOPEndpoint/removePendingResponse returning msg = " + msg);
      }

      return msg;
   }

   public void registerPendingResponse(SequencedRequestMessage req) {
      boolean traceEnabled = ntrace.isTraceEnabled(65000);
      if (traceEnabled) {
         ntrace.doTrace("[/TGIOPEndpoint/registgerPendingResponse requestID = " + req.getRequestID());
      }

      super.registerPendingResponse(req);
      pendingResponses.put(req);
      if (traceEnabled) {
         ntrace.doTrace("]/TGIOPEndpoint/registerPendingResponse");
      }

   }

   protected void handleNamingRequest(RequestMessage req) throws IOException {
      this.dispatchRequest(req, 8);
   }

   public TGIOPEndPointImpl(Connection c, AuthenticatedSubject user) {
      super(c, new ServerContextInterceptor[0]);
      this.user = user;
   }

   public AuthenticatedSubject getSubject(RequestMessage request) {
      if (this.user == null) {
         this.user = SecurityServiceManager.getCurrentSubject(kernelId);
      }

      return this.user;
   }

   public void setCredentials(RequestMessage request, AuthenticatedSubject subject) {
      if (debugSecurity.isEnabled()) {
         WTCLogger.logDebugSecurity("outbound request user set to: " + subject);
      }

   }

   public Object getInboundRequestTxContext(RequestMessage request) {
      Object txContext = request.getCachedTxContext();
      if (txContext == null) {
         txContext = TxHelper.getTransaction();
         request.setCachedTxContext(txContext);
      }

      return txContext;
   }

   public Object getInboundResponseTxContext(ReplyMessage reply) {
      return TxHelper.getTransaction();
   }

   public void setOutboundResponseTxContext(ReplyMessage reply, Object txContext) {
   }

   public void setOutboundRequestTxContext(RequestMessage request, Object txContext) {
   }
}
