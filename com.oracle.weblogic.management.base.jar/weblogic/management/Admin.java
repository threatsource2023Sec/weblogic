package weblogic.management;

import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import javax.naming.Context;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.ProductionModeHelper;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.AdminService;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;

/** @deprecated */
@Deprecated
@Service
public class Admin implements AdminService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean isPasswordEchoAllowed = Boolean.getBoolean("weblogic.management.allowPasswordEcho");

   private Admin() {
   }

   /** @deprecated */
   @Deprecated
   public static final Admin getInstance() {
      return Admin.AdminSingleton.SINGLETON;
   }

   public static final boolean isAdminServer() {
      return ManagementService.getPropertyService(kernelId).isAdminServer();
   }

   public static boolean isBooting() {
      return ManagementService.getRuntimeAccess(kernelId) != null;
   }

   public static final synchronized String getAdminT3Url() {
      return ManagementService.getPropertyService(kernelId).getAdminBinaryURL();
   }

   public static boolean isConfigLoaded() {
      return ManagementService.getRuntimeAccess(kernelId) != null;
   }

   public boolean isProductionModeEnabled() {
      return !isConfigLoaded() ? ProductionModeHelper.getProductionModeProperty() : ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled();
   }

   public boolean isPasswordEchoAllowed() {
      return this.isPasswordEchoAllowed;
   }

   public static synchronized void setAdminURL(String urlString) throws MalformedURLException {
      ManagementService.getPropertyService(kernelId).setAdminHost(urlString);
   }

   public static final Admin createInstance() {
      throw new UnsupportedOperationException("Admin.createInstance");
   }

   public final ServerMBean getLocalServer() {
      throw new UnsupportedOperationException("Admin.getLocalServer");
   }

   public final DomainMBean getActiveDomain() {
      return this.getMBeanHome().getActiveDomain();
   }

   public final String getDomainName() {
      throw new UnsupportedOperationException("Admin.getDomainName");
   }

   public final MBeanHome getMBeanHome(String serverName) throws NamingException {
      ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
      Context ctx = env.getInitialContext();
      return (MBeanHome)ctx.lookup("weblogic.management.home." + serverName);
   }

   public final MBeanHome getMBeanHome() {
      return this.lookupMBeanHome("weblogic.management.home.localhome");
   }

   private MBeanHome lookupMBeanHome(String name) {
      try {
         ServerEnvironment env = (ServerEnvironment)GlobalServiceLocator.getServiceLocator().getService(ServerEnvironment.class, new Annotation[0]);
         Context ctx = env.getInitialContext();
         return (MBeanHome)ctx.lookup(name);
      } catch (NamingException var4) {
         throw new AssertionError("MBeanHome lookup failed" + var4);
      }
   }

   public final MBeanHome getAdminMBeanHome() {
      return this.lookupMBeanHome("weblogic.management.adminhome");
   }

   public final DomainMBean getDomain() {
      try {
         WebLogicObjectName dname = new WebLogicObjectName(this.getDomainName(), "Domain", this.getDomainName());
         return (DomainMBean)this.getAdminMBeanHome().getMBean(dname);
      } catch (Exception var2) {
         throw new AssertionError(var2);
      }
   }

   public final ServerMBean getServer() {
      try {
         return (ServerMBean)this.getAdminMBeanHome().getAdminMBean(getServerName(), "Server", this.getDomainName());
      } catch (Exception var2) {
         throw new AssertionError(var2);
      }
   }

   public final URL getAdminURL() {
      return ManagementService.getPropertyService(kernelId).getAdminURL();
   }

   public String getAdminServerName() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain().getAdminServerName();
   }

   public ServerIdentity getAdminIdentity() {
      return LocalServerIdentity.getIdentity();
   }

   private ServerMBean getAdminServerMBean() {
      throw new UnsupportedOperationException("Admin.getAdminServerMBean");
   }

   /** @deprecated */
   @Deprecated
   public int getMasterEmbeddedLDAPPortFromConfig() {
      throw new UnsupportedOperationException("Admin.getMasterEmbeddedLDAPPortFromConfig");
   }

   public boolean isLocalAdminServer() {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public static final synchronized String getAdminHttpUrl() {
      return ManagementService.getPropertyService(kernelId).getAdminHttpUrl();
   }

   /** @deprecated */
   @Deprecated
   public static final String getServerName() {
      return ManagementService.getPropertyService(kernelId).getServerName();
   }

   private static final class AdminSingleton {
      private static final Admin SINGLETON = (Admin)LocatorUtilities.getService(Admin.class);
   }
}
