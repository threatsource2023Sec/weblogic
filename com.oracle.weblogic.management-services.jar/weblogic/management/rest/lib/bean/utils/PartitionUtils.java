package weblogic.management.rest.lib.bean.utils;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.utils.StringUtil;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.provider.internal.CallerPartitionContext;
import weblogic.management.rest.lib.bean.resources.BeanCollectionResourceMetaData;
import weblogic.management.rest.lib.bean.resources.BeanResourceMetaData;
import weblogic.management.rest.lib.bean.resources.CollectionChildBeanCreateFormResourceMetaData;
import weblogic.management.rest.lib.bean.resources.CustomResourceMetaData;
import weblogic.management.rest.lib.bean.resources.ResourceMetaData;
import weblogic.management.rest.lib.bean.resources.SingletonChildBeanCreateFormResourceMetaData;
import weblogic.management.rest.lib.utils.SecurityUtils;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RealmRuntimeMBean;
import weblogic.management.security.RealmMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionUtils {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(PartitionUtils.class);

   public static boolean isVisible(InvocationContext ic, MemberType memberType) throws Exception {
      if (!isPartitioned()) {
         return true;
      } else {
         Boolean v2p = memberType.getVisibleToPartitions();
         return v2p != null ? v2p : isVisible(ic, true);
      }
   }

   public static boolean isVisible(ResourceMetaData rmd, MemberType memberType) throws Exception {
      if (!rmd.isPartitioned()) {
         return true;
      } else {
         Boolean v2p = memberType.getVisibleToPartitions();
         return v2p != null ? v2p : isVisible(rmd, true);
      }
   }

   public static boolean isVisible(InvocationContext ic) throws Exception {
      if (!isPartitioned()) {
         return true;
      } else {
         return ic.bean() == null ? true : isVisible(ic, false);
      }
   }

   public static boolean isVisible(ResourceMetaData rmd) throws Exception {
      return !isPartitioned() ? true : isVisible(rmd, false);
   }

   private static boolean isVisible(InvocationContext ic, boolean isMember) throws Exception {
      BeanType type = BeanType.getBeanType(ic.request(), ic.bean());
      Boolean v2p = type.getVisibleToPartitions();
      if (v2p == null) {
         v2p = isVisibleByDefault(getRootPartitionBean(ic) != null, type, isMember);
      }

      return !v2p ? false : isPartitionVisibleDomainBean(ic);
   }

   private static boolean isVisible(ResourceMetaData rmd, boolean isMember) throws Exception {
      BeanType type = getBeanType(rmd);
      Boolean v2p = type.getVisibleToPartitions();
      if (v2p == null) {
         v2p = isVisibleByDefault(rmd.isParentedByPartition(), type, isMember);
      }

      return v2p;
   }

   private static Boolean isVisibleByDefault(boolean isPartitionChild, BeanType type, boolean isMember) throws Exception {
      if (isPartitionChild) {
         return Boolean.TRUE;
      } else if (isMember) {
         return Boolean.FALSE;
      } else {
         return type.hasVisibleToPartitionsAlwaysMember() ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   private static BeanType getBeanType(ResourceMetaData rmd) throws Exception {
      if (rmd instanceof BeanResourceMetaData) {
         return ((BeanResourceMetaData)rmd).beanType();
      } else if (rmd instanceof BeanCollectionResourceMetaData) {
         return ((BeanCollectionResourceMetaData)rmd).type().getBeanType();
      } else if (rmd instanceof CollectionChildBeanCreateFormResourceMetaData) {
         return ((CollectionChildBeanCreateFormResourceMetaData)rmd).type().getBeanType();
      } else if (rmd instanceof SingletonChildBeanCreateFormResourceMetaData) {
         return ((SingletonChildBeanCreateFormResourceMetaData)rmd).type().getBeanType();
      } else if (rmd instanceof CustomResourceMetaData) {
         return ((CustomResourceMetaData)rmd).type().getBeanType();
      } else {
         throw new AssertionError("Can't get BeanType for rmd " + rmd);
      }
   }

   private static boolean isPartitionVisibleDomainBean(InvocationContext ic) throws Exception {
      Object bean = ic.bean();
      if (isRootPartitionBean(ic)) {
         return isPartitionVisibleRootPartitionBean(ic);
      } else if (bean instanceof VirtualTargetMBean) {
         return isPartitionVisibleVirtualTarget(ic);
      } else {
         return !(bean instanceof RealmMBean) && !(bean instanceof RealmRuntimeMBean) ? true : isPartitionVisibleRealm(ic);
      }
   }

   private static boolean isPartitionVisibleRootPartitionBean(InvocationContext ic) throws Exception {
      String name = ((WebLogicMBean)ic.bean()).getName();
      return name.equals(getPartitionName());
   }

   private static boolean isPartitionVisibleVirtualTarget(InvocationContext ic) throws Exception {
      String name = ((WebLogicMBean)ic.bean()).getName();
      TargetMBean[] var2 = getPartitionMBean(ic).getAvailableTargets();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WebLogicMBean target = var2[var4];
         if (name.equals(target.getName())) {
            return true;
         }
      }

      return false;
   }

   private static boolean isPartitionVisibleRealm(InvocationContext ic) throws Exception {
      RealmMBean realm = getPartitionMBean(ic).getRealm();
      String tree;
      if (realm == null) {
         tree = TreeUtils.getBeanTree(ic);
         if (tree == "serverRuntime") {
            tree = "serverConfig";
         }

         DomainMBean domain = (DomainMBean)TreeUtils.getRootBean(ic.request(), tree);
         realm = domain.getSecurityConfiguration().getDefaultRealm();
      }

      tree = (String)BeanUtils.getIdentity(ic);
      return tree.equals(realm.getName()) && StringUtil.notEmpty(realm.getManagementIdentityDomain());
   }

   private static PartitionMBean getPartitionMBean(InvocationContext ic) throws Exception {
      PartitionMBean partition = (PartitionMBean)PathUtils.findBean(ic, TreeUtils.getBeanTree(ic), "partitions", getPartitionName());
      if (partition == null) {
         throw new AssertionError("Could not find the partition '" + getPartitionName() + "' in the tree '" + TreeUtils.getBeanTree(ic));
      } else {
         return partition;
      }
   }

   public static Object getRootPartitionBean(InvocationContext ic) throws Exception {
      Object parent;
      for(Object bean = ic.bean(); bean != null; bean = parent) {
         parent = PathUtils.getParent(ic.clone(bean));
         if (isRootPartitionBean(ic.clone(parent))) {
            return parent;
         }
      }

      return null;
   }

   public static boolean isRootPartitionBean(String type) throws Exception {
      return PartitionMBean.class.getName().equals(type) || PartitionRuntimeMBean.class.getName().equals(type) || DomainPartitionRuntimeMBean.class.getName().equals(type);
   }

   public static String getRootPartitionName(InvocationContext ic) throws Exception {
      return isRootPartitionBean(ic) ? ((WebLogicMBean)ic.bean()).getName() : null;
   }

   private static boolean isRootPartitionBean(InvocationContext ic) throws Exception {
      Object bean = ic.bean();
      return bean instanceof PartitionMBean || bean instanceof PartitionRuntimeMBean || bean instanceof DomainPartitionRuntimeMBean;
   }

   public static Object runAs(InvocationContext ic, Callable action) throws Exception {
      SecurityUtils.checkPermission();
      ComponentInvocationContext cic = getCIC(ic);
      if (cic != null) {
         Object var3;
         try {
            CallerPartitionContext.updateContext(kernelId, cic.getPartitionName());
            getCICM();
            var3 = ComponentInvocationContextManager.runAs(kernelId, cic, action);
         } catch (ExecutionException var8) {
            Throwable cause = var8.getCause();
            if (cause instanceof Exception) {
               throw (Exception)cause;
            }

            if (cause instanceof Error) {
               throw (Error)cause;
            }

            throw new RuntimeException(cause);
         } finally {
            CallerPartitionContext.pollContext(kernelId);
         }

         return var3;
      } else {
         return action.call();
      }
   }

   private static ComponentInvocationContext getCIC(InvocationContext ic) throws Exception {
      if (isPartitioned()) {
         return null;
      } else {
         Object partitionRoot = getRootPartitionBean(ic);
         return partitionRoot == null ? null : ic.getCICs().get(getRootPartitionName(ic.clone(partitionRoot)));
      }
   }

   public static boolean isPartitioned() {
      return getPartitionName() != null;
   }

   public static String getPartitionName() {
      String partitionName = getCicPartitionName();
      return "DOMAIN".equals(partitionName) ? null : partitionName;
   }

   public static String getCicPartitionName() {
      return getCICM().getCurrentComponentInvocationContext().getPartitionName();
   }

   private static ComponentInvocationContextManager getCICM() {
      return ComponentInvocationContextManager.getInstance(kernelId);
   }

   public static class CICs {
      private Map cics;

      private ComponentInvocationContext get(String partitionName) throws Exception {
         if (partitionName == null) {
            return null;
         } else {
            if (this.cics == null) {
               this.cics = new HashMap();
            }

            if (!this.cics.containsKey(partitionName)) {
               ComponentInvocationContext cic = null;

               try {
                  cic = PartitionUtils.getCICM().createComponentInvocationContext(partitionName);
               } catch (Throwable var4) {
                  if (PartitionUtils.DEBUG.isDebugEnabled()) {
                     PartitionUtils.DEBUG.debug("Error creating CIC for partition " + partitionName, var4);
                  }

                  return null;
               }

               this.cics.put(partitionName, cic);
            }

            return (ComponentInvocationContext)this.cics.get(partitionName);
         }
      }
   }
}
