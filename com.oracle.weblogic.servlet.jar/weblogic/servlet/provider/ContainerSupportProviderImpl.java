package weblogic.servlet.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.WritableByteChannel;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import weblogic.version;
import weblogic.common.internal.PassivationUtils;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementService;
import weblogic.platform.VM;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.SSL.WLSSSLNioSocket;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.FileSender;
import weblogic.servlet.FileServlet;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.AbstractHttpConnectionHandler;
import weblogic.servlet.internal.FileSenderImpl;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppConfigManager;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.internal.session.SessionContext;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.spi.ContainerSupportProvider;
import weblogic.servlet.spi.RequestExecutor;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.socket.WeblogicSocket;
import weblogic.utils.SanitizedDiagnosticInfo;
import weblogic.work.ServerWorkAdapter;

public class ContainerSupportProviderImpl implements ContainerSupportProvider {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean productionMode;
   private List internalWebAppListener = new ArrayList();
   private static String SAMLServletContextListener;
   private static final String IDCSServletContextListener = "weblogic.security.internal.IDCSServletContextListener";

   public boolean hasPermissionOnChannel(ServletRequestImpl req, ServletResponseImpl rsp) throws IOException {
      WebAppServletContext ctx = req.getContext();
      WebAppConfigManager configManager = ctx.getConfigManager();
      ServerChannel nc = req.getServerChannel();
      ServerChannel admin = ServerChannelManager.findLocalServerChannel(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
      if (admin == null) {
         return true;
      } else {
         if (ChannelHelper.isAdminChannel(nc)) {
            if (!ctx.isInternalApp() && !ctx.isAdminMode() && !configManager.isRequireAdminTraffic()) {
               rsp.sendError(403, "Operation is not allowed on an administration channel");
               return false;
            }
         } else if (!ctx.isInternalUtilitiesWebApp() && (ctx.isInternalApp() || configManager.isRequireAdminTraffic()) && !ctx.isInternalUtilitiesWebSvcs() && !ctx.isInternalSAMLApp() && !ctx.isInternalWSATApp() && !req.getServletStub().isClasspathServlet()) {
            rsp.sendError(403, "Console/Management requests or requests with <require-admin-traffic> specified to 'true' can only be made through an administration channel");
            HTTPLogger.logInternalAppWrongPort(nc.getPublicAddress(), nc.getPublicPort(), nc.getChannelName(), admin.getPublicAddress(), admin.getPublicPort(), admin.getChannelName(), ctx.getContextPath());
            return false;
         }

         return true;
      }
   }

   public ObjectOutputStream getObjectOutputStream(OutputStream os) throws IOException {
      WLObjectOutputStream oos = new WLObjectOutputStream(os);
      oos.setReplacer(RemoteObjectReplacer.getReplacer());
      oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
      return oos;
   }

   public void setServerChannelThreadLocal(ServerChannel channel) {
      ServerHelper.setServerChannel(channel);
   }

   public ServerChannel getServerChannelThreadLocal() {
      return ServerHelper.getServerChannel();
   }

   public SubjectHandle getDeploymentInitiator(WebAppServletContext context) {
      AuthenticatedSubject deploymentInitiator = context.getApplicationContext().getDeploymentInitiator();
      return deploymentInitiator != null ? WlsSecurityProvider.toSubjectHandle(deploymentInitiator) : null;
   }

   public void addInternalWebAppListener(String listener) {
      this.internalWebAppListener.add(listener);
   }

   public String[] getInternalWebAppListeners() {
      if (!this.internalWebAppListener.contains(SAMLServletContextListener)) {
         this.internalWebAppListener.add(SAMLServletContextListener);
      }

      if (!this.internalWebAppListener.contains("weblogic.security.internal.IDCSServletContextListener")) {
         this.internalWebAppListener.add("weblogic.security.internal.IDCSServletContextListener");
      }

      return (String[])this.internalWebAppListener.toArray(new String[this.internalWebAppListener.size()]);
   }

   public RequestExecutor createRequestExecutor(AbstractHttpConnectionHandler conn) {
      return new WlsRequestExecutor(conn);
   }

   public WritableByteChannel getWritableByteChannel(Socket sock) {
      if (sock == null) {
         return null;
      } else {
         return (WritableByteChannel)(sock instanceof WLSSSLNioSocket ? ((WLSSSLNioSocket)sock).getWritableByteChannel() : sock.getChannel());
      }
   }

   public int sizeOf(Object object) throws IOException {
      return PassivationUtils.sizeOf(object);
   }

   public byte[] toByteArray(Object object) throws IOException {
      return PassivationUtils.toByteArray(object);
   }

   public Object toObject(byte[] bytes) throws ClassNotFoundException, IOException {
      return PassivationUtils.toObject(bytes);
   }

   public boolean isProductionMode() {
      return productionMode;
   }

   public boolean isAdminChannel(ServerChannel channel) {
      return ChannelHelper.isAdminChannel(channel);
   }

   public FileSender getZeroCopyFileSender(HttpServletResponse response) {
      return FileSenderImpl.getZeroCopyFileSender(response);
   }

   public FileSender getFileSender(HttpServletResponse response) {
      return FileSenderImpl.getFileSender(response);
   }

   public ObjectInputStream getObjectInputStream(InputStream is) throws IOException {
      WLObjectInputStream ois = new WLObjectInputStream(is);
      ois.setReplacer(RemoteObjectReplacer.getReplacer());
      return ois;
   }

   public void sendFileNative(String filePath, ServletRequestImpl request, long contentLength) throws IOException {
      ServletResponseImpl res = request.getResponse();
      ServletOutputStreamImpl outputStream = (ServletOutputStreamImpl)res.getOutputStream();
      outputStream.setNativeControlsPipe(true);
      Socket socket = request.getConnection().getSocket();
      if (socket instanceof WeblogicSocket) {
         socket = ((WeblogicSocket)socket).getSocket();
      }

      int sockFD = request.getConnection().getSocketFD();
      if (sockFD == -1) {
         sockFD = VM.getVM().getFD(socket);
         request.getConnection().setSocketFD(sockFD);
      }

      FileServlet.transmitFile(filePath, contentLength, sockFD);
      outputStream.setNativeControlsPipe(false);
   }

   public String getReleaseBuildVersion() {
      return version.getReleaseBuildVersion();
   }

   public String getWebServerReleaseString() {
      return version.getWebServerReleaseInfo();
   }

   public String getXPoweredByHeaderValue(String type) {
      StringBuffer buf = new StringBuffer();
      buf.append("Servlet/4.0 JSP/2.3");
      if (type.equals("MEDIUM")) {
         buf.append(" (").append("WebLogic/" + version.getPLInfo()[1]).append(")");
      } else if (type.equals("FULL")) {
         buf.append(" (").append("WebLogic/" + version.getPLInfo()[1]).append(" JDK/");
         buf.append(System.getProperty("java.version")).append(")");
      }

      return buf.toString();
   }

   public String getServerHashForExtendedSessionID(String delimiter) {
      return weblogic.servlet.internal.ServerHelper.createServerEntry(weblogic.servlet.internal.ServerHelper.getNetworkChannelName(), LocalServerIdentity.getIdentity(), delimiter);
   }

   public ComponentInvocationContextManager getComponentInvocationContextManager() {
      return ComponentInvocationContextManager.getInstance(kernelId);
   }

   static {
      productionMode = !Kernel.isServer() || ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled();
      SAMLServletContextListener = "weblogic.security.internal.SAMLServletContextListener";
   }

   public class WlsRequestExecutor extends ServerWorkAdapter implements RequestExecutor, ComponentRequest, SanitizedDiagnosticInfo {
      private final AbstractHttpConnectionHandler connectionHandler;

      public WlsRequestExecutor(AbstractHttpConnectionHandler conn) {
         this.connectionHandler = conn;
      }

      public void run() {
         this.connectionHandler.getServletRequest().run();
      }

      public ComponentInvocationContext getComponentInvocationContext() {
         ServletRequestImpl request = this.connectionHandler.getServletRequest();
         return request.getContext().getComponentInvocationContext();
      }

      protected AuthenticatedSubject getAuthenticatedSubject() {
         ServletRequestImpl request = this.connectionHandler.getServletRequest();
         WebAppServletContext context = request.getContext();
         SessionContext sessionContext = context.getSessionContext();
         SessionInternal session = request.getSessionHelper().getSessionOnThisServer(sessionContext);
         SubjectHandle subject;
         if (session == null) {
            String reqId = request.getSessionHelper().getRequestedSessionId(false);
            if (reqId == null) {
               return null;
            }

            subject = context.getServer().getSessionLogin().getUser(reqId);
         } else {
            subject = SecurityModule.getCurrentUser(context.getSecurityContext(), request, session);
         }

         return WlsSecurityProvider.toAuthSubject(subject);
      }

      public Runnable cancel(String reason) {
         return this.connectionHandler.getServletRequest().cancel(reason);
      }

      public Runnable overloadAction(String reason) {
         return this.connectionHandler.getServletRequest().overloadAction(reason);
      }

      public boolean isAdminChannelRequest() {
         return this.connectionHandler.getServletRequest().isAdminChannelRequest();
      }

      public String getSanitizedDescription() {
         return this.connectionHandler.getServletRequest().getSanitizedDescription();
      }

      public String getSanitizedDescriptionVerbose() {
         return super.toString() + this.connectionHandler.getServletRequest().getSanitizedDescriptionVerbose();
      }
   }
}
