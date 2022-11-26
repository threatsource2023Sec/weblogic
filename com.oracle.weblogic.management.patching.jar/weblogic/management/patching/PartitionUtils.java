package weblogic.management.patching;

import java.security.AccessController;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionUtils {
   public static final String DOMAIN_SCOPE = PartitionTable.getInstance().getGlobalPartitionName();
   private static ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static DomainMBean getDomainMBean() {
      DomainMBean domain = ManagementService.getRuntimeAccess(kernelId).getDomain();
      return domain;
   }

   public static ResourceGroupMBean[] getResourceGroupMBeans() {
      return new ResourceGroupMBean[0];
   }

   public static ResourceGroupTemplateMBean[] getResourceGroupTemplateMBeans() {
      return getDomainMBean().getResourceGroupTemplates();
   }

   public static boolean arePartitionsPresent() {
      return false;
   }

   public static boolean areGlobalRGsPresent() {
      ResourceGroupMBean[] gRGMBeans = getResourceGroupMBeans();
      return gRGMBeans != null && gRGMBeans.length > 0;
   }

   public static boolean areRGTsPresent() {
      ResourceGroupTemplateMBean[] rgtMBeans = getResourceGroupTemplateMBeans();
      return rgtMBeans != null && rgtMBeans.length > 0;
   }

   public static boolean doesRGTExist(String rgtName) {
      return areRGTsPresent() && ((List)Arrays.asList(getResourceGroupTemplateMBeans()).stream().map((rgt) -> {
         return rgt.getName();
      }).collect(Collectors.toList())).contains(rgtName);
   }

   public static String getPartitionInfo() {
      return cicm.getCurrentComponentInvocationContext().getPartitionName();
   }

   public static boolean isPartitionContext() {
      String partitionName = getPartitionInfo();
      return !"DOMAIN".equals(partitionName);
   }
}
