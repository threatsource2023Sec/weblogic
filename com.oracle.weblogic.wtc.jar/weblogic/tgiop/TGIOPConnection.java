package weblogic.tgiop;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.TCRouteEntry;
import com.bea.core.jatmi.internal.TuxedoXid;
import java.io.IOException;
import java.security.AccessController;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.iiop.Connection;
import weblogic.iiop.EndPoint;
import weblogic.iiop.IIOPOutputStream;
import weblogic.iiop.IIOPService;
import weblogic.iiop.ProtocolHandlerIIOP;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.SequencedMessage;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.logging.LogOutputStream;
import weblogic.protocol.AsyncOutgoingMessage;
import weblogic.protocol.ChannelImpl;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.Channel;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.wtc.WTCLogger;
import weblogic.wtc.gwt.MethodParameters;
import weblogic.wtc.gwt.OatmialServices;
import weblogic.wtc.gwt.TDMRemote;
import weblogic.wtc.gwt.TuxedoCorbaConnection;
import weblogic.wtc.gwt.TuxedoCorbaConnectionFactory;
import weblogic.wtc.gwt.WTCService;
import weblogic.wtc.jatmi.BEAObjectKey;
import weblogic.wtc.jatmi.BindInfo;
import weblogic.wtc.jatmi.ClientInfo;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.ObjinfoImpl;
import weblogic.wtc.jatmi.SessionAcallDescriptor;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTcb;
import weblogic.wtc.jatmi.TypedBuffer;
import weblogic.wtc.jatmi.TypedTGIOP;
import weblogic.wtc.jatmi.UserTcb;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.rdsession;
import weblogic.wtc.jatmi.tcm;
import weblogic.wtc.jatmi.tfmh;

public final class TGIOPConnection extends Connection {
   private static boolean initialized = false;
   private static boolean closed = false;
   private static TuxedoCorbaConnectionFactory tuxedoCorbaConnectionFactory;
   private EndPoint endPoint;
   private static final DebugCategory debugSecurity = Debug.getCategory("weblogic.tgiop.security");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected static LogOutputStream log;
   private TuxedoCorbaConnection tuxedoCorbaConnection;
   private MethodParameters methodParameters;
   private AuthenticatedSubject user;
   private dsession session;
   private Channel channel;
   private static final ListenPoint notused = new ListenPoint("notused", 0);
   private static final int TGIOP_FLAG_MASK = -2;

   protected static void p(String s) {
      ntrace.doTrace("<TGIOPConnection> " + s);
   }

   public static LogOutputStream getLog() {
      if (log == null) {
         Class var0 = TGIOPConnection.class;
         synchronized(TGIOPConnection.class) {
            if (log == null) {
               log = new LogOutputStream("IIOPSocket");
            }
         }
      }

      return log;
   }

   public void authenticate(UserInfo ui) {
   }

   public AuthenticatedSubject getUser() {
      return null;
   }

   public void setTxContext(Object tx) {
   }

   public Object getTxContext() {
      return null;
   }

   public ServerChannel getChannel() {
      return ProtocolHandlerIIOP.getProtocolHandler().getDefaultServerChannel();
   }

   public Channel getRemoteChannel() {
      return this.channel;
   }

   private void initTuxedoCorbaConnectionFactory() throws IOException {
      try {
         InitialContext ctx = new InitialContext();
         tuxedoCorbaConnectionFactory = (TuxedoCorbaConnectionFactory)ctx.lookup("tuxedo.services.TuxedoCorbaConnection");
      } catch (NamingException var2) {
         IIOPService.setTGIOPEnabled(false);
         throw new IOException("Problem getting TuxedoCorbaConnectionFactory: " + var2);
      }
   }

   private TGIOPConnection() throws IOException {
      if (!IIOPService.isTGIOPEnabled()) {
         throw new IOException("TGIOP is disabled");
      } else {
         if (!initialized) {
            initialized = true;
            this.initTuxedoCorbaConnectionFactory();
         }

      }
   }

   public TGIOPConnection(MethodParameters methodParameters) throws IOException {
      this();
      this.methodParameters = methodParameters;
      if (this.methodParameters != null) {
         this.session = (dsession)methodParameters.get_gwatmi();
      }

      if (this.methodParameters != null && this.methodParameters.getServiceParameters() != null) {
         this.user = methodParameters.getAuthenticatedSubject();
      } else {
         this.user = SecurityServiceManager.getCurrentSubject(kernelId);
      }

      if (debugSecurity.isEnabled()) {
         WTCLogger.logDebugSecurity("inbound request user set to: " + this.user);
      }

   }

   TGIOPConnection(String tuxDomainId, String hostName, int port) throws IOException {
      this();
      this.channel = new ChannelImpl(hostName, port, "tgiop");

      try {
         this.tuxedoCorbaConnection = tuxedoCorbaConnectionFactory.getTuxedoCorbaConnection();
         List myRoute = this.tuxedoCorbaConnection.getProviderRoute("//" + tuxDomainId, (TypedBuffer)null, (Xid)null, 0);
         TCRouteEntry myEntry = (TCRouteEntry)myRoute.get(0);
         this.session = (dsession)myEntry.getSessionGroup();
      } catch (Exception var6) {
         throw new IOException("Could not get TuxedoCorbaConnection", var6);
      }
   }

   public final synchronized void send(AsyncOutgoingMessage msg) throws IOException {
      IIOPOutputStream out = (IIOPOutputStream)msg;
      int GIOPRequestID = this.getGiopRequestID(out);
      byte[] buf = out.getBufferToWrite();
      if (debugSecurity.isEnabled()) {
         WTCLogger.logDebugSecurity("sending tgiop message, user set to " + SecurityServiceManager.getCurrentSubject(kernelId));
      }

      if (this.tuxedoCorbaConnection != null) {
         this.methodParameters = new MethodParameters(GIOPRequestID, this.session);
         this.sendUsingTuxedoCorbaConnection(buf, buf.length);
      } else {
         this.sendUsingMethodParameters(buf, buf.length);
      }
   }

   private int getGiopRequestID(IIOPOutputStream out) {
      return out.getMessage() instanceof SequencedMessage ? ((SequencedMessage)out.getMessage()).getRequestID() : 0;
   }

   private void sendUsingTuxedoCorbaConnection(byte[] buf, int length) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(65000);
      if (traceEnabled) {
         ntrace.doTrace("[/TGIOPConnection/sendUsingTuxedoCorbaConnection/");
      }

      IOException ioe;
      try {
         TypedTGIOP tgiopMsg = new TypedTGIOP(buf, length);
         BEAObjectKey beaObjKey = new BEAObjectKey(tgiopMsg);
         Objinfo objInfo = new ObjinfoImpl(beaObjKey, (ClientInfo)null, (BindInfo)null, 0);
         this.tuxedoCorbaConnection.tpMethodReq(tgiopMsg, objInfo, this.methodParameters, 16384);
      } catch (TPException var7) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TGIOPConnection/sendUsingTuxedoCorbaConnection/10/exception: " + var7);
         }

         ioe = new IOException("Could not send message via WTC : " + var7);
         ioe.initCause(var7);
         throw ioe;
      } catch (Exception var8) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TGIOPConnection/sendUsingTuxedoCorbaConnection/20/exception: " + var8);
         }

         ioe = new IOException("Could not send message via WTC : " + var8);
         ioe.initCause(var8);
         throw ioe;
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TGIOPConnection/sendUsingTuxedoCorbaConnection/30");
      }

   }

   private void sendUsingMethodParameters(byte[] buf, int length) throws IOException {
      TypedTGIOP giopMsg = new TypedTGIOP(buf, length);
      tcm user = new tcm((short)0, new UserTcb(giopMsg));
      tfmh tmMsg = new tfmh(giopMsg.getHintIndex(), user, 1);
      dsession reply = (dsession)this.methodParameters.get_gwatmi();
      TdomTcb tdom = (TdomTcb)this.methodParameters.get_invokeInfo().getServiceMessage().tdom.body;
      int tpErrno = 0;
      int tpUrCode = 0;
      TPException tpe = null;
      SessionAcallDescriptor myConvDesc = null;
      rdsession receivePlace = null;
      int conversationID;
      if ((conversationID = tdom.get_convid()) != -1) {
         myConvDesc = new SessionAcallDescriptor(conversationID, true);
         receivePlace = reply.get_rcv_place();
      }

      Object[] txInfo = this.methodParameters.getTxInfo();
      TuxedoXid myXid = null;
      TDMRemote myRemoteDomain = null;
      OatmialServices myServices = null;
      XAResource wlsXaResource = null;
      if (txInfo != null) {
         myXid = (TuxedoXid)txInfo[0];
         wlsXaResource = (XAResource)txInfo[1];
         myRemoteDomain = (TDMRemote)txInfo[2];
         myServices = WTCService.getOatmialServices();
      }

      try {
         reply.send_success_return(this.methodParameters.get_invokeInfo().getReqid(), tmMsg, tpErrno, tpUrCode, conversationID);
      } catch (TPException var23) {
         if (wlsXaResource != null) {
            try {
               wlsXaResource.end(myXid, 536870912);
               myServices.removeInboundRdomFromXid(myRemoteDomain, myXid);
               wlsXaResource.rollback(myXid);
            } catch (XAException var21) {
            }
         }

         if (conversationID == -1) {
            reply.send_failure_return(this.methodParameters.get_invokeInfo().getReqid(), var23, conversationID);
         } else {
            receivePlace.remove_rplyObj(myConvDesc);
         }

         throw new IOException("Got TPException: " + var23);
      }

      if (wlsXaResource != null) {
         try {
            if (tpe == null) {
               wlsXaResource.end(myXid, 67108864);
            } else {
               wlsXaResource.end(myXid, 536870912);
               myServices.removeInboundRdomFromXid(myRemoteDomain, myXid);
               wlsXaResource.rollback(myXid);
            }
         } catch (XAException var22) {
         }
      }

      if (conversationID != -1) {
         receivePlace.remove_rplyObj(myConvDesc);
      }

   }

   public final synchronized void close() {
      if (!this.isClosed()) {
         if (this.tuxedoCorbaConnection != null) {
            this.tuxedoCorbaConnection.tpterm();
         }

         closed = true;
      }
   }

   public final synchronized boolean isClosed() {
      return closed;
   }

   public boolean isStateful() {
      return false;
   }

   public ListenPoint getListenPoint() {
      return notused;
   }

   public void setListenPoint(ListenPoint newkey) {
   }

   public final int getMinorVersion() {
      return this.session.getMinorVersion();
   }

   public final void setMinorVersion(int minorVersion) {
      this.session.setMinorVersion(minorVersion);
   }

   public final void setCodeSets(int cs, int ws) {
      this.session.setCodeSets(cs, ws);
   }

   public final int getWcharCodeSet() {
      return this.session.getWcharCodeSet();
   }

   public final int getCharCodeSet() {
      return this.session.getCharCodeSet();
   }

   public void setFlag(int f) {
      this.session.setFlag(f & -2);
   }

   public boolean getFlag(int f) {
      return this.session.getFlag(f);
   }

   public final IOR getRemoteCodeBase() {
      return this.session.getRemoteCodeBase();
   }

   public final void setRemoteCodeBase(IOR ior) {
      this.session.setRemoteCodeBase(ior);
   }

   public EndPoint createEndPoint() {
      if (this.endPoint == null) {
         synchronized(this) {
            if (this.endPoint == null) {
               this.endPoint = new TGIOPEndPointImpl(this, this.user);
            }
         }
      }

      return this.endPoint;
   }

   protected final boolean isSecure() {
      return false;
   }
}
