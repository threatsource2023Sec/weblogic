package weblogic.management.rest.lib.bean.utils;

import java.security.AccessController;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.PathSegment;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.rest.lib.utils.ServerUtils;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class TreeUtils {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static Object getRootBean(HttpServletRequest request, String tree) throws Exception {
      if ("serverRuntime".equals(tree)) {
         return getServerRuntimeBean(request);
      } else if ("serverConfig".equals(tree)) {
         return getServerConfigBean(request);
      } else {
         if (ServerUtils.isAdminServer()) {
            if ("domainRuntime".equals(tree)) {
               return getDomainRuntimeBean(request);
            }

            if ("domainConfig".equals(tree)) {
               return getDomainConfigBean(request);
            }

            if ("edit".equals(tree)) {
               return getEditBean(request);
            }
         }

         throw new AssertionError("Invalid tree name: " + tree);
      }
   }

   public static boolean isEditableTree(InvocationContext ic) throws Exception {
      String tree = getBeanTree(ic);
      return "edit".equals(tree) || "serverRuntime".equals(tree) || "domainRuntime".equals(tree);
   }

   public static boolean isEditTree(InvocationContext ic) throws Exception {
      return "edit".equals(getBeanTree(ic));
   }

   public static String getBeanTree(InvocationContext ic) throws Exception {
      if (ic.bean() == null) {
         return ((PathSegment)ic.uriInfo().getPathSegments().get(2)).getPath();
      } else {
         Object root = PathUtils.getRoot(ic);
         if (getServerRuntimeBean(ic.request()) == root) {
            return "serverRuntime";
         } else if (getServerConfigBean(ic.request()) == root) {
            return "serverConfig";
         } else {
            if (ServerUtils.isAdminServer()) {
               if (getDomainRuntimeBean(ic.request()) == root) {
                  return "domainRuntime";
               }

               if (getDomainConfigBean(ic.request()) == root) {
                  return "domainConfig";
               }

               if (getEditBean(ic.request()) == root) {
                  return "edit";
               }
            }

            String error = "Bean not in a known bean tree : url=" + ic.uriInfo().getPathSegments() + " bean=" + ic.bean() + " parent=" + PathUtils.getParent(ic) + " root=" + root;
            throw new AssertionError(error);
         }
      }
   }

   public static DomainMBean getEditBean(HttpServletRequest request) throws Exception {
      verifyAdminServer();
      String onameTypeName = "weblogic.management.mbeanservers.edit.EditServiceMBean";
      String typeName = PartitionUtils.isPartitioned() ? "weblogic.management.mbeanservers.edit.EditSessionServiceMBean" : onameTypeName;
      AtzUtils.checkGetAccess(request, "EditService", typeName, onameTypeName, "domainConfiguration");
      return EditUtils.getEditAccess(request).getDomainBeanWithoutLock();
   }

   public static DomainMBean getDomainConfigBean(HttpServletRequest request) throws Exception {
      verifyAdminServer();
      AtzUtils.checkGetAccess(request, "DomainRuntimeService", "weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean", "domainConfiguration");
      return ManagementService.getDomainAccess(kernelId).getDomainRuntimeService().getDomainConfiguration();
   }

   public static DomainMBean getServerConfigBean(HttpServletRequest request) throws Exception {
      AtzUtils.checkGetAccess(request, "RuntimeService", "weblogic.management.mbeanservers.runtime.RuntimeServiceMBean", "domainConfiguration");
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   public static DomainRuntimeMBean getDomainRuntimeBean(HttpServletRequest request) throws Exception {
      verifyAdminServer();
      AtzUtils.checkGetAccess(request, "DomainRuntimeService", "weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean", "domainRuntime");
      return ManagementService.getDomainAccess(kernelId).getDomainRuntime();
   }

   public static ServerRuntimeMBean getServerRuntimeBean(HttpServletRequest request) throws Exception {
      AtzUtils.checkGetAccess(request, "RuntimeService", "weblogic.management.mbeanservers.runtime.RuntimeServiceMBean", "serverRuntime");
      return ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
   }

   private static void verifyAdminServer() throws Exception {
      if (!ServerUtils.isAdminServer()) {
         throw new AssertionError("Not running in the admin server");
      }
   }
}
