package weblogic.rjvm;

import java.security.AccessController;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.rjvm.http.TunnelCloseServlet;
import weblogic.rjvm.http.TunnelLoginServlet;
import weblogic.rjvm.http.TunnelRecvServlet;
import weblogic.rjvm.http.TunnelSendServlet;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class InternalWebAppListener implements ServletContextListener {
   private static final String IIOP_TUNNEL_PACKAGE = "weblogic.corba.iiop.http.";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void contextInitialized(ServletContextEvent sce) {
      ServletContext servletContext = sce.getServletContext();
      ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
      if (!server.isDefaultInternalServletsDisabled()) {
         servletContext.addServlet("HTTPClntSend", TunnelSendServlet.class).addMapping(new String[]{"/HTTPClntSend/*"});
         servletContext.addServlet("HTTPClntRecv", TunnelRecvServlet.class).addMapping(new String[]{"/HTTPClntRecv/*"});
         servletContext.addServlet("HTTPClntLogin", TunnelLoginServlet.class).addMapping(new String[]{"/HTTPClntLogin/*"});
         servletContext.addServlet("HTTPClntClose", TunnelCloseServlet.class).addMapping(new String[]{"/HTTPClntClose/*"});
         if (server.isIIOPEnabled()) {
            servletContext.addServlet("ClientSend", "weblogic.corba.iiop.http.TunnelSendServlet").addMapping(new String[]{"/iiop/ClientSend/*"});
            servletContext.addServlet("ClientRecv", "weblogic.corba.iiop.http.TunnelRecvServlet").addMapping(new String[]{"/iiop/ClientRecv/*"});
            servletContext.addServlet("ClientLogin", "weblogic.corba.iiop.http.TunnelLoginServlet").addMapping(new String[]{"/iiop/ClientLogin/*"});
            servletContext.addServlet("ClientClose", "weblogic.corba.iiop.http.TunnelCloseServlet").addMapping(new String[]{"/iiop/ClientClose/*"});
         }

         if (server.isCOMEnabled()) {
            servletContext.addServlet("COM", "weblogic.com.GetORMServlet").addMapping(new String[]{"/com/*"});
         }

         if (server.getIIOP().getEnableIORServlet()) {
            servletContext.addServlet("getior", "weblogic.servlet.utils.iiop.GetIORServlet").addMapping(new String[]{"/getior/*"});
         }
      }

      if (!server.isClasspathServletDisabled()) {
         servletContext.addServlet("classes", "weblogic.servlet.ClasspathServlet").addMapping(new String[]{"/classes/*"});
      }

   }

   public void contextDestroyed(ServletContextEvent sec) {
   }
}
