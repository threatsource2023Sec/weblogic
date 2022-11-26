package weblogic.iiop;

import java.io.IOException;
import java.net.Socket;
import java.security.AccessController;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.security.auth.login.LoginException;
import weblogic.kernel.Kernel;
import weblogic.protocol.ServerChannel;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.SSLCertUtility;
import weblogic.security.utils.SSLIOContext;
import weblogic.security.utils.SSLIOContextTable;
import weblogic.socket.JSSESocket;
import weblogic.socket.SSLFilter;
import weblogic.socket.utils.JSSEUtils;
import weblogic.utils.io.Chunk;

final class MuxableSocketIIOPS extends MuxableSocketIIOP {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static AuthenticatedSubject defaultSubject = null;

   MuxableSocketIIOPS(Chunk head, Socket s, ServerChannel nc) throws IOException {
      super(head, s, nc);
   }

   MuxableSocketIIOPS(ServerChannel nc) {
      super(nc);
   }

   void registerClientSocket(Socket socket) throws IOException {
      connectAction.register(this, socket, true);
   }

   public void register(Socket rs, boolean isClient) throws IOException {
      if (isClient) {
         SSLSocket sslSocket = (SSLSocket)rs;
         JSSESocket jsseSock = JSSEUtils.getJSSESocket(sslSocket);
         if (jsseSock != null) {
            JSSEUtils.registerJSSEFilter(jsseSock, this);
            JSSEUtils.activate(jsseSock, this);
         } else {
            SSLIOContext sslIOCtx = SSLIOContextTable.findContext(sslSocket);
            SSLFilter sslf = (SSLFilter)sslIOCtx.getFilter();
            this.setSocketFilter(sslf);

            try {
               sslSocket.startHandshake();
            } catch (SSLException var10) {
               if (!sslSocket.isClosed()) {
                  try {
                     sslSocket.close();
                  } catch (IOException var9) {
                  }
               }

               throw var10;
            }

            sslf.setDelegate(this);
            sslf.activate();
         }
      }

   }

   public byte getQOS() {
      return 102;
   }

   public void authenticate(UserInfo ui) {
      if (ui == null || ui instanceof AuthenticatedUser || !this.authenticate()) {
         super.authenticate(ui);
      }
   }

   public AuthenticatedSubject getUser() {
      if (this.getSubject() == null && Kernel.isServer()) {
         this.authenticate();
      }

      return this.getSubject() != null ? this.getSubject() : getDefaultSubject();
   }

   private boolean authenticate() {
      X509Certificate[] sslCertChain = null;
      SSLSocket s = (SSLSocket)this.getSocket();

      try {
         sslCertChain = SSLCertUtility.getPeerCertChain(s);
      } catch (Exception var7) {
      }

      if (sslCertChain != null) {
         String X509_TYPE = "X.509";
         PrincipalAuthenticator pa = MuxableSocketIIOP.getPrincipalAuthenticator(kernelId);

         try {
            AuthenticatedSubject subject = pa.assertIdentity(X509_TYPE, sslCertChain, this);
            if (subject != null) {
               this.setSubject(subject);
               return true;
            }
         } catch (LoginException var6) {
         }
      }

      return false;
   }

   private static AuthenticatedSubject getDefaultSubject() {
      if (defaultSubject != null) {
         return defaultSubject;
      } else {
         Class var0 = Connection.class;
         synchronized(Connection.class) {
            if (defaultSubject == null) {
               defaultSubject = IiopConfigurationFacade.getSecureConnectionDefaultSubject(kernelId);
            }
         }

         return defaultSubject;
      }
   }

   protected final boolean isSecure() {
      return true;
   }
}
