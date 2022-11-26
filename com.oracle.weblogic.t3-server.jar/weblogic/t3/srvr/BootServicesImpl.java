package weblogic.t3.srvr;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.security.cert.X509Certificate;
import javax.security.auth.login.LoginException;
import weblogic.common.T3Exception;
import weblogic.common.internal.BootServices;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.T3ClientParams;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rjvm.BootServicesInvocable;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMLogger;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.RemoteRequest;
import weblogic.rjvm.ReplyStream;
import weblogic.security.SimpleCallbackHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class BootServicesImpl implements BootServices, RemoteInvokable, BootServicesInvocable {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private PrincipalAuthenticator pa;
   private byte qos;
   private int port;
   private ServerChannel channel;
   private X509Certificate[] peerCertChain = null;
   private static final String OLDBOOTSTRAPREQUEST_QUEUE = "wl_oldBootStrap";
   private WorkManager workMgr;

   private BootServicesImpl() {
      String realmName = "weblogicDEFAULT";
      this.pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelId, realmName, ServiceType.AUTHENTICATION);
      Debug.assertion(this.pa != null, "Security system not initialized");
      this.workMgr = WorkManagerFactory.getInstance().findOrCreate("wl_oldBootStrap", -1, 8);
   }

   public static void initialize() {
      RJVMManager.getLocalRJVM().getFinder().put(1, new BootServicesImpl());
   }

   public void setConnectionInfo(MsgAbbrevJVMConnection connection) {
      this.qos = connection.getQOS();
      this.port = connection.getLocalPort();
      this.channel = connection.getChannel();
      this.peerCertChain = connection.getJavaCertChain();
   }

   public AuthenticatedUser authenticate(UserInfo ui, PeerInfo pi) throws RemoteException {
      AuthenticatedSubject subject = null;
      X509Certificate[] cert = this.peerCertChain;
      this.peerCertChain = null;
      Object au;
      if (ui instanceof AuthenticatedUser) {
         au = (AuthenticatedUser)ui;
      } else {
         if (cert != null) {
            try {
               String X509_TYPE = "X.509";
               subject = this.pa.assertIdentity(X509_TYPE, cert);
            } catch (LoginException var11) {
            }
         }

         if (subject == null) {
            if (!(ui instanceof DefaultUserInfoImpl)) {
               throw new SecurityException("Received bad UserInfo: " + ui.getClass().getName());
            }

            DefaultUserInfoImpl info = (DefaultUserInfoImpl)ui;
            String username = info.getName();
            String password = info.getPassword();
            if (username != null && username.length() != 0) {
               try {
                  SimpleCallbackHandler handler = new SimpleCallbackHandler(username, password);
                  subject = this.pa.authenticate(handler);
               } catch (LoginException var10) {
                  throw new SecurityException(var10.getMessage());
               }
            } else {
               subject = SubjectUtils.getAnonymousSubject();
            }
         }

         this.checkAdminPort(subject);
         au = subject;
      }

      ((AuthenticatedUser)au).setQOS(this.qos);
      return (AuthenticatedUser)au;
   }

   private void checkAdminPort(AuthenticatedSubject subject) {
      if (ChannelHelper.isLocalAdminChannelEnabled() && SubjectUtils.isUserAnAdministrator(subject) && ManagementService.getRuntimeAccess(kernelId).getServer().getAdministrationPort() != this.port && !ChannelHelper.isAdminChannel(this.channel)) {
         throw new SecurityException("All administrative tasks must go through an Administration Port.");
      }
   }

   public T3ClientParams findOrCreateClientContext(String workspace, UserInfo ui, int idleCallbackID) throws RemoteException {
      throw new InternalError("should never be called");
   }

   public void invoke(RemoteRequest req) throws RemoteException {
      try {
         byte b = req.readByte();
         switch (b) {
            case 1:
               this.authenticate(req);
               break;
            case 2:
               this.findOrCreateClientContext(req);
               break;
            default:
               throw new AssertionError("Unknown OPCODE: " + b);
         }

      } catch (RemoteException var3) {
         throw var3;
      } catch (IOException var4) {
         throw new UnmarshalException("While providing boot service", var4);
      } catch (ClassNotFoundException var5) {
         throw new UnmarshalException("While providing boot service", var5);
      }
   }

   private void authenticate(RemoteRequest req) throws IOException, ClassNotFoundException {
      UserInfo ui = (UserInfo)req.readObjectWLValidated(UserInfo.class);
      PeerInfo pi = req.getPeerInfo();
      BootServicesAuthenticateRequest executeRequest = new BootServicesAuthenticateRequest(this, req, ui, pi);
      ManagedInvocationContext mic = this.registerCIC(req.getPartitionName());
      Throwable var6 = null;

      try {
         this.workMgr.schedule(executeRequest);
      } catch (Throwable var15) {
         var6 = var15;
         throw var15;
      } finally {
         if (mic != null) {
            if (var6 != null) {
               try {
                  mic.close();
               } catch (Throwable var14) {
                  var6.addSuppressed(var14);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   private void findOrCreateClientContext(RemoteRequest req) throws IOException, ClassNotFoundException {
      String workspace = req.readString();
      UserInfo ui = (UserInfo)req.readObjectWLValidated(UserInfo.class);
      int idleCallbackID = req.readInt();
      byte QOS = req.readByte();
      BootServicesClientContextRequest request = new BootServicesClientContextRequest(this, req, workspace, ui, idleCallbackID, QOS);
      this.workMgr.schedule(request);
   }

   private void checkServerLock(AuthenticatedUser au) {
      if (!SubjectUtils.isUserAnAdministrator((AuthenticatedSubject)au)) {
         T3Srvr.getT3Srvr().getLockoutManager().checkServerLock();
      }
   }

   public int hashCode() {
      return 1;
   }

   public boolean equals(Object o) {
      return o == this;
   }

   private ManagedInvocationContext registerCIC(String partitionName) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext cic = manager.createComponentInvocationContext(partitionName);
      return manager.setCurrentComponentInvocationContext(cic);
   }

   private static class BootServicesClientContextRequest extends WorkAdapter {
      BootServicesImpl bootServicesImpl;
      RemoteRequest remoteRequest;
      String workSpace;
      UserInfo userInfo;
      int idleCallbackID;
      byte qos;

      public BootServicesClientContextRequest(BootServicesImpl svc, RemoteRequest request, String ws, UserInfo ui, int idleCBId, byte qs) {
         this.bootServicesImpl = svc;
         this.remoteRequest = request;
         this.workSpace = ws;
         this.userInfo = ui;
         this.idleCallbackID = idleCBId;
         this.qos = qs;
      }

      public void run() {
         try {
            ManagedInvocationContext mic = this.bootServicesImpl.registerCIC(this.remoteRequest.getPartitionName());
            Throwable var2 = null;

            try {
               final AuthenticatedUser au = this.bootServicesImpl.authenticate(this.userInfo, this.remoteRequest.getPeerInfo());
               AuthenticatedSubject as = SecurityServiceManager.getASFromAU(au);
               SecurityServiceManager.runAs(BootServicesImpl.kernelId, as, new PrivilegedExceptionAction() {
                  public Object run() throws IOException {
                     T3ClientParams res;
                     try {
                        BootServicesClientContextRequest.this.bootServicesImpl.checkServerLock(au);
                        ClientContext clc = ClientContext.getClientContext(BootServicesClientContextRequest.this.remoteRequest.getOrigin(), BootServicesClientContextRequest.this.workSpace, au, BootServicesClientContextRequest.this.idleCallbackID, BootServicesClientContextRequest.this.qos);
                        res = clc.getParams();
                     } catch (T3Exception var3) {
                        throw new RemoteException("Failed to create client context", var3);
                     }

                     ReplyStream resp = BootServicesClientContextRequest.this.remoteRequest.getResponseStream();
                     resp.writeObjectWL(res);
                     resp.send();
                     return null;
                  }
               });
            } catch (Throwable var15) {
               var2 = var15;
               throw var15;
            } finally {
               if (mic != null) {
                  if (var2 != null) {
                     try {
                        mic.close();
                     } catch (Throwable var14) {
                        var2.addSuppressed(var14);
                     }
                  } else {
                     mic.close();
                  }
               }

            }
         } catch (Throwable var17) {
            Throwable t = var17;
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMessaging()) {
               RJVMLogger.logDebug2("Execute problem", var17);
            }

            try {
               this.remoteRequest.getResponseStream().sendThrowable(t);
            } catch (IOException var13) {
               RJVMLogger.logDebug2("Failed to deliver error response " + var17 + " to client", var13);
            }

            if (var17 instanceof Error) {
               throw (Error)var17;
            }
         }

      }
   }

   private static class BootServicesAuthenticateRequest extends WorkAdapter {
      BootServicesImpl bootServicesImpl;
      RemoteRequest remoteRequest;
      UserInfo userInfo;
      PeerInfo peerInfo;

      public BootServicesAuthenticateRequest(BootServicesImpl svc, RemoteRequest request, UserInfo ui, PeerInfo pi) {
         this.bootServicesImpl = svc;
         this.remoteRequest = request;
         this.userInfo = ui;
         this.peerInfo = pi;
      }

      public void run() {
         try {
            AuthenticatedUser au = this.bootServicesImpl.authenticate(this.userInfo, this.peerInfo);
            ReplyStream resp = this.remoteRequest.getResponseStream();
            resp.writeObject(au);
            resp.send();
         } catch (Throwable var4) {
            Throwable t = var4;
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMessaging()) {
               RJVMLogger.logDebug2("Execute problem ", var4);
            }

            try {
               this.remoteRequest.getResponseStream().sendThrowable(t);
            } catch (IOException var3) {
               RJVMLogger.logDebug2("Failed to deliver error response " + var4 + " to client", var3);
            }

            if (var4 instanceof Error) {
               throw (Error)var4;
            }
         }

      }
   }
}
