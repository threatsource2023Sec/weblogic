package weblogic.ejb.container;

import java.security.AccessController;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.EJBContainerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class ReadConfig {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String getJavaCompilerPreClassPath() {
      String result = null;
      if (null != getEJBContainerMBean()) {
         result = getEJBContainerMBean().getJavaCompilerPreClassPath();
      }

      if (null == result && null != getServerMBean()) {
         result = getServerMBean().getJavaCompilerPreClassPath();
      }

      return result;
   }

   public static String getJavaCompilerPostClassPath() {
      String result = null;
      if (null != getEJBContainerMBean()) {
         result = getEJBContainerMBean().getJavaCompilerPostClassPath();
      }

      if (null == result && null != getServerMBean()) {
         result = getServerMBean().getJavaCompilerPostClassPath();
      }

      return result;
   }

   public static String getJavaCompiler() {
      String result = null;
      if (null != getEJBContainerMBean()) {
         result = getEJBContainerMBean().getJavaCompiler();
      }

      if (result == null) {
         result = "jdt";
      }

      if (null == result && null != getServerMBean()) {
         result = getServerMBean().getJavaCompiler();
         String serverExtraEjbcOptions = getServerMBean().getExtraEjbcOptions();
         if (null == serverExtraEjbcOptions && "javac".equalsIgnoreCase(result)) {
            result = null;
         }
      }

      return result;
   }

   public static String getExtraEjbcOptions() {
      String result = null;
      if (null != getEJBContainerMBean()) {
         result = getEJBContainerMBean().getExtraEjbcOptions();
      }

      if (null == result && null != getServerMBean()) {
         result = getServerMBean().getExtraEjbcOptions();
      }

      return result;
   }

   public static boolean getForceGeneration() {
      boolean result = false;
      if (null != getEJBContainerMBean()) {
         result = getEJBContainerMBean().getForceGeneration();
      }

      return result;
   }

   public static boolean getKeepGenerated() {
      boolean result = false;
      if (null != getEJBContainerMBean()) {
         result = getEJBContainerMBean().getKeepGenerated();
      }

      return result;
   }

   private static EJBContainerMBean getEJBContainerMBean() {
      return KernelStatus.isServer() ? ManagementService.getRuntimeAccess(kernelId).getDomain().getEJBContainer() : null;
   }

   private static ServerMBean getServerMBean() {
      return KernelStatus.isServer() ? ManagementService.getRuntimeAccess(kernelId).getServer() : null;
   }

   public static boolean isClusteredServer() {
      boolean isInCluster = false;
      ServerMBean server = getServerMBean();
      if (server != null) {
         isInCluster = server.getCluster() != null;
      }

      return isInCluster;
   }
}
